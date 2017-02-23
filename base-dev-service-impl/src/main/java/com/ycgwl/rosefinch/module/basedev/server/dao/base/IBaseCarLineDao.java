package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCarLineEntity;

public interface IBaseCarLineDao extends IBaseDao<BaseCarLineEntity,Long> {


	int uniqueCheckByLineCode(Map<String, Object> map);

	void removeBaseCarLine(Long id)throws BusinessException;

	void removeBaseCarLineByIdList(List<Long> idList);

	int uniqueCheckBylineCode(String lineCode);

	void updateBlFlagByMap(Map<String, Object> paramMap);


}
