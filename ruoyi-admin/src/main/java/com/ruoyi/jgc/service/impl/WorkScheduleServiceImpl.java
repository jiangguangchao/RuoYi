package com.ruoyi.jgc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.WorkScheduleMapper;
import com.ruoyi.jgc.domain.WorkSchedule;
import com.ruoyi.jgc.service.IWorkScheduleService;

/**
 * 排班Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
@Service
public class WorkScheduleServiceImpl implements IWorkScheduleService 
{
    @Autowired
    private WorkScheduleMapper workScheduleMapper;

    /**
     * 查询排班
     * 
     * @param id 排班主键
     * @return 排班
     */
    @Override
    public WorkSchedule selectWorkScheduleById(Long id)
    {
        return workScheduleMapper.selectWorkScheduleById(id);
    }

    /**
     * 查询排班列表
     * 
     * @param workSchedule 排班
     * @return 排班
     */
    @Override
    public List<WorkSchedule> selectWorkScheduleList(WorkSchedule workSchedule)
    {
        return workScheduleMapper.selectWorkScheduleList(workSchedule);
    }

    /**
     * 新增排班
     * 
     * @param workSchedule 排班
     * @return 结果
     */
    @Override
    public int insertWorkSchedule(WorkSchedule workSchedule)
    {
        return workScheduleMapper.insertWorkSchedule(workSchedule);
    }

    /**
     * 修改排班
     * 
     * @param workSchedule 排班
     * @return 结果
     */
    @Override
    public int updateWorkSchedule(WorkSchedule workSchedule)
    {
        return workScheduleMapper.updateWorkSchedule(workSchedule);
    }

    /**
     * 批量删除排班
     * 
     * @param ids 需要删除的排班主键
     * @return 结果
     */
    @Override
    public int deleteWorkScheduleByIds(Long[] ids)
    {
        return workScheduleMapper.deleteWorkScheduleByIds(ids);
    }

    /**
     * 删除排班信息
     * 
     * @param id 排班主键
     * @return 结果
     */
    @Override
    public int deleteWorkScheduleById(Long id)
    {
        return workScheduleMapper.deleteWorkScheduleById(id);
    }
}
