package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.GetPackageTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.GetPackageTimeVo;

/**
*
* @Title:T_GET_PACKAGE_TIME表的Service接口
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月1日  下午4:47
*
*/
public interface IGetPackageTimeService extends IBaseService<GetPackageTimeEntity, Long>{

    GetPackageTimeEntity editPackageTime(GetPackageTimeVo getPackageTimeVo,
            String currentUserCode);

    GetPackageTimeEntity insertPackageTime(GetPackageTimeVo packageTimeVo,
            String currentUserCode);

    int uniqueCheckByConfigName(Map<String, Object> map);

    int uniqueCheckByConfigCode(String configCode);

    void deleteGetPackage(Long configId);

    void batchlogicalDeleteById(List<Long> list);

    void batchUpdateBlFlagById(Map<String, Object> paramMap);

    List<BaseSiteEntity> getByConfigCodeAndSites(String code,
            List<BaseSiteEntity> baseSiteEntities);

    Map<String, Object> getSitesByPackageTime(String configCode);

    Set<String> getChildSites(String siteCode);
}
