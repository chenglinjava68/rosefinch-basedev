package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseEmployeeVo;

public interface IBaseEmployeeDao extends IBaseDao<BaseEmployeeEntity, Long> {
    // 检测配置名称的唯一性
    int uniqueCheckByEmployeeName(Map<String, Object> map);

    // 检测配置编码的唯一性
    int uniqueCheckByEployeeCode(String employeeCode);

    // 根据不同条件查找
    Pagination<BaseEmployeeVo> queryEmployeeBySomeIf(Map<String, Object> paraMap, Page page, Sort[] sorts);


    Pagination<BaseEmployeeVo> queryEmployeeByNameOrCode(Map<String, Object> paraMap, Page page, Sort[] sorts);

    // 启用
    int startOneEmployee(Map<String, Object> map);

    // 停用
    int stopOneEmployee(Map<String, Object> map);


    //批量删除
    int deleteSomeEmployee(long [] ids);

    //删除
    int deleteEmployeeById(long id);


    /**
     * 批量插入员工
     *
     * @param empList
     * @author guoh.mao
     */
    void batchInsertEmployee(List<BaseEmployeeEntity> empList);


    /**
     * 通过员工编码判断员工是否存在
     *
     * @param employeeCode
     * @return
     * @author guoh.mao
     */
    boolean isExist(String employeeCode);

    /**
     * 通过编码更新
     *
     * @param baseEmployeeEntity
     * @return
     * @author guoh.mao
     */
    int updateByCode(BaseEmployeeEntity baseEmployeeEntity);


    /**
     * 将所属门店清空
     *
     * @param siteCode
     * @return
     * @author guoh.mao
     */
    int clearOwnerSite(String siteCode);

    /**
     * 设置员工的所属门店
     *
     * @param map
     * @return
     * @author guoh.mao
     */
    int setOwnerSite(Map<String, Object> map);

    /**
     * 根据Code获取员工信息
     */
    BaseEmployeeEntity getByCode(String employeeCode);

}
