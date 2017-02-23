package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseUnloadTimeEntity;

public interface IBaseUnloadTimeDao extends IBaseDao<BaseUnloadTimeEntity,Long>{

	void logicalDeleteById(Long id)throws BusinessException;

	int uniqueCheckByConfigCode(String configCode);

	int uniqueCheckByConfigName(Map<String, Object> map);

	void batchlogicalDeleteById(List<Long> list);

	void batchUpdateBlFlagById(Map<String, Object> paramMap);

}
