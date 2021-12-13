package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.ComponentMachine;
import com.xxxx.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

public interface ICommentMachineService extends IService<ComponentMachine> {

    List<ComponentMachine> SelectAllMachineByFKid(int id);

    RespPageBean GetComponentMachinesByPage(Integer currentPage, Integer size, ComponentMachine machine, LocalDate[] beginDateScope);
}
