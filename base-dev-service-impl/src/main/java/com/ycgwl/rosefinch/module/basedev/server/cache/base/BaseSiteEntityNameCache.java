package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;



/**
* @Title: 网点缓存
* @Description: 根据网点简称（siteNameShort）获取网点缓存
* @Company: 远成快运
* @author zb
* @date 16.7.20 10:40
*/
public class BaseSiteEntityNameCache extends CacheSupport<BaseSiteEntity> {
    //注解加入
	@Autowired
	private IBaseSiteService baseSiteService;


	@Override
	public BaseSiteEntity doGet(String siteNameShort) throws BusinessException {
		if(StringUtils.isEmpty(siteNameShort)){
			return null;
		}

		return  baseSiteService.getBySiteNameShort(siteNameShort);

	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.SITE_INFO_NAME_CATCHE_UUID;
	}
}
