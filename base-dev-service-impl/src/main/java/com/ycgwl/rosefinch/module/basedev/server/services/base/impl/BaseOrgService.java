package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.cache.base.ICache;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseOrgDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseEmployeeService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseOrgService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCommonConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseOrgVo;

@Service
public class BaseOrgService implements IBaseOrgService {
	@Autowired
	private IBaseOrgDao baseOrgEntityDao;
	@Autowired
	private IBaseEmployeeService baseEmployeeService;
	@Autowired
	private IBaseSiteDao baseSiteDao;
	
	private static final String BOS_SITE_NAME_YC_EXPRESS = "快运本部";
	private static final String BOS_BIG_AREA_SUFFIX = "区域公司";
	private static final String AD_COMPANY_SUFFIX = "快运";
	private static final String BOS_COMPANY_SUFFIX = "分公司";
	
	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int deleteById(Long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<BaseOrgEntity> get(Map<String, Object> arg0) {
		return baseOrgEntityDao.get(arg0);
	}

	public BaseOrgEntity getById(Long arg0) {
		return baseOrgEntityDao.getById(arg0);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public List<BaseOrgEntity> getPage(Map<String, Object> arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public int getPageTotalCount(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseOrgEntity> getPagination(Map<String, Object> arg0, Page arg1, Sort... arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int insert(BaseOrgEntity arg0) {
		return baseOrgEntityDao.insert(arg0);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int update(BaseOrgEntity arg0) {
		return baseOrgEntityDao.update(arg0);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int updateStatusById(Long arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public List<BaseOrgEntity> queryByOrgName(String orgName) {
		return baseOrgEntityDao.queryByOrgName("%"+orgName+"%");
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseOrgEntity> getPage(QueryPageVo queryPageVo) {
		Map<String, Object> map = queryPageVo.getParaMap();
		// 没有删除的
		map.put("delFlag", BaseCommonConstant.INT_ZERO);
		Pagination<BaseOrgEntity> pageInfo  = baseOrgEntityDao.getPagination(map,queryPageVo.getPage());
		
		if (null == pageInfo) {
			pageInfo = new Pagination<BaseOrgEntity>();
			pageInfo.setCount(0);
		}
		
		return pageInfo;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public BaseOrgEntity insertBaseOrgEntity(BaseOrgVo baseOrgVo, String currentUserName){
		BaseOrgEntity baseOrgEntity = new BaseOrgEntity();
		BeanUtils.copyProperties(baseOrgVo, baseOrgEntity);
		baseOrgEntity.setOrgId(BasicEntityIdentityGenerator.getInstance().generateId());
		
		baseOrgEntity.setBlFlag(BaseCommonConstant.INT_ONE);
		baseOrgEntity.setDelFlag(BaseCommonConstant.INT_ZERO);
		Date date = new Date();
		baseOrgEntity.setCreateTime(date);
		baseOrgEntity.setCreateUserCode(currentUserName);
		baseOrgEntity.setModifyTime(date);
		baseOrgEntity.setModifyUserCode(currentUserName);
		
		baseOrgEntity.setCountry(BaseOrgConstant.REGION_CODE_CHINA);
		
		
		// TODO，AD域中组织机构属性是没有行政区域信息的，暂时都设置为“TODO”
		if (StringUtils.isBlank(baseOrgVo.getProvince())) {
			baseOrgEntity.setProvince("TODO");
		}
		if (StringUtils.isBlank(baseOrgVo.getCity())) {
			baseOrgEntity.setCity("TODO");
		}
		if (StringUtils.isBlank(baseOrgVo.getCounty())) {
			baseOrgEntity.setCounty("TODO");
		}
		if (StringUtils.isBlank(baseOrgVo.getOrgAddress())) {
			baseOrgEntity.setOrgAddress("TODO");
		}
		
		
		this.insert(baseOrgEntity);
		
		return baseOrgEntity;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public void updateBlFlag(BaseOrgEntity baseOrgEntity) {
		baseOrgEntityDao.updateBlFlag(baseOrgEntity);
		// 使组织缓存失效
		invalidBaseOrgEntityCache(baseOrgEntity.getOrgCode());
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public BaseOrgEntity updateBaseOrgEntity(BaseOrgVo baseOrgVo, String currentUserName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCode", baseOrgVo.getOrgCode());
		List<BaseOrgEntity> list = this.get(paramMap);
		
		BaseOrgEntity baseOrgEntity = new BaseOrgEntity();
		
		if (!CollectionUtils.isEmpty(list)) {
			BaseOrgEntity oldBaseOrgEntity = list.get(0);
			BeanUtils.copyProperties(baseOrgVo, oldBaseOrgEntity);
			this.update(oldBaseOrgEntity);
			baseOrgEntity = oldBaseOrgEntity;
			
			// 使组织缓存失效
			invalidBaseOrgEntityCache(baseOrgVo.getOrgCode());
		}
		
		return baseOrgEntity;
	}
	

	/**
	 * 
	 * {@inheritDoc}
	 */
	public int uniqueCheckByOrgCode(String orgCode) {
		return baseOrgEntityDao.uniqueCheckByOrgCode(orgCode);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public int uniqueCheckByOrgName(Map<String, Object> map) {
		return baseOrgEntityDao.uniqueCheckByOrgName(map);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public int uniqueCheckByOrgNameShort(Map<String, Object> map) {
		return baseOrgEntityDao.uniqueCheckByOrgNameShort(map);
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	public BaseOrgEntity constructFullBaseOrgEntity(BaseOrgEntity baseOrgEntity) {
		BaseOrgEntity parent = getBaseOrgEntityCache().get(baseOrgEntity.getUpOrgCode());
		
		if (null != parent) {
			// 上级组织
			baseOrgEntity.setUpOrgName(parent.getOrgName());
		}else {   // 一级节点的上级数据中没有（固定的，都是“远成集团”）
			baseOrgEntity.setUpOrgCode(BaseOrgConstant.YC_GROUP_CODE);
			baseOrgEntity.setUpOrgName(BaseOrgConstant.YC_GROUP_NAME);
		}
		
		// 获取缓存
		ICache<String, String> cache = getDictDataValueNameCache();
		String orgTypeName = cache.get(BaseOrgConstant.TYPE_ALIAS_ORG_TYPE+BaseCommonConstant.COMMA+baseOrgEntity.getOrgType());
		baseOrgEntity.setOrgTypeName(orgTypeName);
		
		String defaultCurrencyName = cache.get(BaseOrgConstant.TYPE_ALIAS_DEFAULT_CURRENCY+BaseCommonConstant.COMMA+baseOrgEntity.getDefaultCurrency());
		baseOrgEntity.setDefaultCurrencyName(defaultCurrencyName);
		
		ICache<String, BaseRegionEntity> regionCache = getBaseRegionEntityCache();
		
		// 省、市、区（县）
		BaseRegionEntity province = regionCache.get(baseOrgEntity.getProvince());
		
		if (null != province) {
			baseOrgEntity.setProvinceName(province.getRegionName());
		}
		
		// 市
		BaseRegionEntity city = regionCache.get(baseOrgEntity.getCity());
		if (null != city) {
			baseOrgEntity.setCityName(city.getRegionName());
		}
		
		// 区（县）
		BaseRegionEntity district = regionCache.get(baseOrgEntity.getCounty());
		if (null != district) {
			baseOrgEntity.setCountyName(district.getRegionName());
		}
		
		
		return baseOrgEntity;
	}
	
	
	private ICache<String, String> getDictDataValueNameCache() {
		ICache<String, String> cache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
		return cache;
	}
	
	private ICache<String, BaseRegionEntity> getBaseRegionEntityCache() {
		ICache<String, BaseRegionEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_CATCHE_UUID);
		return cache;
	}
	
	
	private void invalidBaseOrgEntityCache(String orgCode){
		ICache<String, BaseOrgEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
		cache.invalid(orgCode);
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public int batchUpdateBlFlag(Integer blFlag, List<String> codes) {
		Map<String, Object> map = new HashMap<>();
		map.put("blFlag", blFlag);
		map.put("codeList", codes);
		int result = baseOrgEntityDao.batchUpdateBlFlag(map);
		
		for (String code : codes) {
			// 使缓存失效
			invalidBaseOrgEntityCache(code);
		}
		return result;
	}
	
	private ICache<String, BaseOrgEntity> getBaseOrgEntityCache() {
		ICache<String, BaseOrgEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
		return cache;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public void batchInsertOrUpdateOneLevelOuAndUser(List<BaseOrgEntity> orgList,
			List<BaseEmployeeEntity> empList) {
		if (CollectionUtils.isNotEmpty(orgList)) {
			String parentGuid = orgList.get(0).getUpOrgCode();
			// 父组织架构
			BaseOrgEntity parent = getParentOrgByGuid(parentGuid);
			
			this.batchInsertOrUpdateOrg(orgList, parent);
			
		}
		
		if (CollectionUtils.isNotEmpty(empList)) {
			String parentGuid = empList.get(0).getOrgCode();
			BaseOrgEntity parent = null;
			
			if (StringUtils.isNotBlank(parentGuid)) {
				// 父组织架构
				parent = getParentOrgByGuid(parentGuid);
			}
			
			baseEmployeeService.batchInsertOrUpdateEmployee(empList, parent);
		}
	}
	
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public void batchInsertOrUpdateOrg(List<BaseOrgEntity> orgList, BaseOrgEntity parent) {
		
		if (CollectionUtils.isNotEmpty(orgList)) {
			for (BaseOrgEntity baseOrgEntity : orgList) {
				// 不同步
				if (baseOrgEntity.getIgnore()) {
					return;
				}
				
				boolean isExist = baseOrgEntityDao.isExistByGUID(baseOrgEntity.getGuid());
				
				if (null != parent) {
					baseOrgEntity.setUpOrgCode(parent.getOrgCode());
				}
				
				if (isExist) {
					baseOrgEntityDao.updateByGUID(baseOrgEntity);
				} else {
					// set orgCode
					setOrgCode(baseOrgEntity);
					
					baseOrgEntityDao.insert(baseOrgEntity);
				}
			}
		}
	}
	
	
	private BaseOrgEntity getParentOrgByGuid(String parentGuid) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("guid", parentGuid);
		paramMap.put("delFlag", BaseCommonConstant.INT_ZERO);
		
		List<BaseOrgEntity> list = baseOrgEntityDao.get(paramMap);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
	private void setOrgCode(BaseOrgEntity baseOrgEntity){
		if (BaseOrgConstant.ORG_TYPE_YCEXPRESS.equals(Integer.valueOf(baseOrgEntity.getOrgType()))) {  // 快运本部
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("siteName", BOS_SITE_NAME_YC_EXPRESS);
//			paramMap.put("delFlag", BaseCommonConstant.INT_ZERO);
			
			List<BaseSiteEntity> list = baseSiteDao.get(paramMap);
			if (CollectionUtils.isNotEmpty(list)) {
				baseOrgEntity.setOrgCode(list.get(0).getSiteCode());
			}
		}else if (BaseOrgConstant.ORG_TYPE_BIG_AREA.equals(Integer.valueOf(baseOrgEntity.getOrgType()))) {  // 大区
			String baPrefix = baseOrgEntity.getOrgName().substring(2, 4);
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("siteName", baPrefix + BOS_BIG_AREA_SUFFIX);
//			paramMap.put("delFlag", BaseCommonConstant.INT_ZERO);
			
			List<BaseSiteEntity> list = baseSiteDao.get(paramMap);
			if (CollectionUtils.isNotEmpty(list)) {
				baseOrgEntity.setOrgCode(list.get(0).getSiteCode());
			}
		}else if (BaseOrgConstant.ORG_TYPE_COMPANY.equals(Integer.valueOf(baseOrgEntity.getOrgType()))) {   // 分公司
			int ind = baseOrgEntity.getOrgName().indexOf(AD_COMPANY_SUFFIX);
			String comPrefix = baseOrgEntity.getOrgName().substring(0, ind);
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("siteName", comPrefix + BOS_COMPANY_SUFFIX);
//			paramMap.put("delFlag", BaseCommonConstant.INT_ZERO);
			
			List<BaseSiteEntity> list = baseSiteDao.get(paramMap);
			if (CollectionUtils.isNotEmpty(list)) {
				baseOrgEntity.setOrgCode(list.get(0).getSiteCode());
			}
		}
	}
	

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void insertOrUpdateRootOu(BaseOrgEntity ycg) {
		boolean isExist = baseOrgEntityDao
				.isExistByGUID(ycg.getGuid());
		if (isExist) {
			baseOrgEntityDao.updateByGUID(ycg);
		} else {
			baseOrgEntityDao.insert(ycg);
		}
	}

	
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public List<BaseOrgEntity> getChildren(String upOrgCode) {
		return baseOrgEntityDao.getChildren(upOrgCode);
	}
	
}


