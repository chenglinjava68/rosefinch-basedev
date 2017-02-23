package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeTypeEntity;

public interface IBaseEmployeeTypeDao extends IBaseDao<BaseEmployeeTypeEntity, Long> {
   //删除
    int deleteEmployeeTypeByEmployeeCode(String employeeCode);

    //查找
    List<Integer> getEmployeeTypeByEmployeeCode(String employeeCode);
}
