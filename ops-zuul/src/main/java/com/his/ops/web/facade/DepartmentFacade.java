package com.his.ops.web.facade;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value="ops-department")
@RestController
@RequestMapping("/department")
public interface DepartmentFacade {
	
	/**
	 * 增加部门信息
	 */
	@RequestMapping("/insert")
	public Object departmentInsert(
			@RequestParam("id") String id,
			@RequestParam("depNo") String depNo,
			@RequestParam("depName") String depName,
			@RequestParam(value="depDescribe", required=false) String depDescribe
		);
	

	/**
	 * 修改逻辑状态字段
	 */
	@RequestMapping("/updateStatus")
	public int updateStatus(@RequestParam("selectedIDs") int[] selectedIDs);
	
	
	
	/**
	 * 修改信息
	 */
	@RequestMapping("/update")
	public Object updateDepartment(
			@RequestParam("id") String id,
			@RequestParam("depNo") String depNo,
			@RequestParam("depName") String depName,
			@RequestParam(value="depDescribe", required=false) String depDescribe
		);
	
	/**
	 * 根据条件查询列表
	 * @param pageDto
	 * @param bean
	 * @return
	 * 
	 * 
	 */
	@RequestMapping("/queryByCondition")
	public Map<String, Object> departmentDo(
			@RequestParam("page") int page, 
			@RequestParam("rows") int rows, 
			@RequestParam(value="sort", required=false) String sort, 
			@RequestParam(value="order", required=false) String order, 
			@RequestParam(value="depNo", required=false) String depNo,
			@RequestParam(value="depName", required=false) String depName);
	
	/**
	 * 逻辑删除数据查询
	 * @param pageDto
	 * @param bean
	 * @return
	 */
	@RequestMapping("/queryRestore")
	public Map<String, Object> restoreDepartment(
			@RequestParam("page") int page, 
			@RequestParam("rows") int rows
		);
}
