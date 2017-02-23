package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCarLineDetail;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCarLineEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseCarLineVo;

public interface IBaseCarLineService extends IBaseService<BaseCarLineEntity, Long>{


	BaseCarLineEntity insertCarLine(BaseCarLineVo baseCarLineVo, String currentUserName);

	int uniqueCheckByLineName(Long id, String lineName);

	void removeBaseCarLine(Long id);

	void removeBaseCarLineByIdList(List<Long> idList);


	List<BaseCarLineDetail> findBaseCarLineDetailListByLineId(String lineCode);

	BaseCarLineEntity modifyBaseCarLine(BaseCarLineVo baseCarLineVo, String currentUserName);

	int uniqueCheckBylineCode(String lineCode);

	void modifyBlFlagByIdList(Map<String, Object> paramMap);

}
