package com.ruoyi.jgc.service;

import java.util.List;
import com.ruoyi.jgc.domain.Machine;

/**
 * 放疗机器Service接口
 * 
 * @author ruoyi
 * @date 2024-03-23
 */
public interface IMachineService 
{
    /**
     * 查询放疗机器
     * 
     * @param id 放疗机器主键
     * @return 放疗机器
     */
    public Machine selectMachineById(Integer id);

    /**
     * 查询放疗机器列表
     * 
     * @param machine 放疗机器
     * @return 放疗机器集合
     */
    public List<Machine> selectMachineList(Machine machine);

    /**
     * 新增放疗机器
     * 
     * @param machine 放疗机器
     * @return 结果
     */
    public int insertMachine(Machine machine);

    /**
     * 修改放疗机器
     * 
     * @param machine 放疗机器
     * @return 结果
     */
    public int updateMachine(Machine machine);

    /**
     * 批量删除放疗机器
     * 
     * @param ids 需要删除的放疗机器主键集合
     * @return 结果
     */
    public int deleteMachineByIds(Integer[] ids);

    /**
     * 删除放疗机器信息
     * 
     * @param id 放疗机器主键
     * @return 结果
     */
    public int deleteMachineById(Integer id);
}
