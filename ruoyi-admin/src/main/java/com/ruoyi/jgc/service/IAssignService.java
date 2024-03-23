package com.ruoyi.jgc.service;

import java.util.List;

public interface IAssignService<T> {


    public List<T> getAssignList(String scopeId);

    public List<T> refreshAssignList(String scopeId);

    public boolean addBean(String scopeId, T t);

    public boolean removeBean(String scopeId, T t);

    public boolean moveToEnd(String scopeId, T t);
}
