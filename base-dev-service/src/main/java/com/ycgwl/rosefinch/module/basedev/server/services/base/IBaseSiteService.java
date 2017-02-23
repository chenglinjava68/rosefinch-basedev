package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseSiteVo;

/**
 *
 * @author guoh.mao
 *
 */
public interface IBaseSiteService extends IBaseService<BaseSiteEntity, Long>{
	/**
	 * 通过网点名称模糊查询
	 *
	 * @param siteName
	 * @return
	 * @author guoh.mao
	 */
	List<BaseSiteEntity> fuzzyQueryBySiteName(String siteName);

	/**
	 * 设置临时属性（非数据库字段）值
	 *
	 * @param baseSiteEntity
	 * @author guoh.mao
	 */
	BaseSiteEntity constructFullBaseSiteEntity(BaseSiteEntity baseSiteEntity);

	/**
	 * 分页获取所属财务中心
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseSiteEntity> getUpFinanceCenterList(QueryPageVo queryPageVo);

	/**
	 * 分页获取账单财务中心
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseSiteEntity> getUpSettleCenterList(QueryPageVo queryPageVo);

	/**
	 * 分页查询
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseSiteEntity> getPage(QueryPageVo queryPageVo);

	/**
	 * 级联查询当前登录用户所属网点及所有下级网点
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseSiteEntity> queryOwnerSiteCascade(QueryPageVo queryPageVo, String ownerSite);

	Pagination<BaseSiteEntity> getSitePageListByCondition(QueryPageVo queryPageVo, String userName);

	Pagination<BaseSiteEntity> getUpFinaceCenterPage(QueryPageVo queryPageVo, String userName);

	/**
     * 下一站是网点并且是中心发网点
     * @param map
     * @return
     */
    List <HashMap<String,String>> isFristWebsite(Map<String,String> map) throws Exception;



    /**
     * TODO: DOCUMENT ME!
     *
     * @param queryPageVo
     * @return
     * @author guoh.mao
     */
    Pagination<BaseSiteEntity> getPaginationBaseSiteList(QueryPageVo queryPageVo);

    /**
     *
     * TODO: DOCUMENT ME!
     *
     * @param baseSiteVo
     * @return
     * @author guoh.mao
     */
    void insertBaseSite(BaseSiteVo baseSiteVo, String createUserCode) throws BusinessException;

    int uniqueCheckBySiteCode(String siteCode);
	int uniqueCheckBySiteName(Map<String, Object> map);
	int uniqueCheckBySiteNameShort(Map<String, Object> map);

	Pagination<BaseSiteEntity> queryBaseSite(Map<String, Object> paraMap, Page page, Sort[] sorts);

	void updateBaseSite(BaseSiteVo baseSiteVo, String createUserCode) throws BusinessException;

	int batchUpdateBlFlag(Integer blFlag, List<String> codes, String currentUserCode);

	List<BaseSiteEntity> getSiteListByName(String siteName)throws BusinessException;
	BaseSiteEntity getBySiteNameShort(String siteNameShort) ;

	List<BaseSiteEntity> queryOwnerSiteCascade2(String ownerSite);

	List<BaseSiteEntity> getBaseSiteCodeList(String siteCode);
	//通过片区编号集合获取门店信息
	List<BaseSiteEntity> getBaseSiteByAreaCode(Map<String, Object> params);

	List<BaseSiteEntity> getCalculateTotalAmountBaseSite(Map<String, Object> params);

	BaseSiteEntity getAffiliatedCompany(String siteCode);
	
	/**
	 * 关联员工
	 *
	 * @param siteCode
	 * @param empCodes
	 * @author guoh.mao
	 */
	void assosiateToEmployee(String siteCode, String empCodes);
	
	
	/**
	 * 通过门店编码获取门店
	 *
	 * @param siteCode
	 * @return
	 * @author guoh.mao
	 */
	BaseSiteEntity getByCode(String siteCode);
}
