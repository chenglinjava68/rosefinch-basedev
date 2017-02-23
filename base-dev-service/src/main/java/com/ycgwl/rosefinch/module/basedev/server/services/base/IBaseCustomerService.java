package com.ycgwl.rosefinch.module.basedev.server.services.base;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCustomerEntity;

/**
 *
 * Title: Description:客户资料接口
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年10月25日 下午3:08:40
 *
 */
public interface IBaseCustomerService extends IBaseService<BaseCustomerEntity, Long> {

    // 同步客户资料用的接口
    void synCustomerData(String str) throws Exception;
}
