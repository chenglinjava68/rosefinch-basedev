package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.GetPackageTimeEntity;

/**
*
* @Title:T_GET_PACKAGE_TIME表对应的Dao
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月1日  下午4:38
*
*/
public interface IGetPackageTimeDao extends IBaseDao<GetPackageTimeEntity,Long>{

    //检测配置名称的唯一性
    int uniqueCheckByConfigName(Map<String, Object> map);
    //检测配置编码的唯一性
    int uniqueCheckByConfigCode(String configCode);

    //删除一条数据
    void logicalDeleteById(Long configId) throws BusinessException;

    //批量删除
    void batchlogicalDeleteById(List<Long> list);

    //批量启用/停用
    void batchUpdateBlFlagById(Map<String, Object> map);

}
