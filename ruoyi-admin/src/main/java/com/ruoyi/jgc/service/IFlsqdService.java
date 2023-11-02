package com.ruoyi.jgc.service;

import java.util.List;
import com.ruoyi.jgc.domain.Flsqd;

/**
 * 放疗申请单Service接口
 * 
 * @author jgc
 * @date 2023-10-28
 */
public interface IFlsqdService 
{
    /**
     * 查询放疗申请单
     * 
     * @param id 放疗申请单主键
     * @return 放疗申请单
     */
    public Flsqd selectFlsqdById(String id);

    /**
     * 查询放疗申请单列表
     * 
     * @param flsqd 放疗申请单
     * @return 放疗申请单集合
     */
    public List<Flsqd> selectFlsqdList(Flsqd flsqd);

    /**
     * 新增放疗申请单
     * 
     * @param flsqd 放疗申请单
     * @return 结果
     */
    public int insertFlsqd(Flsqd flsqd);

    /**
     * 修改放疗申请单
     * 
     * @param flsqd 放疗申请单
     * @return 结果
     */
    public int updateFlsqd(Flsqd flsqd);

    public boolean startLC(Flsqd flsqd);
    public boolean lcNext(Flsqd flsqd, Long signUerId);
    public boolean sign(Flsqd flsqd, Long userId);

    /**
     * 批量删除放疗申请单
     * 
     * @param ids 需要删除的放疗申请单主键集合
     * @return 结果
     */
    public int deleteFlsqdByIds(String[] ids);

    /**
     * 删除放疗申请单信息
     * 
     * @param id 放疗申请单主键
     * @return 结果
     */
    public int deleteFlsqdById(String id);
}
