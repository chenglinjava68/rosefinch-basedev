package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;

import com.ycgwl.rosefinch.common.shared.entity.IUser;
import com.ycgwl.rosefinch.module.authorization.shared.domain.FunctionEntity;
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

public interface IBaseRedisCacheService{
	/**
	 * 通过接口获取应用系统信息缓存数据
	 * @param appKey
	 * @return BaseAppInfoEntity
	 */
	public BaseAppInfoEntity getBaseAppInfoEntityFromCache(String appKey);
	
	/**
	 * 刷新应用系统信息缓存数据
	 * @param appKey
	 */
	public void refreshBaseAppInfoEntityFromCache(String appKey);
	
	/**
	 * 通过接口获取配置信息缓存数据
	 * @param configCode
	 * @return BaseConfigEntity
	 */
	public BaseConfigEntity getBaseConfigEntityFromCache(String configCode);
	
	/**
	 * 刷新应用配置信息缓存数据
	 * @param appKey
	 */
	public void refreshBaseConfigEntityFromCache(String configCode);
	
	/**
	 * 通过接口获取 接口信息缓存数据
	 * @param interfaceCode
	 * @return BaseInterfaceInfoEntity
	 */
	public BaseInterfaceInfoEntity getBaseInterfaceInfoFromCache(String interfaceCode);
	
	/**
	 * 刷新接口信息缓存数据
	 * @param interfaceCode
	 */
	public void refreshBaseInterfaceInfoFromCache(String interfaceCode);
	
	/**
	 * 通过接口获取网点缓存数据
	 * @param orgCode
	 * @return BaseOrgEntity
	 */
	public BaseOrgEntity getBaseOrgFromCache(String orgCode);
	
	/**
	 * 刷新网点缓存数据
	 * @param orgCode
	 */
	public void refreshBaseOrgFromCache(String orgCode);
	
	/**
	 * 通过接口获取产品缓存数据
	 * @param productCode
	 * @return BaseProductEntity
	 */
	public BaseProductEntity getBaseProductFromCache(String productCode);
	
	/**
	 * 刷新产品缓存数据
	 * @param productCode
	 */
	public void refreshBaseProductFromCache(String productCode);
	
	
	/**
	 * 通过接口获取行政区域缓存数据
	 * @param regionCode
	 * @return BaseRegionEntity
	 */
	public BaseRegionEntity getBaseRegionFromCache(String regionCode);
	
	/**
	 * 刷新行政区域缓存数据
	 * @param regionCode
	 */
	public void refreshBaseRegionFromCache(String regionCode);
	
	/**
	 * 通过接口获取行政区域下级缓存数据
	 * @param regionCode
	 * @return List<BaseRegionEntity>
	 */
	public List<BaseRegionEntity> getBaseRegionListCascadeFromCache(String regionCode);
	
	
	/**
	 * 刷新行政区域下级缓存数据
	 * @param regionCode
	 */
	public void refreshBaseRegionListCascadeFromCache(String regionCode);
	
	/**
	 * 通过接口获取网点缓存数据
	 * @param siteCode
	 * @return BaseSiteEntity
	 */
	public BaseSiteEntity getBaseSiteFromCache(String siteCode);
	
	/**
	 * 刷新网点缓存数据
	 * @param siteCode
	 */
	public void refreshBaseSiteFromCache(String siteCode);
	
	/**
	 *  根据网点简称（siteNameShort）获取网点缓存
	 * @param siteNameShort
	 * @return BaseSiteEntity
	 */
	public BaseSiteEntity getBaseSiteEntityNameFromCache(String siteNameShort);
	
	/**
	 * 刷新简称（siteNameShort）获取网点缓存
	 * @param siteNameShort
	 */
	public void refreshBaseSiteEntityNameFromCache(String siteNameShort);
	
	
	/**
	 *   根据网点编号获取网点资金路由缓存
	 * @param siteCode
	 * @return List<BaseSiteFinLineEntity>
	 */
	public List<BaseSiteFinLineEntity> getBaseSiteFinLineFromCache(String siteCode);
	
	
	
	/**
	 * 刷新网点编号获取网点资金路由缓存
	 * @param siteCode
	 */
	public void refreshBaseSiteFinLineFromCache(String siteCode);
	
