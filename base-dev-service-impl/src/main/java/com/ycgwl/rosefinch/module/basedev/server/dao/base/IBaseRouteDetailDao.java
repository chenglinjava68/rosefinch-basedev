package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRouteDetailEntity;

/**
*
* @Title:T_BASE_ROUTE_DETAIL表对应的Dao
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月28日  下午3:33
*
*/
public interface IBaseRouteDetailDao extends IBaseDao<BaseRouteDetailEntity,Long>{

    //根据routeCode删除明细记录
    void deleteAllByRouteCode(String routeCode) throws BusinessException;

    //批量插入明细数据
    void insertBatchDetail(List<BaseRouteDetailEntity> list);

    List<BaseRouteDetailEntity> queryBaseRouteDetailListByRouteCode(String routeCode);
}
