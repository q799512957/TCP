package com.hdsxtech.tjbhzc_datarecp_tcp.factory;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 消息的具体值域\属性
 *
 * @author wangxiri
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class MsgField implements Cloneable {

  //  Logger logger = LoggerFactory.getLogger(MsgField.class);

    @XmlAttribute(name = "id", required = true)
    private String id; // 值域的id
    @XmlAttribute(name = "type", required = true)
    private String type;// 值域的类型
    @XmlAttribute(name = "length")
    private int length = 0;// 该属性为字节长度
    @XmlAttribute(name = "cname")
    private String cname; // 值域的中文描述
    @XmlElement(name = "fields")
    private MsgElement list;
    // 该域是否被使用
    private boolean checked = false;
    private Object value; // 值
    private FieldType fieldtype;// 字段类型

    /**
     * 根据输入的字节解码值
     *
     * @param b
     */
    public int frombytes(byte[] b, int offset) {
        byte[] b1 = Arrays.copyOfRange(b, offset, b.length);
        int filed_length = frombytes(b1);
        return filed_length;
    }

    /**
     * 根据输入的字节解码值
     *
     * @param b
     */
    public int frombytes(byte[] b) {
        if(  b.length == 0 ) return 0;
        int filed_length = 0;
        switch (this.getFieldtype()) {
            case INT8: // 有符号整型，1byte
                this.value = b[0];
                filed_length = 1;
                break;
            case BYTE:// 无符号整型，1byte
            case UINT8:
                this.value = DataTypeUtil.Byte2Uint8(b[0]);
                filed_length = 1;
                break;
            case INT16:// 有符号整型，2byte
                this.value = DataTypeUtil.Bytes2Short(b);
                filed_length = 2;
                break;
            case UINT16:// 无符号整型，2byte
                this.value = DataTypeUtil.Bytes2ushort(b);
                filed_length = 2;
                break;
            case INT24: // 生成序列号 3byte
                this.value = b[0] + b[1] * 256 + b[2] * 256 * 256;
                filed_length = 3;
                break;
            case INT:
            case INT32:// 有符号整型，4byte
                this.value = DataTypeUtil.Bytes2int32(b);
                filed_length = 4;
                break;
            case UINT32:
                this.value = DataTypeUtil.byte2unsignInt32(b);
                filed_length = 4;
                break;
            case DOUBLE8:
                this.value = DataTypeUtil.bytes2Double(b);
                filed_length = 8;
                break;
            case BCD:
                int bcd = this.getLength();
                this.value = DataTypeUtil.bytes2BCD(b, bcd);
                filed_length = bcd;
                break;
            case STRING:
                if (this.getLength() == 0) {
                    int b_index = 0;
                    for (int i = 0; i < b.length; i++) {
                        if (b[i] == 0) {
                            b_index = i;
                            break;
                        }
                    }
                    this.value = DataTypeUtil.bytes2GBKString(b, b_index);
                    filed_length = b_index + 1;
                } else {
                    this.value = DataTypeUtil.bytes2GBKString(b, this.getLength());
                    filed_length = this.getLength();
                }
                break;
            case BYTES:
            //    byte[] bb = new byte[b.length - 1];
                byte [] bb = new byte[this.length];
                System.arraycopy(b, 0, bb, 0, this.length);
                this.value = bb;
                filed_length = bb.length;
                break;
            case LIST:
                try {
                    List<MsgElement> e = new ArrayList<MsgElement>();
                    int offset_list = 0;
                    while (offset_list + this.list.getLength() <= b.length) {
                        byte[] b_list = new byte[this.list.getLength()];
                        System.arraycopy(b, offset_list, b_list, 0, b_list.length);
                        MsgElement e_list = (MsgElement) this.list.clone();
                        e_list.frombytes(b_list);
                        e.add(e_list);
                        offset_list += b_list.length;
                    }
                    this.value = e;
                    filed_length = offset_list;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case LLVAR:
                int fldlen = DataTypeUtil.Byte2Uint8(b[0]);
                byte[] b1 = new byte[fldlen];
                System.arraycopy(b, 1, b1, 0, fldlen);
                this.value = b1;
                filed_length = fldlen;
                break;
            case TLV:
                try {
                    HashMap<Integer,byte[]> map=new HashMap<>();
                    int offset_list = 0;
                    while (offset_list <b.length-1) {
                    	 int tag = DataTypeUtil.Byte2Uint8(b[offset_list++]);
                         int len = b[offset_list++];
                         byte[] value = new byte[len];
                         for ( int i = 0; i < len ; i ++){
                             value[i] = b[i + 2];
                         }
                        map.put(tag, value);
                        offset_list+=len;
                    }
                    this.value = map;
                    filed_length = offset_list;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case IMAGE:
                System.out.println("......................");
                break;
            default:
                this.checked = false;
                break;
        }
        return filed_length;
    }

    public FieldType getFieldtype() {
        if (this.fieldtype == null)
            this.fieldtype = FieldType.valueOf(this.type);
        return fieldtype;
    }

    public void setFieldtype(FieldType fieldtype) {
        this.fieldtype = fieldtype;
    }

    /**
     * 将字段转换为bytes
     *
     * @return
     */
    public byte[] toBytes() {

        byte[] b = null;
        switch (this.getFieldtype()) {
            case INT8:
            case BYTE:
            case UINT8:
                if (null == this.value) {
                    this.value = (byte) 0x00;
                }
                b = new byte[1];
                int vv = Integer.valueOf(this.value.toString()) & 0Xffffffff;
                b[0] = (byte) vv;
                break;
            case INT16:
            case UINT16:
                b = new byte[2];
                //object默认值为null 强转空指针
                if (null == this.value) {
                    this.value = 0;
                }
                int val = (int) this.value;
                b[0] = (byte) (val >> 0);
                b[1] = (byte) (val >> 8);
                break;
            case INT24:
                b = new byte[3];
                //object默认值为null 强转空指针
                if (null == this.value) {
                    this.value = 0;
                }
                int val24 = (int) this.value;
                b[0] = (byte) (val24 >> 0);
                b[1] = (byte) (val24 >> 8);
                b[2] = (byte) (val24 >> 16 );
                break;
            case UINT32:
                b = new byte[4];
                //object默认值为null 强转空指针
                if (null == this.value) {
                    this.value = 0l;
                }
                long vall = (long) this.value;
                b[0] = (byte) (vall >> 0);
                b[1] = (byte) (vall >> 8);
                b[2] = (byte) (vall >> 16);
                b[3] = (byte) (vall >> 24);
                break;
            case BCD:
                if (null == this.value) {
                    this.value = "";
                }
                b = DataTypeUtil.str2BCD((String) this.value, this.getLength());
                break;
            case STRING:
                if (null == this.value) {
                    this.value = "";
                }
                String s = (String) this.value;
                try {
                    byte[] bb = s.getBytes("GBK");
                    b = new byte[this.getLength()];
                    System.arraycopy(bb, 0, b, 0, bb.length);
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
                break;
            case BYTES:
                if (null == this.value) {
                    this.value = new byte[0];
                }
                b = (byte[]) this.value;
                break;

            case LIST:
                if (this.value != null) {
                    List<MsgElement> v_list = (List<MsgElement>) this.value;
                    if (v_list != null) {
                    }
                    b = new byte[this.list.getLength() * v_list.size()];
                    int offset = 0;
                    for (MsgElement e : v_list) {
                        byte[] b_t = e.tobytes();
                        System.arraycopy(b_t, 0, b, offset, b_t.length);
                        offset += b_t.length;
                    }
                }
                break;
            case LLVAR:
                byte[] v = (byte[]) this.value;
                byte fldlen = (byte) (v.length & 0xff);
                b = new byte[fldlen + 1];
                b[0] = fldlen;
                System.arraycopy(v, 0, b, 1, fldlen);
                break;
                
            case TLV:
            	HashMap<Byte,byte[]> map=(HashMap<Byte, byte[]>) this.value;
            	int offset = 0;
            	for(Entry<Byte, byte[]> obj:map.entrySet()){
            		byte id =obj.getKey();
            		byte[] values = map.get(id);
            		System.arraycopy(values, 0, b, offset, values.length);
                    offset += values.length;
            	}
            	break;
            default:
                b = new byte[0];
                break;
        }
        return b;
    }

    /**
     * 对于确定字段长度的，根据类型确定长度
     *
     * @return
     */
    public int getLength() {
        if (this.length != 0)
            return this.length;
        switch (this.getFieldtype()) {
            case INT8:
            case BYTE:
            case UINT8:
                this.length = 1;
                break;
            case INT16:
            case UINT16:
                this.length = 2;
                break;
            case INT:
            case INT32:
            case UINT32:
                this.length = 4;
                break;
            case INT24:
                this.length = 3;
                break;
            case DOUBLE8:
                this.length = 8;
                break;
            case LIST:
                if (this.value != null) {
                    List<MsgElement> v_list = (List<MsgElement>) this.value;
                    if (v_list != null)
                        this.length = v_list.size() * this.list.getLength();
                }
                break;
            case LLVAR:
                if (this.value != null) {
                    byte[] v = (byte[]) this.value;
                    this.length = v.length + 1;
                }
                break;
            case BYTES:
                if (this.value != null) {
                    byte[] v = (byte[]) this.value;
                    this.length = v.length;
                }
                break;
            case STRING:
                if (this.value != null) {
                    String str = (String) this.value;
                    if (this.length == 0) {
                        return str.length()*2;
                    } else {
                        return str.length();
                    }
                }
                break;
            case TLV:
            	if(this.value != null){
            		HashMap<Byte,byte[]> map = (HashMap<Byte,byte[]>) this.value;
            		for(Entry<Byte, byte[]> obj:map.entrySet()){
                		byte[] values = obj.getValue();
                		this.length += values.length;
            		}
            		return this.length;
            	}
            	break;
		default:
                this.length = 0;
                break;
        }
        return this.length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String str = id + ":" + cname + ":" + value + ":" + this.getFieldtype() + "(" + this.length + ")";
        if (this.list != null) {
            str += ", list=" + this.list;
        }
        str += "]";
        return str;
    }
}
