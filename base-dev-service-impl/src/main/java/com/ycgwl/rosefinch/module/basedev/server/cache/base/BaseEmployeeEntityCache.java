package com.ycgwl.rosefinch.module.basedev.server.cache.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheSupport;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseEmployeeService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;

/**
 * @Title: 配置信息信息缓存
 * @Description: 根据配置编号获取配置缓存
 * @Company: 远成快运
 * @author zb
 * @date 16.7.20 10:40
 */
public class BaseEmployeeEntityCache extends CacheSupport<BaseEmployeeEntity> {
    // 注解加入
    @Autowired
    private IBaseEmployeeService baseEmployeeService;

    @Override
    public BaseEmployeeEntity doGet(String employeeName) throws BusinessException {
        if (StringUtils.isEmpty(employeeName)) {
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Name", employeeName);
        params.put(BaseOrgConstant.DEL_FLAG, BaseOrgConstant.ZERO);
        List<BaseEmployeeEntity> list = baseEmployeeService.get(params);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public String getCacheId() {
        return BaseCacheConstant.BASE_CONFIG_CATCHE_UUID;
    }
}
