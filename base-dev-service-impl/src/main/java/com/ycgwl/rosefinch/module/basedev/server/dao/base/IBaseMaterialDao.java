package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseMaterialEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseMaterialVo;

public interface IBaseMaterialDao extends IBaseDao<BaseMaterialEntity, Long>{

	 List<BaseMaterialVo> getMaterialNameByInsert(String goodsName);

	List<BaseMaterialVo> getMaterialCodeByInsert(String goodsCode);

	BaseMaterialVo preUpdateGoods(Long id);

	int deletegoods(Long id);

	int batchDeleteByIds(List<Long> list);


}
