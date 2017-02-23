package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRegionService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;

/**
* @Title: 
* @Description: 
* @Company: 远成快运 
* @author guoh.mao
* @date 2016年8月26日 上午11:08:35
 */
public class BaseRegionListCascadeCache extends CacheSupport<List<BaseRegionEntity>> {
	@Autowired
	private IBaseRegionService baseRegionService;
	
	@Override
	public List<BaseRegionEntity> doGet(String regionCode) throws BusinessException {
		if(StringUtils.isEmpty(regionCode)){
			return null;
		}
		return baseRegionService.getRegionListCascade(regionCode);
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.REGION_LIST_CASCADE_CATCHE_UUID;
	}
}