	/**
	 *    通过产品名称获取产品编码缓存
	 * @param productName
	 * @return string
	 */
	public String getProductCodeByProductNameFromCache(String productName);
	
	
	/**
	 * 刷新通过产品名称获取产品编码缓存
	 * @param productName
	 */
	public void refreshProductCodeByProductNameFromCache(String productName);
	
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
	public String getRegionCodeByRegionNameFromCache(String regionName);
	
	
	/**
	 * 刷新名字获取行政区域缓存
	 * @param regionName
	 */
	public void refreshRegionCodeByRegionNameFromCache(String regionName);
	
	/**
	 *    通过区域名称获取区域编码缓存
	 * @param key
	 * @return BaseSiteFinLineEntity
	 */
	public BaseSiteFinLineEntity getSiteFinLineCodeAndLineTypeFromCache(String key);
	
	/**
	 * 刷新通过区域名称获取区域编码缓存
	 * @param key
	 */
	public void refreshSiteFinLineCodeAndLineTypeFromCache(String key);
	
	/**
	 *  通过CODE或者URI缓存功能对象
	 * @param key
	 * @return FunctionEntity
	 */
	public FunctionEntity getFunctionFromCache(String key);
	
	/**
	 * 刷新 通过CODE或者URI缓存功能对象
	 * @param key
	 */
	public void refreshFunctionFromCache(String key);
	
	/**
	 *通过父级节点获取下级节点的数据
	 * @param key
	 * @return List<FunctionEntity>
	 */
	public List<FunctionEntity> getFunctionMenuFromCache(String key);
	
	
	/**
	 * 刷新 通过父级节点获取下级节点的数据缓存
	 * @param key
	 */
	public void refreshFunctionMenuFromCache(String key);
	
	/**
	 * 用户数据
	 * @param key
	 * @return IUser
	 */
	public IUser getUserCache(String key);
	
	/**
	 * 刷新 用户数据
	 * @param key
	 */
	public void refreshUserCache(String key);
	
	/**
	 * 用户部门数据权限缓存
	 * @param key
	 * @return List<String>
	 */
	public List<String> getUserDeptAuthorityCache(String key);
	
	/**
	 * 刷新用户部门数据权限缓存
	 * @param key
	 */
	public void refreshUserDeptAuthorityCache(String key);
	
	/**
	 * 通过数据字典类型alias获取数据字典数据列表
	 * @param key
	 * @return List<DictionaryEntity>
	 */
	public List<DictionaryEntity> getDictDataCache(String key);
	
	/**
	 * 刷新通过数据字典类型alias获取数据字典数据列表
	 * @param key
	 */
	public void refreshDictDataCache(String key);
	
	/**
	 * 通过数据字典类型+,+數據字典key獲取value
	 * @param key
	 * @return String
	 */
	public String getDictDataValueNameCache(String key);
	
	/**
	 * 刷新通过数据字典类型+,+數據字典key獲取value
	 * @param key
	 */
	public void refreshDictDataValueNameCache(String key);
	
	/**
	 * 通过数据字典类型alias获取数据字典实体列表
	 * @param key
	 * @return List<DictionaryDataEntity>
	 */
	public List<DictionaryDataEntity> getDictEntityCache(String key);
	
	/**
	 * 刷新通过数据字典类型alias获取数据字典实体列表
	 * @param key
	 */
	public void refreshDictEntityCache(String key);
	
	/**
	 * 获取组织
	 * @param key
	 * @return DepartmentEntity
	 */
	DepartmentEntity getDepartmentCache(String key);
	
	/**
	 * 刷新组织缓存
	 * @param key
	 */
	public void refreshDepartmentCache(String key);
	
	/**
	 * 获取职员
	 * @param key
	 * @return EmployeeEntity
	 */
	EmployeeEntity getEmployeeCache(String key);
	
	/**
	 * 刷新职员缓存
	 * @param key
	 */
	public void refreshEmployeeCache(String key);
	/**
	 * 下级组织缓存
	 * @param key
	 * @return EmployeeEntity
	 */
	List<DepartmentEntity> getDepartmentDirectChildCache(String key);
	
	
	/**
	 * 刷新职员缓存
	 * @param key
	 */
	public void refreshDepartmentDirectChildCache(String key);
}
