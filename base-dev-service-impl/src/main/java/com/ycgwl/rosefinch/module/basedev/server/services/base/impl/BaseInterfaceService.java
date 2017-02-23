package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.ycgwl.cache.base.ICache;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseInterfaceDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseInterfaceService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseInterfaceInfoEntity;

@Service
public class BaseInterfaceService implements IBaseInterfaceService {

	public static Logger logger = LoggerFactory.getLogger(BaseInterfaceService.class);
	// 接口管理的持久层
	@Autowired
	private IBaseInterfaceDao baseInterfaceDao;
//	// 接口管理的数据服务层
//	@Autowired(required=true)
//	private IBaseInterfaceService baseInterfaceService;

	@Transactional
	@Override
	public int insert(BaseInterfaceInfoEntity baseInterfaceInfo) {
		// 进行数据检验
		if (baseInterfaceInfo == null) {
			throw new BusinessException("传入的接口信息不能为空");
		}
		// 检验接口名不能为空
		if (StringUtils.isEmpty(baseInterfaceInfo.getInterfaceName())) {
			throw new BusinessException("接口名字不能为空");
		}
		// 当前时间
		Date nowDate = new Date();
		// 当前登录人
		baseInterfaceInfo.setInterfaceId(BasicEntityIdentityGenerator.getInstance().generateId());
		baseInterfaceInfo.setCreateTime(nowDate);
		baseInterfaceInfo.setModifyTime(nowDate);
		int rt =  baseInterfaceDao.insert(baseInterfaceInfo);
		refreshCache(baseInterfaceInfo.getAppKey());
		return rt;
	}

	@Transactional
	@Override
	public int update(BaseInterfaceInfoEntity baseInterfaceInfo) {
		// 进行数据校验
		if (baseInterfaceInfo == null) {
			throw new BusinessException("传入的接口信息不能为空!");
		}
		if (baseInterfaceInfo.getInterfaceId() == 0) {
			throw new BusinessException("传入的接口ID不能为空!");
		}
		// 当前时间
		Date nowDate = new Date();
		//直接用Data 插入格式为 '2016-07-22 11:13:59:025'  数据库中的日期格式为 2016/7/22 11:05:16
		// 当前登录人
		//baseInterfaceInfo.setInterfaceId(String.valueOf(BasicEntityIdentityGenerator.getInstance().generateId()));
		baseInterfaceInfo.setCreateTime(nowDate);
		baseInterfaceInfo.setModifyTime(nowDate);
		// 更新接口信息
		int num = baseInterfaceDao.update(baseInterfaceInfo);
		refreshCache(baseInterfaceInfo.getAppKey());
		if (num < 1) {
			throw new BusinessException("更新接口信息失败！");
		}
		return num;
	}

	@Override
	public BaseInterfaceInfoEntity getById(Long id) {
		// 检验接口ID不能为空
		if (id == null) {
			throw new BusinessException("接口ID不能为空！");
		}
		// 根据ID进行数据的查询
		return baseInterfaceDao.getById(id);                                                                                                                                                                                         
	}

	@Override
	public List<BaseInterfaceInfoEntity> get(Map<String, Object> params) {
		// 判定查询是否为空
		if (params == null || params.isEmpty()) {
			 throw new BusinessException("传入的查询参数不能为空！");
			// 查出全部数据
			//return baseInterfaceDao.queryAllInterfaceData();
		}
		
		List<BaseInterfaceInfoEntity> baseInterfaceInfoEntity = null;
		baseInterfaceInfoEntity = baseInterfaceDao.get(params);
		System.out.println("*************************>>>size"+baseInterfaceInfoEntity.size());
		if (baseInterfaceInfoEntity.size()>0) {
			logger.info("ID===》"+baseInterfaceInfoEntity.get(0).getInterfaceId());
			logger.info("NAME===>"+baseInterfaceInfoEntity.get(0).getInterfaceName());
		}
//		
		return baseInterfaceDao.get(params);
	}

	@Override
	public List<BaseInterfaceInfoEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
		// 判定查询是否为空
		if (params == null || params.isEmpty()) {
			throw new BusinessException("传入的查询参数不能为空！");
		}
		return baseInterfaceDao.getPage(params, pageNum, pageSize);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public int getPageTotalCount(Map<String, Object> params) {
		// 判断数据是否为空
		if (params == null || params.isEmpty()) {
			throw new BusinessException("传入的查询参数不能为空！");
		}
		//
		return baseInterfaceDao.getPageTotalCount(params);
	}

	@Transactional
	@Override
	public int deleteById(Long id) {
		// 判断ID书否为空
		if (id == null) {
			throw new BusinessException("传入的接口ID不能为空！");
		}
		// TODO Auto-generated method stub
		return baseInterfaceDao.deleteById(id);
	}
	
	@Transactional
	@Override
	public int deleteInterById(Long id, String appKey) {
		// 判断ID书否为空
		if (id == null) {
			throw new BusinessException("传入的接口ID不能为空！");
		}
		// TODO Auto-generated method stub
		int rt = baseInterfaceDao.deleteById(id);
		refreshCache(appKey);
		return rt;
	}
	
	
    
	@Deprecated
	@Transactional
	@Override
	public int updateStatusById(Long id, int status) {
		// 判定ID是否为空
		if (id == null) {
			throw new BusinessException("传入的ID为空");
		}
		// 根据ID进行数据状态的更新
		return baseInterfaceDao.updateStatusById(id, status);
	}
    
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public Pagination<BaseInterfaceInfoEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {
		// TODO Auto-generated method stub
		   return baseInterfaceDao.getPagination(params, page, sorts);
	}

//	/**
//	 * 根据接口名字 模糊查询
//	 * 
//	 * @param interfaceName
//	 * @return
//	 */
//	@Transactional
//	public List<BaseInterfaceInfoEntity> queryByInterfaceName(String interfaceName) {
//		//检验接口名字不能为空
//		if (interfaceName==null || interfaceName.isEmpty()) {
//			throw new BusinessException("查询的接口名字不能为空！");
//		}
//		return baseInterfaceDao.queryByInterfaceName(interfaceName);
//	}

	/**
	 * 查询全部数据
	 */
	@Transactional
	@Override
	public List<BaseInterfaceInfoEntity> queryAllInterfaceData() {
		// TODO Auto-generated method stub
		System.out.println("====================》》》》》Service 查询全部数据");
		return baseInterfaceDao.queryAllInterfaceData();
	}

	

	/**
	 * 清除系统配置缓存
	 * @param key
	 */
	private void refreshCache(String key){
		//系统配置
        CacheManager.getInstance().getCache(BaseCacheConstant.INTERFACE_CATCHE_UUID).invalid(key);
	}


}
