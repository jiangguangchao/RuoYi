package com.ruoyi.jgc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.TargetregionMapper;
import com.ruoyi.jgc.domain.Targetregion;
import com.ruoyi.jgc.service.ITargetregionService;

/**
 * 靶区Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
@Service
public class TargetregionServiceImpl implements ITargetregionService 
{
    @Autowired
    private TargetregionMapper targetregionMapper;

    /**
     * 查询靶区
     * 
     * @param id 靶区主键
     * @return 靶区
     */
    @Override
    public Targetregion selectTargetregionById(Long id)
    {
        return targetregionMapper.selectTargetregionById(id);
    }

    /**
     * 查询靶区列表
     * 
     * @param targetregion 靶区
     * @return 靶区
     */
    @Override
    public List<Targetregion> selectTargetregionList(Targetregion targetregion)
    {
        return targetregionMapper.selectTargetregionList(targetregion);
    }

    /**
     * 新增靶区
     * 
     * @param targetregion 靶区
     * @return 结果
     */
    @Override
    public int insertTargetregion(Targetregion targetregion)
    {
        return targetregionMapper.insertTargetregion(targetregion);
    }

    /**
     * 修改靶区
     * 
     * @param targetregion 靶区
     * @return 结果
     */
    @Override
    public int updateTargetregion(Targetregion targetregion)
    {
        return targetregionMapper.updateTargetregion(targetregion);
    }

    /**
     * 批量删除靶区
     * 
     * @param ids 需要删除的靶区主键
     * @return 结果
     */
    @Override
    public int deleteTargetregionByIds(Long[] ids)
    {
        return targetregionMapper.deleteTargetregionByIds(ids);
    }

    /**
     * 删除靶区信息
     * 
     * @param id 靶区主键
     * @return 结果
     */
    @Override
    public int deleteTargetregionById(Long id)
    {
        return targetregionMapper.deleteTargetregionById(id);
    }
}
