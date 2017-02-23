package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteFinLineService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteFinLineEntity;



/**
* @Title: 资金路由缓存(条件是siteCode-lineType)
* @Description: 根据网点编号获取网点资金路由缓存
* @Company: 远成快运 
* @author zb
* @date 16.9.20 10:40
*/
public class SiteFinLineCodeAndLineTypeCache extends CacheSupport<BaseSiteFinLineEntity> {
    //注解加入
	@Autowired
	private IBaseSiteFinLineService baseSiteFinLineService;
	
	
	@Override
	public BaseSiteFinLineEntity doGet(String key) throws BusinessException {
		List<BaseSiteFinLineEntity> list = new ArrayList<BaseSiteFinLineEntity>();
		if(StringUtils.isEmpty(key)){
			return null;
		}
		String[] param = key.split(BaseOrgConstant.SPLIT_SEPARATOR);
		if(param.length!=2){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("siteCode", param[0]);
		params.put("lineType", param[1]);
		//params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);在查询方法中已经加上了
		try{
			list = baseSiteFinLineService.searchCacheBaseSiteFinLine(params);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);//获取第一个
		
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID_CODE_LEVETYPE;
	}
}
