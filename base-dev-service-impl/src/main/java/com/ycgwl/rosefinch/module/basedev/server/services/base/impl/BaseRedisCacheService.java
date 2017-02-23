package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.cache.base.ICache;
import com.ycgwl.framework.shared.util.string.StringUtil;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.common.shared.entity.IUser;
import com.ycgwl.rosefinch.module.authorization.shared.domain.FunctionEntity;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseConfigEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseInterfaceInfoEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteFinLineEntity;
import com.ycgwl.rosefinch.module.dict.shared.domain.DictionaryDataEntity;
import com.ycgwl.rosefinch.module.dict.shared.domain.DictionaryEntity;
import com.ycgwl.rosefinch.module.organization.shared.domain.DepartmentEntity;
import com.ycgwl.rosefinch.module.organization.shared.domain.EmployeeEntity;

@Service("baseRedisCacheService")
public class BaseRedisCacheService implements IBaseRedisCacheService{
	/**
	 * 通过接口获取应用系统信息缓存数据
	 * @param appKey
	 * @return BaseAppInfoEntity
	 */
	@Override
	public BaseAppInfoEntity getBaseAppInfoEntityFromCache(String appKey){
		ICache<String, BaseAppInfoEntity> baseAppInfoCache = CacheManager.getInstance().getCache(BaseCacheConstant.APP_INFO_CATCHE_UUID);
		return baseAppInfoCache.get(appKey);
	}

	@Override
	public void refreshBaseAppInfoEntityFromCache(String appKey) {
		ICache<String, BaseAppInfoEntity> baseAppInfoCache = CacheManager.getInstance().getCache(BaseCacheConstant.APP_INFO_CATCHE_UUID);
		if(StringUtil.isEmpty(appKey)){
			baseAppInfoCache.invalid();
		}else{
			baseAppInfoCache.invalid(appKey);
		}
	}
	
	/**
	 * 通过接口获取配置信息缓存数据
	 * @param configCode
	 * @return BaseConfigEntity
	 */
	@Override
	public BaseConfigEntity getBaseConfigEntityFromCache(String configCode){
		ICache<String, BaseConfigEntity> baseConfigCache = CacheManager.getInstance().getCache(BaseCacheConstant.BASE_CONFIG_CATCHE_UUID);
		return baseConfigCache.get(configCode);
	}
	
	@Override
	public void refreshBaseConfigEntityFromCache(String configCode) {
		ICache<String, BaseConfigEntity> baseConfigCache = CacheManager.getInstance().getCache(BaseCacheConstant.BASE_CONFIG_CATCHE_UUID);
		if(StringUtil.isEmpty(configCode)){
			baseConfigCache.invalid();
		}else{
			baseConfigCache.invalid(configCode);
		}
	}

	/**
	 * 通过接口获取 接口信息缓存数据
	 * @param interfaceCode
	 * @return BaseInterfaceInfoEntity
	 */
	@Override
	public BaseInterfaceInfoEntity getBaseInterfaceInfoFromCache(String interfaceCode){
		ICache<String, BaseInterfaceInfoEntity> baseInterfaceInfoCache = CacheManager.getInstance().getCache(BaseCacheConstant.INTERFACE_CATCHE_UUID);
		return baseInterfaceInfoCache.get(interfaceCode);
	}
	
	@Override
	public void refreshBaseInterfaceInfoFromCache(String interfaceCode) {
		ICache<String, BaseInterfaceInfoEntity> baseInterfaceInfoCache = CacheManager.getInstance().getCache(BaseCacheConstant.INTERFACE_CATCHE_UUID);
		if(StringUtil.isEmpty(interfaceCode)){
			baseInterfaceInfoCache.invalid();
		}else{
			baseInterfaceInfoCache.invalid(interfaceCode);
		}
	}

	/**
	 * 通过接口获取网点缓存数据
	 * @param orgCode
	 * @return BaseOrgEntity
	 */
	@Override
	public BaseOrgEntity getBaseOrgFromCache(String orgCode){
		ICache<String, BaseOrgEntity> baseOrgCache = CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
		return baseOrgCache.get(orgCode);
	}
	
	@Override
	public void refreshBaseOrgFromCache(String orgCode) {
		ICache<String, BaseOrgEntity> baseOrgCache = CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
		if(StringUtil.isEmpty(orgCode)){
			baseOrgCache.invalid();
		}else{
			baseOrgCache.invalid(orgCode);
		}
	}

	/**
	 * 通过接口获取产品缓存数据(根据产品编号返回产品实体)
	 * @param productCode
	 * @return BaseProductEntity
	 */
	@Override
	public BaseProductEntity getBaseProductFromCache(String productCode){
		ICache<String, BaseProductEntity> baseProductCache = CacheManager.getInstance().getCache(BaseCacheConstant.PRODUCT_CATCHE_UUID);
		return baseProductCache.get(productCode);
	}

