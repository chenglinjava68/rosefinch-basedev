package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.cache.base.ICache;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.context.UserContext;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseProductDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseProductService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseProductVo;

@Service
public class BaseProductService implements IBaseProductService {
	@Autowired
	private IBaseProductDao baseProductDao;

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public int insert(BaseProductEntity t) {
		return baseProductDao.insert(t);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public int update(BaseProductEntity t) {
		return baseProductDao.update(t);
	}

	@Override
	public BaseProductEntity getById(Long id) {
		return baseProductDao.getById(id);
	}

	@Override
	public List<BaseProductEntity> get(Map<String, Object> params) {
		return baseProductDao.get(params);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public List<BaseProductEntity> getPage(Map<String, Object> params,
			int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public int getPageTotalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public int deleteById(Long id) {
		return baseProductDao.deleteById(id);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public int updateStatusById(Long id, int status) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseProductEntity> getPagination(
			Map<String, Object> params, Page page, Sort... sorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseProductEntity> getPage(QueryPageVo queryPageVo) {
		Map<String, Object> map = queryPageVo.getParaMap();
		// delFlag=0
		map.put("delFlag", 0);

		Pagination<BaseProductEntity> pageInfo  = baseProductDao.getPagination(map,queryPageVo.getPage(),queryPageVo.getSorts());

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseProductEntity>();
			pageInfo.setCount(0);
		}

		return pageInfo;
	}

	@Override
	public int uniqueCheckByProductCode(String productCode) {
		return baseProductDao.uniqueCheckByProductCode(productCode);
	}

	@Override
	public int uniqueCheckByProductName(Map<String, Object> hashmap) {
		return baseProductDao.uniqueCheckByProductName(hashmap);
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public BaseProductEntity insertBaseProduct(BaseProductVo baseProductVo, String currentUserName) {
		BaseProductEntity baseProductEntity = new BaseProductEntity();
		if (null != baseProductVo) {

			BeanUtils.copyProperties(baseProductVo, baseProductEntity);
			baseProductEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
			baseProductEntity.setDelFlag(0);
			Date date = new Date();
			baseProductEntity.setCreateTime(date);
			baseProductEntity.setCreateUserCode(currentUserName);
			baseProductEntity.setModifyTime(date);
			baseProductEntity.setModifyUserCode(currentUserName);

			baseProductDao.insert(baseProductEntity);
		}

		return baseProductEntity;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public BaseProductEntity updateBaseProduct(BaseProductVo baseProductVo,String currentUserCode) {
		BaseProductEntity oldBaseProductEntity = baseProductDao.getById(baseProductVo.getHiddenId());
		BeanUtils.copyProperties(baseProductVo, oldBaseProductEntity);
		oldBaseProductEntity.setModifyTime(new Date());
		oldBaseProductEntity.setModifyUserCode(currentUserCode);
		baseProductDao.update(oldBaseProductEntity);

		// 使缓存失效
		invalidCache(oldBaseProductEntity.getProductCode());

		// 产品名称有修改，使缓存失效
		if (oldBaseProductEntity.getProductName().equals(baseProductVo.getProductName())) {
			invalidGetProductCodeByProductNameCache(oldBaseProductEntity.getProductName());
		}

		return oldBaseProductEntity;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public BaseProductEntity constructFullBaseProductEntity(BaseProductEntity baseProductEntity){
		ICache<String, String> cache = getDictDataValueNameCache();
		String regionMatchModeName = cache.get(BaseOrgConstant.TYPE_ALIAS_REGION_MATCH_MODE+BaseOrgConstant.SPLIT_SEPARATOR+baseProductEntity.getRegionMatchMode());
		String statusName = cache.get(BaseOrgConstant.TYPE_ALIAS_BASE_PRODUCT_STATUS+BaseOrgConstant.SPLIT_SEPARATOR+baseProductEntity.getStatus());
		String productLevelName = cache.get(BaseOrgConstant.TYPE_ALIAS_REGION_PRODUCT_LEVEL+BaseOrgConstant.SPLIT_SEPARATOR+baseProductEntity.getProductLevel());
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("productCode", baseProductEntity.getUpProductCode());
		List<BaseProductEntity> list = baseProductDao.get(paramMap);

		if (!CollectionUtils.isEmpty(list)) {
			baseProductEntity.setUpProductName(list.get(0).getProductName());
		}

		baseProductEntity.setRegionMatchModeName(regionMatchModeName);
		baseProductEntity.setStatusName(statusName);
		baseProductEntity.setProductLevelName(productLevelName);
		return baseProductEntity;
	}

	private ICache<String, String> getDictDataValueNameCache() {
		ICache<String, String> cache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
		return cache;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void logicalDeleteById(Long id) {
		baseProductDao.logicalDeleteById(id);
		BaseProductEntity baseProductEntity = getById(id);

		// 使缓存失效
		invalidCache(baseProductEntity.getProductCode());

		// 使缓存失效
		invalidGetProductCodeByProductNameCache(baseProductEntity.getProductName());
	}


	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseProductEntity> getPaginationBaseProductList(
			QueryPageVo queryPageVo) {

		Sort[] sorts = new Sort[1];
		Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
		sorts[0] = sort;
		queryPageVo.setSorts(sorts);

		Map<String, Object> map = queryPageVo.getParaMap();
		Object object = map.get("productLevelStr");
		if (null != object) {
			String productLevels = object.toString();

			if (StringUtils.isNotBlank(productLevels)) {
				String[] productLevelArr = productLevels.split(",");
				map.put("pls", productLevelArr);
			}
		}

		Pagination<BaseProductEntity> pageInfo = baseProductDao.getPaginationBaseProductList(map, queryPageVo.getPage(), sorts);

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseProductEntity>();
			pageInfo.setCount(0);
		}

		List<BaseProductEntity> list = pageInfo.getList();
		List<BaseProductEntity> resultList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(list)) {
			for (BaseProductEntity baseProductEntity : list) {
				BaseProductEntity productEntity = this.constructFullBaseProductEntity(baseProductEntity);
				resultList.add(productEntity);
			}

			// set list
			pageInfo.setList(resultList);
		}

		return pageInfo;
	}


	/**
	 * 使缓存失效
	 *
	 * @param productCode 产品编码
	 * @author guoh.mao
	 */
	private void invalidCache(String productCode) {
		ICache<String, BaseProductEntity> productCache = CacheManager.getInstance().getCache(BaseCacheConstant.PRODUCT_CATCHE_UUID);
		productCache.invalid(productCode);
	}

	private void invalidGetProductCodeByProductNameCache(String productName) {
		ICache<String, String> cache = CacheManager.getInstance().getCache(BaseCacheConstant.GET_PRODUCT_CODE_BY_PRODUCT_NAME_CACHE_UUID);
		cache.invalid(productName);
	}
	 /**
     * 批量修改路由状态：停用、启用
     * @author
     * @param list要修改的id集合， blFlag操作：启用、禁用
     */
    @Transactional
	@Override
	public void modifyBlFlagByIdList(List<Long> list, Integer blFlag) {
		  Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("blFlag", blFlag);
	        paramMap.put("list", list);
	        paramMap.put("modifyTime", new Date());
	        paramMap.put("modifyUserCode", UserContext.getCurrentUser().getUserName());
	        baseProductDao.updateProductBlFlagByMap(paramMap);

	}

}
