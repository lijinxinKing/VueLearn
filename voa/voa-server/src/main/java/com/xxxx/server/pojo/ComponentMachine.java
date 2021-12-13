package com.xxxx.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_component_machine")
@ApiModel(value="Component 机器", description="")
public class ComponentMachine implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "ComponentID")
    private String fkComponent;

    @ApiModelProperty(value = "手机号码")
    private String isOk;

    @ApiModelProperty(value = "住宅电话")
    private String isIdle;

    @ApiModelProperty(value = "联系地址")
    private String user;

    @ApiModelProperty(value = "是否启用")
    private Boolean currentVersion;

    @ApiModelProperty(value = "用户名")
    private String currentServer;

    @ApiModelProperty(value = "密码")
    private String comments;

    @ApiModelProperty(value = "用户头像")
    private String machineName;

    @ApiModelProperty(value = "备注")
    private String ip;

    @ApiModelProperty(value = "备注")
    private String connected;

    @ApiModelProperty(value = "备注")
    private String running;

    @ApiModelProperty(value = "备注")
    private String ctime;

    @ApiModelProperty(value = "备注")
    private String utime;

    @ApiModelProperty(value = "备注")
    private String reportAddress;

    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<ComponentMachine> machines;
}
