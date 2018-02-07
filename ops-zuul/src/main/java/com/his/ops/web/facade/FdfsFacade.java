package com.his.ops.web.facade;

import java.io.File;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.his.ops.dto.ResponseInfo;

@FeignClient("ops-fdfs")
@RestController
@RequestMapping("/fdfs")
public interface FdfsFacade {

	/**
     * 文件上传
     * @param image
     * @param request
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseInfo upload(
    		@RequestParam("username") String username,
    		@RequestParam("file") File file);
	
}
