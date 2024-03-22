package com.kt.edu.thirdproject.common.transform.model;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonHeader {

    private String appName;

    private String svcName;

    private String fnName;

    private String fnCd;

    private String globalNo;

    private String chnlType;

    private String envrFlag;

    private String trFlag;

    private String trDate;

    private String trTime;

    private String clntIp;

    private String responseType;

    private String responseCode;

    private String responseLogcd;

    private String responseTitle;

    private String responseBasc;

    private String responseDtal;

    private String responseSystem;

    private String userId;

    private String realUserId;

    private String filler;

    private String langCode;

    private String orgId;

    private String srcId;

    private String curHostId;

    private String lgDateTime;

    private String tokenId;

    private String cmpnCd;

    private String lockType;

    private String lockId;

    private String lockTimeSt;

    private String businessKey;

    private String arbitraryKey;

    private String resendFlag;

    private String phase;

    private String logPoint;
}
