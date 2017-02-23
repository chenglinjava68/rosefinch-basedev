package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseAreaService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAreaEntity;



/**
* @Title: 配置信息信息缓存
* @Description: 根据配置编号获取配置缓存
* @Company: 远成快运
* @author zb
* @date 16.7.20 10:40
*/
public class BaseAreaEntityCache extends CacheSupport<BaseAreaEntity> {
    //注解加入
	@Autowired
	private IBaseAreaService baseAreaService;


	@Override
	public BaseAreaEntity doGet(String areaCode) throws BusinessException {
		if(StringUtils.isEmpty(areaCode)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("areaCode", areaCode);
		params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);
		List<BaseAreaEntity> list = baseAreaService.get(params);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}

	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.BASE_AREA_CATCHE_UUID;
	}
}
