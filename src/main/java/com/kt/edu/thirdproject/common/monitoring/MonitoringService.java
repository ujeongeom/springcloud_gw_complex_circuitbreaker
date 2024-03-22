package com.kt.edu.thirdproject.common.monitoring;

import com.kt.edu.thirdproject.common.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

@Service
@Slf4j
public class MonitoringService {
    //@Autowired
    //private MonOpenFeign mon;

    @Autowired
    Environment env;

    @Async
    public void sendReqAsync(ServerWebExchange exchange, String reqBody) {

        LogUtil.trace(exchange, "[CTG:MON]{}", reqBody);

        try{

            LogUtil.info(exchange, "[CTG:DEBUG] sendReqAsync logPoint:{}", env.getProperty("app-info.log-point"));

           // mon.bmonReqSendKV(reqBody, env.getProperty("app-info.log-point"));
        } catch(Exception ex){
            LogUtil.error(exchange, "[CTG:DEBUG] error Mon sendReqAsync:{}", ex.getMessage());

        } finally {
            LogUtil.info(exchange, "[CTG:DEBUG] 요청로그 BMON연동처리");
        }
    }


    @Async
    public void sendResAsync(ServerWebExchange exchange, String globalNo, String reqBody) {

        LogUtil.trace(exchange, "[CTG:DEBUG] sendResAsync reqBody:{}", reqBody);

        try{
            //mon.bmonResSendKV(reqBody, env.getProperty("app-info.log-point"));
        } catch(Exception ex){

            LogUtil.error(exchange, "[CTG:DEBUG] error Mon sendResAsync:{}", ex.getMessage());

        } finally {

            LogUtil.error(exchange, "[CTG:DEBUG] 응답로그 BMON연동처리");

        }
    }

}
