package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseProductService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;

/**
* @Title: 通过区域名称获取区域编码缓存
* @Description: 
* @Company: 远成快运 
* @author guoh.mao
* @date 2016年8月23日 下午7:12:22
 */
public class GetProductCodeByProductNameCache extends CacheSupport<String> {
	//注解加入
	@Autowired
	private IBaseProductService baseProductService;
	
	@Override
	public String doGet(String productName) throws BusinessException {
		if(StringUtils.isEmpty(productName)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productName", productName);
		params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);
		List<BaseProductEntity> list = baseProductService.get(params);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0).getProductCode();
		}else{
			return null;
		}
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.GET_PRODUCT_CODE_BY_PRODUCT_NAME_CACHE_UUID;
	}
}
