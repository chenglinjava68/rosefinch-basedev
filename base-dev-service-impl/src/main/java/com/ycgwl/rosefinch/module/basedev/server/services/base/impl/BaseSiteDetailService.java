package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteDetailService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteDetailEntity;

@Service
public class BaseSiteDetailService implements IBaseSiteDetailService {
	@Transactional
	public int deleteById(Long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<BaseSiteDetailEntity> get(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseSiteDetailEntity getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<BaseSiteDetailEntity> getPage(Map<String, Object> arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPageTotalCount(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteDetailEntity> getPagination(Map<String, Object> arg0, Page arg1, Sort... arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public int insert(BaseSiteDetailEntity arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	public int update(BaseSiteDetailEntity arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	public int updateStatusById(Long arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
