package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteFinLineEntity;

/**
 * 
 * @author guoh.mao
 *
 */
public interface IBaseSiteFinLineService extends IBaseService<BaseSiteFinLineEntity, Long>{
	/**
	 * 删除某网点资金路由
	 *
	 * @param siteCode
	 * @author guoh.mao
	 */
	void deleteBySiteCode(String siteCode);
	
	/**
	 * 缓存查询当前有效的网点资金路由
	 * @param map
	 * @return List<BaseSiteFinLineEntity>
	 * @authon zb
	 */
	List<BaseSiteFinLineEntity> searchCacheBaseSiteFinLine(Map<String,Object> map);
}
