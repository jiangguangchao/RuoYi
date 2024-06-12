package com.ruoyi.jgc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.OrgandamageMapper;
import com.ruoyi.jgc.domain.Organdamage;
import com.ruoyi.jgc.service.IOrgandamageService;

/**
 * 器官危害Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
@Service
public class OrgandamageServiceImpl implements IOrgandamageService 
{
    @Autowired
    private OrgandamageMapper organdamageMapper;

    /**
     * 查询器官危害
     * 
     * @param id 器官危害主键
     * @return 器官危害
     */
    @Override
    public Organdamage selectOrgandamageById(Long id)
    {
        return organdamageMapper.selectOrgandamageById(id);
    }

    /**
     * 查询器官危害列表
     * 
     * @param organdamage 器官危害
     * @return 器官危害
     */
    @Override
    public List<Organdamage> selectOrgandamageList(Organdamage organdamage)
    {
        return organdamageMapper.selectOrgandamageList(organdamage);
    }

    /**
     * 新增器官危害
     * 
     * @param organdamage 器官危害
     * @return 结果
     */
    @Override
    public int insertOrgandamage(Organdamage organdamage)
    {
        return organdamageMapper.insertOrgandamage(organdamage);
    }

    /**
     * 修改器官危害
     * 
     * @param organdamage 器官危害
     * @return 结果
     */
    @Override
    public int updateOrgandamage(Organdamage organdamage)
    {
        return organdamageMapper.updateOrgandamage(organdamage);
    }

    /**
     * 批量删除器官危害
     * 
     * @param ids 需要删除的器官危害主键
     * @return 结果
     */
    @Override
    public int deleteOrgandamageByIds(Long[] ids)
    {
        return organdamageMapper.deleteOrgandamageByIds(ids);
    }

    /**
     * 删除器官危害信息
     * 
     * @param id 器官危害主键
     * @return 结果
     */
    @Override
    public int deleteOrgandamageById(Long id)
    {
        return organdamageMapper.deleteOrgandamageById(id);
    }
}
