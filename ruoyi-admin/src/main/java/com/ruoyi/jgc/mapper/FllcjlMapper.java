package com.ruoyi.jgc.mapper;

import java.util.List;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.domain.FllcjlDto;
import com.ruoyi.jgc.domain.WorkloadDto;
import com.ruoyi.jgc.domain.WorkloadQuery;

/**
 * 放疗流程记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-10-31
 */
public interface FllcjlMapper 
{
    /**
     * 查询放疗流程记录
     * 
     * @param flid 放疗流程记录主键
     * @return 放疗流程记录
     */
    public FllcjlDto selectFllcjlByFlid(String flid);

    /**
     * 查询放疗流程记录列表
     * 
     * @param fllcjl 放疗流程记录
     * @return 放疗流程记录集合
     */
    public List<FllcjlDto> selectFllcjlList(Fllcjl fllcjl);

    public List<FllcjlDto> selectWorkload(Fllcjl query);

    public List<WorkloadDto> workloadStat(WorkloadQuery query);

    /**
     * 新增放疗流程记录
     * 
     * @param fllcjl 放疗流程记录
     * @return 结果
     */
    public int insertFllcjl(Fllcjl fllcjl);

    /**
     * 修改放疗流程记录
     * 
     * @param fllcjl 放疗流程记录
     * @return 结果
     */
    public int updateFllcjl(Fllcjl fllcjl);

    /**
     * 删除放疗流程记录
     * 
     * @param flid 放疗流程记录主键
     * @return 结果
     */
    public int deleteFllcjlByFlid(String flid);

    /**
     * 批量删除放疗流程记录
     * 
     * @param flids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFllcjlByFlids(String[] flids);


}
