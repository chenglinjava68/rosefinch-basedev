package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IGetPackageTimeDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IGetPackageTimeReleDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IGetPackageTimeService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.GetPackageTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.GetPackageTimeReleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.GetPackageTimeReleVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.GetPackageTimeVo;

/**
*
* @Title:T_GET_PACKAGE_TIME表Service的实现
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月1日  下午4:47
*
*/
@Service
public class GetPackageTimeService implements IGetPackageTimeService {
    @Autowired
    private IGetPackageTimeDao getPackageTimeDao;

    @Autowired
    private IGetPackageTimeReleDao getPackageTimeReleDao;

    @Autowired
    private IBaseSiteDao baseSiteDao;

    @Transactional
    @Override
    public int insert(GetPackageTimeEntity t) {
        return getPackageTimeDao.insert(t);
    }

    @Transactional
    @Override
    public int update(GetPackageTimeEntity t) {
        return getPackageTimeDao.update(t);
    }

    @Override
    public GetPackageTimeEntity getById(Long id) {
        return getPackageTimeDao.getById(id);
    }

    @Override
    public List<GetPackageTimeEntity> get(Map<String, Object> params) {
        return getPackageTimeDao.get(params);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
    @Override
    public List<GetPackageTimeEntity> getPage(Map<String, Object> params,
            int pageNum, int pageSize) {
        return getPackageTimeDao.getPage(params, pageNum, pageSize);
    }

    @Override
    public int getPageTotalCount(Map<String, Object> params) {
        return getPackageTimeDao.getPageTotalCount(params);
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return getPackageTimeDao.deleteById(id);
    }

    @Override
    public int updateStatusById(Long id, int status) {
        return getPackageTimeDao.updateStatusById(id, status);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
    @Override
    public Pagination<GetPackageTimeEntity> getPagination(
            Map<String, Object> params, Page page, Sort... sorts) {
        Pagination<GetPackageTimeEntity> pageInfo = getPackageTimeDao.getPagination(params, page, sorts);
        if(pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())){
            ICache<String, String> cache = getDictDataValueNameCache();
            for (GetPackageTimeEntity entity : pageInfo.getList()) {
                String qualifiedGetPackageTime1Name = cache.get(BaseOrgConstant.TYPE_ALIAS_QUALIFIED_GETPACKAGE_TIME + BaseOrgConstant.SPLIT_SEPARATOR + entity.getQualifiedGetPackageTime1());
                entity.setQualifiedGetPackageTime1Name(qualifiedGetPackageTime1Name);
                //获取门店列表
              /*  List<BaseOrgEntity> orgList = getPackageTimeReleDao.getOrgByConfigCode(entity.getConfigCode());
                if(CollectionUtils.isNotEmpty(orgList)){
                    String orgNames = "";
                    List<String> siteCodeList = new ArrayList<String>();
                    for (BaseOrgEntity baseOrgEntity : orgList) {
                        orgNames += "," + baseOrgEntity.getOrgName();
                        siteCodeList.add(baseOrgEntity.getOrgCode());
                    }
                    entity.setSiteNames(orgNames.substring(1));
                    entity.setSiteCodeList(siteCodeList);
                }*/
            }
        }

        return pageInfo;
    }

    /**
     * 逻辑删除揽件时效设置
     */
    @Transactional
    @Override
    public void deleteGetPackage(Long configId) {
        // 判定ID是否为空
        if (configId == null) {
            throw new BusinessException("ID为空");
        }
        getPackageTimeDao.logicalDeleteById(configId);
    }

    /**
     * 修改揽件时效设置
     * @param baseConfigVo
     * @param currentUserCode
     * @return
     */
    @Transactional
    @Override
    public GetPackageTimeEntity editPackageTime(GetPackageTimeVo getPackageTimeVo, String currentUserCode) {
        GetPackageTimeEntity oldEntity = getPackageTimeDao.getById(getPackageTimeVo.getConfigId());
        BeanUtils.copyProperties(getPackageTimeVo, oldEntity);
        oldEntity.setModifyTime(new Date());
        oldEntity.setModifyUserCode(currentUserCode);
        getPackageTimeDao.update(oldEntity);
        getPackageTimeReleDao.deleteAllReles(oldEntity.getConfigCode());
        batchInsertReles(getPackageTimeVo.getSiteCodeList(), oldEntity.getConfigCode());
        return oldEntity;
    }

    /**
     * 新增揽件时效设置
     * @param baseConfigVo
     * @param currentUserCode
     * @return
     */
    @Override
    @Transactional
    public GetPackageTimeEntity insertPackageTime(GetPackageTimeVo packageTimeVo, String currentUserCode) {
        GetPackageTimeEntity packageTimeEntity = new GetPackageTimeEntity();

        if (null != packageTimeVo) {
            BeanUtils.copyProperties(packageTimeVo, packageTimeEntity);
            packageTimeEntity.setConfigId(BasicEntityIdentityGenerator.getInstance().generateId());
            Date date = new Date();
            packageTimeEntity.setCreateTime(date);
            packageTimeEntity.setCreateUserCode(currentUserCode);
            packageTimeEntity.setModifyTime(date);
            packageTimeEntity.setModifyUserCode(currentUserCode);
            getPackageTimeDao.insert(packageTimeEntity);
            batchInsertReles(packageTimeVo.getSiteCodeList(), packageTimeEntity.getConfigCode());
        }
        return packageTimeEntity;
    }

    /**
     * 批量插入揽件时效门店记录
     * @param siteCodeList
     * @param configCode
     */
    private void batchInsertReles(List<GetPackageTimeReleVo> siteCodeList, String configCode){
        if(CollectionUtils.isNotEmpty(siteCodeList)){
            for (GetPackageTimeReleVo vo : siteCodeList) {
                GetPackageTimeReleEntity rele = new GetPackageTimeReleEntity();
                rele.setConfigId(BasicEntityIdentityGenerator.getInstance().generateId());
                rele.setSiteCode(vo.getSiteCode());
                rele.setConfigCode(configCode);
                getPackageTimeReleDao.insert(rele);
            }
        }
    }

    /**
     * 根据code和name判断揽件时效是否存在
     */
    @Override
    public int uniqueCheckByConfigName(Map<String, Object> map) {
        return getPackageTimeDao.uniqueCheckByConfigName(map);
    }

    /**
     * 根据揽件code判断揽件时效是否存在
     */
    @Override
    public int uniqueCheckByConfigCode(String configCode) {
        return getPackageTimeDao.uniqueCheckByConfigCode(configCode);
    }

    /**
     * 获取数据字典缓存
     * @return
     */
    private ICache<String, String> getDictDataValueNameCache() {
        return CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
    }

    /**
     *批量删除揽件时效记录
     */
    @Transactional
    @Override
    public void batchlogicalDeleteById(List<Long> list) {
        getPackageTimeDao.batchlogicalDeleteById(list);
    }

    /**
     *批量修改揽件时效启用状态
     */
    @Transactional
    @Override
    public void batchUpdateBlFlagById(Map<String, Object> paramMap){
        getPackageTimeDao.batchUpdateBlFlagById(paramMap);
    }

    /**
     * 根据揽件时效编号获取对应的门店集合
     */
    @Override
    public List<BaseSiteEntity> getByConfigCodeAndSites(String code, List<BaseSiteEntity> baseSiteEntities){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("configCode", code);
        paramMap.put("list", baseSiteEntities);
        return getPackageTimeReleDao.getByConfigCodeAndSites(paramMap);
    }

    /**
     * 根据揽件时效编号获取对应的门店集合
     */
    @Override
    public Map<String, Object> getSitesByPackageTime(String configCode){
        Map<String, Object> siteMap = new HashMap<String, Object>();
        List<BaseSiteEntity> siteList = getPackageTimeReleDao.getSiteByConfigCode(configCode);
        if(CollectionUtils.isNotEmpty(siteList)){
            String siteNames = "";
            List<GetPackageTimeReleVo> siteCodeList = new ArrayList<GetPackageTimeReleVo>();
            for (BaseSiteEntity baseSiteEntity : siteList) {
                siteNames += "，" + baseSiteEntity.getSiteNameShort();
                GetPackageTimeReleVo vo = new GetPackageTimeReleVo();
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
}
