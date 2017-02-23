package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseUnloadTimeReleEntity;

public interface IBaseUnloadTimeReleDao extends IBaseDao<BaseUnloadTimeReleEntity,Long>{

	public List<BaseSiteEntity> getByConfigCodeAndSites(Map<String, Object> paramMap);

	public List<BaseSiteEntity> getSiteByConfigCode(String configCode);

	public void deleteAllReles(String configCode);

}
