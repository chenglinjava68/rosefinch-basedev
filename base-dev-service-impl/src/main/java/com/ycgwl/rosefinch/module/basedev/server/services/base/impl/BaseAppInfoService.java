package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.shared.util.string.StringUtil;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseAppInfoDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseInterfaceDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseAppInfoService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseInterfaceInfoEntity;

@Service("baseAppInfoService")
public class BaseAppInfoService implements IBaseAppInfoService {
	public static Logger LOGGER = LoggerFactory.getLogger(BaseAppInfoService.class);

	@Autowired
	private IBaseAppInfoDao baseAppInfoDao;
	@Autowired
	private IBaseInterfaceDao baseInterfaceDao;
	@Transactional
	@Override
	public int insert(BaseAppInfoEntity baseAppInfo) {
		//判定传入的参数是否为空
		if(baseAppInfo == null){
			throw new BusinessException("传入的参数为空");
		}
		if(StringUtil.isEmpty(baseAppInfo.getAppKey())){
			throw new BusinessException("APPKEY不允许为空");
		}
		BaseAppInfoEntity entity = this.getByAppKey(baseAppInfo.getAppKey());
		if(entity!=null){
			throw new BusinessException("APPKEY不允许重复");
		}
		Date nowDate = new Date();
		baseAppInfo.setAppId(BasicEntityIdentityGenerator.getInstance().generateId());
		baseAppInfo.setCreateTime(nowDate);
		baseAppInfo.setModifyTime(nowDate);
		int rt =  baseAppInfoDao.insert(baseAppInfo);
		List<BaseInterfaceInfoEntity> interfaceEntitys = baseAppInfo.getBaseInterfaceInfoList();

		if(CollectionUtils.isNotEmpty(interfaceEntitys)){
			for (BaseInterfaceInfoEntity baseInterfaceInfoEntity : interfaceEntitys) {
				baseInterfaceInfoEntity.setInterfaceId(BasicEntityIdentityGenerator.getInstance().generateId());
				baseInterfaceInfoEntity.setAppKey(baseAppInfo.getAppKey());
				baseInterfaceInfoEntity.setCreateTime(nowDate);
				baseInterfaceInfoEntity.setDelFlag(0);
				baseInterfaceInfoEntity.setCreateUserCode(baseAppInfo.getCreateUserCode());
			}
			baseInterfaceDao.batchInsertBaseInterfaceInfoEntity(interfaceEntitys);
		}
		return rt;
	}

	@Transactional
	@Override
	public int update(BaseAppInfoEntity baseAppInfoEntity) {
		// 判定数据是否为空
		if (baseAppInfoEntity == null) {
			throw new BusinessException("对应的应用系统数据为空");
		}
		// 判定对应的ID是否为空
		if (baseAppInfoEntity.getAppId() == null) {
			throw new BusinessException("对应的应用系统数据ID为空");
		}
		if(StringUtil.isEmpty(baseAppInfoEntity.getAppKey())){
			throw new BusinessException("APPKEY不允许为空");
		}
		BaseAppInfoEntity entity = this.getByAppKey(baseAppInfoEntity.getAppKey());
		if(entity!=null&&!entity.getAppId().equals(baseAppInfoEntity.getAppId())){
			throw new BusinessException("APPKEY不允许重复");
		}
		Date nowDate = new Date();
		baseAppInfoEntity.setModifyTime(nowDate);
		//进行数据的更新
		int num = baseAppInfoDao.update(baseAppInfoEntity);
		if(num < 1){
			throw new BusinessException("更新应用系统数据失败");
		}
		List<BaseInterfaceInfoEntity> interfaceEntitys = baseAppInfoEntity.getBaseInterfaceInfoList();
		if(CollectionUtils.isNotEmpty(interfaceEntitys)){
			for (BaseInterfaceInfoEntity baseInterfaceInfoEntity : interfaceEntitys) {
				baseInterfaceInfoEntity.setInterfaceId(BasicEntityIdentityGenerator.getInstance().generateId());
				baseInterfaceInfoEntity.setAppKey(baseAppInfoEntity.getAppKey());
				baseInterfaceInfoEntity.setDelFlag(0);
				baseInterfaceInfoEntity.setModifyTime(nowDate);
				baseInterfaceInfoEntity.setModifyUserCode(baseAppInfoEntity.getModifyUserCode());
				baseInterfaceInfoEntity.setCreateTime(baseAppInfoEntity.getCreateTime());
				baseInterfaceInfoEntity.setCreateUserCode(baseAppInfoEntity.getCreateUserCode());
			}
			List<BaseInterfaceInfoEntity> baseInterfaceInfoEntities = baseInterfaceDao.getByAppkey(baseAppInfoEntity.getAppKey());

			baseInterfaceDao.deleteByAppkey(baseAppInfoEntity.getAppKey());
			baseInterfaceDao.batchInsertBaseInterfaceInfoEntity(interfaceEntitys);
			refreshDetailCache(baseAppInfoEntity.getAppKey(),baseInterfaceInfoEntities);
		}
		refreshCache(baseAppInfoEntity.getAppKey());
		return num;
	}

