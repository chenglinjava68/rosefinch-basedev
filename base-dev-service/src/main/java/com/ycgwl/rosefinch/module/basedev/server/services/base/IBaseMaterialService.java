package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseMaterialEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseMaterialVo;

/**
 * @Title:物料管理服务接口
 * @Description:
 * @Company: 远成快运
 * @author HXL
 * @date 2016年11月9日 上午10:35:37
 */
public interface IBaseMaterialService extends IBaseService<BaseMaterialEntity, Long>{
	public List<BaseMaterialVo> getMaterialNameByInsert(String goodsName)throws BusinessException;
	public List<BaseMaterialVo> getMaterialCodeByInsert(String goodsCode)throws BusinessException;
	public BaseMaterialVo preUpdateGoods(Long id)throws BusinessException;
	public int deletegoods(Long id);
	public int batchDeleteByIds(Long[] ids);


}
