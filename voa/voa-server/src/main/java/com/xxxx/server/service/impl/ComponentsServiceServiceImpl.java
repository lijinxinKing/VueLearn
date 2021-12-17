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
        component.setEnabled(true);
        componentsMapper.addComponent(component);
        int result = component.getResult();
        if(result == 1){
            return RespBean.success("添加成功",component);
        }else{
            return RespBean.error("添加失败，请检查!");
        }
    }

    @Override
    public RespBean deleteComponent(Integer id) {
        Components components = new Components();
        components.setId(id);
        componentsMapper.deleteComponent(components);
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
