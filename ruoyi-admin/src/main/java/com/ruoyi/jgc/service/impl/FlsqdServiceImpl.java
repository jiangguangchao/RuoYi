package com.ruoyi.jgc.service.impl;

import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.mapper.FllcjlMapper;
import com.ruoyi.system.mapper.SysPostMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.reflection.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.FlsqdMapper;
import com.ruoyi.jgc.domain.Flsqd;
import com.ruoyi.jgc.service.IFlsqdService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 放疗申请单Service业务层处理
 * 
 * @author jgc
 * @date 2023-10-28
 */
@Service
public class FlsqdServiceImpl implements IFlsqdService 
{
    @Autowired
    private FlsqdMapper flsqdMapper;

    @Autowired
    private SysPostMapper sysPostMapper;

    @Autowired
    private FllcjlMapper fllcjlMapper;

    public static final String[] lcArr = {"dw", "bqgh", "bqhz","bqtj","jhsj","jhhz","fwyz"};

    private static final Logger log = LoggerFactory.getLogger(FlsqdServiceImpl.class);

    /**
     * 查询放疗申请单
     * 
     * @param id 放疗申请单主键
     * @return 放疗申请单
     */
    @Override
    public Flsqd selectFlsqdById(String id)
    {
        return flsqdMapper.selectFlsqdById(id);
    }

    /**
     * 查询放疗申请单列表
     * 
     * @param flsqd 放疗申请单
     * @return 放疗申请单
     */
    @Override
    public List<Flsqd> selectFlsqdList(Flsqd flsqd)
    {
        return flsqdMapper.selectFlsqdList(flsqd);
    }

    /**
     * 新增放疗申请单
     * 
     * @param flsqd 放疗申请单
     * @return 结果
     */
    @Override
    public int insertFlsqd(Flsqd flsqd)
    {
        String fldzt = flsqd.getFldzt();
        int r = flsqdMapper.insertFlsqd(flsqd);
        if (fldzt.equals("jxz") && r > 0) {
            log.info("创建放疗申请单[{}]时状态为进行中，流程开启", flsqd.getId());
            lcNext(flsqd, null);
        }
        return r;
    }

    /**
     * 修改放疗申请单
     * 
     * @param flsqd 放疗申请单
     * @return 结果
     */
    @Override
    public int updateFlsqd(Flsqd flsqd)
    {
        return flsqdMapper.updateFlsqd(flsqd);
    }

    @Override
    public boolean startLC(Flsqd flsqd) {
        if (!flsqd.getFldzt().equals("wks")) {
            log.warn("当前申请单[{}]状态[{}]不是未开启状态，不能进行开启操作", flsqd.getId(), flsqd.getFldzt());
            return false;
        }
        lcNext(flsqd, null);
        return true;
    }

    /**
     * 批量删除放疗申请单
     * 
     * @param ids 需要删除的放疗申请单主键
     * @return 结果
     */
    @Override
    public int deleteFlsqdByIds(String[] ids)
    {
        return flsqdMapper.deleteFlsqdByIds(ids);
    }

    /**
     * 删除放疗申请单信息
     * 
     * @param id 放疗申请单主键
     * @return 结果
     */
    @Override
    public int deleteFlsqdById(String id)
    {
        return flsqdMapper.deleteFlsqdById(id);
    }

    @Override
    public boolean sign(Flsqd flsqd, Long userId) {
        return lcNext(flsqd, userId);
    }

    @Override
    public boolean lcNext(Flsqd flsqd, Long signUerId) {

        int index = -1;
        if (ArrayUtils.contains(lcArr, flsqd.getDqlcjdmc())) {
            index = ArrayUtils.lastIndexOf(lcArr, flsqd.getDqlcjdmc());
        }

        Long oldUser = flsqd.getDqczry();
        if (signUerId != null) {
            oldUser = signUerId;
        }

        if (index >= 0) {
            Fllcjl fllcjl = new Fllcjl();
            fllcjl.setCzr(flsqd.getDqczry());
            fllcjl.setCzsj(new Date());
            fllcjl.setFlid(flsqd.getId());
            fllcjl.setLcjdmc(flsqd.getDqlcjdmc());
            fllcjl.setLcjdxh(index);
            fllcjlMapper.insertFllcjl(fllcjl);
        }

        String oldJd = flsqd.getDqlcjdmc();
        String oldZt = flsqd.getFldzt();


        if ((index + 1) == lcArr.length) {
            flsqd.setFldzt("yjs");//状态改为结束
            flsqd.setDqlcjdmc("");//流程已结束，流节点置为空
            flsqd.setDqczry(null);//流程已结束，任务已经不属于任何人了
        } else {
            if (!StringUtils.hasText(oldJd)) {
                log.debug("当前申请单[{}] 流程节点为空，开始将节点置为第一个流程节点，且申请单状态改为进行中");
                flsqd.setFldzt("jxz");//状态改为进行中
            }
            String nextJd = lcArr[index + 1];
            flsqd.setDqlcjdmc(nextJd);
            flsqd.setDqczry(getAllocateUser(nextJd));
        }

        updateFlsqd(flsqd);

        log.info("放疗申请单流程变化 申请单状态 [{}]->[{}], 流程节点[{}]->[{}], 所属操作人员[{}]->[{}]",
                oldZt, flsqd.getFldzt(), oldJd, flsqd.getDqlcjdmc(), oldUser, flsqd.getDqczry());

        return true;
    }

    public Long getAllocateUser(String jd) {
        String postCode = jd;
        List<SysUserPostVo> sysUserPostVos = sysPostMapper.selectUserByPost(postCode);
        if (CollectionUtils.isEmpty(sysUserPostVos)) {
            log.error("根据岗位[{}]查询不到所在岗位人员", postCode);
            return null;
        }

        List<SysUserPostVo> userList = sysUserPostVos.stream()
                .filter(u -> "0".equals(u.getStatus()) && "1".equals(u.getAssignWork()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userList)) {
            log.error("根据岗位[{}]查询到的用户，无法接受工作", postCode);
            return null;
        }

        int i = RandomUtils.nextInt(0, sysUserPostVos.size());
        return sysUserPostVos.get(i).getUserId();
    }

}
