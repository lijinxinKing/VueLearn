package com.xxxx.server.controller;

import com.xxxx.server.pojo.*;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.IComponentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;


@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IComponentsService componentsService;

    private Integer adminId;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam,
                          HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(),request);
    }


    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public RespBean register(@RequestBody RegisterParam registerParam,
                          HttpServletRequest request) {
        return adminService.register(registerParam,request);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (null==principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！");
    }

    @ApiOperation(value = "查询(用于登录)")
    @RequestMapping("/getParentComponent")
    public List<Components> GetComponents(){
        return componentsService.GetParentComponent();
    }
}