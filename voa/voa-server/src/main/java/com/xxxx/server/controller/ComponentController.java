package com.xxxx.server.controller;

import com.xxxx.server.pojo.ComponentMachine;
import com.xxxx.server.pojo.Components;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.ICommentMachineService;
import com.xxxx.server.service.IComponentsService;
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

    @ApiOperation(value = "通过id查询机器列表")
    @RequestMapping("/selectComponentMachine")
    public List<ComponentMachine> getMenusByAdminId(int id){
        return commentMachineService.SelectAllMachineByFKid(id);
    }

    @ApiOperation(value = "分页获取")
    @GetMapping("/")
    public RespPageBean getMachines(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    ComponentMachine machine){
        return commentMachineService.GetComponentMachinesByPage(currentPage,size,machine);
    }

    @ApiOperation(value = "获取所有项目")
    @GetMapping("/getAllComponent")
    public List<Components> getAllComponents() {
        return componentsService.getAllComponents(-1);
    }

    @ApiOperation(value = "添加项目")
    @PostMapping("/addComponent")
    public RespBean addDep(@RequestBody Components component){
        return componentsService.addComponent(component);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public RespBean deleteDep(@PathVariable Integer id){
        return componentsService.deleteComponent(id);
    }

    @ApiOperation(value = "查询(用于登录)")
    @RequestMapping("/getParentComponent")
    public List<Components> GetComponents(){
        return componentsService.GetParentComponent();
    }

}
