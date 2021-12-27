package com.xxxx.server.controller;

import com.xxxx.server.pojo.*;
import com.xxxx.server.service.ICommentMachineService;
import com.xxxx.server.service.IComponentsService;
import com.xxxx.server.service.impl.AdminServiceImpl;
import com.xxxx.server.utils.AdminUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/component/basic")
public class ComponentController {

    @Autowired
    private ICommentMachineService commentMachineService;

    @Autowired
    private IComponentsService componentsService;

    @Autowired
    private AdminServiceImpl adminService;

    @ApiOperation(value = "通过id查询机器列表")
    @RequestMapping("/selectComponentMachine/")
    public List<ComponentMachine> getMenusByAdminId(@RequestParam(defaultValue = "1")int componentId){
        return commentMachineService.SelectAllMachineByFKid(componentId);
    }


    @ApiOperation(value = "分页获取")
    @GetMapping("/")
    public RespPageBean getMachines(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "1") Integer componentId,
                                    ComponentMachine machine){
        return commentMachineService.GetComponentMachinesByPage(currentPage,size,machine,componentId);
    }

    ///////////////////////////获取项目名称///////////////////////////////////////////////
    // 根据 用户 ID 查询

    @ApiOperation(value = "获取所有项目")
    @GetMapping("/getAllComponent")
    public RespBean getAllComponents() {
        int componentId = AdminUtils.getCurrentAdmin().getComponentId();
        List<Components> result = componentsService.getAllComponents(componentId);
        return RespBean.success("获取成功",result);
    }

    @ApiOperation(value = "添加项目")
    @PostMapping("/addComponent")
    public RespBean addDep(@RequestBody Components component){
        System.out.println(component.toString());
        return componentsService.addComponent(component);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDep(@PathVariable Integer id){
        return componentsService.deleteComponent(id);
    }



}
