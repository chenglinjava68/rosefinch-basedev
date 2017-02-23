package com.ycgwl.rosefinch.module.basedev.server.dao.base;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;

public interface IBaseOrgDao extends IBaseDao<BaseOrgEntity, Long> {
	List<BaseOrgEntity> queryByOrgName(String orgName);
	void updateBlFlag(BaseOrgEntity baseOrgEntity);
	int uniqueCheckByOrgCode(String orgCode);
	int uniqueCheckByOrgName(Map<String, Object> map);
	int uniqueCheckByOrgNameShort(Map<String, Object> map);

	int batchUpdateBlFlag(Map<String, Object> map);
	List<BaseOrgEntity> queryByOrgName2(HashMap<String, Object> map);
	

	
	/**
	 * 批量插入组织架构
	 *
	 * @param orgList
	 * @author guoh.mao
	 */
	void batchInsertOrg(List<BaseOrgEntity> orgList);
	
	
	
	/**
	 * 通过组织架构GUID判断组织架构是否已经存在
	 *
	 * @param GUID
	 * @return
	 * @author guoh.mao
	 */
	boolean isExistByGUID(String GUID);
	
	
	/**
	 * 通过GUID进行更新
	 *
	 * @param baseOrgEntity
	 * @return
	 * @author guoh.mao
	 */
	int updateByGUID(BaseOrgEntity baseOrgEntity);
	
	
	/**
	 * 获得子节点
	 *
	 * @param upOrgCode
	 * @return
	 * @author guoh.mao
	 */
	List<BaseOrgEntity> getChildren(String upOrgCode);
	
}
