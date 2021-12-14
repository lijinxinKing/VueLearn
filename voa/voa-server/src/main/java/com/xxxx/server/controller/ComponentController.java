package com.xxxx.server.controller;

import com.xxxx.server.pojo.ComponentMachine;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.ICommentMachineService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/component/basic")
public class ComponentController {

    @Autowired
    private ICommentMachineService commentMachineService;

    @ApiOperation(value = "通过id查询机器列表")
    @RequestMapping("/selectComponentMachine")
    public List<ComponentMachine> getMenusByAdminId(int id){

        return commentMachineService.SelectAllMachineByFKid(id);
    }

    @ApiOperation(value = "获取所有员工(分页)")
    @GetMapping("/")
    public RespPageBean getMachines(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    ComponentMachine machine){
        return commentMachineService.GetComponentMachinesByPage(currentPage,size,machine);
    }

}
