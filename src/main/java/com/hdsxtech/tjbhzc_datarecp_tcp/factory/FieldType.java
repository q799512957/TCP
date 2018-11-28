package com.hdsxtech.tjbhzc_datarecp_tcp.factory;


/**
 * 数据的类型
 * <p>
 * INT8
 * 有符号整型，1byte
 * BYTE(UINT8)
 * 无符号整型，1byte
 * INT16
 * 有符号整型，2byte
 * WORD(UINT16)
 * 无符号整型，2byte
 * INT24 3byte
 * INT32
 * 有符号整型，4byte
 * DWORD(UINT32)
 * 无符号整型，4byte
 * BCD[n]
 * 8421码，nbyte
 * BYTE[n]
 * nbyte
 * STRING
 * GBK编码，采用0终结符，若无数据，则放一个0x00终结符
 * LIST 表示为字段组合
 * LLVAR  表示第一个自己为字段长度的字段
 *
 * IMAGE 图片
 *
 * @author wangxiri
 */
public enum FieldType {
    INT8, UINT8, BYTE, INT16, UINT16,INT24, INT, INT32, UINT32, BCD, BYTES, STRING, LIST, LLVAR,DOUBLE8,TLV,IMAGE;

}
