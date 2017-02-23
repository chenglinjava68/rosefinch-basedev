package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRouteDetailEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRouteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRouteVo;

/**
*
* @Title:T_BASE_ROUTE表的Service接口
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月28日  下午3:51
*
*/
public interface IBaseRouteService extends IBaseService<BaseRouteEntity, Long>{

    BaseRouteEntity modifyBaseRoute(BaseRouteVo baseRouteVo,
            String currentUserCode);

    BaseRouteEntity createBaseRoute(BaseRouteVo baseRouteVo,
            String currentUserCode);

    void removeBaseRoute(Long routeId);

    void removeBaseRouteByIdList(List<Long> list);

    void modifyBlFlagByIdList(Map<String, Object> paramMap);

    List<BaseRouteDetailEntity> findBaseRouteDetailListByRouteCode(String routeCode);

    int uniqueCheckByRouteName(Long id, String routeName);

    int uniqueCheckByRouteCode(String routeCode);
}
