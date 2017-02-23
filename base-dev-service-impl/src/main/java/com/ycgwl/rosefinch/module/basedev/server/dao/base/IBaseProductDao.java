package com.ycgwl.rosefinch.module.basedev.server.dao.base;


import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;

public interface IBaseProductDao extends IBaseDao<BaseProductEntity, Long> {
	int uniqueCheckByProductCode(String productCode);
	int uniqueCheckByProductName(Map<String, Object> hashmap);
	void logicalDeleteById(Long id);

	Pagination<BaseProductEntity> getPaginationBaseProductList(Map<String, Object> paraMap, Page page, Sort[] sorts);
	void updateProductBlFlagByMap(Map<String, Object> paramMap);
	void deleteAllByProductId(Long id);
}
