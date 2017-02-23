package com.ycgwl.rosefinch.module.basedev.server.services.base;



import java.util.List;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseInterfaceInfoEntity;



public interface IBaseInterfaceService extends IBaseService<BaseInterfaceInfoEntity, Long>{



   /**
    * 查询全部的接口信息
    * @param interfaceName
    * @return
    */
	List<BaseInterfaceInfoEntity> queryAllInterfaceData();

	 /**
    * 查询全部的接口信息
    * @param interfaceName
    * @return
    */
	int deleteInterById(Long id,String appKey)throws BusinessException;

}
