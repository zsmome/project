package com.his.ops.opsactivemq.util;

/**
 * 邮件模板路径
 * @author Administrator
 *
 */
public interface MailVMPath {

	/**
	 * 包路径
	 */
	public static final String PACKAGE_PATH = "vm/";
	
	/**
	 * 模板后缀名
	 */
	public static final String SUFFIX = ".vm";
	
	/**
	 * 异常模板
	 */
	public static final String EXCEPTION = PACKAGE_PATH + "exception" + SUFFIX;
	
	/**
	 * 激活模板
	 */
	public static final String ACTIVATE = PACKAGE_PATH + "activate" + SUFFIX;
	
}
