package com.his.ops.utils;

import java.security.MessageDigest;

/**
 * 加密方法
 * 功能：MD5加密
 * @author Administrator
 *
 */
public class EncipherHelper {
	
	/**
	 * MD5加密
	 * @param s
	 * @return
	 */
	public static String MD5(String s) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	/**
	 * 将字节转成十六进制字符串
	 * @param bytes
	 * @return
	 */
	private static String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}
}
