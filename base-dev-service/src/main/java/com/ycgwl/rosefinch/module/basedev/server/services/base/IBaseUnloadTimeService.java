package com.ycgwl.rosefinch.module.basedev.server.services.base;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseUnloadTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseUnloadTimeVo;

public interface IBaseUnloadTimeService extends IBaseService<BaseUnloadTimeEntity, Long>{

	BaseUnloadTimeEntity insertUnloadTime(BaseUnloadTimeVo baseUnloadTimeVo, String currentUserCode);

	BaseUnloadTimeEntity editUnloadTime(BaseUnloadTimeVo baseUnloadTimeVo, String currentUserCode);

	void deleteUnloadTime(Long id);

	int uniqueCheckByConfigCode(String configCode);

	int uniqueCheckByConfigName(Map<String, Object> map);

	void batchlogicalDeleteById(List<Long> list);


	List<BaseSiteEntity> getByConfigCodeAndSites(String configCode, List<BaseSiteEntity> baseSiteEntities);

	Map<String, Object> getOrgsByUnloadTime(String configCode);

    Set<String> getChildSites(String siteCode);

	void batchUpdateBlFlagById(Map<String, Object> paramMap);

}
