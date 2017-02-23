package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteFinLineDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteFinLineService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteFinLineEntity;

@Service
public class BaseSiteFinLineService implements IBaseSiteFinLineService {
	
	@Autowired
	private IBaseSiteFinLineDao baseSiteFinLineDao;

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int deleteById(Long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<BaseSiteFinLineEntity> get(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseSiteFinLineEntity getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<BaseSiteFinLineEntity> getPage(Map<String, Object> arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public int getPageTotalCount(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Pagination<BaseSiteFinLineEntity> getPagination(Map<String, Object> arg0, Page arg1, Sort... arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int insert(BaseSiteFinLineEntity arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int update(BaseSiteFinLineEntity arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int updateStatusById(Long arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void deleteBySiteCode(String siteCode) {
		baseSiteFinLineDao.deleteBySiteCode(siteCode);
	}
	
	/**
	 * 缓存查询当前有效的网点资金路由
	 * @param map
	 * @return List<BaseSiteFinLineEntity>
	 * @authon zb
	 */
	public List<BaseSiteFinLineEntity> searchCacheBaseSiteFinLine(Map<String,Object> map){
		return baseSiteFinLineDao.searchCacheBaseSiteFinLine(map);
	};
	
}
