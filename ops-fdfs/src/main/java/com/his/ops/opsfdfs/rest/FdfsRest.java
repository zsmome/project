package com.his.ops.opsfdfs.rest;


import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.opsfdfs.dto.ResponseInfo;
import com.his.ops.opsfdfs.facade.UserInfoFacade;
import com.his.ops.opsfdfs.wapper.FastDFSClientWrapper;

/**
 * 上传图片，读取图片
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/fdfs")
public class FdfsRest {
	
	private static final Logger LOG = LoggerFactory.getLogger(FdfsRest.class);
	
	@Autowired
	private UserInfoFacade userInfoFacade;
	
	@Autowired
    private FastDFSClientWrapper dfsClient;
 
    
    /**
     * 文件上传
     * @param image
     * @param request
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseInfo upload(
    		@RequestParam("username") String username,
    		@RequestParam("file") File file) {
    	LOG.info(username);
    	LOG.info(file.toString());
    	LOG.info(file.length()+"");
    	LOG.info(file.getName());
    	
    	String url;
		try {
			url = dfsClient.uploadFile(file);
			userInfoFacade.updateUserInfoByUsername(username, url);
			LOG.info("上传成功:"+url);
			return new ResponseInfo(
					ResponseInfo.STATUS_SUCCESS,
					"上传成功"
					);
		} catch (IOException e) {
			LOG.info("上传失败");
			return new ResponseInfo(
					ResponseInfo.STATUS_SUCCESS,
					"上传失败"
					);
		}
    }
}
