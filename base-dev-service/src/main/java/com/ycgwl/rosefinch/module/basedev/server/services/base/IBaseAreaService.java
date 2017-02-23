package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAreaEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseAreaVo;

public interface IBaseAreaService extends IBaseService<BaseAreaEntity, Long>{

	int insertArea(BaseAreaVo baseAreaVo , String currentUser)throws BusinessException;

	int updateArea(BaseAreaVo baseAreaVo, String currentUser)throws BusinessException;

	int deleteAreaById(Long areaId);

	List<BaseAreaVo> getAreaCodeByInsert(String areaCode);

	List<BaseAreaVo> getAreaNameByInsert(String areaName);

	int stopAndstartAreaById(Long[] areaId, String typeValue,String currentUser);

	int batchDeleteById(Long[] areaId);

}
