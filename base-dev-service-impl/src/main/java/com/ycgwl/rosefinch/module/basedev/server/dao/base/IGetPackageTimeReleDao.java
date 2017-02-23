package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.GetPackageTimeReleEntity;

/**
*
* @Title:T_GET_PACKAGE_TIME_RELE表对应的Dao
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月1日  下午4:38
*
*/
public interface IGetPackageTimeReleDao extends IBaseDao<GetPackageTimeReleEntity,Long>{
    public void batchInsertRele(List<GetPackageTimeReleEntity> list);

    public void deleteAllReles(String configCode);

    public List<BaseSiteEntity> getByConfigCodeAndSites(Map<String, Object> paramMap);

    public List<BaseSiteEntity> getSiteByConfigCode(String configCode);
}
