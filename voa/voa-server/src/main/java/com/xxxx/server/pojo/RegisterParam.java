package com.xxxx.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AdminLogin对象",description = "")
public class RegisterParam {
    @ApiModelProperty(value = "用户名",required = true)
    private String loginid;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "确认密码",required = true)
    private String confirmPassword;
    @ApiModelProperty(value = "Component",required = true)
    private Integer componentId;
    @ApiModelProperty(value = "email",required = true)
    private String email;
}
