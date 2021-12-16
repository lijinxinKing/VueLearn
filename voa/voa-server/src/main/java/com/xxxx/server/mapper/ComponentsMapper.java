package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Components;

import java.util.List;

public interface ComponentsMapper extends BaseMapper<Components> {
    List<Components> GetAllComponent();

    List<Components> GetAllComponentByRoleId(int id);

    List<Components> GetParentComponent();
}
