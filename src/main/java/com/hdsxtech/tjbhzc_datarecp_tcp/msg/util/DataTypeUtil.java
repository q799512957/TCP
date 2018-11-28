package com.hdsxtech.tjbhzc_datarecp_tcp.msg.util;

import java.util.List;

/**
 * 
 */
@SuppressWarnings({ "rawtypes"})
public final class DataTypeUtil {
	/**
	 * 判断对象是否为空
	 * @param objects
	 * @return
	 */
	public static boolean isNotEmpty(Object... objects) {
		for (Object obj : objects) {
			if (obj == null) {
				return false;
			}
			if (obj instanceof String) {
				if ("null".equals(((String) obj).trim())||"".equals(((String) obj).trim())) {
					return false;
				}
			}
			if (obj instanceof List){
				if (((List) obj).size()==0) {
					return false;
				}
			}
		}
		
		return true;
	}
}
