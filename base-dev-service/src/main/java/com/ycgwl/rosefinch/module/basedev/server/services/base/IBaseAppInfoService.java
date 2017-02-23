package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;

public interface IBaseAppInfoService extends IBaseService<BaseAppInfoEntity, Long>{

	@Override
	int insert(BaseAppInfoEntity t) throws BusinessException;

	BaseAppInfoEntity getQueryKeyData(String appKey) ;

	BaseAppInfoEntity getQueryNameData(String appName) ;

	List<BaseAppInfoEntity> getQueryAllData() ;

	BaseAppInfoEntity getQueryData(String appKey, String appName);

	int deleteBaseAppInfoById(Long id, String appKey,String currentUserCode) throws BusinessException;

	BaseAppInfoEntity getByAppKey(String appKey);

}
