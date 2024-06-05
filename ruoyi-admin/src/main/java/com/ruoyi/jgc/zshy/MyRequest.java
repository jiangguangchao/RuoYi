package com.ruoyi.jgc.zshy;


import cn.hutool.core.map.MapUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @program: hnbd-atmospheric-inversion-project
 * @description:
 * @author:
 * @create: 2024-05-30 15:37
 */
public class MyRequest {



    @Test
    public void fileRequest() {
        Map<String, String> map = MapUtil.<String, String>builder().put("Accept-Encoding", "gzip, deflate").build();
        RequestUtil.sendGet("http://192.168.0.189:12017/obsfile/list?startTime=100", map, false, false, false);
    }

    @Test
    public void courseList(){
        Map<String, String> bodyMap = MapUtil.<String, String>builder()
                .put("learnPlanId", "d30ab951-f0ae-4ca8-928c-8fe375db71a9")
                .put("parentId", "")
                .put("learnState", "2")
                .put("top", "0")
                .put("courseType", "0")
                .build();
        RequestUtil.sendPost("https://sdnew.91huayi.com/health/HealthCourse/GetLearnCourseList", bodyMap, RequestUtil.zshyHeader(),true, true, false);
    }
}
