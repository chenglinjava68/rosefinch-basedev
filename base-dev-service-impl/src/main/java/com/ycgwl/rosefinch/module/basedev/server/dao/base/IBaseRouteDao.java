package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRouteEntity;

/**
*
* @Title:T_BASE_ROUTE表对应的Dao
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月28日  下午3:30
*
*/
public interface IBaseRouteDao extends IBaseDao<BaseRouteEntity,Long>{

    //删除一条数据
    void deleteByRouteId(Long routeId) throws BusinessException;
    //批量删除
    void deleteByRouteIdList(List<Long> list);
    //批量启用、停用
    void updateBlFlagByMap(Map<String, Object> paramMap);
    //校验路由名称是否存在
    int uniqueCheckByRouteName(Map<String, Object> map);
    //检测路由编号是否唯一
    int uniqueCheckByRouteCode(String routeCode);

    String getProductByCode(String productCode);
}
