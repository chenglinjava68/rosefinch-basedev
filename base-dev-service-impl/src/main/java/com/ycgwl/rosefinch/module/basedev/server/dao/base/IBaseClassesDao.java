package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseClassesEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesVo;

public interface IBaseClassesDao extends IBaseDao<BaseClassesEntity, Long>{

    // 检测名字的唯一性
    int uniqueCheckByClassesName(Map<String, Object> map);

    // 检测配置编码的唯一性
    int uniqueCheckByClassesCode(String classesCode);

    // 根据不同条件查找
    Pagination<BaseClassesVo> queryClassesBySomeIf(Map<String, Object> paraMap, Page page, Sort[] sorts);

    // 启用
    int startOneClasses(Map<String, Object> map);

    // 停用
    int stopOneClasses(Map<String, Object> map);


    //批量删除
    int deleteSomeClasses(long [] ids);

    //删除
    int deleteClassesById(long id);

    //查找车线
    Pagination<BaseClassesVo> queryCarLine(Map<String, Object> paraMap, Page page, Sort[] sorts);

    //查找车牌号
    Pagination<BaseClassesVo> queryVehicelCode(Map<String, Object> paraMap, Page page, Sort[] sorts);

    Integer queryCarTypeByvehicelNo(String vehicelNo);
}
