package com.ycgwl.rosefinch.module.basedev.server.dao.base;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteFinLineEntity;

public interface IBaseSiteFinLineDao extends IBaseDao<BaseSiteFinLineEntity, Long> {
	void deleteBySiteCode(String siteCode);
	
	/**
	 * 缓存查询当前有效的网点资金路由
	 * @param map
	 * @return List<BaseSiteFinLineEntity>
	 * @authon zb
	 */
	List<BaseSiteFinLineEntity> searchCacheBaseSiteFinLine(Map<String,Object> map);
}
