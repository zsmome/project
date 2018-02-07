package com.his.ops.web.contorller;


import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.his.ops.dto.ResponseInfo;
import com.his.ops.entity.UserInfoEntity;
import com.his.ops.web.facade.FdfsFacade;
import com.his.ops.web.facade.UserInfoFacade;

/**
 * 上传图片，读取图片
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/userInfo")
public class ImageController {
	
	//打印日志
	public static final Logger LOG = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	UserInfoFacade userInfoFacade; 
	
	@Autowired
	FdfsFacade fdfsFacade;

	@RequestMapping("/uploadImage")
	public ResponseInfo uploadImage(
			@RequestParam("image") MultipartFile image) throws IOException {
		Subject subject = SecurityUtils.getSubject();
    	final String username = (String)subject.getPrincipal();
		String fileName = image.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		LOG.info(suffix);
		File file = File.createTempFile("upload", suffix);
		image.transferTo(file);
		LOG.info("文件大小："+file.length());
		try {
			return fdfsFacade.upload(username, file);
		} finally {
			file.deleteOnExit();
		}
	}
	
	@RequestMapping("/readImage")
	public String readImage(HttpSession session, HttpServletResponse response) {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		UserInfoEntity userInfoEntity = userInfoFacade.selectUserInfoByUsername(username);
		//读取图片流
		String urlPath = userInfoEntity.getHeadPortraits();
		LOG.info("头像地址"+urlPath);
		if(urlPath != null) {
			return urlPath;
		} else {
			String defaultUrl = "http://192.168.183.130:9999/group1/M00/00/00/wKi3glp38saACbkyAAAasmRqhgk130.jpg";
			return defaultUrl;
		}
	}
}
