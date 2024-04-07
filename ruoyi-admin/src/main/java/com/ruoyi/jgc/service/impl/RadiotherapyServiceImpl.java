package com.ruoyi.jgc.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.RadiotherapyMapper;
import com.ruoyi.jgc.domain.Radiotherapy;
import com.ruoyi.jgc.service.IRadiotherapyService;

/**
 * 放射治疗Service业务层处理
 * 
 * @author jgc
 * @date 2024-04-07
 */
@Service
public class RadiotherapyServiceImpl implements IRadiotherapyService 
{
    @Autowired
    private RadiotherapyMapper radiotherapyMapper;

    /**
     * 查询放射治疗
     * 
     * @param id 放射治疗主键
     * @return 放射治疗
     */
    @Override
    public Radiotherapy selectRadiotherapyById(Long id)
    {
        return radiotherapyMapper.selectRadiotherapyById(id);
    }

    /**
     * 查询放射治疗列表
     * 
     * @param radiotherapy 放射治疗
     * @return 放射治疗
     */
    @Override
    public List<Radiotherapy> selectRadiotherapyList(Radiotherapy radiotherapy)
    {
        return radiotherapyMapper.selectRadiotherapyList(radiotherapy);
    }

    /**
     * 新增放射治疗
     * 
     * @param radiotherapy 放射治疗
     * @return 结果
     */
    @Override
    public int insertRadiotherapy(Radiotherapy radiotherapy)
    {
        radiotherapy.setCreateTime(DateUtils.getNowDate());
        return radiotherapyMapper.insertRadiotherapy(radiotherapy);
    }

    /**
     * 修改放射治疗
     * 
     * @param radiotherapy 放射治疗
     * @return 结果
     */
    @Override
    public int updateRadiotherapy(Radiotherapy radiotherapy)
    {
        radiotherapy.setUpdateTime(DateUtils.getNowDate());
        return radiotherapyMapper.updateRadiotherapy(radiotherapy);
    }

    /**
     * 批量删除放射治疗
     * 
     * @param ids 需要删除的放射治疗主键
     * @return 结果
     */
    @Override
    public int deleteRadiotherapyByIds(Long[] ids)
    {
        return radiotherapyMapper.deleteRadiotherapyByIds(ids);
    }

    /**
     * 删除放射治疗信息
     * 
     * @param id 放射治疗主键
     * @return 结果
     */
    @Override
    public int deleteRadiotherapyById(Long id)
    {
        return radiotherapyMapper.deleteRadiotherapyById(id);
    }
}
