package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.ComponentMachineMapper;
import com.xxxx.server.pojo.ComponentMachine;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.ICommentMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ComponentMachineServiceImpl extends ServiceImpl<ComponentMachineMapper, ComponentMachine> implements ICommentMachineService {

    @Autowired
    ComponentMachineMapper componentMachineMapper;

    @Override
    public List<ComponentMachine> SelectAllMachineByFKid(int id) {
        List<ComponentMachine> componentMachines = componentMachineMapper.SelectAllMachineByFKid(id);
        return componentMachines;
    }

    @Override
    public RespPageBean GetComponentMachinesByPage(Integer currentPage, Integer size, ComponentMachine machine) {
        //开启分页
        Page<ComponentMachine> page = new Page<>(currentPage, size);
        IPage<ComponentMachine> machinesByPage = componentMachineMapper.GetMachinesByPage(page, machine);
        RespPageBean respPageBean = new RespPageBean(machinesByPage.getTotal(), machinesByPage.getRecords());
        return respPageBean;
    }


}
