/*
 * Copyright 2014-2019 the original author or authors.
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
package com.webank.webase.front.transaction.entity;

import com.webank.webase.front.base.code.ConstantCode;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * transHandler interface parameter.
 * handle transactions of deploy/call contract
 */
@Data
public class ReqTransHandle {
    @NotNull(message = ConstantCode.PARAM_FAIL_USER_IS_EMPTY)
    private String user;
    private String contractName;
    private String version;
    private String contractAddress;
    private String contractPath;
    @NotBlank(message = ConstantCode.PARAM_FAIL_FUNCNAME_IS_EMPTY)
    private String funcName;
    private int groupId = 1;
    @NotNull(message = ConstantCode.PARAM_FAIL_USEAES_IS_EMPTY)
    private Boolean useAes;
    private List<Object> funcParam = new ArrayList<>();
}
