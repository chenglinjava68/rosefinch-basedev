package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseEmployeeVo;

public interface IBaseEmployeeService extends IBaseService<BaseEmployeeEntity, Long> {
    // 检测配置名称的唯一性
    int uniqueCheckByEmployeeName(Map<String, Object> map);

    // 检测配置编码的唯一性
    int uniqueCheckByEployeeCode(String employeeCode);

    // 根据不同条件查找
    Pagination<BaseEmployeeVo> queryEmployeeBySomeIf(Map<String, Object> paraMap, Page page, Sort[] sorts);

    /**
     * 按员工名称或编码查询
     *
     * @param paraMap
     * @param page
     * @param sorts
     * @return
     * @author guoh.mao
     */
    Pagination<BaseEmployeeVo> queryEmployeeByNameOrCode(Map<String, Object> paraMap, Page page, Sort[] sorts);

    // 停用
    int startOneEmployee(Map<String, Object> map);

    // 停用
    int stopOneEmployee(Map<String, Object> map);


    //批量启用
    int startSomeEmployee(long [] ids,String currentUserCode);

    //批量停用
    int stopSomeEmployee(long [] ids,String currentUserCode);

    //批量删除
    int deleteSomeEmployee(long [] ids);

    //删除
    int deleteEmployeeById(long id);

    //插入
    BaseEmployeeEntity insertEmployee(BaseEmployeeVo baseEmployeeVo,String currentUser)throws BusinessException;

    //修改
    BaseEmployeeEntity updateEmployee(BaseEmployeeVo baseEmployeeVo,String currentUser)throws BusinessException;

   //查找
    List<Integer> getEmployeeTypeByEmployeeCode(String employeeCode);


    /**
     * 批量插入员工
     *
     * @param empList
     * @author guoh.mao
     */
    void batchInsertEmployee(List<BaseEmployeeEntity> empList);


    /**
     * 批量插入或更新员工
     *
     * @param empList
     * @author guoh.mao
     */
    void batchInsertOrUpdateEmployee(List<BaseEmployeeEntity> empList, BaseOrgEntity parent);

    /**
     * 根据Code获取员工信息
     */
    BaseEmployeeEntity getByCode(String employeeCode);

}