	private void refreshDetailCache(String appKey,List<BaseInterfaceInfoEntity> baseInterfaceInfoEntities) {
		for (BaseInterfaceInfoEntity baseInterfaceInfoEntity : baseInterfaceInfoEntities) {
	        CacheManager.getInstance().getCache(BaseCacheConstant.INTERFACE_CATCHE_UUID).invalid(appKey+BaseOrgConstant.SPLIT_SEPARATOR+baseInterfaceInfoEntity.getInterfaceCode());
		}
	}

	@Transactional
	@Override
	public int deleteBaseAppInfoById(Long id,String appKey, String currentUserCode) {
		// 判定ID是否为空
		if (id == null) {
			throw new BusinessException("ID为空");
		}

		int num = baseAppInfoDao.deleteById(id);
		List<BaseInterfaceInfoEntity> baseInterfaceInfoEntities = baseInterfaceDao.getByAppkey(appKey);
		baseInterfaceDao.deleteByAppkey(appKey);
		refreshDetailCache(appKey, baseInterfaceInfoEntities);
		refreshCache(appKey);
		if (num < 1) {
			throw new BusinessException("根据ID删除价格条目失败");
		}
		return num;

	}


	@Override
	public BaseAppInfoEntity getById(Long id) {
		if(id == null){
			throw new BusinessException("传入的参数为空");
		}
		BaseAppInfoEntity baseApp = baseAppInfoDao.getByAppId(id);
		LOGGER.info("baseApp: id "+baseApp.getAppId());
		LOGGER.info("baseApp: key "+baseApp.getAppKey());
		LOGGER.info("baseApp: name "+baseApp.getAppName());
		return baseApp;
	}

	@Override
	public List<BaseAppInfoEntity> get(Map<String, Object> params) {

		List<BaseAppInfoEntity> baseApp = baseAppInfoDao.get(params);
		LOGGER.info("baseApp: id "+baseApp.get(0).getAppId());
		LOGGER.info("baseApp: key "+baseApp.get(0).getAppKey());
		LOGGER.info("baseApp: name "+baseApp.get(0).getAppName());
		return baseAppInfoDao.get(params);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public List<BaseAppInfoEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
		return baseAppInfoDao.getPage(params, pageNum, pageSize);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public int getPageTotalCount(Map<String, Object> params) {
		return baseAppInfoDao.getPageTotalCount(params);
	}

	@Override
	public int deleteById(Long id) {
		if(id == null){
			throw new BusinessException("传入的ID为空");
		}
		BaseAppInfoEntity baseApp = baseAppInfoDao.getById(id);
		if(baseApp == null){
			throw new BusinessException("根据传入参数查询到对应数据为空");
		}

		LOGGER.info("baseApp: 删除id "+baseApp.getAppId());
		return baseAppInfoDao.deleteById(id);
	}

	@Deprecated
	@Override
	public int updateStatusById(Long id, int status) {
		return baseAppInfoDao.updateStatusById(id, status);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public Pagination<BaseAppInfoEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {
		return baseAppInfoDao.getPagination(params, page, sorts);
	}

	@Override
	public BaseAppInfoEntity getQueryData(String appKey, String appName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appKey", appKey);
		map.put("appName", appName);
		List<BaseAppInfoEntity> baseAppList = baseAppInfoDao.get(map);
		if(CollectionUtils.isEmpty(baseAppList)){
			return null;
		}
		BaseAppInfoEntity baseApp = baseAppList.get(0);
		LOGGER.info(" id "+baseApp.getAppId());
		LOGGER.info(" key "+baseApp.getAppKey());
		LOGGER.info(" name "+baseApp.getAppName());
		return baseApp;
	}

	@Override
	public BaseAppInfoEntity getQueryKeyData(String appKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appKey", appKey);

		List<BaseAppInfoEntity> baseAppList = baseAppInfoDao.get(map);
		if(CollectionUtils.isEmpty(baseAppList)){
			return null;
		}
		BaseAppInfoEntity baseApp = baseAppList.get(0);
		LOGGER.info(" id "+baseApp.getAppId());
		LOGGER.info(" key "+baseApp.getAppKey());
		LOGGER.info(" name "+baseApp.getAppName());
		return baseApp;
	}

	@Override
	public BaseAppInfoEntity getQueryNameData(String appName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appName", appName);

		List<BaseAppInfoEntity> baseAppList = baseAppInfoDao.get(map);
		if(CollectionUtils.isEmpty(baseAppList)){
			return null;
		}
		BaseAppInfoEntity baseApp = baseAppList.get(0);
		LOGGER.info(" id "+baseApp.getAppId());
		LOGGER.info(" key "+baseApp.getAppKey());
		LOGGER.info(" name "+baseApp.getAppName());
		return baseApp;
	}

	@Override
	public List<BaseAppInfoEntity> getQueryAllData() {
		return baseAppInfoDao.getQueryAllData();
	}



	/**
	 * 清除系统配置缓存
	 * @param key
	 */
	private void refreshCache(String key){
		//系统配置
        CacheManager.getInstance().getCache(BaseCacheConstant.APP_INFO_CATCHE_UUID).invalid(key);
	}

	@Override
	public BaseAppInfoEntity getByAppKey(String appKey) {

		return baseAppInfoDao.getByAppKey(appKey);
	}
}
