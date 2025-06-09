package com.cms.base.service;

import java.util.List;
import java.util.Map;

public interface BaseService<T, ID> {

    public int selectCouint(Map<String, Object> conditions);
    
    public List<T> searchAllData(Map<String, Object> conditions);

    public void registData(T obj);
    
    T searchSingleById(String id);
    
    public void initEdit(String id);
    
    public void updateData(T obj);
    
    public void initDetail(String id);
    
    public void deleteDataById(T obj);
    
    public void deleteAll(List<Integer> ids);
    
}
