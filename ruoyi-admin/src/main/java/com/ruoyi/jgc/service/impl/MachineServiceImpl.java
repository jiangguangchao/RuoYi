package com.ruoyi.jgc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.MachineMapper;
import com.ruoyi.jgc.domain.Machine;
import com.ruoyi.jgc.service.IMachineService;

/**
 * 放疗机器Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-03-23
 */
@Service
public class MachineServiceImpl implements IMachineService 
{
    @Autowired
    private MachineMapper machineMapper;

    /**
     * 查询放疗机器
     * 
     * @param id 放疗机器主键
     * @return 放疗机器
     */
    @Override
    public Machine selectMachineById(Integer id)
    {
        return machineMapper.selectMachineById(id);
    }

    /**
     * 查询放疗机器列表
     * 
     * @param machine 放疗机器
     * @return 放疗机器
     */
    @Override
    public List<Machine> selectMachineList(Machine machine)
    {
        return machineMapper.selectMachineList(machine);
    }

    /**
     * 新增放疗机器
     * 
     * @param machine 放疗机器
     * @return 结果
     */
    @Override
    public int insertMachine(Machine machine)
    {
        machine.setCreateTime(DateUtils.getNowDate());
        return machineMapper.insertMachine(machine);
    }

    /**
     * 修改放疗机器
     * 
     * @param machine 放疗机器
     * @return 结果
     */
    @Override
    public int updateMachine(Machine machine)
    {
        machine.setUpdateTime(DateUtils.getNowDate());
        return machineMapper.updateMachine(machine);
    }

    /**
     * 批量删除放疗机器
     * 
     * @param ids 需要删除的放疗机器主键
     * @return 结果
     */
    @Override
    public int deleteMachineByIds(Integer[] ids)
    {
        return machineMapper.deleteMachineByIds(ids);
    }

    /**
     * 删除放疗机器信息
     * 
     * @param id 放疗机器主键
     * @return 结果
     */
    @Override
    public int deleteMachineById(Integer id)
    {
        return machineMapper.deleteMachineById(id);
    }
}
