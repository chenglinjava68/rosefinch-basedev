package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAreaEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseAreaVo;

public interface IBaseAreaDao extends IBaseDao<BaseAreaEntity, Long>{

	int deleteAreaById(Long areaId);

	List<BaseAreaVo> getAreaCodeByInsert(String areaCode);

	List<BaseAreaVo> getAreaNameByInsert(String areaName);

	int stopAndstartAreaById(Map<String, Object> params);

	int batchDeleteById(List<Long> list);

}
