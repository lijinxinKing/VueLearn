package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Components;

import java.util.List;

public interface IComponentsService extends IService<Components> {


    List<Components> GetAllComponent();
}
