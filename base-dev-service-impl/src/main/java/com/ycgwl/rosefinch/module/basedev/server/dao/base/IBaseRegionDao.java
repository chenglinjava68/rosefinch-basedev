package com.ycgwl.rosefinch.module.basedev.server.dao.base;


import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRegionListVo;

public interface IBaseRegionDao extends IBaseDao<BaseRegionEntity, Long> {
	List<BaseRegionEntity> getProvinceList();
	List<BaseRegionEntity> getSubRegionListByRegionParent(String regionParent);
	BaseRegionEntity getByRegionCode(String regionCode);

	List<BaseRegionListVo> getHotCityList();
	List<BaseRegionListVo> getCityVoListByPingyingAndHanzi(String name);
	Pagination<BaseRegionListVo> getVoPagination(Map<String, Object> paraMap, Page page, Sort[] sorts);

	List<BaseRegionEntity> getRegionListCascade(String regionCode);

	List<BaseRegionEntity> getCityByProvinceAndCity(Map<String, Object> map);
	List<BaseRegionEntity> getDistrictByProvinceAndCityAndDistrict(Map<String, Object> map);
	List<String> getAllSeq(String regionName);
	List<BaseRegionEntity> getBaseRegionEntity(Map<String, Object> paramMap);
}
