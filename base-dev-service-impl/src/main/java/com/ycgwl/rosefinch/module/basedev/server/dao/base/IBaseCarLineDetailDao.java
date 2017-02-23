package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCarLineDetail;

public interface IBaseCarLineDetailDao extends IBaseDao<BaseCarLineDetail, Long> {

	void deleteAllByCarLineId(String string);

	List<BaseCarLineDetail> queryBaseCarLineDetailListByLineId(String lineCode);
}
