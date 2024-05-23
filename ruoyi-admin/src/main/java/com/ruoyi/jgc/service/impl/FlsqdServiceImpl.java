package com.ruoyi.jgc.service.impl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.SysUserPostVo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.jgc.domain.Fllcjl;
import com.ruoyi.jgc.mapper.FllcjlMapper;
import com.ruoyi.jgc.service.IAssignWorkService;
import com.ruoyi.system.mapper.SysPostMapper;
import com.sun.jna.platform.win32.Winspool;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.reflection.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jgc.mapper.FlsqdMapper;
import com.ruoyi.jgc.domain.Flsqd;
import com.ruoyi.jgc.domain.Machine;
import com.ruoyi.jgc.domain.Radiotherapy;
import com.ruoyi.jgc.service.IFlsqdService;
import com.ruoyi.jgc.service.IRadiotherapyService;

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

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IAssignWorkService assignWorkService;
    @Autowired
    private AssignUserAtPostServiceImpl assignUserAtPostService;
    @Autowired
    private AssignMachinServiceImpl assignMachinService;
    @Autowired
    private IRadiotherapyService radiotherapyService;

    public static final String FLSQD_ID_CACHE_KEY = "FLSQD_ID_CACHE_KEY";

    public static final String[] lcArr = {"dw", "bqgh", "bqhz","bqtj","jhsj","jhhz","fwyz", "fszl"};

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

    @Override
    public String getNewId() {
        Map<String, Object> flsqdIdMap = redisCache.getCacheMap(FLSQD_ID_CACHE_KEY);
        if (CollectionUtils.isEmpty(flsqdIdMap)) {
            flsqdIdMap = new HashMap<>();
            flsqdIdMap.put("year", DateUtils.parseDateToStr("yyyy", new Date()));
            flsqdIdMap.put("idNum", 1);
        }

        String year = (String) flsqdIdMap.get("year");
        Integer newIdNum = (Integer) flsqdIdMap.get("idNum");
        if (!DateUtils.parseDateToStr("yyyy", new Date()).equals(year)) {
            year = DateUtils.parseDateToStr("yyyy", new Date());
            newIdNum = 1;
            flsqdIdMap.put("year", year);
            flsqdIdMap.put("idNum", newIdNum);
            redisCache.setCacheMap(FLSQD_ID_CACHE_KEY, flsqdIdMap);
        }
        String fldId = flsqdIdMap.get("year") + "-" + newIdNum;
        return fldId;
    }

    public boolean updateNewId() {
        Map<String, Object> flsqdIdMap = redisCache.getCacheMap(FLSQD_ID_CACHE_KEY);
        log.info("更新redis中放疗单id，更新前 {}", JSON.toJSONString(flsqdIdMap));
        if (CollectionUtils.isEmpty(flsqdIdMap)) {
            flsqdIdMap = new HashMap<>();
            flsqdIdMap.put("year", DateUtils.parseDateToStr("yyyy", new Date()));
            flsqdIdMap.put("idNum", 1);
        } else {
            flsqdIdMap.put("idNum", (Integer)flsqdIdMap.get("idNum") + 1);
        }
        redisCache.setCacheMap(FLSQD_ID_CACHE_KEY, flsqdIdMap);
        log.info("更新redis中放疗单id，更新后 {}", JSON.toJSONString(flsqdIdMap));
        return true;
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
        if (!StringUtils.hasText(flsqd.getCreateBy())) {
            log.error("放疗单创建人不能为空");
            throw new ServiceException("放疗单创建人不能为空");
        }
//        flsqd.setId(getNewId());
        flsqd.setCreateTime(new Date());
        flsqd.setTreatStatus("wks");//新增的放疗单的疗程状态是“未开始”
        String fldzt = flsqd.getFldzt();
        int r = flsqdMapper.insertFlsqd(flsqd);
        updateNewId();
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

    /**
     * 放疗申请单从当前节点转移到下一个流程节点，并且在流程记录表新增一条记录，
     * 以便保存当前节点操作人和操作时间等信息
     * @param flsqd
     * @param signUerId
     * @return
     */
    @Override
    public boolean lcNext(Flsqd flsqd, Long signUerId) {

//        //index 表示当前节点序号，-1通常是新创建的放疗单，并且还没有开启流程，
//        //新创建的放疗单开启流程后，会自动来到第一个节点，这个时候index就是0
//        int index = -1;
//        if (ArrayUtils.contains(lcArr, flsqd.getDqlcjdmc())) {
//            index = ArrayUtils.lastIndexOf(lcArr, flsqd.getDqlcjdmc());
//        }
//
//        Long oldUser = flsqd.getDqczry();
//        if (signUerId != null) {
//            oldUser = signUerId;
//        }
//
//        //新增一条流程记录
//        if (index >= 0) {
//            Fllcjl fllcjl = new Fllcjl();
//            fllcjl.setCzr(flsqd.getDqczry());
//            fllcjl.setCzsj(new Date());
//            fllcjl.setFlid(flsqd.getId());
//            fllcjl.setLcjdmc(flsqd.getDqlcjdmc());
//            fllcjl.setLcjdxh(index);
//            fllcjlMapper.insertFllcjl(fllcjl);
//        }
//
//        String presentNode = flsqd.getDqlcjdmc();//当前节点
//        String presentFldZt = flsqd.getFldzt();//当前放疗单状态
//
//
//        if (index == lcArr.length - 1) {
//            //当前节点已经是最后一个节点，最后一个节点是放射治疗节点
//            flsqd.setFldzt("yjs");//状态改为结束
//            flsqd.setDqlcjdmc("");//流程已结束，流节点置为空
//            flsqd.setDqczry(null);//流程已结束，任务已经不属于任何人了
//
//            //因为是最后一个放射治疗节点都是双人上岗，所以这里新增流程记录的时候，按照签字人数新增流程记录
//            //也就是说当前这个任务有几个人共同完成，就生成几条流程记录
//
//
//        } else {
//            if (!StringUtils.hasText(presentNode)) {
//                log.debug("当前申请单[{}] 流程节点为空，开始将节点置为第一个流程节点，且申请单状态改为进行中", flsqd.getId());
//                flsqd.setFldzt("jxz");//状态改为进行中
//            }
//            String nextJd = lcArr[index + 1];
//            flsqd.setDqlcjdmc(nextJd);
//            if (nextJd.equals("fszl")) {
//                //当前节点nextJd是"fszl",表示当前节点是放射治疗节点，需要自动分配机器
//                List<Machine> assMachines = assignMachinService.getAssignList(flsqd.getZljq());
//                if (CollectionUtils.isEmpty(assMachines)) {
//                    log.warn("放疗申请单[{}]分配放疗机器[{}]时，无可用机器", flsqd.getId(), flsqd.getZljq());
//                } else {
//                    Radiotherapy radiotherapy = new Radiotherapy();
//                    radiotherapy.setFldId(flsqd.getId());
//                    radiotherapy.setMachineId(assMachines.get(0).getId());
//                    radiotherapyService.insertRadiotherapy(radiotherapy);
//                    assignMachinService.moveToEnd(flsqd.getZljq(), assMachines.get(0));
//                }
//            } else {
//                //当前节点nextJd不是"fszl",表示当前节点是放射治疗之前的某个节点，需要自动分配岗位上的人员
//                List<SysUserPostVo> assignUsersByPost = assignUserAtPostService.getAssignList(nextJd);
//                if (CollectionUtils.isEmpty(assignUsersByPost)) {
//                    log.error("当前申请单[{}] 岗位[{}]没有工作人员，无法自动分配任务", flsqd.getId(), nextJd);
//                } else {
//                    flsqd.setDqczry(assignUsersByPost.get(0).getUserId());
//                    // assignWorkService.removeToEndAtPost(nextJd, flsqd.getDqczry());
//                    assignUserAtPostService.moveToEndById(nextJd, flsqd.getDqczry());
//                }
//            }
//
//        }
//
//        updateFlsqd(flsqd);
//
//        log.info("放疗申请单流程变化 申请单状态 [{}]->[{}], 流程节点[{}]->[{}], 所属操作人员[{}]->[{}]",
//                presentFldZt, flsqd.getFldzt(), presentNode, flsqd.getDqlcjdmc(), oldUser, flsqd.getDqczry());
//
//        return true;

        return lcStepNext(flsqd, signUerId);
    }

    public boolean lcStepNext(Flsqd flsqd, Long signUerId) {

        if ("yjs".equals(flsqd.getFldzt())) {
            log.warn("当前放疗单[{}]已经结束，无法到下一个流程节点", flsqd.getId());
            return false;
        }

        log.info("开始将放疗单[{}]转移到下一个节点", JSON.toJSONString(flsqd));

        //index 表示当前节点序号，-1通常是新创建的放疗单，并且还没有开启流程，
        //新创建的放疗单开启流程后，会自动来到第一个节点，这个时候index就是0
        int index = -1;
        String presentNode = flsqd.getDqlcjdmc();//当前节点
        String nextNode = "";
        Long presentUser = flsqd.getDqczry();//当前节点所属的操作人
        String presentStatus = flsqd.getFldzt();//当前放疗单状态

        //如果未开始，直接开始
        if ("wks".equals(flsqd.getFldzt())) {
            log.info("当前放疗单[{}] 状态为‘未开始’，开始将节点置为第一个流程节点，且申请单状态改为进行中", flsqd.getId());
            flsqd.setFldzt("jxz");
            flsqd.setDqlcjdmc(lcArr[0]);
        } else {
            index = ArrayUtils.lastIndexOf(lcArr, flsqd.getDqlcjdmc());
        }


        List<Fllcjl> fllcjls = new ArrayList<>();

        if (index == lcArr.length - 1) {
            //当前节点是放射治疗节点，也就是最后一个节点
            log.info("当前放疗单[{}]节点是放射治疗节点，需要结束当前流程", flsqd.getId());
            flsqd.setFldzt("yjs");//状态改为结束
            flsqd.setDqlcjdmc("");//流程已结束，流节点置为空
            flsqd.setDqczry(null);//流程已结束，任务已经不属于任何人了

            //因为是最后一个放射治疗节点必须多人上岗（一般是双人），所以这里新增流程记录的时候，按照签字人数新增流程记录
            //也就是说当前这个任务有几个人共同完成，就生成几条流程记录
            List<SysUser> userList = new ArrayList<>();//模拟获取当前机器当前时段的操作人员
            for (SysUser u : userList) {
                Fllcjl fllcjl = new Fllcjl();
                fllcjl.setCzr(u.getUserId());
                fllcjl.setCzsj(new Date());
                fllcjl.setFlid(flsqd.getId());
                fllcjl.setLcjdmc(presentNode);
                fllcjl.setLcjdxh(index);
                fllcjls.add(fllcjl);
            }
        } else {
            nextNode = lcArr[index + 1];
            flsqd.setDqlcjdmc(nextNode);
            if (index == lcArr.length - 2) {
                //当前节点是放射治疗前的复位验证（fwyz），流转到下一步放射治疗时要分配机器，同时要生成一条放射治疗记录
                log.info("当前放疗单[{}]节点是复位验证节点，下一节点需要分配治疗机器", flsqd.getId());
                flsqd.setDqczry(-1l);//-1代表空
                List<Machine> assMachines = assignMachinService.getAssignList(flsqd.getZljq());
                if (CollectionUtils.isEmpty(assMachines)) {
                    log.warn("放疗单[{}]分配放疗机器[{}]时，无可用机器", flsqd.getId(), flsqd.getZljq());
                } else {
                    
                    flsqd.setMachineId(assMachines.get(0).getId());
                    assignMachinService.moveToEnd(flsqd.getZljq(), assMachines.get(0));

                }

            } else {
                //分配操作人
                log.info("放疗单[{}]下一节点需要分配操作人", flsqd.getId());
                List<SysUserPostVo> assignUsersByPost = assignUserAtPostService.getAssignList(nextNode);
                if (CollectionUtils.isEmpty(assignUsersByPost)) {
                    log.error("当前放疗单[{}] 岗位[{}]没有工作人员，无法自动分配任务", flsqd.getId(), nextNode);
                } else {
                    flsqd.setDqczry(assignUsersByPost.get(0).getUserId());
                    assignUserAtPostService.moveToEndById(nextNode, flsqd.getDqczry());
                }
            }

            if (index > -1) {
                Fllcjl fllcjl = new Fllcjl();
                fllcjl.setCzr(presentUser);
                fllcjl.setCzsj(new Date());
                fllcjl.setFlid(flsqd.getId());
                fllcjl.setLcjdmc(presentNode);
                fllcjl.setLcjdxh(index);
                fllcjls.add(fllcjl);
            }
        }

        for (Fllcjl fllcjl : fllcjls) {
            fllcjlMapper.insertFllcjl(fllcjl);
        }
        updateFlsqd(flsqd);

        log.info("放疗单[{}]流程变化 状态 [{}]->[{}], 流程节点[{}]->[{}], 所属操作人员[{}]->[{}]",
                flsqd.getId(), presentStatus, flsqd.getFldzt(), presentNode, flsqd.getDqlcjdmc(), presentUser, flsqd.getDqczry());

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
