package com.his.ops.service;

import java.util.List;

import com.his.ops.entity.DepartmentEntity;

public interface DepartmentService extends BaseService<DepartmentEntity> {
    /**
     * 根据主键切换可见状态：前端相当于删除和恢复
     * @return
     */
    int updateStatus(int []ids);
    
    /**
     * 查询所有记录
     * @return
     */
    List<DepartmentEntity> selectAllByCondition(DepartmentEntity condition);
}
