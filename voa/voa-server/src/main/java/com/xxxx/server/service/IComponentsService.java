package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Components;
import com.xxxx.server.pojo.RespBean;

import java.util.List;

public interface IComponentsService extends IService<Components> {

    List<Components>GetParentComponent();

    List<Components> getAllComponents(Integer parentId);

    RespBean addComponent(Components component);

    RespBean deleteComponent(Integer id);
}
