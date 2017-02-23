package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.math.BigDecimal;
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
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseCarLineDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseCarLineDetailDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseCarLineService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCarLineDetail;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCarLineEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseCarLineDetailVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseCarLineVo;
@Service
public class BaseCarLineService implements IBaseCarLineService {

	@Autowired
	private IBaseCarLineDao baseCarLineDao;
	// 缓存接口
    @Autowired
    private IBaseRedisCacheService baseRedisCacheService;
    @Autowired
    private IBaseCarLineDetailDao baseCarLineDetailDao;

	@Transactional
	@Override
	public int insert(BaseCarLineEntity t) {
		return baseCarLineDao.insert(t);
	}
	@Transactional
	@Override
	public int update(BaseCarLineEntity t) {
		return baseCarLineDao.update(t);
	}

	@Override
	public BaseCarLineEntity getById(Long id) {
		return baseCarLineDao.getById(id);
	}
	@Override
	public List<BaseCarLineEntity> get(Map<String, Object> params) {
		return baseCarLineDao.get(params);
	}
	@Transactional
	@Override
	public List<BaseCarLineEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
		return baseCarLineDao.getPage(params, pageNum, pageSize);
	}

	@Override
	public int getPageTotalCount(Map<String, Object> params) {
		return baseCarLineDao.getPageTotalCount(params);
	}

	@Override
	public int deleteById(Long id) {
		return baseCarLineDao.deleteById(id);
	}
	@Transactional
	@Override
	public int updateStatusById(Long id, int status) {
		return baseCarLineDao.updateStatusById(id, status);
	}

	private ICache<String, BaseSiteEntity> getBaseSiteEntityCache() {
		return CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
	}
	private ICache<String, String> getDictDataValueNameCache() {
        ICache<String, String> cache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
        return cache;
    }
    private ICache<String, BaseOrgEntity> getBaseOrgEntityCache() {
        return CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	@Override
	public Pagination<BaseCarLineEntity> getPagination(Map<String, Object> map, Page page, Sort... sorts)throws BusinessException {
		ICache<String, String> cache = getDictDataValueNameCache();
		Pagination<BaseCarLineEntity> pageInfo = baseCarLineDao.getPagination(map, page, sorts);

		if(pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())){
			for (BaseCarLineEntity entity : pageInfo.getList()) {
				 //车线类型
                if(entity.getLineType()!=null){
                	entity.setCarLineTypeName(cache.get(BaseOrgConstant.TYPE_ALIAS_BASE_CARLINE_LINE_TYPE + BaseOrgConstant.SPLIT_SEPARATOR + entity.getLineType()));
                }
			   //所属机构
               if(StringUtils.isNotBlank(entity.getOwnerOrg())){
                //从缓存获取组织机构
                 BaseOrgEntity org = getBaseOrgEntityCache().get(entity.getOwnerOrg());
                if(org != null){
                    entity.setOwnerOrgName(org.getOrgName());
                 }
               }

             //起始网点
               if(StringUtils.isNotBlank(entity.getBeginSiteCode())){
                   BaseSiteEntity baseSite = getBaseSiteEntityCache().get(entity.getBeginSiteCode());
                   if(baseSite != null){
                       entity.setBeginSiteName(baseSite.getSiteNameShort());
                   }
               }
               //目的网点
               if(StringUtils.isNotBlank(entity.getEndSiteCode())){
                   BaseSiteEntity baseSite = getBaseSiteEntityCache().get(entity.getEndSiteCode());
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
	public BaseCarLineEntity insertCarLine(BaseCarLineVo vo, String currentUserCode) {
		BaseCarLineEntity carLine = new BaseCarLineEntity();
	        if (null != vo) {
	            BeanUtils.copyProperties(vo, carLine);
	            carLine.setId(BasicEntityIdentityGenerator.getInstance().generateId());
	            carLine.setDelFlag(0);
	            Date date = new Date();
	            carLine.setCreateTime(date);
	            carLine.setCreateUserCode(currentUserCode);
	            carLine.setModifyTime(date);
	            carLine.setModifyUserCode(currentUserCode);
	            baseCarLineDao.insert(carLine);
	            // 添加车线明细记录
	            batchCreateDetail(vo.getDetailList(), carLine);
	            // 更新车线总停留时间、总路程、途经站数、总时间
	            baseCarLineDao.update(carLine);
	        }
	        return carLine;
	}

    /**
     * 添加车线明细
     *
     * @param detailVoList
     * @param baseCarLineEntity
     */
    private void batchCreateDetail(List<BaseCarLineDetailVo> detailVoList, BaseCarLineEntity baseCarLineEntity) {
        Long totalStayTime = 0L;// 总停留时间
        BigDecimal totalMileage = BigDecimal.ZERO;// 总路程
        Long totalTime = 0L;// 总时间
        int i = 0;
        if (CollectionUtils.isNotEmpty(detailVoList)) {
            for (BaseCarLineDetailVo baseCarLineDetailVo : detailVoList) {
                BaseCarLineDetail entity = new BaseCarLineDetail();
                BeanUtils.copyProperties(baseCarLineDetailVo, entity);
                entity.setId(BasicEntityIdentityGenerator.getInstance()
                        .generateId());
                entity.setLineCode(baseCarLineEntity.getLineCode());
//               entity.setOrderBy(i+1);
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
                baseCarLineDetailDao.insert(entity);
                totalStayTime += entity.getStayTime();// 总停留时间
                totalTime += entity.getArriveTime() + entity.getStayTime();// 总时间=行驶时间+停留时间
                totalMileage = totalMileage.add(new BigDecimal(Double.toString(entity.getArriveMileage())));// 总路程
                i++;
            }
        }
        baseCarLineEntity.setTotalStayTime(totalStayTime);// 总停留时间
        baseCarLineEntity.setTotalMileage(totalMileage.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());// 总路程
        baseCarLineEntity.setSiteCount(i - 2);// 途经站数
        baseCarLineEntity.setTotalTime(totalTime);// 总时间
    }
	@Override
	public int uniqueCheckByLineName(Long id, String lineName) {
		 Map<String, Object> map = new HashMap<String, Object>();
	        map.put("lineName", lineName);
	        map.put("id", id);

		return baseCarLineDao.uniqueCheckByLineCode(map);
	}
	@Override
	public void removeBaseCarLine(Long id) {
		baseCarLineDao.removeBaseCarLine(id);
	}
	@Override
	public void removeBaseCarLineByIdList(List<Long> idList) {
		baseCarLineDao.removeBaseCarLineByIdList(idList);
	}

	@Override
	public List<BaseCarLineDetail> findBaseCarLineDetailListByLineId(String lineCode) {
		  Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("lineCode", lineCode);
	      //根据orderBy升序排序
	        List<BaseCarLineDetail> list = baseCarLineDetailDao.queryBaseCarLineDetailListByLineId(lineCode);
	        if(CollectionUtils.isNotEmpty(list)){
	            for (BaseCarLineDetail entity : list) {
	                //当前网点
	                if(StringUtils.isNotBlank(entity.getCurrSiteCode())){
	                    BaseSiteEntity baseSite = getBaseSiteEntityCache().get(entity.getCurrSiteCode());
	                    if(baseSite != null){
	                        entity.setCurrSiteName(baseSite.getSiteNameShort());
	                    }
	                }
	                //下一网点
	                if(StringUtils.isNotBlank(entity.getNextSiteCode())){
	                    BaseSiteEntity baseSite = getBaseSiteEntityCache().get(entity.getNextSiteCode());
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
	        }
	        return list;
	}
	 /**
     * 编辑车线信息
     */
	@Override
	public BaseCarLineEntity modifyBaseCarLine(BaseCarLineVo baseCarLineVo, String currentUserCode) {
		 BaseCarLineEntity oldEntity = baseCarLineDao.getById(baseCarLineVo.getId());
	        BeanUtils.copyProperties(baseCarLineVo, oldEntity);
	        oldEntity.setModifyTime(new Date());
	        oldEntity.setModifyUserCode(currentUserCode);
	        baseCarLineDetailDao.deleteAllByCarLineId(oldEntity.getLineCode());
	        batchCreateDetail(baseCarLineVo.getDetailList(), oldEntity);
	        baseCarLineDao.update(oldEntity);
	        return oldEntity;

	}
	@Override
	public int uniqueCheckBylineCode(String lineCode) {
		 return baseCarLineDao.uniqueCheckBylineCode(lineCode);
	}
	/**
     * 批量修改路由状态：停用、启用
     * @author
     * @param list要修改的id集合， blFlag操作：启用、禁用
     */
    @Transactional
	@Override
	public void modifyBlFlagByIdList(Map<String, Object> paramMap) {
	        baseCarLineDao.updateBlFlagByMap(paramMap);
	}
}
