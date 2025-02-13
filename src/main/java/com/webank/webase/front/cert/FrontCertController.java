/*
 * Copyright 2014-2019  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webank.webase.front.cert;

import com.webank.webase.front.base.response.BaseResponse;
import com.webank.webase.front.base.code.ConstantCode;
import com.webank.webase.front.base.exception.FrontException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * get Front's sdk's cert(.crt) in /resource
 * get ca.crt/node.crt of the node which linked with Front
 * expose api for node-manager's cert manage module
 */
@Api(value = "/cert", tags = "cert manage interface")
@Slf4j
@RestController
@RequestMapping(value = "cert")
public class FrontCertController {
    @Autowired
    FrontCertService certService;

    @GetMapping("")
    public Object getFrontCerts() {
        Instant startTime = Instant.now();
        log.info("start getFrontCerts. startTime:{}", startTime.toEpochMilli());
        // one crt file has multiple certs string
        List<String> certList = new ArrayList<>();
        List<String> sdkCertList = new ArrayList<>();
        String chainCertStr;
        String nodeCrtStr;
        String agencyCrtStr;
        String sdkCrtStr;
        // node的crt文件可能包含节点、机构、链证书三个
        // sdk的node.crt文件一般包含sdk节点证书，机构证书两个
        try {
            certList = certService.getNodeCerts();
            chainCertStr = certService.getChainCert();
            sdkCertList = certService.getSDKNodeCert();
        }catch (FrontException e) {
            return new BaseResponse(ConstantCode.CERT_FILE_NOT_FOUND, e.getMessage());
        }
        Map<String, String> map = new HashMap<>();
        nodeCrtStr = certList.get(0);
        agencyCrtStr = certList.get(1);
        sdkCrtStr = sdkCertList.get(0);
        if(!StringUtils.isEmpty(nodeCrtStr)) {
            map.put("node", nodeCrtStr);
        }
        if(!StringUtils.isEmpty(agencyCrtStr)) {
            map.put("agency", agencyCrtStr);
        }
        if(!StringUtils.isEmpty(chainCertStr)) {
            map.put("chain", chainCertStr);
        }
        if(!StringUtils.isEmpty(sdkCrtStr)) {
            map.put("sdk", sdkCrtStr);
        }
        log.info("end getFrontCerts. startTime:{}, certMap:{}",
                Duration.between(startTime, Instant.now()).toMillis(), map);
        return map;
    }

}
