package com.ruoyi.jgc.mapper;

import java.util.List;
import com.ruoyi.jgc.domain.Targetregion;

/**
 * 靶区Mapper接口
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
public interface TargetregionMapper 
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
     * 删除靶区
     * 
     * @param id 靶区主键
     * @return 结果
     */
    public int deleteTargetregionById(Long id);

    /**
     * 批量删除靶区
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTargetregionByIds(Long[] ids);
}
