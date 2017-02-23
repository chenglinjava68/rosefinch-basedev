package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.ycgwl.framework.shared.util.string.StringUtil;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseEmployeeDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseOrgDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteDetailDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteFinLineDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseOrgService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAreaEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteDetailEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteFinLineEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseSiteDetailVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseSiteVo;
import com.ycgwl.rosefinch.module.organization.shared.domain.EmployeeEntity;

@Service
public class BaseSiteService implements IBaseSiteService {
	@Autowired
	private IBaseSiteDao baseSiteDao;
	@Autowired
	private IBaseSiteDetailDao baseSiteDetailDao;
	@Autowired
	private IBaseOrgDao baseOrgDao;
	@Autowired
	private IBaseSiteFinLineDao baseSiteFinLineDao;
	@Autowired
	private IBaseOrgService baseOrgService;
	@Autowired
	private IBaseRedisCacheService baseRedisCacheService;
	@Autowired
	private IBaseEmployeeDao baseEmployeeDao;

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int deleteById(Long arg0) {
		return baseSiteDao.deleteById(arg0);
	}

	@Override
	public List<BaseSiteEntity> get(Map<String, Object> arg0) {
		return baseSiteDao.get(arg0);
	}

	@Override
	public BaseSiteEntity getById(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public List<BaseSiteEntity> getPage(Map<String, Object> arg0, int arg1,
			int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public int getPageTotalCount(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> getPagination(Map<String, Object> arg0,
			Page arg1, Sort... arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int insert(BaseSiteEntity arg0) {
		return baseSiteDao.insert(arg0);
	}


	/**
	 * 更新统一采用这个方法
	 * 切记：modifyTime是从前端隐藏域中传过来的，不要手动设值
	 *
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int update(BaseSiteEntity baseSiteEntity) {
		// 先update，若下一步失败则会回滚
		baseSiteDao.update(baseSiteEntity);

		// 更新modify_time为当前系统时间
		int result = baseSiteDao.optimisticLock(baseSiteEntity);
		if (result == 0) {
			throw new BusinessException("数据已过期，请刷新后重试");
		}
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int updateStatusById(Long arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public List<BaseSiteEntity> fuzzyQueryBySiteName(String siteName) {
		return baseSiteDao.fuzzyQueryBySiteName("%" + siteName + "%");
	}

	/**
	 * {@inheritDoc}
	 *
	 * 业务规则：
	 * 若网点类型为：三级网点，则所属财务中心为：网点类型为二级网点的网点；
	 * 若网点类型为：二级网点，则所属财务中心为：网点类型为一级网点的网点；
	 * 若网点类型为：一级网点、一级分拨中心、二级分拨中心或同行，则所属财务中心为：网点类型为二级财务中心的网点；
	 * 若网点类型为：二级财务中心，则所属财务中心为：网点类型为一级财务中心的网点；
	 * 若网点类型为：一级财务中心，则所属财务中心为：快运本部/远成物流（一级财务中心只有 快运本部/远成物流）。一级财务中心不需要手工创建，FIXME
	 * 
	 * modified by guoh.mao
	 * 若网点类型为：一级财务中心，则所属财务中心为：它自己
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> getUpFinanceCenterList(QueryPageVo queryPageVo) {
		Pagination<BaseSiteEntity> pageInfo = null;

		Map<String, Object> map = queryPageVo.getParaMap();
		map.put("delFlag", 0);
		
		Object snsObj = map.get("siteNameShort");
		
		if (null != snsObj) {
			if (StringUtils.isNotBlank(snsObj.toString())) {
				// 网点编号
				map.put("siteCode", snsObj.toString());
			}
		}
		

		// 网点类型不为空
		if (null != map.get("siteType")) {
			Integer siteType = Integer.valueOf(map.get("siteType").toString());

			// 若网点类型为：三级网点，则所属财务中心为：网点类型为二级网点的网点
			if (BaseOrgConstant.THIRD_LEVEL_SITE.equals(siteType)) {
				map.put("siteType", BaseOrgConstant.SECOND_LEVEL_SITE);
				pageInfo = baseSiteDao.getUpFinanceCenterList(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
			}else if (BaseOrgConstant.SECOND_LEVEL_SITE.equals(siteType)) { // 若网点类型为：二级网点，则所属财务中心为：网点类型为一级网点的网点
				map.put("siteType", BaseOrgConstant.FIRST_LEVEL_SITE);
				pageInfo = baseSiteDao.getUpFinanceCenterList(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
			} else if (BaseOrgConstant.FIRST_LEVEL_SITE.equals(siteType)  // // 若网点类型为：一级网点、一级分拨中心、二级分拨中心或同行，则所属财务中心为：网点类型为二级财务中心的网点
					|| BaseOrgConstant.FIRST_LEVEL_DISTRIBUTION_CENTER
					.equals(siteType)
					|| BaseOrgConstant.SECOND_LEVEL_DISTRIBUTION_CENTER
					.equals(siteType)
					|| BaseOrgConstant.COMPETITOR.equals(siteType)) {
				map.put("siteType",
						BaseOrgConstant.SECOND_LEVEL_FINANCE_CENTER);
				pageInfo = baseSiteDao.getUpFinanceCenterList(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
			} else if (BaseOrgConstant.SECOND_LEVEL_FINANCE_CENTER
					.equals(siteType)) {   // 若网点类型为：二级财务中心，则所属财务中心为：网点类型为一级财务中心的网点
				map.put("siteType", BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER);
				pageInfo = baseSiteDao.getUpFinanceCenterList(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
			} else if (BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER
					.equals(siteType)) {/*   // 若网点类型为：一级财务中心，则所属财务中心为：快运本部/远成物流（一级财务中心只有 快运本部/远成物流）。一级财务中心不需要手工创建，FIXME
				List<BaseSiteEntity> list = new ArrayList<BaseSiteEntity>();
				BaseSiteEntity ycExpress = new BaseSiteEntity();
				pageInfo = new Pagination<BaseSiteEntity>();

				ycExpress.setSiteCode(BaseOrgConstant.YC_EXPRESS_TEMP_CODE);
				ycExpress.setSiteName(BaseOrgConstant.YC_EXPRESS_NAME);
				ycExpress.setOrderBy(1);
				list.add(ycExpress);
				pageInfo.setCount(1);
				pageInfo.setTotalPages(1);
				pageInfo.setList(list);
			*/}
		}

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseSiteEntity>();
			pageInfo.setCount(0);
		}

		return pageInfo;
	}

