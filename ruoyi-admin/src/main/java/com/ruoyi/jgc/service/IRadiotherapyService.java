package com.ruoyi.jgc.service;

import java.util.List;
import com.ruoyi.jgc.domain.Radiotherapy;

/**
 * 放射治疗Service接口
 * 
 * @author jgc
 * @date 2024-04-07
 */
public interface IRadiotherapyService 
{
    /**
     * 查询放射治疗
     * 
     * @param id 放射治疗主键
     * @return 放射治疗
     */
    public Radiotherapy selectRadiotherapyById(Long id);

    /**
     * 查询放射治疗列表
     * 
     * @param radiotherapy 放射治疗
     * @return 放射治疗集合
     */
    public List<Radiotherapy> selectRadiotherapyList(Radiotherapy radiotherapy);

    /**
     * 新增放射治疗
     * 
     * @param radiotherapy 放射治疗
     * @return 结果
     */
    public int insertRadiotherapy(Radiotherapy radiotherapy);

    /**
     * 修改放射治疗
     * 
     * @param radiotherapy 放射治疗
     * @return 结果
     */
    public int updateRadiotherapy(Radiotherapy radiotherapy);

    /**
     * 批量删除放射治疗
     * 
     * @param ids 需要删除的放射治疗主键集合
     * @return 结果
     */
    public int deleteRadiotherapyByIds(Long[] ids);

    /**
     * 删除放射治疗信息
     * 
     * @param id 放射治疗主键
     * @return 结果
     */
    public int deleteRadiotherapyById(Long id);
}
