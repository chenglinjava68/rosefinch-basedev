package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRegionService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;

/**
* @Title: 通过产品名称获取产品编码缓存
* @Description: 
* @Company: 远成快运 
* @author guoh.mao
* @date 2016年8月23日 下午7:12:22
 */
public class GetRegionCodeByRegionNameCache extends CacheSupport<String> {
	//注解加入
	@Autowired
	private IBaseRegionService baseRegionService;
	
	/**
	 * 传入参数regionName支持三种格式：
	 * 1）洪山区
	 * 2）湖北省-武汉市
	 * 3）湖北省-武汉市-洪山区
	 * 第一种格式：可以直接单独传入省、市、区（县）名称，区（县）名称在数据库中不唯一，因此尽量不要直接传入区（县）名称
	 * 第二、三种格式：开头部分必须为省，否则不能找到对应区域编码
	 * {@inheritDoc}
	 */
	@Override
	public String doGet(String regionName) throws BusinessException {
		if (StringUtils.isEmpty(regionName)) {
			return null;
		}

		List<BaseRegionEntity> list = new ArrayList<>();

		if (regionName.contains(BaseOrgConstant.REGION_NAME_SEPARATOR)) {
			String[] regionArr = regionName
					.split(BaseOrgConstant.REGION_NAME_SEPARATOR);
			if (regionArr.length == 2) {
				list = baseRegionService.getCityByProvinceAndCity(regionArr[0],
						regionArr[1]);
			} else if (regionArr.length == 3) {
				list = baseRegionService
						.getDistrictByProvinceAndCityAndDistrict(regionArr[0],
								regionArr[1], regionArr[2]);
			}
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("regionName", regionName);
			params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);
			list = baseRegionService.get(params);
		}

		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0).getRegionCode();
		} else {
			return null;
		}
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.GET_REGION_CODE_BY_REGION_NAME_CACHE_UUID;
	}
}
