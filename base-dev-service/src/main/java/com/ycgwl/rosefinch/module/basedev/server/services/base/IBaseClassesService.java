package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseClassesDetailEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseClassesEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesDetailVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesVo;

public interface IBaseClassesService extends IBaseService<BaseClassesEntity, Long> {
    // 检测配置编码的唯一性
    int uniqueCheckByClassesCode(String classesCode);

    // 检测名字的唯一性
    int uniqueCheckByClassesName(Map<String, Object> map);

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

    //批量启用
    int startSomeClasses(long [] ids,String currentUserCode);

    //批量停用
    int stopSomeClasses(long [] ids,String currentUserCode);

    //插入
    BaseClassesEntity insertBaseClasses(BaseClassesVo baseClassesVo,String currentUser)throws BusinessException;

    //修改
    BaseClassesEntity updateBaseClasses(BaseClassesVo baseClassesVo,String currentUser)throws BusinessException;

    //查找车线
    Pagination<BaseClassesVo> queryCarLine(Map<String, Object> paraMap, Page page, Sort[] sorts);

    //查找车牌号
    Pagination<BaseClassesVo> queryVehicelNo(Map<String, Object> paraMap, Page page, Sort[] sorts);

    //查找指定车次的详细信息
    List<BaseClassesDetailVo> queryClassesDetailByClassesId(String classesCode);

    //查找车线详细表
    List<BaseClassesDetailVo> queryCarLineDetailByLineId(String lineCode);

    //修改明细
    int updateSomeClassesDetail(List<BaseClassesDetailVo> baseClassesDetailVos);

    //查询
    BaseClassesDetailEntity queryByOrder(Map<String, Object> map);

}