	@Override
	public void refreshBaseProductFromCache(String productCode) {
		ICache<String, BaseProductEntity> baseProductCache = CacheManager.getInstance().getCache(BaseCacheConstant.PRODUCT_CATCHE_UUID);
		if(StringUtil.isEmpty(productCode)){
			baseProductCache.invalid();
		}else{
			baseProductCache.invalid(productCode);
		}
	}

	/**
	 * 通过接口获取行政区域缓存数据
	 * @param regionCode
	 * @return BaseRegionEntity
	 */
	@Override
	public BaseRegionEntity getBaseRegionFromCache(String regionCode){
		ICache<String, BaseRegionEntity> baseRegionCache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_CATCHE_UUID);
		return baseRegionCache.get(regionCode);
	}

	@Override
	public void refreshBaseRegionFromCache(String regionCode) {
		ICache<String, BaseRegionEntity> baseRegionCache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_CATCHE_UUID);
		if(StringUtil.isEmpty(regionCode)){
			baseRegionCache.invalid();
		}else{
			baseRegionCache.invalid(regionCode);
		}
	}
	
	/**
	 * 通过接口获取行政区域下级缓存数据
	 * @param regionCode
	 * @return List<BaseRegionEntity>
	 */
	@Override
	public List<BaseRegionEntity> getBaseRegionListCascadeFromCache(String regionCode){
		ICache<String, List<BaseRegionEntity>> baseRegionListCascadeCache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_LIST_CASCADE_CATCHE_UUID);
		return baseRegionListCascadeCache.get(regionCode);
	}

	@Override
	public void refreshBaseRegionListCascadeFromCache(String regionCode) {
		ICache<String, List<BaseRegionEntity>> baseRegionListCascadeCache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_LIST_CASCADE_CATCHE_UUID);
		if(StringUtil.isEmpty(regionCode)){
			baseRegionListCascadeCache.invalid();
		}else{
			baseRegionListCascadeCache.invalid(regionCode);
		}
	}
	
	/**
	 * 通过接口获取网点缓存数据(通过网点编码获取网点实体)
	 * @param siteCode
	 * @return BaseSiteEntity
	 */
	@Override
	public BaseSiteEntity getBaseSiteFromCache(String siteCode){
		ICache<String, BaseSiteEntity> baseSiteCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
		return baseSiteCache.get(siteCode);
	}

	@Override
	public void refreshBaseSiteFromCache(String siteCode) {
		ICache<String, BaseSiteEntity> baseSiteCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
		if(StringUtil.isEmpty(siteCode)){
			baseSiteCache.invalid();
		}else{
			baseSiteCache.invalid(siteCode);
		}
	}
	
	/**
	 *  根据网点简称（siteNameShort）获取网点缓存(根据网点简称获取网点实体)
	 * @param siteNameShort
	 * @return BaseSiteEntity
	 */
	@Override
	public BaseSiteEntity getBaseSiteEntityNameFromCache(String siteNameShort){
		ICache<String, BaseSiteEntity> baseSiteEntityNameCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_NAME_CATCHE_UUID);
		return baseSiteEntityNameCache.get(siteNameShort);
	}

	@Override
	public void refreshBaseSiteEntityNameFromCache(String siteNameShort) {
		ICache<String, BaseSiteEntity> baseSiteEntityNameCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_NAME_CATCHE_UUID);
		if(StringUtil.isEmpty(siteNameShort)){
			baseSiteEntityNameCache.invalid();
		}else{
			baseSiteEntityNameCache.invalid(siteNameShort);
		}
	}
	
	/**
	 *   根据网点编号获取网点资金路由缓存
	 * @param siteCode
	 * @return List<BaseSiteFinLineEntity>
	 */
	@Override
	public List<BaseSiteFinLineEntity> getBaseSiteFinLineFromCache(String siteCode){
		ICache<String, List<BaseSiteFinLineEntity>> baseSiteFinLineCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID);
		return baseSiteFinLineCache.get(siteCode);
	}
	
	@Override
	public void refreshBaseSiteFinLineFromCache(String siteCode) {
		ICache<String, List<BaseSiteFinLineEntity>> baseSiteFinLineCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID);
		if(StringUtil.isEmpty(siteCode)){
			baseSiteFinLineCache.invalid();
		}else{
			baseSiteFinLineCache.invalid(siteCode);
		}
	}
	
	/**
	 *    通过区域名称获取区域编码缓存
	 * @param productName
	 * @return string
	 */
	@Override
	public String getProductCodeByProductNameFromCache(String productName){
		ICache<String,String> productNameCache = CacheManager.getInstance().getCache(BaseCacheConstant.GET_PRODUCT_CODE_BY_PRODUCT_NAME_CACHE_UUID);
		return productNameCache.get(productName);
	}
	
	@Override
	public void refreshProductCodeByProductNameFromCache(String productName) {
		ICache<String,String> productNameCache = CacheManager.getInstance().getCache(BaseCacheConstant.GET_PRODUCT_CODE_BY_PRODUCT_NAME_CACHE_UUID);
		if(StringUtil.isEmpty(productName)){
			productNameCache.invalid();
		}else{
			productNameCache.invalid(productName);
		}
	}	

	/**
	 *
	 * 传入参数regionName支持三种格式：
	 * 1）洪山区
	 * 2）湖北省-武汉市
	 * 3）湖北省-武汉市-洪山区
	 * 第一种格式：可以直接单独传入省、市、区（县）名称，区（县）名称在数据库中不唯一，因此尽量不要直接传入区（县）名称
	 * 第二、三种格式：开头部分必须为省，否则不能找到对应区域编码
	 * {@inheritDoc}
	 * @param regionName
	 * @return string
	 */
	@Override
	public String getRegionCodeByRegionNameFromCache(String regionName){
		ICache<String,String> regionNameCache = CacheManager.getInstance().getCache(BaseCacheConstant.GET_REGION_CODE_BY_REGION_NAME_CACHE_UUID);
		return regionNameCache.get(regionName);
	}
	
	@Override
	public void refreshRegionCodeByRegionNameFromCache(String regionName) {
		ICache<String,String> regionNameCache = CacheManager.getInstance().getCache(BaseCacheConstant.GET_REGION_CODE_BY_REGION_NAME_CACHE_UUID);
		if(StringUtil.isEmpty(regionName)){
			regionNameCache.invalid();
		}else{
			regionNameCache.invalid(regionName);
		}
	}	

	/**
	 *    通过网点编号+路由类型获取资金路由缓存
	 * @param key
	 * @return BaseSiteFinLineEntity
	 */
	@Override
	public BaseSiteFinLineEntity getSiteFinLineCodeAndLineTypeFromCache(String key){
		ICache<String,BaseSiteFinLineEntity> baseSiteFinLineCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID_CODE_LEVETYPE);
		return baseSiteFinLineCache.get(key);
	}
	
	@Override
	public void refreshSiteFinLineCodeAndLineTypeFromCache(String key) {
		ICache<String,BaseSiteFinLineEntity> baseSiteFinLineCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID_CODE_LEVETYPE);
		if(StringUtil.isEmpty(key)){
			baseSiteFinLineCache.invalid();
		}else{
			baseSiteFinLineCache.invalid(key);
		}
	}

	/**
	 *  通过CODE或者URI缓存功能对象
	 * @param key
	 * @return FunctionEntity
	 */
	@Override
	public FunctionEntity getFunctionFromCache(String key){
		ICache<String,FunctionEntity> functionCache = CacheManager.getInstance().getCache(DpapConstants.FUNCTION_ENTITY_CACHE_UUID);
		return functionCache.get(key);
	}
	
	@Override
	public void refreshFunctionFromCache(String key) {
		ICache<String,FunctionEntity> functionCache = CacheManager.getInstance().getCache(DpapConstants.FUNCTION_ENTITY_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			functionCache.invalid();
		}else{
			functionCache.invalid(key);
		}
	}

	/**
	 *通过父级节点获取下级节点的数据
	 * @param key
	 * @return List<FunctionEntity>
	 */
	@Override
	public List<FunctionEntity> getFunctionMenuFromCache(String key){
		ICache<String,List<FunctionEntity>> functionMenuCache = CacheManager.getInstance().getCache(DpapConstants.MENU_CACHE_UUID);
		return functionMenuCache.get(key);
	}
	
	@Override
	public void refreshFunctionMenuFromCache(String key) {
		ICache<String,List<FunctionEntity>> functionMenuCache = CacheManager.getInstance().getCache(DpapConstants.MENU_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			functionMenuCache.invalid();
		}else{
			functionMenuCache.invalid(key);
		}
	}

	/**
	 * 用户数据
	 * @param key
	 * @return IUser
	 */
	@Override
	public IUser getUserCache(String key){
		ICache<String,IUser> userCache = CacheManager.getInstance().getCache(DpapConstants.USER_CACHE_UUID);
		return userCache.get(key);
	}
	
	@Override
	public void refreshUserCache(String key) {
		ICache<String,IUser> userCache = CacheManager.getInstance().getCache(DpapConstants.USER_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			userCache.invalid();
		}else{
			userCache.invalid(key);
		}
	}

	/**
	 * 用户部门数据权限缓存
	 * @param key
	 * @return List<String>
	 */
	@Override
	public List<String> getUserDeptAuthorityCache(String key){
		ICache<String,List<String>> userDeptAuthorityCache = CacheManager.getInstance().getCache(DpapConstants.USER_DEPT_AUTH);
		return userDeptAuthorityCache.get(key);
	}
	
	@Override
	public void refreshUserDeptAuthorityCache(String key) {
		ICache<String,List<String>> userDeptAuthorityCache = CacheManager.getInstance().getCache(DpapConstants.USER_DEPT_AUTH);
		if(StringUtil.isEmpty(key)){
			userDeptAuthorityCache.invalid();
		}else{
			userDeptAuthorityCache.invalid(key);
		}
	}

	/**
	 * 部门缓存
	 * @param key
	 * @return DepartmentEntity
	 */
	@Override
	public DepartmentEntity getDepartmentCache(String key){
		ICache<String,DepartmentEntity> departmentCache = CacheManager.getInstance().getCache(DpapConstants.DEPARTMENT_CACHE_UUID);
		return departmentCache.get(key);
	}
	
	@Override
	public void refreshDepartmentCache(String key) {
		ICache<String,DepartmentEntity> departmentCache = CacheManager.getInstance().getCache(DpapConstants.DEPARTMENT_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			departmentCache.invalid();
		}else{
			departmentCache.invalid(key);
		}
	}

	/**
	 * 职员缓存(根据员工编号查询员工)
	 * @param key
	 * @return EmployeeEntity
	 */
	@Override
	public EmployeeEntity getEmployeeCache(String key){
		ICache<String,EmployeeEntity> employeeCache = CacheManager.getInstance().getCache(DpapConstants.EMPLOYEE_CACHE_UUID);
		return employeeCache.get(key);
	}
	
	@Override
	public void refreshEmployeeCache(String key) {
		ICache<String,EmployeeEntity> employeeCache = CacheManager.getInstance().getCache(DpapConstants.EMPLOYEE_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			employeeCache.invalid();
		}else{
			employeeCache.invalid(key);
		}
	}

	/**
	 * 获取部门下级缓存
	 * @param key
	 * @return List<DepartmentEntity>
	 */
	@Override
	public List<DepartmentEntity> getDepartmentDirectChildCache(String key){
		ICache<String,List<DepartmentEntity>> departmentDirectChild = CacheManager.getInstance().getCache(DpapConstants.DEPARTMENT_DIRECT_CHILD_CACHE_UUID);
		return departmentDirectChild.get(key);
	}
	
	@Override
	public void refreshDepartmentDirectChildCache(String key) {
		ICache<String,List<DepartmentEntity>> departmentDirectChild = CacheManager.getInstance().getCache(DpapConstants.DEPARTMENT_DIRECT_CHILD_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			departmentDirectChild.invalid();
		}else{
			departmentDirectChild.invalid(key);
		}
	}

	/**
	 * 通过数据字典类型alias获取数据字典数据列表
	 * @param key
	 * @return List<DictionaryEntity>
	 */
	@Override
	public List<DictionaryEntity> getDictDataCache(String key){
		ICache<String,List<DictionaryEntity>> dictDataCache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_CACHE_UUID);
		return dictDataCache.get(key);
	}
	
	@Override
	public void refreshDictDataCache(String key) {
		ICache<String,List<DictionaryEntity>> dictDataCache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			dictDataCache.invalid();
		}else{
			dictDataCache.invalid(key);
		}
	}

	/**
	 * 通过数据字典类型+,+數據字典key獲取value
	 * @param key
	 * @return String
	 */
	@Override
	public String getDictDataValueNameCache(String key){
		ICache<String,String> dictDataValueNameCache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
		return dictDataValueNameCache.get(key);
	}
	
	@Override
	public void refreshDictDataValueNameCache(String key) {
		ICache<String,String> dictDataValueNameCache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			dictDataValueNameCache.invalid();
		}else{
			dictDataValueNameCache.invalid(key);
		}
	}

	/**
	 * 通过数据字典类型alias获取数据字典实体列表
	 * @param key
	 * @return List<DictionaryDataEntity>
	 */
	@Override
	public List<DictionaryDataEntity> getDictEntityCache(String key){
		ICache<String,List<DictionaryDataEntity>> dictEntityCache = CacheManager.getInstance().getCache(DpapConstants.DICT_CACHE_UUID);
		return dictEntityCache.get(key);
	}

	@Override
	public void refreshDictEntityCache(String key) {
		ICache<String,List<DictionaryDataEntity>> dictEntityCache = CacheManager.getInstance().getCache(DpapConstants.DICT_CACHE_UUID);
		if(StringUtil.isEmpty(key)){
			dictEntityCache.invalid();
		}else{
			dictEntityCache.invalid(key);
		}
	}
}
