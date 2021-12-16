package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.ComponentsMapper;
import com.xxxx.server.pojo.Components;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentsServiceServiceImpl extends ServiceImpl<ComponentsMapper, Components> implements IComponentsService {

    @Autowired
    ComponentsMapper componentsMapper;

    @Override
    public List<Components> GetParentComponent() {
        return componentsMapper.GetParentComponent();
    }

    @Override
    public List<Components> getAllComponents(Integer parentId) {
        return componentsMapper.GetAllComponent(parentId);
    }

    @Override
    public RespBean addComponent(Components component) {
        return componentsMapper.addComponent(component);
    }

    @Override
    public RespBean deleteComponent(Integer id) {
        Components components = new Components();
        components.setId(id);
        componentsMapper.deleteDep(components);
        if (-2 == components.getResult()){
            return RespBean.error("该项目下还有子项目，删除失败!");
        }
        if (-1 == components.getResult()){
            return RespBean.error("该项目下还有员工，删除失败!");
        }
        if (1 == components.getResult()){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
