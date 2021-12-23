package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.IRoleService;
import com.xxxx.server.utils.AdminUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;

    @Autowired
    IRoleService roleService;

    @ApiModelProperty(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin>GetAllAdmin(String keyWords){
        return adminService.getAllAdmins(AdminUtils.getCurrentAdmin(),keyWords);
    }

    @ApiModelProperty(value = "获取所有角色")
    @GetMapping("/roles")
    public List<Role>GetAllRoles(){
        return roleService.list();
    }

    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId,Integer[] rids){
        return adminService.updateAdminRole(adminId,rids);
    }

    @ApiOperation(value = "更新操作员")
    @PostMapping("/updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin){
        if (adminService.updateById(admin)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id){
        if (adminService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败");
    }

}
