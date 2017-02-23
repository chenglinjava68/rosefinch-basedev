package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseVehicleEntity;

/**
*
* @Title:T_BASE_VEHICLE表对应的Dao
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月21日  下午3:25
*
*/
public interface IBaseVehicleDao extends IBaseDao<BaseVehicleEntity,Long>{

    //根据车牌号查询是否有 重复
    int uniqueCheckByVehicelNo(Map<String, Object> paramMap);
    //删除一条数据
    void logicalDeleteById(Long configId) throws BusinessException;
    //批量删除
    void batchlogicalDeleteById(List<Long> list);
    //批量启用、停用
    void updateBlFlagByMap(Map<String, Object> paramMap);
    Pagination<Map<String, Object>> queryVehicleRuns(Map<String, Object> paraMap, Page page, Sort[] sorts);

    String queryVehicleName(String runNo);
    //检测车辆编码的唯一性
    int uniqueCheckByVehicleCode(String vehicelCode);

}
