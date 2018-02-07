package com.his.ops.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.his.ops.entity.DepartmentEntity;
@Mapper
@Repository
public interface DepartmentEntityMapper extends BaseMapper<DepartmentEntity> {
    /**
     * 根据主键切换可见状态：前端相当于删除和恢复
     * @param ids
     * @return
     */
    int updateStatus(int []ids);
    
    /**
     * 查询所有记录
     * @param condition
     * @return
     */
    List<DepartmentEntity> selectAllByCondition(DepartmentEntity condition);
}