package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;

public interface IBaseAppInfoDao extends IBaseDao<BaseAppInfoEntity, Long>{

	List<BaseAppInfoEntity> getQueryAllData();

	BaseAppInfoEntity getByAppId(Long id);

	BaseAppInfoEntity getByAppKey(String appKey);


}
