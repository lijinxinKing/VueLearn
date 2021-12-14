package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.ComponentsMapper;
import com.xxxx.server.pojo.Components;
import com.xxxx.server.service.IComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentsServiceServiceImpl extends ServiceImpl<ComponentsMapper, Components> implements IComponentsService {

    @Autowired
    ComponentsMapper componentsMapper;

    @Override
    public List<Components> GetAllComponent() {
        return componentsMapper.GetAllComponent();
    }
}
