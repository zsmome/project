package com.his.ops.opsfdfs.dto;

import java.util.Map;

/**
 * 响应信息类
 * @author Administrator
 *
 */
public class ResponseInfo {
	
	/**
	 * 额外信息
	 */
	public static final String INFO_EXTRA = "extra";
	
	/**
	 * 数据
	 */
	public static final String INFO_DATA = "data";
	
	/**
	 * 成功状态
	 */
	public static final String STATUS_SUCCESS = "success";
	
	/**
	 * 失败状态
	 */
	public static final String STATUS_FAIL = "fail";
	
	/**
	 * 错误状态
	 */
	public static final String STATUS_ERROR = "error";
	
	/**
	 * 返回的数据信息
	 */
	private Map<String, Object> info;
	
	/**
	 * 响应的状态情况
	 */
	private String responseStatus;
	
	/**
	 * 状态说明情况
	 */
	private Object msg;

	public ResponseInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 默认是成功状态
	 * @param msg
	 */
	public ResponseInfo(Object msg) {
		this(STATUS_SUCCESS, msg);
	}

	public ResponseInfo(String responseStatus, Object msg) {
		super();
		this.responseStatus = responseStatus;
		this.msg = msg;
	}
	

	public ResponseInfo(Map<String, Object> info, String responseStatus, Object msg) {
		super();
		this.info = info;
		this.responseStatus = responseStatus;
		this.msg = msg;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
	
}
