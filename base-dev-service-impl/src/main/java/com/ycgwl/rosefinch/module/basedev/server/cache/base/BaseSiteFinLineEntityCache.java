package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteFinLineService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteFinLineEntity;



/**
* @Title: 资金路由缓存
* @Description: 根据网点编号获取网点资金路由缓存
* @Company: 远成快运 
* @author zb
* @date 16.7.20 10:40
*/
public class BaseSiteFinLineEntityCache extends CacheSupport<List<BaseSiteFinLineEntity>> {
    //注解加入
	@Autowired
	private IBaseSiteFinLineService baseSiteFinLineService;
	
	
	@Override
	public List<BaseSiteFinLineEntity> doGet(String siteCode) throws BusinessException {
		List<BaseSiteFinLineEntity> list = new ArrayList<BaseSiteFinLineEntity>();
		if(StringUtils.isEmpty(siteCode)){
			return list;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("siteCode", siteCode);
		//params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);在查询方法中已经加上了
		list = baseSiteFinLineService.searchCacheBaseSiteFinLine(params);
		return list;
		
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID;
	}
}
