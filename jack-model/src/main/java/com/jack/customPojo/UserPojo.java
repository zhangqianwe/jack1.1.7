package com.jack.customPojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jack.domain.BaseModel;
import lombok.Data;

/**
 * @Auther: zhangqianwen
 * @Date: 2020/6/16 17:34
 * @Description:
 */
@Data
public class UserPojo extends BaseModel {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mobile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String orgCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
}
