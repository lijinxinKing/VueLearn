package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.pojo.ComponentMachine;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

public interface ComponentMachineMapper extends BaseMapper<ComponentMachine> {

    List<ComponentMachine>SelectAllMachineByFKid(int id);

    /**
     * 获取所有员工(分页)
     * @param page
     * @param componentMachine
     * @param beginDateScope
     * @return
     */
    IPage<ComponentMachine> GetMachinesByPage(Page<ComponentMachine> page,
                                                       @Param("componentMachine") ComponentMachine componentMachine,
                                                       @Param("beginDateScope") LocalDate[] beginDateScope);

}
