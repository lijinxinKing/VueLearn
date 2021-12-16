package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Components;
import com.xxxx.server.pojo.RespBean;

import java.util.List;

public interface ComponentsMapper extends BaseMapper<Components> {

    List<Components> GetAllComponent(Integer parentId);

    List<Components> GetParentComponent();

    void deleteDep(Components dep);

    RespBean addComponent(Components component);
}
