package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ycgwl.cache.CacheManager;
import com.ycgwl.cache.base.ICache;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseUnloadTimeDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseUnloadTimeReleDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseUnloadTimeService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseUnloadTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseUnloadTimeReleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseUnloadTimeReleVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseUnloadTimeVo;
@Service
public class BaseUnloadTimeService implements IBaseUnloadTimeService {

	@Autowired
	private IBaseUnloadTimeDao baseUnloadTimeDao;
	@Autowired
	private IBaseUnloadTimeReleDao baseUnloadTimeReleDao;

	@Autowired
	private IBaseSiteDao baseSiteDao;

	@Override
    @Transactional
	public int insert(BaseUnloadTimeEntity t) {
		 return baseUnloadTimeDao.insert(t);
	}

	@Override
	@Transactional
	public int update(BaseUnloadTimeEntity t) {
		  return baseUnloadTimeDao.update(t);
	}

	@Override
    @Transactional
	public BaseUnloadTimeEntity getById(Long id) {
		 return baseUnloadTimeDao.getById(id);
	}

	@Override
    @Transactional
	public List<BaseUnloadTimeEntity> get(Map<String, Object> params) {
		 return baseUnloadTimeDao.get(params);
	}

	@Override
	@Transactional
	public List<BaseUnloadTimeEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
		//
		return baseUnloadTimeDao.getPage(params, pageNum, pageSize);
	}

	@Override
    @Transactional
	public int getPageTotalCount(Map<String, Object> params) {
		 return baseUnloadTimeDao.getPageTotalCount(params);
	}

	@Override
	@Transactional
	public int deleteById(Long id) {
		  return baseUnloadTimeDao.deleteById(id);
	}

	@Override
	public int updateStatusById(Long id, int status) {
		 return baseUnloadTimeDao.updateStatusById(id, status);
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public Pagination<BaseUnloadTimeEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {
		//

        Pagination<BaseUnloadTimeEntity> pageInfo = baseUnloadTimeDao.getPagination(params, page, sorts);
        if(pageInfo != null){
            for (BaseUnloadTimeEntity entity : pageInfo.getList()) {
                ICache<String, String> cache = getDictDataValueNameCache();
                //正班车卸车时效
                String nomalUnloadTimeName = cache.get("BASE_NOMAL_UNLOAD_TIME" + BaseOrgConstant.SPLIT_SEPARATOR + entity.getNomalUnloadTime());
                String overtimeUnloadTimeName = cache.get("BASE_OVERTIME_UNLOAD_TIME" + BaseOrgConstant.SPLIT_SEPARATOR +entity.getOvertimeUnloadTime());
                entity.setNomalUnloadTimeName(nomalUnloadTimeName);
                entity.setOvertimeUnloadTimeName(overtimeUnloadTimeName);

            }
        }
        return pageInfo;
	}
	 /**
     * 新增卸车时效设置
     * @param baseConfigVo
     * @param currentUserCode
     * @return
     */
	@Transactional
	@Override
	public BaseUnloadTimeEntity insertUnloadTime(BaseUnloadTimeVo baseUnloadTimeVo, String currentUserCode) {
		BaseUnloadTimeEntity baseUnloadTimeEntity = new BaseUnloadTimeEntity();

	        if (null != baseUnloadTimeVo) {
	            BeanUtils.copyProperties(baseUnloadTimeVo, baseUnloadTimeEntity);
	            baseUnloadTimeEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
	           // baseUnloadTimeEntity.setDelFlag(0);
	            Date date = new Date();
	            baseUnloadTimeEntity.setCreateTime(date);
	            baseUnloadTimeEntity.setCreateUserCode(currentUserCode);
	            baseUnloadTimeEntity.setModifyTime(date);
	            baseUnloadTimeEntity.setModifyUserCode(currentUserCode);
	            baseUnloadTimeDao.insert(baseUnloadTimeEntity);
	            batchInsertReles(baseUnloadTimeVo.getSiteCodeList(), baseUnloadTimeEntity.getConfigCode());
	            }

	        return baseUnloadTimeEntity;
	    }
	private void batchInsertReles(List<BaseUnloadTimeReleVo> siteCodeList, String configCode){
        if(CollectionUtils.isNotEmpty(siteCodeList)){
            for (BaseUnloadTimeReleVo vo : siteCodeList) {
                BaseUnloadTimeReleEntity rele = new BaseUnloadTimeReleEntity();
                rele.setId(BasicEntityIdentityGenerator.getInstance().generateId());
                rele.setSiteCode(vo.getSiteCode());
                rele.setConfigCode(configCode);
                baseUnloadTimeReleDao.insert(rele);
            }
        }
    }

	private ICache<String, String> getDictDataValueNameCache() {
        ICache<String, String> cache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
        return cache;
    }
    @Transactional
	@Override
	public BaseUnloadTimeEntity editUnloadTime(BaseUnloadTimeVo baseUnloadTimeVo, String currentUserCode) {
		BaseUnloadTimeEntity oldEntity = baseUnloadTimeDao.getById(baseUnloadTimeVo.getHiddenId());
	        BeanUtils.copyProperties(baseUnloadTimeVo, oldEntity);
	        oldEntity.setModifyTime(new Date());
	        oldEntity.setModifyUserCode(currentUserCode);
	        baseUnloadTimeDao.update(oldEntity);
	        baseUnloadTimeReleDao.deleteAllReles(oldEntity.getConfigCode());
	        batchInsertReles(baseUnloadTimeVo.getSiteCodeList(), oldEntity.getConfigCode());
	        return oldEntity;
	}

	@Override
	public void deleteUnloadTime(Long id) {
		  // 判定ID是否为空
        if (id == null) {
            throw new BusinessException("ID为空");
        }
        baseUnloadTimeDao.logicalDeleteById(id);

	}

	@Override
	public int uniqueCheckByConfigCode(String configCode) {
		 return baseUnloadTimeDao.uniqueCheckByConfigCode(configCode);
	}

	@Override
	public int uniqueCheckByConfigName(Map<String, Object> map) {
		 return baseUnloadTimeDao.uniqueCheckByConfigName(map);
	}

	/**
	 * 批量删除
	 */
	@Transactional
	@Override
	public void batchlogicalDeleteById(List<Long> list) {
		baseUnloadTimeDao.batchlogicalDeleteById(list);

	}


	@Override
	public List<BaseSiteEntity> getByConfigCodeAndSites(String code, List<BaseSiteEntity> baseSiteEntities) {
		 Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("configCode", code);
	        paramMap.put("list", baseSiteEntities);
	        return baseUnloadTimeReleDao.getByConfigCodeAndSites(paramMap);
	}

	@Override
	public Map<String, Object> getOrgsByUnloadTime(String configCode) {
	    Map<String, Object> siteMap = new HashMap<String, Object>();
        List<BaseSiteEntity> siteList = baseUnloadTimeReleDao.getSiteByConfigCode(configCode);
        if(CollectionUtils.isNotEmpty(siteList)){
            String siteNames = "";
            List<BaseUnloadTimeReleVo> siteCodeList = new ArrayList<BaseUnloadTimeReleVo>();
            for (BaseSiteEntity baseSiteEntity : siteList) {
                siteNames += "," + baseSiteEntity.getSiteNameShort();
                BaseUnloadTimeReleVo vo = new BaseUnloadTimeReleVo();
                vo.setSiteCode(baseSiteEntity.getSiteCode());
                vo.setSiteName(baseSiteEntity.getSiteNameShort());
                siteCodeList.add(vo);
            }
            siteMap.put("siteNames", siteNames.substring(1));
            siteMap.put("siteCodes", siteCodeList);
        }
        return siteMap;
	}

	@Override
    public Set<String> getChildSites(String siteCode){
        List<String> list = baseSiteDao.queryChildSitesBySiteCode(siteCode);
        Set<String> set = new HashSet<String>();
        if(CollectionUtils.isNotEmpty(list)){
            set.addAll(list);
        }
        return set;
    }

	@Override
	public void batchUpdateBlFlagById(Map<String, Object> paramMap) {
		baseUnloadTimeDao.batchUpdateBlFlagById(paramMap);

	}

}
