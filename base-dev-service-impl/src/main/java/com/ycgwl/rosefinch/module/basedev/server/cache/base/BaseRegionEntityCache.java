package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRegionService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;



/**
* @Title: 行政区域缓存
* @Description:根据行政区域编号（regionCode）获取行政区域缓存
* @Company: 远成快运 
* @author zb
* @date 16.7.20 10:40
*/
public class BaseRegionEntityCache extends CacheSupport<BaseRegionEntity> {
    //注解加入
	@Autowired
	private IBaseRegionService baseRegionService;
	
	
	@Override
	public BaseRegionEntity doGet(String regionCode) throws BusinessException {
		if(StringUtils.isEmpty(regionCode)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regionCode", regionCode);
		params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);
		List<BaseRegionEntity> list = baseRegionService.get(params);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.REGION_CATCHE_UUID;
	}
}
