package com.ruoyi.jgc.service;

import java.util.List;
import com.ruoyi.jgc.domain.Targetregion;

/**
 * 靶区Service接口
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
public interface ITargetregionService 
{
    /**
     * 查询靶区
     * 
     * @param id 靶区主键
     * @return 靶区
     */
    public Targetregion selectTargetregionById(Long id);

    /**
     * 查询靶区列表
     * 
     * @param targetregion 靶区
     * @return 靶区集合
     */
    public List<Targetregion> selectTargetregionList(Targetregion targetregion);

    /**
     * 新增靶区
     * 
     * @param targetregion 靶区
     * @return 结果
     */
    public int insertTargetregion(Targetregion targetregion);

    /**
     * 修改靶区
     * 
     * @param targetregion 靶区
     * @return 结果
     */
    public int updateTargetregion(Targetregion targetregion);

    /**
     * 批量删除靶区
     * 
     * @param ids 需要删除的靶区主键集合
     * @return 结果
     */
    public int deleteTargetregionByIds(Long[] ids);

    /**
     * 删除靶区信息
     * 
     * @param id 靶区主键
     * @return 结果
     */
    public int deleteTargetregionById(Long id);
}
