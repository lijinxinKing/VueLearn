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
@TableName("t_component_machines")
@ApiModel(value="Component 机器", description="")
public class ComponentMachine implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "ComponentID")
    private String fkComponent;

    @ApiModelProperty(value = "IsOK")
    private String isOk;

    @ApiModelProperty(value = "空闲状态")
    private String isIdle;

    @ApiModelProperty(value = "使用人")
    private String user;

    @ApiModelProperty(value = "版本")
    private Boolean currentVersion;

    @ApiModelProperty(value = "Server")
    private String currentServer;

    @ApiModelProperty(value = "密码")
    private String comments;

    @ApiModelProperty(value = "机器名称")
    private String machineName;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "连接状态")
    private String connected;

    @ApiModelProperty(value = "状态")
    private String running;

    @ApiModelProperty(value = "创建时间")
    private String ctime;

    @ApiModelProperty(value = "修改时间")
    private String utime;

    @ApiModelProperty(value = "报告地址")
    private String reportAddress;

    @ApiModelProperty(value = "有效bug数")
    private String validBugs;

    @ApiModelProperty(value = "机器名")
    private String deviceName;

    @ApiModelProperty(value = "机器列表")
    @TableField(exist = false)
    private List<ComponentMachine> machines;
}
