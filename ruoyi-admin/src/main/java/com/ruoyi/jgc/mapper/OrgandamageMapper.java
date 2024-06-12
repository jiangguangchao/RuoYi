package com.ruoyi.jgc.mapper;

import java.util.List;
import com.ruoyi.jgc.domain.Organdamage;

/**
 * 器官危害Mapper接口
 * 
 * @author ruoyi
 * @date 2024-06-12
 */
public interface OrgandamageMapper 
{
    /**
     * 查询器官危害
     * 
     * @param id 器官危害主键
     * @return 器官危害
     */
    public Organdamage selectOrgandamageById(Long id);

    /**
     * 查询器官危害列表
     * 
     * @param organdamage 器官危害
     * @return 器官危害集合
     */
    public List<Organdamage> selectOrgandamageList(Organdamage organdamage);

    /**
     * 新增器官危害
     * 
     * @param organdamage 器官危害
     * @return 结果
     */
    public int insertOrgandamage(Organdamage organdamage);

    /**
     * 修改器官危害
     * 
     * @param organdamage 器官危害
     * @return 结果
     */
    public int updateOrgandamage(Organdamage organdamage);

    /**
     * 删除器官危害
     * 
     * @param id 器官危害主键
     * @return 结果
     */
    public int deleteOrgandamageById(Long id);

    /**
     * 批量删除器官危害
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrgandamageByIds(Long[] ids);
}
