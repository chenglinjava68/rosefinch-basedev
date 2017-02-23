package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseInterfaceService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseInterfaceInfoEntity;



/**
* @Title: 接口信息缓存
* @Description: 根据接口编号应用系统信息缓存
* @Company: 远成快运
* @author zb
* @date 16.7.20 10:40
*/
public class BaseInterfaceEntityCache extends CacheSupport<BaseInterfaceInfoEntity> {
    //注解加入
	@Autowired
	private IBaseInterfaceService baseInterfaceService;


	@Override
	public BaseInterfaceInfoEntity doGet(String interfaceCodeAndAppKey) throws BusinessException {
		if(StringUtils.isEmpty(interfaceCodeAndAppKey)){
			return null;
		}
		String[] str = interfaceCodeAndAppKey.split(BaseOrgConstant.SPLIT_SEPARATOR);
		Map<String, Object> params = new HashMap<String, Object>();
		if(str.length>1){
			params.put("appKey", str[0]);
			params.put("interfaceCode", str[1]);
			List<BaseInterfaceInfoEntity> list = baseInterfaceService.get(params);
			if(!CollectionUtils.isEmpty(list)){
				return list.get(0);
			}else{
				return null;
			}

		}else{
			throw new BusinessException("没有足够参数");
		}


	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.INTERFACE_CATCHE_UUID;
	}
}
