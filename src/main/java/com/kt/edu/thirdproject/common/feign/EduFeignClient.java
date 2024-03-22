package com.kt.edu.thirdproject.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name="eduGwClient", url= "${api.kosgw.url}")
@FeignClient(name="eduGwClient", url= "localhost:8080")
public interface EduFeignClient {
   /* @PostMapping("/api/v1/employee")
    public String authTokn(@RequestBody AuthToknInDs authToknInDs);

    @GetMapping("/system/healthz")
    public String getSystemHealth();*/

}