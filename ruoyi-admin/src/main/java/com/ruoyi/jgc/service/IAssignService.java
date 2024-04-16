package com.ruoyi.jgc.service;

import java.util.List;

public interface IAssignService<T, U> {


    public List<T> getAssignList(String slotId);

    public List<T> refreshAssignList(String slotId);

    public boolean addBeanById(String slotId, U id);
    public boolean addBeanById(List<String> slotIds, U id);

    public boolean removeBeanById(String slotId, U id);
    public boolean removeBeanById(List<String> slotIds, U id);

    public boolean moveToEndById(String slotId, U id);
    public boolean addBean(String slotId, T t);
    public boolean addBean(List<String> slotIds, T t);

    public boolean removeBean(String slotId, T t);
    public boolean removeBean(List<String> slotIds, T t);

    public boolean moveToEnd(String slotId, T t);

    public boolean beanChangeSlot(String preSlotId, String slotId, U id);
    public boolean beanChangeSlot(List<String> preSlotIds, List<String> slotIds, U id);


}
