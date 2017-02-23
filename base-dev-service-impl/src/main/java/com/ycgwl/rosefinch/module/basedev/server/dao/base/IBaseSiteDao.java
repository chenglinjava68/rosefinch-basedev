package com.ycgwl.rosefinch.module.basedev.server.dao.base;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;

public interface IBaseSiteDao extends IBaseDao<BaseSiteEntity, Long> {

	List<BaseSiteEntity> fuzzyQueryBySiteName(String siteName);
	void deleteBySiteCode(String siteCode);
	Pagination<BaseSiteEntity> getUpSettleCenterList(Map<String, Object> paraMap, Page page, Sort[] sorts);
	
	/**
	 * 所属财务中心
	 *
	 * @param paraMap
	 * @param page
	 * @param sorts
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseSiteEntity> getUpFinanceCenterList(Map<String, Object> paraMap, Page page, Sort[] sorts);
	

	/**
	 * 级联查询当前登录用户所属网点及所有下级网点
	 *
	 * @param paraMap
	 * @param page
	 * @param sorts
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseSiteEntity> queryOwnerSiteCascade(Map<String, Object> paraMap, Page page, Sort[] sorts);
	Pagination<BaseSiteEntity> getSitePageListByCondition(Map<String, Object> map, Page page, Sort[] sorts);

	/**
     * 下一站是网点并且是中心发网点
     * @param map
     * @return
     */
    List <HashMap<String,String>> isFristWebsite(Map<String,String> map) throws Exception;



    Pagination<BaseSiteEntity> getPaginationBaseSiteList(Map<String, Object> paraMap, Page page, Sort[] sorts);



    int uniqueCheckBySiteCode(String siteCode);
	int uniqueCheckBySiteName(Map<String, Object> map);
	int uniqueCheckBySiteNameShort(Map<String, Object> map);

	Pagination<BaseSiteEntity> queryBaseSite(Map<String, Object> paraMap, Page page, Sort[] sorts);


	int optimisticLock(BaseSiteEntity baseSiteEntity);


	int batchUpdateBlFlag(Map<String, Object> map);
	BaseSiteEntity getBySiteNameShort(String siteNameShort);
	List<BaseSiteEntity> queryOwnerSiteCascade2(Map<String, Object> map);
	List<BaseSiteEntity> getBaseSiteCodeList(String siteCode);
	List<BaseSiteEntity> getBaseSiteByAreaCode(Map<String, Object> params);
	List<BaseSiteEntity> getCalculateTotalAmountBaseSite(Map<String, Object> params);

	/**
	 * 获取门店所属分公司
	 *
	 * @param siteCode
	 * @return
	 * @author guoh.mao
	 */
	BaseSiteEntity getAffiliatedCompany(String siteCode);

	/**
	 * 根据编号或简称查询门店
	 *
	 * @param map
	 * @return
	 */
	List<BaseSiteEntity> queryBySiteNameOrCode(Map<String, Object> params);

	List<String> queryChildSitesBySiteCode(String siteCode);
}
