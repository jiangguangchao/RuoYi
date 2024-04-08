package com.ruoyi.jgc.service.impl;

import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.jgc.domain.Machine;
import com.ruoyi.jgc.service.IMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ruoyi
 * @description: 在每种类型放疗机器上维护一个机器实例的队列。在放疗申请单中已经选择了使用哪种类型的机器进行治疗，
 * 每个种类的机器可能有多台实例（虽然目前每个种类的机器只有一个实例，主要考虑后期加机器兼容问题）
 * 还需要将放疗单分配到具体的机器实例上，当前service的作用就是在机器种类上维护一个机器实例的队列，任务轮流接收
 * @author:
 * @create: 2024-04-07 10:06
 */

@Service
public class AssignMachinServiceImpl extends AbstractAssignService<Machine, Integer>{

    @Autowired
    private IMachineService machineService;

    @Override
    public String getType() {
        return "MACHINE";
    }

    @Override
    public List<Machine> getBeanListFromDB(String slotId) {
        Machine query = new Machine();
        query.setType(slotId);
        return machineService.selectMachineList(query);
    }

    @Override
    public Machine getBeanById(Integer Id) {
        return machineService.selectMachineById(Id);
    }

    @Override
    public boolean checkBeanStatus(Machine machine) {
        return "Y".equals(machine.getWorkState());
    }
}
