package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseOrgService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;



/**
* @Title: 网点缓存
* @Description: 根据网点编号（siteCode）获取网点缓存
* @Company: 远成快运 
* @author zb
* @date 16.7.20 10:40
*/
public class BaseOrgEntityCache extends CacheSupport<BaseOrgEntity> {
    //注解加入
	@Autowired
	private IBaseOrgService baseOrgService;
	
	
	@Override
	public BaseOrgEntity doGet(String orgCode) throws BusinessException {
		if(StringUtils.isEmpty(orgCode)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgCode", orgCode);
		params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);
		List<BaseOrgEntity> list = baseOrgService.get(params);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.ORG_INFO_CATCHE_UUID;
	}
}
