package com.his.ops.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.ops.entity.DepartmentEntity;
import com.his.ops.mapper.DepartmentEntityMapper;
import com.his.ops.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentEntityMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DepartmentEntity record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public DepartmentEntity selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DepartmentEntity> selectAll() {
		// TODO Auto-generated method stub
		return mapper.selectAll();
	}

	@Override
	public int updateByPrimaryKey(DepartmentEntity record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateStatus(int []ids) {
		// TODO Auto-generated method stub
		return mapper.updateStatus(ids);
	}

	@Override
	public List<DepartmentEntity> selectAllByCondition(DepartmentEntity condition) {
		// TODO Auto-generated method stub
		return mapper.selectAllByCondition(condition);
	}

}