	/**
	 * {@inheritDoc}
	 *
	 * 业务规则：
	 * 若网点类型为：一级分拨中心、二级分拨中心或同行，则账单财务中心为：一级/二级财务中心
	 * 若网点类型为：二/三级网点，则账单财务中心为：网点类型为 二级财务中心 的网点
	 * 若网点类型为：一级网点，则账单财务中心为：网点类型为 二级财务中心 的网点
	 * 若网点类型为：二级财务中心，则账单财务中心为：网点类型为 一级财务中心 的网点
	 * 若网点类型为：一级财务中心，则账单财务中心为：快运本部
	 * 
	 * modified by guoh.mao
	 * 若网点类型为：一级财务中心，则账单财务中心为：它自己
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> getUpSettleCenterList(
			QueryPageVo queryPageVo) {
		Pagination<BaseSiteEntity> pageInfo = null;

		Map<String, Object> map = queryPageVo.getParaMap();
		map.put("delFlag", 0);

		// 网点类型不为空
		if (null != map.get("siteType")) {
			Integer siteType = Integer.valueOf(map.get("siteType").toString());

			if (BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER
					.equals(siteType)) {/*
				List<BaseSiteEntity> list = new ArrayList<BaseSiteEntity>();
				BaseSiteEntity ycExpress = new BaseSiteEntity();
				pageInfo = new Pagination<BaseSiteEntity>();

				ycExpress.setSiteCode(BaseOrgConstant.YC_EXPRESS_TEMP_CODE);
				ycExpress.setSiteName(BaseOrgConstant.YC_EXPRESS_NAME);
				ycExpress.setOrderBy(1);
				list.add(ycExpress);
				pageInfo.setCount(1);
				pageInfo.setTotalPages(1);
				pageInfo.setList(list);
			*/}else {
				pageInfo = baseSiteDao.getUpSettleCenterList(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
			}
		}

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseSiteEntity>();
			pageInfo.setCount(0);
		}

		return pageInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BaseSiteEntity constructFullBaseSiteEntity(BaseSiteEntity baseSiteEntity) {
		// 设置上级部门名称
		setUpSiteName(baseSiteEntity);
		// 设置所属财务中心名称
		setUpFinanceCenterName(baseSiteEntity);
		// 设置账单财务中心名称
		setUpSettleCenterName(baseSiteEntity);

		ICache<String, String> cache = getDictDataValueNameCache();

		// 业务类型
		String siteServicesTypeName = cache
				.get(BaseOrgConstant.TYPE_ALIAS_SITE_SERVICES_TYPE
						+ BaseOrgConstant.SPLIT_SEPARATOR
						+ baseSiteEntity.getSiteServicesType());
		baseSiteEntity.setSiteServicesTypeName(siteServicesTypeName);

		// 网点属性
		String siteKindName = cache.get(BaseOrgConstant.TYPE_ALIAS_SITE_KIND
				+ BaseOrgConstant.SPLIT_SEPARATOR
				+ baseSiteEntity.getSiteKind());
		baseSiteEntity.setSiteKindName(siteKindName);

		// 网点类型
		String siteTypeName = cache.get(BaseOrgConstant.TYPE_ALIAS_SITE_TYPE
				+ BaseOrgConstant.SPLIT_SEPARATOR
				+ baseSiteEntity.getSiteType());
		baseSiteEntity.setSiteTypeName(siteTypeName);


		// 本位币币别
		String defaultCurrencyName = cache.get(BaseOrgConstant.TYPE_ALIAS_DEFAULT_CURRENCY+BaseOrgConstant.SPLIT_SEPARATOR+baseSiteEntity.getDefaultCurrency());
		baseSiteEntity.setDefaultCurrencyName(defaultCurrencyName);

		ICache<String, BaseRegionEntity> regionCache = getBaseRegionEntityCache();
		// 省、市、区（县）
		BaseRegionEntity province = regionCache.get(baseSiteEntity.getProvince());
		if (null != province) {
			baseSiteEntity.setProvinceName(province.getRegionName());
		}
		// 市
		BaseRegionEntity city = regionCache.get(baseSiteEntity.getCity());
		if (null != city) {
			baseSiteEntity.setCityName(city.getRegionName());
		}
		// 区（县）
		BaseRegionEntity district = regionCache.get(baseSiteEntity.getCounty());
		if (null != district) {
			baseSiteEntity.setCountyName(district.getRegionName());
		}

		// 寄件转运中心 名称
		setSendpieceTrancenterName(baseSiteEntity);

		// 到件转运中心 名称
		setArrivepieceTrancenterName(baseSiteEntity);

		// 片区
		setAreaName(baseSiteEntity);

		return baseSiteEntity;
	}

	private void setArrivepieceTrancenterName(BaseSiteEntity baseSiteEntity) {
		if (StringUtils.isNoneBlank(baseSiteEntity.getArrivepieceTrancenter())) {
			ICache<String, BaseSiteEntity> siteCache = getBaseSiteEntityCache();
			BaseSiteEntity site = siteCache.get(baseSiteEntity.getArrivepieceTrancenter());

			if (null != site) {
				baseSiteEntity.setArrivepieceTrancenterName(site.getSiteNameShort());
			}
		}
	}

	private void setSendpieceTrancenterName(BaseSiteEntity baseSiteEntity) {
		if (StringUtils.isNoneBlank(baseSiteEntity.getSendpieceTrancenter())) {
			ICache<String, BaseSiteEntity> siteCache = getBaseSiteEntityCache();
			BaseSiteEntity site = siteCache.get(baseSiteEntity.getSendpieceTrancenter());

			if (null != site) {
				baseSiteEntity.setSendpieceTrancenterName(site.getSiteNameShort());
			}
		}
	}


	private void setUpSettleCenterName(BaseSiteEntity baseSiteEntity) {
		if (StringUtils.isNoneBlank(baseSiteEntity.getUpSettleCenter())) {
			ICache<String, BaseSiteEntity> siteCache = getBaseSiteEntityCache();
			BaseSiteEntity site = siteCache.get(baseSiteEntity.getUpSettleCenter());

			if (null != site) {
				baseSiteEntity.setUpSettleCenterName(site.getSiteNameShort());
			}
		}
	}

	private void setUpFinanceCenterName(BaseSiteEntity baseSiteEntity) {
		if (StringUtils.isNoneBlank(baseSiteEntity.getUpFinanceCenter())) {
			ICache<String, BaseSiteEntity> siteCache = getBaseSiteEntityCache();
			BaseSiteEntity site = siteCache.get(baseSiteEntity.getUpFinanceCenter());

			if (null != site) {
				baseSiteEntity.setUpFinanceCenterName(site.getSiteNameShort());
			}
		}
	}

	private void setUpSiteName(BaseSiteEntity baseSiteEntity) {
		//-------------设置上级部门名称------------
		if (StringUtils.isNoneBlank(baseSiteEntity.getUpSite())) {
			ICache<String, BaseSiteEntity> siteCache = getBaseSiteEntityCache();
			BaseSiteEntity site = siteCache.get(baseSiteEntity.getUpSite());

			if (null != site) {
				baseSiteEntity.setUpSiteName(site.getSiteNameShort());
			}
		}
	}

	private void setAreaName(BaseSiteEntity baseSiteEntity) {
		if (StringUtils.isNoneBlank(baseSiteEntity.getAreaCode())) {
			ICache<String, BaseAreaEntity> cache = getBaseAreaEntityCache();
			BaseAreaEntity baseAreaEntity = cache.get(baseSiteEntity.getAreaCode());

			if (null != baseAreaEntity) {
				baseSiteEntity.setAreaName(baseAreaEntity.getAreaName());
			}
		}
	}


	private ICache<String, BaseRegionEntity> getBaseRegionEntityCache() {
		ICache<String, BaseRegionEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_CATCHE_UUID);
		return cache;
	}


	/**
	 * 获取数据项名称缓存
	 *
	 * @return
	 * @author guoh.mao
	 */
	private ICache<String, String> getDictDataValueNameCache() {
		ICache<String, String> cache = CacheManager.getInstance()
				.getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
		return cache;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> getPage(QueryPageVo queryPageVo) {
		Map<String, Object> map = queryPageVo.getParaMap();
		map.put("delFlag", 0);
		Pagination<BaseSiteEntity> pageInfo = baseSiteDao.getPagination(map,
				queryPageVo.getPage());

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseSiteEntity>();
			pageInfo.setCount(0);
		}
		return pageInfo;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> queryOwnerSiteCascade(
			QueryPageVo queryPageVo, String ownerSite) {
		Map<String, Object> map = queryPageVo.getParaMap();
		map.put("delFlag", 0);
		map.put("blFlag", 1);
		map.put("ownerSite", ownerSite);

		Pagination<BaseSiteEntity> pageInfo = baseSiteDao.queryOwnerSiteCascade(map, queryPageVo.getPage(), queryPageVo.getSorts());

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseSiteEntity>();
			pageInfo.setCount(0);
		}
		return pageInfo;

	}
	/**
	 *lishuang
	 * 分页查询方法
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> getSitePageListByCondition(QueryPageVo queryPageVo,String userCode) {
		Map<String, Object> map = queryPageVo.getParaMap();
		map.put("delFlag", 0);
		map.put("blFlag", 1);
		ICache<String,EmployeeEntity> empCache = CacheManager.getInstance().getCache(DpapConstants.EMPLOYEE_CACHE_UUID);
		String ownerSite = empCache.get(userCode).getOwnerSite() ;
		//1的情况是说查全部
		Integer flag  = (Integer) (map.get("flag")==null?2:map.get("flag"));
		if(flag==1){
			map.put("ownerSite", "YCG");
		}else{
			map.put("ownerSite", ownerSite);
		}
		//拿到当前所需要的网点类型
		List<Integer> list =  new ArrayList<Integer>();

		String[] strs =map.get("siteType")==null?null: map.get("siteType").toString().split(",");
		for (int i = 0; strs!=null&&i < strs.length; i++) {
			try{
				list.add(Integer.parseInt(strs[i]));
			}catch(Exception e){
				break;
			}
		}
		if(CollectionUtils.isNotEmpty(list)){
			map.put("list", list);
		}
		Pagination<BaseSiteEntity> pageInfo = baseSiteDao.getSitePageListByCondition(map, queryPageVo.getPage(), queryPageVo.getSorts());
		if (null == pageInfo) {
			pageInfo = new Pagination<BaseSiteEntity>();
			pageInfo.setCount(0);
		}
		return pageInfo;
	}
	/**
	 * 查询上级中心控件
	 *
	 *lishuang
	 * @param queryPageVo
	 * @return
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> getUpFinaceCenterPage(QueryPageVo queryPageVo,String userCode) {
		Map<String, Object> map = queryPageVo.getParaMap();
		//String userCode = UserContext.getCurrentUser().getUserName();
		ICache<String,EmployeeEntity> empCache = CacheManager.getInstance().getCache(DpapConstants.EMPLOYEE_CACHE_UUID);
		ICache<String, BaseSiteEntity> baseSiteCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);

		String siteName = (String) map.get("siteName");
		String ownerSite=null;

		 try {
			ownerSite = baseSiteCache.get(empCache.get(userCode).getOwnerSite()).getUpFinanceCenter();
		} catch (Exception e) {
			e.printStackTrace();
		}


		Integer blSite=null;
		try{
			if( map.get("blSite")==null) new NullPointerException("blsite can not be null");
			blSite = (Integer) map.get("blSite");
		}catch(Exception e){
			blSite=0;
		}
		List<Integer> list = new ArrayList<Integer>();
		//加载需要的网点类型
		list.add(1);
		list.add(2);
		if(blSite==1){
			list.add(3);
		}
		map.put("list", list);
		map.put("delFlag", 0);
		map.put("blFlag", 1);
		if(StringUtil.isNotEmpty(siteName)){

			map.put("ownerSite", "YCG");
		}else{

			map.put("ownerSite", ownerSite);
		}
		Pagination<BaseSiteEntity> pageInfo = baseSiteDao.getSitePageListByCondition(map, queryPageVo.getPage(), queryPageVo.getSorts());

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseSiteEntity>();
			pageInfo.setCount(0);
		}
		return pageInfo;

	}

	@Override
	public List<HashMap<String, String>> isFristWebsite(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return baseSiteDao.isFristWebsite(map);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseSiteEntity> getPaginationBaseSiteList(
			QueryPageVo queryPageVo) {
		Sort[] sorts = new Sort[1];
		Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
		sorts[0] = sort;
		queryPageVo.setSorts(sorts);


		Map<String, Object> map = queryPageVo.getParaMap();

		Object object = map.get("siteType");

		if (null != object) {
			String siteTypes = object.toString();
			String[] siteTypeArr = siteTypes.split(",");
			map.put("sts", siteTypeArr);
		}

		Pagination<BaseSiteEntity> pageInfo = baseSiteDao.getPaginationBaseSiteList(map, queryPageVo.getPage(), queryPageVo.getSorts());

		if (null == pageInfo) {
			pageInfo = new Pagination<BaseSiteEntity>();
			pageInfo.setCount(0);
		}
		return pageInfo;
	}


	/**
	 * {@inheritDoc}
	 * @throws ParseException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public void insertBaseSite(BaseSiteVo baseSiteVo, String createUserCode) throws BusinessException {
		//--------------保存网点基本信息-------------
		BaseSiteEntity baseSiteEntity = new BaseSiteEntity();
		BeanUtils.copyProperties(baseSiteVo, baseSiteEntity);

		baseSiteEntity.setSiteId(BasicEntityIdentityGenerator.getInstance().generateId());
		baseSiteEntity.setCountry(BaseOrgConstant.REGION_CODE_CHINA);

		try {
			// 加盟时间
			if (StringUtils.isNotBlank(baseSiteVo.getJoinTimeStr())) {
				Date joinTime = FORMAT.parse(baseSiteVo.getJoinTimeStr());
				baseSiteEntity.setJoinTime(joinTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("加盟时间格式不正确");
		}
		
		
		// 一级财务中心的所属财务财务中心和账单财务中心都是它自己
		if (baseSiteEntity.getSiteType().equals(BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER)) {
			baseSiteEntity.setUpFinanceCenter(baseSiteEntity.getSiteCode());
			baseSiteEntity.setUpSettleCenter(baseSiteEntity.getSiteCode());
		}

		Date cd = new Date();
		baseSiteEntity.setDelFlag(0);
		baseSiteEntity.setCreateUserCode(createUserCode);
		baseSiteEntity.setCreateTime(cd);
		baseSiteEntity.setModifyUserCode(createUserCode);
		baseSiteEntity.setModifyTime(cd);
		baseSiteDao.insert(baseSiteEntity);


		//--------------保存网点附加信息-------------
		BaseSiteDetailEntity baseSiteDetailEntity = new BaseSiteDetailEntity();
		BaseSiteDetailVo baseSiteDetailVo = baseSiteVo.getBaseSiteDetailVo();
		BeanUtils.copyProperties(baseSiteDetailVo, baseSiteDetailEntity);

		baseSiteDetailEntity.setSiteDetailId(BasicEntityIdentityGenerator.getInstance().generateId());
		// 网点编码
		baseSiteDetailEntity.setSiteCode(baseSiteEntity.getSiteCode());
		baseSiteDetailDao.insert(baseSiteDetailEntity);


		//--------------保存组织架构-------------
		BaseOrgEntity baseOrgEntity = new BaseOrgEntity();
		baseOrgEntity.setOrgId(BasicEntityIdentityGenerator.getInstance().generateId());
		baseOrgEntity.setOrgCode(baseSiteEntity.getSiteCode());
		baseOrgEntity.setOrgName(baseSiteEntity.getSiteName());
		baseOrgEntity.setOrgNameShort(baseSiteEntity.getSiteNameShort());
		baseOrgEntity.setOrderBy(baseSiteEntity.getOrderBy());
		baseOrgEntity.setUpOrgCode(baseSiteEntity.getUpSite());

		// 组织类型
		baseOrgEntity.setOrgType(getOrgType(baseSiteEntity.getSiteType()).toString());

		baseOrgEntity.setDefaultCurrency(baseSiteEntity.getDefaultCurrency().toString());
		// 中国
		baseOrgEntity.setCountry(BaseOrgConstant.REGION_CODE_CHINA);
		baseOrgEntity.setProvince(baseSiteEntity.getProvince());
		baseOrgEntity.setCity(baseSiteEntity.getCity());
		baseOrgEntity.setCounty(baseSiteEntity.getCounty());
		baseOrgEntity.setOrgAddress(baseSiteEntity.getSiteAddress());

		baseOrgEntity.setBlFlag(1);
		baseOrgEntity.setDelFlag(0);
		baseOrgEntity.setCreateUserCode(createUserCode);
		baseOrgEntity.setCreateTime(cd);
		baseOrgEntity.setModifyUserCode(createUserCode);
		baseOrgEntity.setModifyTime(cd);

		baseOrgDao.insert(baseOrgEntity);

		//--------------生产资金路由信息-------------
		initBaseSiteFinLine(baseSiteEntity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public void updateBaseSite(BaseSiteVo baseSiteVo, String createUserCode) throws BusinessException {
		// ------------更新门店基本信息------------
		BaseSiteEntity oldBaseSiteEntity = baseSiteDao
				.getById(Long.valueOf(baseSiteVo.getHiddenId()));
		BeanUtils.copyProperties(baseSiteVo, oldBaseSiteEntity);

		try {
			// 加盟时间
			if (StringUtils.isNotBlank(baseSiteVo.getJoinTimeStr())) {
				Date joinTime = FORMAT.parse(baseSiteVo.getJoinTimeStr());
				oldBaseSiteEntity.setJoinTime(joinTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BusinessException("加盟时间格式不正确");
		}

		oldBaseSiteEntity.setModifyUserCode(createUserCode);   // 修改人
		oldBaseSiteEntity.setModifyTime(new Date(Long.valueOf(baseSiteVo.getHiddenModifyTime())));    // 修改时间
		this.update(oldBaseSiteEntity);  // 会做乐观锁校验

		// ------------更新资金路由------------
		baseSiteFinLineDao.deleteBySiteCode(oldBaseSiteEntity.getSiteCode());
		initBaseSiteFinLine(oldBaseSiteEntity);

		// ------------同步更新组织架构------------
		syncUpdateBaseOrg(createUserCode, oldBaseSiteEntity);

		// ------------更新门店附加信息------------
		syncUpdateBaseSiteDetail(baseSiteVo.getBaseSiteDetailVo(), oldBaseSiteEntity);

		// ------------更新缓存------------
		invalidBaseOrgEntityCache(oldBaseSiteEntity.getSiteCode());
		invalidBaseSiteCache(oldBaseSiteEntity.getSiteCode(),oldBaseSiteEntity.getSiteNameShort());
		invalidBaseSiteFinLine(oldBaseSiteEntity.getSiteCode());
	}

	/**
	 * 同步更新附加信息
	 *
	 * @param baseSiteDetailVo
	 * @param baseSiteEntity
	 * @author guoh.mao
	 */
	private void syncUpdateBaseSiteDetail(BaseSiteDetailVo baseSiteDetailVo, BaseSiteEntity baseSiteEntity) {
		BaseSiteDetailEntity oldBaseSiteDetailEntity = baseSiteDetailDao
				.getById(Long.valueOf(baseSiteDetailVo.getHiddenId()));
		if (null != oldBaseSiteDetailEntity) {
			BeanUtils.copyProperties(baseSiteDetailVo, oldBaseSiteDetailEntity);
			baseSiteDetailDao.update(oldBaseSiteDetailEntity);
		} else {   // 附加信息没有则新建出来
			BaseSiteDetailEntity baseSiteDetailEntity = new BaseSiteDetailEntity();
			BeanUtils.copyProperties(baseSiteDetailVo, baseSiteDetailEntity);

			baseSiteDetailEntity.setSiteDetailId(BasicEntityIdentityGenerator.getInstance().generateId());
			// 网点编码
			baseSiteDetailEntity.setSiteCode(baseSiteEntity.getSiteCode());
			baseSiteDetailDao.insert(baseSiteDetailEntity);
		}
	}

	/**
	 * 同步更新组织架构
	 *
	 * @param createUserCode
	 * @param oldBaseSiteEntity
	 * @author guoh.mao
	 */
	private void syncUpdateBaseOrg(String createUserCode,
			BaseSiteEntity newBaseSiteEntity) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgCode", newBaseSiteEntity.getSiteCode());
		List<BaseOrgEntity> list = baseOrgDao.get(paramMap);

		if (!CollectionUtils.isEmpty(list)) {
			BaseOrgEntity baseOrgEntity = list.get(0);

			/*baseOrgEntity.setOrgId(
					BasicEntityIdentityGenerator.getInstance().generateId());*/

			baseOrgEntity.setOrgCode(newBaseSiteEntity.getSiteCode());
			baseOrgEntity.setOrgName(newBaseSiteEntity.getSiteName());
			baseOrgEntity.setOrgNameShort(newBaseSiteEntity.getSiteNameShort());
			baseOrgEntity.setOrderBy(newBaseSiteEntity.getOrderBy());
			baseOrgEntity.setUpOrgCode(newBaseSiteEntity.getUpSite());

			// 组织类型
			baseOrgEntity.setOrgType(
					getOrgType(newBaseSiteEntity.getSiteType()).toString());

			baseOrgEntity.setDefaultCurrency(
					newBaseSiteEntity.getDefaultCurrency().toString());
			baseOrgEntity.setProvince(newBaseSiteEntity.getProvince());
			baseOrgEntity.setCity(newBaseSiteEntity.getCity());
			baseOrgEntity.setCounty(newBaseSiteEntity.getCounty());
			baseOrgEntity.setOrgAddress(newBaseSiteEntity.getSiteAddress());

			baseOrgEntity.setModifyUserCode(createUserCode);
			baseOrgEntity.setModifyTime(new Date());

			baseOrgDao.update(baseOrgEntity);
		}
	}


	/**
	 * 通过网点类型获得组织类型
	 *
	 * @param siteType 网点类型
	 * @return
	 * @author guoh.mao
	 */
	private Integer getOrgType(Integer siteType) {
		// 一级财务中心
		if (BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER.equals(siteType)) {
			return BaseOrgConstant.ORG_TYPE_YCEXPRESS;
		} else if (BaseOrgConstant.SECOND_LEVEL_FINANCE_CENTER
				.equals(siteType)) { // 二级财务中心
			return BaseOrgConstant.ORG_TYPE_COMPANY;
		} else if (BaseOrgConstant.FIRST_LEVEL_SITE.equals(siteType)
				|| BaseOrgConstant.SECOND_LEVEL_SITE.equals(siteType)
				|| BaseOrgConstant.THIRD_LEVEL_SITE.equals(siteType)
				|| BaseOrgConstant.COMPETITOR.equals(siteType)) { // 一级网点、二级网点、三级网点、同行
			return BaseOrgConstant.ORG_TYPE_SHOP;
		} else if (BaseOrgConstant.FIRST_LEVEL_DISTRIBUTION_CENTER
				.equals(siteType)
				|| BaseOrgConstant.SECOND_LEVEL_DISTRIBUTION_CENTER
						.equals(siteType)) { // 一级分拨中心、二级分拨中心
			return BaseOrgConstant.ORG_TYPE_TRANSFER_DISTRIBUTION_CENTER;
		}
		return null;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	private void initBaseSiteFinLine(BaseSiteEntity baseSiteEntity) {
		List<BaseSiteFinLineEntity> baseSiteFinLineEntities = new ArrayList<BaseSiteFinLineEntity>();
		prepareBaseSiteFinLineList(baseSiteEntity.getSiteCode(), baseSiteEntity,
				baseSiteFinLineEntities);

		if (!CollectionUtils.isEmpty(baseSiteFinLineEntities)) {
			for (BaseSiteFinLineEntity baseSiteFinLineEntity : baseSiteFinLineEntities) {

				baseSiteFinLineEntity.setId(BasicEntityIdentityGenerator
						.getInstance().generateId());

				/*Date date = new Date();
				baseSiteFinLineEntity.setCreateTime(date);
				baseSiteFinLineEntity.setCreateUserCode(
						UserContext.getCurrentUser().getUserName());
				baseSiteFinLineEntity.setModifyTime(date);
				baseSiteFinLineEntity.setModifyUserCode(
						UserContext.getCurrentUser().getUserName());*/
				baseSiteFinLineEntity.setDelFlag(0);

				// insert
				baseSiteFinLineDao.insert(baseSiteFinLineEntity);
			}
		}
	}


	private void prepareBaseSiteFinLineList(String thisBaseSiteCode, BaseSiteEntity baseSiteEntity, List<BaseSiteFinLineEntity> baseSiteFinLineEntities){
		if (baseSiteEntity.getSiteType().equals(BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER)) {
			BaseSiteFinLineEntity baseSiteFinLineEntity = new BaseSiteFinLineEntity();
			baseSiteFinLineEntity.setSiteCode(thisBaseSiteCode);

			baseSiteFinLineEntity.setSiteSon(baseSiteEntity.getSiteCode());
			// 快运本部的parent是自己
			baseSiteFinLineEntity.setSiteParent(baseSiteEntity.getSiteCode());

			setLineType(baseSiteEntity, baseSiteFinLineEntity);

			Date periodBeginTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(periodBeginTime);
			// 往后推10年
			calendar.add(Calendar.YEAR, 10);

			baseSiteFinLineEntity.setPeriodBeginTime(periodBeginTime);
			baseSiteFinLineEntity.setPeriodEndTime(calendar.getTime());
			baseSiteFinLineEntities.add(baseSiteFinLineEntity);
		} else {
			prepareBaseSiteFinLine(thisBaseSiteCode, baseSiteEntity, baseSiteFinLineEntities);
		}
	}

	private void prepareBaseSiteFinLine(String thisBaseSiteCode, BaseSiteEntity baseSiteEntity, List<BaseSiteFinLineEntity> baseSiteFinLineEntities){
		if (!baseSiteEntity.getSiteType().equals(BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER)) {
			BaseSiteFinLineEntity baseSiteFinLineEntity = new BaseSiteFinLineEntity();
			baseSiteFinLineEntity.setSiteCode(thisBaseSiteCode);

			baseSiteFinLineEntity.setSiteSon(baseSiteEntity.getSiteCode());
			baseSiteFinLineEntity.setSiteParent(baseSiteEntity.getUpFinanceCenter());

			setLineType(baseSiteEntity, baseSiteFinLineEntity);

			Date periodBeginTime = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(periodBeginTime);
			// 往后推10年
			calendar.add(Calendar.YEAR, 10);

			baseSiteFinLineEntity.setPeriodBeginTime(periodBeginTime);
			baseSiteFinLineEntity.setPeriodEndTime(calendar.getTime());

			baseSiteFinLineEntity.setDelFlag(0);

			baseSiteFinLineEntities.add(baseSiteFinLineEntity);


			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("siteCode", baseSiteEntity.getUpFinanceCenter());

			List<BaseSiteEntity> list = baseSiteDao.get(paramMap);
			if (!CollectionUtils.isEmpty(list)) {
				BaseSiteEntity upFinanceCenter = list.get(0);

				// 递归调用
				prepareBaseSiteFinLine(thisBaseSiteCode, upFinanceCenter, baseSiteFinLineEntities);
			}
		}
	}

	/**
	 * 设置 结算路由类型
	 *
	 * @param baseSiteEntity
	 * @param baseSiteFinLineEntity
	 * @author guoh.mao
	 */
	private void setLineType(BaseSiteEntity baseSiteEntity,
			BaseSiteFinLineEntity baseSiteFinLineEntity) {
		if (BaseOrgConstant.THIRD_LEVEL_SITE
				.equals(baseSiteEntity.getSiteType())) {
			baseSiteFinLineEntity
					.setLineType(BaseOrgConstant.THIRD_LEVEL_SITE_LINE_TYPE);
		} else if (BaseOrgConstant.SECOND_LEVEL_SITE
				.equals(baseSiteEntity.getSiteType())) {
			baseSiteFinLineEntity
					.setLineType(BaseOrgConstant.SECOND_LEVEL_SITE_LINE_TYPE);
		} else if (BaseOrgConstant.FIRST_LEVEL_SITE
				.equals(baseSiteEntity.getSiteType())
				|| BaseOrgConstant.FIRST_LEVEL_DISTRIBUTION_CENTER
						.equals(baseSiteEntity.getSiteType())
				|| BaseOrgConstant.SECOND_LEVEL_DISTRIBUTION_CENTER
						.equals(baseSiteEntity.getSiteType())
				|| BaseOrgConstant.COMPETITOR
						.equals(baseSiteEntity.getSiteType())) {   // 一级网点、一级分拨中心、二级分拨中心、同行
			baseSiteFinLineEntity
					.setLineType(BaseOrgConstant.FIRST_LEVEL_SITE_LINE_TYPE);
		} else if (BaseOrgConstant.SECOND_LEVEL_FINANCE_CENTER
				.equals(baseSiteEntity.getSiteType())) {
			baseSiteFinLineEntity.setLineType(
					BaseOrgConstant.SECOND_LEVEL_FINANCE_CENTER_LINE_TYPE);
		} else if (BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER
				.equals(baseSiteEntity.getSiteType())) {
			baseSiteFinLineEntity.setLineType(
					BaseOrgConstant.FIRST_LEVEL_FINANCE_CENTER_LINE_TYPE);
		}
	}


	@Override
	public int uniqueCheckBySiteCode(String siteCode) {
		return baseSiteDao.uniqueCheckBySiteCode(siteCode);
	}

	@Override
	public int uniqueCheckBySiteName(Map<String, Object> map) {
		return baseSiteDao.uniqueCheckBySiteName(map);
	}

	@Override
	public int uniqueCheckBySiteNameShort(Map<String, Object> map) {
		return baseSiteDao.uniqueCheckBySiteNameShort(map);
	}


	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public Pagination<BaseSiteEntity> queryBaseSite(Map<String, Object> paraMap,
			Page page, Sort[] sorts) {
		Pagination<BaseSiteEntity> pageInfo = baseSiteDao.queryBaseSite(paraMap, page, sorts);

		if (null == pageInfo) {
			pageInfo = new Pagination<>();
			pageInfo.setCount(0);
		}else {
			List<BaseSiteEntity> list = pageInfo.getList();

			if (CollectionUtils.isNotEmpty(list)) {
				for (BaseSiteEntity baseSiteEntity : list) {
					// 构造完整的门店实例
					constructFullBaseSiteEntity(baseSiteEntity);
				}
			}
		}
		return pageInfo;
	}



	/**
	 * 门店缓存
	 *
	 * @return
	 * @author guoh.mao
	 */
	private ICache<String, BaseSiteEntity> getBaseSiteEntityCache() {
		ICache<String, BaseSiteEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
		return cache;
	}

	/**
	 * 使门店缓存失效
	 *
	 * @param baseSiteCode 网点编码
	 * @author guoh.mao
	 */
	private void invalidBaseSiteCache(String siteCode,String siteNameShort) {
		ICache<String, BaseSiteEntity> siteCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
		siteCache.invalid(siteCode);
		ICache<String, BaseSiteEntity> siteNameCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_NAME_CATCHE_UUID);
		siteNameCache.invalid(siteNameShort);

	}

	private void invalidBaseOrgEntityCache(String orgCode){
		ICache<String, BaseOrgEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
		cache.invalid(orgCode);
	}


	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public int batchUpdateBlFlag(Integer blFlag, List<String> codes, String currentUserCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("blFlag", blFlag);
		map.put("codeList", codes);
		map.put("currentUserCode", currentUserCode);

		int result = baseSiteDao.batchUpdateBlFlag(map);
		for (String code : codes) {
			// 使缓存失效
			String siteNameShort = baseRedisCacheService.getBaseSiteFromCache(code).getSiteNameShort();
			invalidBaseSiteCache(code,siteNameShort);
		}

		// 同步更新组织架构的启用状态
		baseOrgService.batchUpdateBlFlag(blFlag, codes);

		return result;
	}


	private void invalidBaseSiteFinLine(String orgCode){
		// 使资金路由缓存失效
		invalidBaseSiteFinLineCache(orgCode);

		Map<String, Object> map = new HashMap<>();
		map.put("siteCode", orgCode);
		List<BaseSiteFinLineEntity> list = baseSiteFinLineDao.get(map);

		// 不为空
		if (!CollectionUtils.isEmpty(list)) {
			for (BaseSiteFinLineEntity baseSiteFinLineEntity : list) {
				// 结算路由类型（0：三级网点到二级网点 1：二级级网点到一级网点   2：一级网点到分公司   3：分公司到快运本部   4：快运本部到快运本部）
				invalidSiteFinLineCodeAndLineTypeCache(orgCode, baseSiteFinLineEntity.getLineType());
			}

		}
	}


	/**
	 * 使资金路由缓存失效
	 *
	 * @param siteCode 网点编码
	 * @author guoh.mao
	 */
	private void invalidBaseSiteFinLineCache(String siteCode){
		ICache<String, BaseSiteFinLineEntity> baseSiteFinLineCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID);
		baseSiteFinLineCache.invalid(siteCode);
	}
	private void invalidSiteFinLineCodeAndLineTypeCache(String siteCode, Integer lineType){
		ICache<String, BaseSiteFinLineEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_FIN_LINE_CATCHE_UUID_CODE_LEVETYPE);
		cache.invalid(siteCode+BaseOrgConstant.SPLIT_SEPARATOR+lineType);
	}


	/**
	 * 获取片区缓存
	 *
	 * @return
	 * @author guoh.mao
	 */
	private ICache<String, BaseAreaEntity> getBaseAreaEntityCache() {
		ICache<String, BaseAreaEntity> cache = CacheManager.getInstance().getCache(BaseCacheConstant.BASE_AREA_CATCHE_UUID);
		return cache;
	}

	@Override
	public List<BaseSiteEntity> getSiteListByName(String siteName) {
		HashMap<String, Object> map =  new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(siteName)){
			map.put("siteName", "%"+siteName+"%");
			map.put("siteCode", siteName);
		}
		if(map.isEmpty()){
			throw new BusinessException("参数不足");
		}
		return baseSiteDao.queryBySiteNameOrCode(map);
	}
	/**
	 * 网点简称精确查询方法
	 * lishuang
	 */
	@Override
	public BaseSiteEntity getBySiteNameShort(String siteNameShort) {
		return baseSiteDao.getBySiteNameShort(siteNameShort);
	}

	@Override
	public List<BaseSiteEntity> queryOwnerSiteCascade2(String ownerSite) {
		Map<String, Object> map =new HashMap<String,Object>();
		map.put("delFlag", 0);
		map.put("blFlag", 1);
		map.put("ownerSite", ownerSite);
		return baseSiteDao.queryOwnerSiteCascade2(map);
	}
	@Override
	public List<BaseSiteEntity> getBaseSiteCodeList(String siteCode){

		return baseSiteDao.getBaseSiteCodeList(siteCode);
	}

	/**
	 * 通过片区编号集合获取门店信息
	 */
	@Override
	public List<BaseSiteEntity> getBaseSiteByAreaCode(Map<String, Object> params) {

		return baseSiteDao.getBaseSiteByAreaCode(params);
	}

	@Override
	public List<BaseSiteEntity> getCalculateTotalAmountBaseSite(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return baseSiteDao.getCalculateTotalAmountBaseSite(params);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public BaseSiteEntity getAffiliatedCompany(String siteCode) {
		return baseSiteDao.getAffiliatedCompany(siteCode);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void assosiateToEmployee(String siteCode, String empCodes){
		baseEmployeeDao.clearOwnerSite(siteCode);
		
		if (StringUtils.isNotBlank(empCodes)) {
			Map<String, Object> map = new HashMap<>();
			map.put("siteCode", siteCode);
			
			String[] empCodeArr = empCodes.split(",");
			List<String> empCodeList = Arrays.asList(empCodeArr);
			
			map.put("empCodeList", empCodeList);
			
			baseEmployeeDao.setOwnerSite(map);
		}
	}

	
	@Override
	public BaseSiteEntity getByCode(String siteCode) {
		return getBaseSiteEntityCache().get(siteCode);
	}
	
}

