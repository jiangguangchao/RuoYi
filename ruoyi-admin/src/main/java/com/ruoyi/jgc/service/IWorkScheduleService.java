package com.ruoyi.jgc.service;

import java.util.List;
import com.ruoyi.jgc.domain.WorkSchedule;

/**
 * 排班Service接口
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
public interface IWorkScheduleService 
{
    /**
     * 查询排班
     * 
     * @param id 排班主键
     * @return 排班
     */
    public WorkSchedule selectWorkScheduleById(Long id);

    /**
     * 查询排班列表
     * 
     * @param workSchedule 排班
     * @return 排班集合
     */
    public List<WorkSchedule> selectWorkScheduleList(WorkSchedule workSchedule);

    /**
     * 新增排班
     * 
     * @param workSchedule 排班
     * @return 结果
     */
    public int insertWorkSchedule(WorkSchedule workSchedule);

    /**
     * 修改排班
     * 
     * @param workSchedule 排班
     * @return 结果
     */
    public int updateWorkSchedule(WorkSchedule workSchedule);

    /**
     * 批量删除排班
     * 
     * @param ids 需要删除的排班主键集合
     * @return 结果
     */
    public int deleteWorkScheduleByIds(Long[] ids);

    /**
     * 删除排班信息
     * 
     * @param id 排班主键
     * @return 结果
     */
    public int deleteWorkScheduleById(Long id);
}
