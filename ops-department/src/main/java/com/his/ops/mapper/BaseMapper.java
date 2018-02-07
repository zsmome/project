package com.his.ops.mapper;

import java.util.List;


public interface BaseMapper<T> {
	/**
	 * 根据主键删除记录
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入一条记录
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 根据主键查询一条记录
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 查询所有记录
     * @return
     */
    List<T> selectAll();

    /**
     * 根据主键更新记录 
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);
}
