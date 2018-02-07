package com.his.ops.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorMessageHelper {
	public static Map<String, List<String>> errorMessageResult(BindingResult result) {
		if(result.hasErrors()) {
			//所有错误信息
			List<FieldError> errors = result.getFieldErrors();
			
			//存储错误信息，返回出去
			Map<String, List<String>> errorMessageMap = new HashMap<>();
			
			for (FieldError fieldError : errors) {
				//获取信息
				String field = fieldError.getField();
				String message = fieldError.getDefaultMessage();
				
				List<String> messageList = null;
				//有该字段,则在原集合添加
				if(errorMessageMap.containsKey(field)) {
					messageList = errorMessageMap.get(field);
				} else {
					messageList = new ArrayList<>();
				}
				messageList.add(message);
				//存储值
				errorMessageMap.put(field, messageList);
			}
			return errorMessageMap;
		}
		return null;
	}
}
