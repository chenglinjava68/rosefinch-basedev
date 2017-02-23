package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseAppInfoService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;



/**
* @Title: 应用系统信息缓存
* @Description: 根据appKey应用系统KEY应用系统信息缓存
* @Company: 远成快运
* @author zb
* @date 16.7.20 10:40
*/
public class BaseAppInfoEntityCache extends CacheSupport<BaseAppInfoEntity> {
    //注解加入
	@Autowired
	private IBaseAppInfoService baseAppInfoService;


	@Override
	public BaseAppInfoEntity doGet(String appKey) throws BusinessException {
		if(StringUtils.isEmpty(appKey)){
			return null;
		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("appKey", appKey);
//		List<BaseAppInfoEntity> list = baseAppInfoService.get(params);
//		if(!CollectionUtils.isEmpty(list)){
//			return list.get(0);
//		}else{
//			return null;
//		}
		return baseAppInfoService.getByAppKey(appKey);
	}


	@Override
	public String getCacheId() {
		return BaseCacheConstant.APP_INFO_CATCHE_UUID;
	}
}
