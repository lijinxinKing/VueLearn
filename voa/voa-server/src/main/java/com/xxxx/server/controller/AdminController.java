package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.utils.AdminUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;

    @ApiModelProperty(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin>GetAllAdmin(String keyWords){
        return adminService.getAllAdmins(AdminUtils.getCurrentAdmin(),keyWords);
    }
}
