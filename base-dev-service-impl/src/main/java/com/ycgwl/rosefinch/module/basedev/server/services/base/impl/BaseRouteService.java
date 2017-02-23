package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseRouteDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseRouteDetailDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRouteService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRouteDetailEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRouteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRouteDetailVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRouteVo;

/**
 *
 * @Title:T_BASE_ROUTE表Service的实现
 * @Description:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月28日 下午4:01
 *
 */
@Service
public class BaseRouteService implements IBaseRouteService {
    @Autowired
    private IBaseRouteDao baseRouteDao;

    @Autowired
    private IBaseRouteDetailDao baseRouteDetailDao;

    // 缓存接口
    @Autowired
    private IBaseRedisCacheService baseRedisCacheService;

    @Override
    public int deleteById(Long id) throws BusinessException {
        return 0;
    }

    @Transactional
    @Override
    public List<BaseRouteEntity> get(Map<String, Object> param)
            throws BusinessException {
        return baseRouteDao.get(param);
    }

    @Override
    public BaseRouteEntity getById(Long id) throws BusinessException {
        return baseRouteDao.getById(id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<BaseRouteEntity> getPage(Map<String, Object> params,
            int pageNum, int pageSize) throws BusinessException {
        return baseRouteDao.getPage(params, pageNum, pageSize);
    }

    @Override
    public int getPageTotalCount(Map<String, Object> param)
            throws BusinessException {
        return baseRouteDao.getPageTotalCount(param);
    }

    /**
     * 获取机构缓存
     * @return
     */
    private ICache<String, BaseOrgEntity> getBaseOrgEntityCache() {
        return CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
    }

    /**
     * 获取产品缓存
     * @return
     */
    private ICache<String, BaseProductEntity> getBaseProductEntityCache() {
        return CacheManager.getInstance().getCache(BaseCacheConstant.PRODUCT_CATCHE_UUID);
    }

    /**
     * 获取网点缓存
     * @return
     */
    private ICache<String, BaseSiteEntity> getSiteInfoEntityCache() {
        return CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
    }

    private ICache<String, String> getDictDataValueNameCache() {
        return CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
    }

    /**
     * 分页查询路由信息
     * @author caijue
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public Pagination<BaseRouteEntity> getPagination(
            Map<String, Object> params, Page page, Sort... sorts)
            throws BusinessException {
        Pagination<BaseRouteEntity> pageInfo = baseRouteDao.getPagination(params, page, sorts);
        Map<String, String> productMap = new HashMap<String, String>();
        if(pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())){
            for (BaseRouteEntity entity : pageInfo.getList()) {
                //所属机构
                if(StringUtils.isNotBlank(entity.getOwnerOrg())){
                    //从缓存获取组织机构
                    BaseOrgEntity org = getBaseOrgEntityCache().get(entity.getOwnerOrg());
                    if(org != null){
                        entity.setOwnerOrgName(org.getOrgName());
                    }
                }
                //产品类型
                if(StringUtils.isNotBlank(entity.getProductCode())){
                    if(StringUtils.isNotBlank(productMap.get(entity.getProductCode()))){
                        entity.setProductName(productMap.get(entity.getProductCode()));
                    } else {
                        BaseProductEntity product = getBaseProductEntityCache().get(entity.getProductCode());
                        if(product != null){
                            entity.setProductName(product.getProductName());
                        } else {
                            //从数据库读取产品类型
                            entity.setProductName(baseRouteDao.getProductByCode(entity.getProductCode()));
                        }
                        if(StringUtils.isNotBlank(entity.getProductName())){
                            productMap.put(entity.getProductCode(), entity.getProductName());
                        }
                    }
                }
                //起始网点
                if(StringUtils.isNotBlank(entity.getBeginSiteCode())){
                    BaseSiteEntity baseSite = getSiteInfoEntityCache().get(entity.getBeginSiteCode());
                    if(baseSite != null){
                        entity.setBeginSiteName(baseSite.getSiteNameShort());
                    }
                }
                //目的网点
                if(StringUtils.isNotBlank(entity.getEndSiteCode())){
                    BaseSiteEntity baseSite = getSiteInfoEntityCache().get(entity.getEndSiteCode());
                    if(baseSite != null){
                        entity.setEndSiteName(baseSite.getSiteNameShort());
                    }
                }
                //起始行政区域
                String[] begionRegions = getRegionInfo(entity.getBeginRegionCode());
                if(begionRegions != null){
                    entity.setBeginRegionName(begionRegions[0]);
                    entity.setBeginRegionCodeStr(begionRegions[1]);
                }
                //到达行政区域
                String[] endRegions = getRegionInfo(entity.getEndRegionCode());
                if(endRegions != null){
                    entity.setEndRegionName(endRegions[0]);
                    entity.setEndRegionCodeStr(endRegions[1]);
                }
            }
        }
        return pageInfo;
    }

    /**
     * 根据regionCode拼接行政区域名称
     * @author caijue
     * @param regionCode
     * @return
     */
    private String[] getRegionInfo(String regionCode) {
        if(StringUtils.isNotBlank(regionCode)){
            List<BaseRegionEntity> regionList = baseRedisCacheService.getBaseRegionListCascadeFromCache(regionCode);

            if (CollectionUtils.isNotEmpty(regionList)) {
                String regionName = "";
                String regionCodeStr = "";
                int i = 0;

                for (BaseRegionEntity baseRegionEntity : regionList) {
                    if (i > 0) {
                        regionName += "-";
                        regionName += baseRegionEntity.getRegionName();

                        regionCodeStr += "-";
                        regionCodeStr += baseRegionEntity.getRegionCode();
                    }else {
                        regionName += baseRegionEntity.getRegionName();
                        regionCodeStr += baseRegionEntity.getRegionCode();
                    }
                    i++;
                }
                return new String[]{regionName, regionCodeStr};
            }
        }
        return null;
    }

    @Transactional
    @Override
    public int insert(BaseRouteEntity t) throws BusinessException {
        return baseRouteDao.insert(t);
    }

    @Transactional
    @Override
    public int update(BaseRouteEntity t) throws BusinessException {
        return baseRouteDao.update(t);
    }

    @Transactional
    @Override
    public int updateStatusById(Long arg0, int arg1) throws BusinessException {
        return 0;
    }

    /**
     * 编辑路由信息
     * @author caijue
     * @param baseRouteVo，currentUserCode当前登录用户
     */
    @Transactional
    @Override
    public BaseRouteEntity modifyBaseRoute(BaseRouteVo baseRouteVo,
            String currentUserCode) {
        BaseRouteEntity oldEntity = baseRouteDao.getById(baseRouteVo.getId());
        BeanUtils.copyProperties(baseRouteVo, oldEntity);
        oldEntity.setModifyTime(new Date());
        oldEntity.setModifyUserCode(currentUserCode);
        baseRouteDetailDao.deleteAllByRouteCode(oldEntity.getRouteCode());
        batchCreateDetail(baseRouteVo.getDetailList(), oldEntity);
        baseRouteDao.update(oldEntity);
        return oldEntity;
    }

    /**
     * 添加路由信息
     * @author caijue
     * @param baseRouteVo，currentUserCode当前登录用户
     * @return 返回当前路由实体
     */
    @Transactional
    @Override
    public BaseRouteEntity createBaseRoute(BaseRouteVo baseRouteVo,
            String currentUserCode) {
        BaseRouteEntity baseRouteEntity = new BaseRouteEntity();
        if (null != baseRouteVo) {
            BeanUtils.copyProperties(baseRouteVo, baseRouteEntity);
            // ID
            baseRouteEntity.setId(BasicEntityIdentityGenerator.getInstance()
                    .generateId());
            baseRouteEntity.setDelFlag(0);
            Date date = new Date();
            // 创建时间、创建人
            baseRouteEntity.setCreateTime(date);
            baseRouteEntity.setCreateUserCode(currentUserCode);
            // 修改时间、修改人
            baseRouteEntity.setModifyTime(date);
            baseRouteEntity.setModifyUserCode(currentUserCode);
            // 添加路由主表记录
            baseRouteDao.insert(baseRouteEntity);
            // 添加路由明细记录
            batchCreateDetail(baseRouteVo.getDetailList(), baseRouteEntity);
            // 更新路由总停留时间、总路程、途经站数、总时间
            baseRouteDao.update(baseRouteEntity);
        }
        return baseRouteEntity;
    }

    /**
     * 添加路由明细
     * @author caijue
     * @param detailVoList
     * @param baseRouteEntity
     */
    private void batchCreateDetail(List<BaseRouteDetailVo> detailVoList,
            BaseRouteEntity baseRouteEntity) {
        Long totalStayTime = 0L;// 总停留时间
        BigDecimal totalMileage = BigDecimal.ZERO;// 总路程
        Long totalTime = 0L;// 总时间
        int i = 0;
        if (CollectionUtils.isNotEmpty(detailVoList)) {
//            List<BaseRouteDetailEntity> list = new ArrayList<BaseRouteDetailEntity>();
            for (BaseRouteDetailVo baseRouteDetailVo : detailVoList) {
                BaseRouteDetailEntity entity = new BaseRouteDetailEntity();
                BeanUtils.copyProperties(baseRouteDetailVo, entity);
                entity.setId(BasicEntityIdentityGenerator.getInstance()
                        .generateId());
                entity.setRouteCode(baseRouteEntity.getRouteCode());
//                entity.setOrderBy(i+1);
                if(entity.getStayTime() == null){
                    entity.setStayTime(0L);
                }
                if(entity.getArriveTime() == null){
                    entity.setArriveTime(0L);
                }
                if(entity.getArriveMileage() == null){
                    entity.setArriveMileage(0D);
                }
//                list.add(entity);
                baseRouteDetailDao.insert(entity);
                totalStayTime += entity.getStayTime();// 总停留时间
                totalTime += entity.getArriveTime()
                        + entity.getStayTime();// 总时间=行驶时间+停留时间
                totalMileage = totalMileage.add(new BigDecimal(Double
                        .toString(entity.getArriveMileage())));// 总路程
                i++;
            }
            // 批量插入路由明细记录
//            baseRouteDetailDao.insertBatchDetail(list);
        }
        baseRouteEntity.setTotalStayTime(totalStayTime);// 总停留时间
        baseRouteEntity.setTotalMileage(totalMileage.setScale(2,
                BigDecimal.ROUND_HALF_UP).doubleValue());// 总路程
        baseRouteEntity.setSiteCount(i - 2);// 途经站数
        baseRouteEntity.setTotalTime(totalTime);// 总时间
    }

    /**
     * 删除单个路由记录
     * @author caijue
     * @param 路由ID
     */
    @Transactional
    @Override
    public void removeBaseRoute(Long routeId) {
        baseRouteDao.deleteByRouteId(routeId);
    }

    /**
     * 批量删除路由记录
     * @author caijue
     * @param list需要删除的id集合
     */
    @Transactional
    @Override
    public void removeBaseRouteByIdList(List<Long> list) {
        baseRouteDao.deleteByRouteIdList(list);
    }

    /**
     * 批量修改路由状态：停用、启用
     * @author caijue
     * @param list要修改的id集合， blFlag操作：启用、禁用
     */
    @Transactional
    @Override
    public void modifyBlFlagByIdList(Map<String, Object> paramMap) {
        baseRouteDao.updateBlFlagByMap(paramMap);
    }

    /**
     * 根据routeCode获取路由明细
     * @author caijue
     * @param routeCode 路由CODE
     * @return 返回路由明细记录
     */
    @Override
    public List<BaseRouteDetailEntity> findBaseRouteDetailListByRouteCode(String routeCode){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("routeCode", routeCode);
//        List<BaseRouteDetailEntity> list = baseRouteDetailDao.get(paramMap);
        //根据orderBy升序排序
        List<BaseRouteDetailEntity> list = baseRouteDetailDao.queryBaseRouteDetailListByRouteCode(routeCode);
        if(CollectionUtils.isNotEmpty(list)){
            for (BaseRouteDetailEntity entity : list) {
                //当前网点
                if(StringUtils.isNotBlank(entity.getCurrSiteCode())){
                    BaseSiteEntity baseSite = getSiteInfoEntityCache().get(entity.getCurrSiteCode());
                    if(baseSite != null){
                        entity.setCurrSiteName(baseSite.getSiteNameShort());
                    }
                }
                //下一网点
                if(StringUtils.isNotBlank(entity.getNextSiteCode())){
                    BaseSiteEntity baseSite = getSiteInfoEntityCache().get(entity.getNextSiteCode());
                    if(baseSite != null){
                        entity.setNextSiteName(baseSite.getSiteNameShort());
                    }
                }
                //当前行政区域
                String[] currRegions = getRegionInfo(entity.getCurrRegionCode());
                if(currRegions != null){
                    entity.setCurrRegionName(currRegions[0]);
                    entity.setCurrRegionCodeStr(currRegions[1]);
                }

                //当前行政区域
                String[] nextRegions = getRegionInfo(entity.getNextRegionCode());
                if(nextRegions != null){
                    entity.setNextRegionName(nextRegions[0]);
                    entity.setNextRegionCodeStr(nextRegions[1]);
                }
                ICache<String, String> cache = getDictDataValueNameCache();
                //途经类型-BASE_VEHICLE_ALL_TYPE
                if(entity.getViaType() != null){
                    entity.setViaTypeName(cache.get(BaseOrgConstant.BASE_VEHICLE_VIA_TYPE + BaseOrgConstant.SPLIT_SEPARATOR + entity.getViaType()));
                }
                //运输类型
                if(entity.getClassType() != null){
                    entity.setClassTypeName(cache.get(BaseOrgConstant.BASE_ROUTE_CLASSTYPE + BaseOrgConstant.SPLIT_SEPARATOR + entity.getClassType()));
                }
            }
        } else {
            list = new ArrayList<BaseRouteDetailEntity>();
        }
        return list;
    }

    /**
     * 校验路由名称是否存在
     * @author caijue
     * @param 路由ID，路由名称
     * @return 返回匹配的条数
     */
    @Override
    public int uniqueCheckByRouteName(Long id, String routeName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("routeName", routeName);
        map.put("id", id);
        return baseRouteDao.uniqueCheckByRouteName(map);
    }

    /**
     * 根据路由编号判断是否存在
     */
    @Override
    public int uniqueCheckByRouteCode(String routeCode) {
        return baseRouteDao.uniqueCheckByRouteCode(routeCode);
    }
}
