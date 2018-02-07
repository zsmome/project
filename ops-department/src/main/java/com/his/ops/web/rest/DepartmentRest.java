package com.his.ops.web.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.his.ops.entity.DepartmentEntity;
import com.his.ops.service.DepartmentService;
import com.his.ops.utils.ErrorMessageHelper;

@RestController
@RequestMapping("/department")
public class DepartmentRest {
	
	//打印日志信息
	private final static Logger LOG = LoggerFactory.getLogger(DepartmentRest.class);
	@Autowired
	
	private DepartmentService departmentService;
	
	
	/*---------------响应--开始-------------*/
	
	
	/**
	 * 增加部门信息
	 */
	@RequestMapping(value="/insert", method={RequestMethod.GET, RequestMethod.POST})
		public Object departmentInsert(@Valid DepartmentEntity bean, BindingResult result) {
		Map<String, List<String>> errorMessageMap;
		errorMessageMap = ErrorMessageHelper.errorMessageResult(result);
		
		//有错误信息，返回出去
		if(errorMessageMap != null) {
			LOG.info("增加部门有验证错误信息"+errorMessageMap);
			return errorMessageMap;
		}
		
		int number = -1;
		try {
			number = departmentService.insert(bean);
		} catch (Exception e) {
			LOG.info("您插入的主键，数据库已经存在！");
		}
		Map<String, String> map = new HashMap<>();
		//注册失败 
		if(number <= 0) {
			LOG.info("增加部门信息失败");
			map.put("failed", "新增失败！");
			return map;
		} else {//注册成功
			LOG.info("增加部门信息成功");
			map.put("success", "新增成功！");
			return map;
		}
	}
	

	/**
	 * 修改逻辑状态字段
	 */
	@RequestMapping("/updateStatus")
	public int updateStatus(int[] selectedIDs) {
		LOG.info("修改部门信息状态码："+selectedIDs);
		return departmentService.updateStatus(selectedIDs);
	}
	
	
	
	/**
	 * 修改信息
	 */
	@RequestMapping("/update")
	public Object updateDepartment(@Valid DepartmentEntity bean, BindingResult result) {
		Map<String, List<String>> errorMessageMap;
		errorMessageMap = ErrorMessageHelper.errorMessageResult(result);
		
		//有错误信息，返回出去
		if(errorMessageMap != null) {
			LOG.info("修改部门有错误信息："+errorMessageMap);
			return errorMessageMap;
		}
		
		int number = -1;
		try {
			number = departmentService.updateByPrimaryKey(bean);
		} catch (Exception e) {
			LOG.error("部门信息的主键不存在："+bean);
		}
		Map<String, String> map = new HashMap<>();
		//注册失败 
		if(number <= 0) {
			LOG.info("部门信息修改失败："+bean);
			map.put("failed", "修改失败！");
			return map;
		} else {//注册成功
			LOG.info("部门信息修改成功："+bean);
			map.put("success", "修改成功！");
			return map;
		}
	}
	
	@Value("${server.port}")
	private String port;
	
	/**
	 * 根据条件查询列表
	 * @param pageDto
	 * @param bean
	 * @return
	 */
	@RequestMapping("/queryByCondition")
	public Map<String, Object> departmentDo(
			@RequestParam("page") int page, 
			@RequestParam("rows") int rows, 
			@RequestParam(value="sort", required=false) String sort, 
			@RequestParam(value="order", required=false) String order, 
			@RequestParam(value="depNo", required=false) String depNo,
			@RequestParam(value="depName", required=false) String depName) {
		LOG.info("负载的端口号："+port);
		Page<DepartmentEntity> paging = PageHelper.startPage(page, rows);
		if(sort != null) {
			//获取多个字段排序
			String[] sortArr = sort.split(",");
			String[] orderArr = order.split(",");
			StringBuilder orderSb = new StringBuilder();
			for(int i = 0; i < sortArr.length; i++) {
				if(i == sortArr.length-1) {
					orderSb.append(sortArr[i]+" "+orderArr[i]);
				} else {
					orderSb.append(sortArr[i]+" "+orderArr[i]+",");
				}
			}
			//设置排序
			paging.setOrderBy(orderSb.toString());
		}
		//根据条件查询
		DepartmentEntity bean = new DepartmentEntity();
		bean.setDepNo(depNo);
		bean.setDepName(depName);
		List<DepartmentEntity> list = departmentService.selectAllByCondition(bean);
		LOG.info("查询部门信息："+list);
		//封装前台 json 对象
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("total", paging.getTotal());
		pageMap.put("rows", list);
		
		return pageMap;
	}
	
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
			) {
		
		//获取多个字段排序
		Page<DepartmentEntity> paging = PageHelper.startPage(page, rows);
		//根据条件查询
		DepartmentEntity bean = new DepartmentEntity();
		bean.setStatus(false);
		List<DepartmentEntity> list = departmentService.selectAllByCondition(bean);
		
		LOG.info("部门信息已删除的数据查询："+list);
		//封装前台 json 对象
		Map<String, Object> pageMap = new HashMap<>();
		pageMap.put("total", paging.getTotal());
		pageMap.put("rows", list);
		
		return pageMap;
	}
	
	
	/*---------------响应--结束-------------*/

}
