package com.cms.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T, ID> {
    
    /**
     * 统计符合条件的记录数
     */
    int selectCount(Map<String, Object> conditions);

    /**
     * 根据条件查询列表
     */
    List<T> select(Map<String, Object> conditions);

    /**
     * 根据ID获取记录
     */
    T selectOne(Map<String, Object> conditions);
    
    /**
     * 插入一条记录
     */
    int insert(T entity);

    /**
     * 更新一条记录
     */
    int update(T entity);

    /**
     * 全削除
     */
    void deleteByIds(@Param("ids") List<Integer> ids);

    
    /**
     * 获取用户密码 
     */
    T getPwd(String user);
}
