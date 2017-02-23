package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseVehicleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseVehicleVo;

/**
*
* @Title:T_BASE_VEHICLE表的Service接口
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月21日  下午3:25
*
*/
public interface IBaseVehicleService extends IBaseService<BaseVehicleEntity, Long>{

    BaseVehicleEntity modifyVehicle(BaseVehicleVo vo,
            String currentUserCode);

    BaseVehicleEntity insertVehicle(BaseVehicleVo vo,
            String currentUserCode);

    int uniqueCheckByVehicelNo(Map<String, Object> map);

    void deleteVehicle(Long id);

    void batchlogicalDeleteById(List<Long> list);

    void modifyBlFlagByIdList(Map<String, Object> paramMap);
    Pagination<Map<String, Object>> queryVehicleRuns(Map<String, Object> paraMap, Page page, Sort[] sorts);

    int uniqueCheckByVehicleCode(String vehicelCode);
}
