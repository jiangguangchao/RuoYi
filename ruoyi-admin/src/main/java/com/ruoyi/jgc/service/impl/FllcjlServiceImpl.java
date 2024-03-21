package com.ruoyi.jgc.service.impl;

import java.util.List;

import com.ruoyi.jgc.domain.FllcjlDto;
import com.ruoyi.jgc.domain.WorkloadDto;
import com.ruoyi.jgc.domain.WorkloadQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.FllcjlMapper;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.service.IFllcjlService;

/**
 * 放疗流程记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-31
 */
@Service
public class FllcjlServiceImpl implements IFllcjlService 
{
    @Autowired
    private FllcjlMapper fllcjlMapper;

    /**
     * 查询放疗流程记录
     * 
     * @param flid 放疗流程记录主键
     * @return 放疗流程记录
     */
    @Override
    public Fllcjl selectFllcjlByFlid(String flid)
    {
        return fllcjlMapper.selectFllcjlByFlid(flid);
    }

    /**
     * 查询放疗流程记录列表
     * 
     * @param fllcjl 放疗流程记录
     * @return 放疗流程记录
     */
    @Override
    public List<FllcjlDto> selectFllcjlList(Fllcjl fllcjl)
    {
        return fllcjlMapper.selectFllcjlList(fllcjl);
    }

    @Override
    public List<FllcjlDto> selectWorkload(WorkloadQuery query) {
        return fllcjlMapper.selectWorkload(query);
    }

    @Override
    public List<WorkloadDto> workloadStat(WorkloadQuery query) {
        return fllcjlMapper.workloadStat(query);
    }

    /**
     * 新增放疗流程记录
     * 
     * @param fllcjl 放疗流程记录
     * @return 结果
     */
    @Override
    public int insertFllcjl(Fllcjl fllcjl)
    {
        return fllcjlMapper.insertFllcjl(fllcjl);
    }

    /**
     * 修改放疗流程记录
     * 
     * @param fllcjl 放疗流程记录
     * @return 结果
     */
    @Override
    public int updateFllcjl(Fllcjl fllcjl)
    {
        return fllcjlMapper.updateFllcjl(fllcjl);
    }

    /**
     * 批量删除放疗流程记录
     * 
     * @param flids 需要删除的放疗流程记录主键
     * @return 结果
     */
    @Override
    public int deleteFllcjlByFlids(String[] flids)
    {
        return fllcjlMapper.deleteFllcjlByFlids(flids);
    }

    /**
     * 删除放疗流程记录信息
     * 
     * @param flid 放疗流程记录主键
     * @return 结果
     */
    @Override
    public int deleteFllcjlByFlid(String flid)
    {
        return fllcjlMapper.deleteFllcjlByFlid(flid);
    }
}
