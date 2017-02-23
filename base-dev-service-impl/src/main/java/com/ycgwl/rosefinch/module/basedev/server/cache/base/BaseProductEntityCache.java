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
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCommonConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;



/**
* @Title:产品缓存
* @Description:根据产品code产品缓存
* @Company: 远成快运 
* @author zb
* @date 16.7.20 10:40
*/
public class BaseProductEntityCache extends CacheSupport<BaseProductEntity> {
    //注解加入
	@Autowired
	private IBaseProductService baseProductService;
	
	
	@Override
	public BaseProductEntity doGet(String productCode) throws BusinessException {
		if(StringUtils.isEmpty(productCode)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productCode", productCode);
		params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);   //没有逻辑删除的
		params.put(BaseCommonConstant.BL_FLAG, BaseCommonConstant.INT_ONE);  // 已启用
		
		params.put("status",1);//状态正常
		List<BaseProductEntity> list = baseProductService.get(params);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public String getCacheId() {
		return BaseCacheConstant.PRODUCT_CATCHE_UUID;
	}
}
