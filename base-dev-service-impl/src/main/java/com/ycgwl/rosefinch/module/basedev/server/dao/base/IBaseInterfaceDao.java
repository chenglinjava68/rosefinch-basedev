package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseInterfaceInfoEntity;

public interface IBaseInterfaceDao extends IBaseDao<BaseInterfaceInfoEntity, Long>{

	List<BaseInterfaceInfoEntity> queryByInterfaceName(String interfaceName);
	List<BaseInterfaceInfoEntity> queryAllInterfaceData();
	void batchInsertBaseInterfaceInfoEntity(List<BaseInterfaceInfoEntity> interfaceEntitys);
	void deleteByAppkey(String appKey);
	List<BaseInterfaceInfoEntity> getByAppkey(String appKey);

}
