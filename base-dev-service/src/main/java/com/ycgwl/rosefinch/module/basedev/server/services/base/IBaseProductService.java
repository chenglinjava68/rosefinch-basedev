package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Pagination;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseProductVo;

/**
 *
 * @author guoh.mao
 *
 */
public interface IBaseProductService extends IBaseService<BaseProductEntity, Long>{
	/**
	 * 分页查询产品
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseProductEntity> getPage(QueryPageVo queryPageVo);

	/**
	 *
	 * 产品编号唯一校验
	 *
	 * @param productCode
	 * @return
	 * @author guoh.mao
	 */
	int uniqueCheckByProductCode(String productCode);

	/**
	 * 产品名称唯一校验
	 *
	 * @param hashmap
	 * @return
	 * @author guoh.mao
	 */
	int uniqueCheckByProductName(Map<String, Object> hashmap);


	/**
	 * 保存产品
	 *
	 * @param baseProductEntity
	 * @return
	 * @author guoh.mao
	 */
	BaseProductEntity insertBaseProduct(BaseProductVo baseProductVo, String currentUserName);

	/**
	 * 修改产品
	 *
	 * @param baseProductVo
	 * @return
	 * @author guoh.mao
	 * @param currentUserName
	 */
	BaseProductEntity updateBaseProduct(BaseProductVo baseProductVo, String currentUserName);

	/**
	 * 构造包含完整属性的BaseProductEntity（包括非数据库字段）
	 *
	 * @param baseProductEntity
	 * @return
	 * @author guoh.mao
	 */
	BaseProductEntity constructFullBaseProductEntity(BaseProductEntity baseProductEntity);

	/**
	 * 通过ID逻辑删除
	 *
	 * @param id
	 * @author guoh.mao
	 */
	void logicalDeleteById(Long id);


	/**
	 * 获得产品列表（产品公共选择器）
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseProductEntity> getPaginationBaseProductList(QueryPageVo queryPageVo);

	void modifyBlFlagByIdList(List<Long> idList, Integer blFlag);
}


