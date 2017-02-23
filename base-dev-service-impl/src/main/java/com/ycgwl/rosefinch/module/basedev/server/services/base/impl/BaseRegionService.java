package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseRegionDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRegionService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRegionListVo;
import com.ycgwl.rosefinch.module.organization.shared.domain.EmployeeEntity;
/**
 * 行政区域
 * @author IT-71664-Zhangxingwang
 * @date 2016-9-18 11:49:44
 */
@Service
public class BaseRegionService implements IBaseRegionService {
	public static Logger LOGGER = LoggerFactory.getLogger(BaseRegionService.class);
	//基础区域数据持久层
	@Autowired
	private IBaseRegionDao baseRegionDao;

	/**
	 * 物理删除数据
	 * @author IT-71664-Zhangxingwang
	 * @date 2016-9-18 11:47:47
	 */
	@Override
	@Transactional
	public int deleteById(Long id) {
		//判定ID是否为空
		if(id == null){
			throw new BusinessException("传入的ID为空");
		}
		//根据ID查询对应的数据
		BaseRegionEntity baseRegion = baseRegionDao.getById(id);
		if(baseRegion == null){
			throw new BusinessException("根据传入参数查询到对应数据为空");
		}
		LOGGER.info("baseRegion: 删除id "+baseRegion.getRegionId());
		//进行数据的物理删除
		int result = baseRegionDao.deleteById(id);
		// 使缓存失效
		invalidGetRegionCodeByRegionNameCache(baseRegion);
		return result;
	}

	@Override
	public List<BaseRegionEntity> get(Map<String, Object> params) {
		List<BaseRegionEntity> baseRegionList = baseRegionDao.get(params);

		// FIXME
//		LOGGER.info("baseRegion: id "+baseRegion.get(0).getRegionId());


		/*LOGGER.info("baseRegion: CityCode "+baseRegion.get(0).getCityCode());
		LOGGER.info("baseRegion: CountryCode "+baseRegion.get(0).getCountryCode());
		LOGGER.info("baseRegion: CountryCode "+baseRegion.get(0).getDistrictCode());
		LOGGER.info("baseRegion: CountryCode "+baseRegion.get(0).getProvinceCode());*/
		return baseRegionList;
	}

	@Override
	public BaseRegionEntity getById(Long id) {
		if(id == null){
			throw new BusinessException("传入的参数为空");
		}
		BaseRegionEntity baseRegion = baseRegionDao.getById(id);
		LOGGER.info("baseRegion: id "+baseRegion.getRegionId());

		// FIXME
		/*LOGGER.info("baseRegion: CityCode "+baseRegion.getCityCode());
		LOGGER.info("baseRegion: CountryCode "+baseRegion.getCountryCode());
		LOGGER.info("baseRegion: CountryCode "+baseRegion.getDistrictCode());
		LOGGER.info("baseRegion: CountryCode "+baseRegion.getProvinceCode());*/
		BaseRegionEntity baseEntity = baseRegionDao.getById(id);
		convertBaseRegionEntity(baseEntity);
		return baseEntity;
	}

	@Override
	public List<BaseRegionEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
		return baseRegionDao.getPage(params, pageNum, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public int getPageTotalCount(Map<String, Object> params) {
		return baseRegionDao.getPageTotalCount(params);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public Pagination<BaseRegionEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {
		Pagination<BaseRegionEntity> pagiNation = baseRegionDao.getPagination(params, page, sorts);
		if(CollectionUtils.isEmpty(pagiNation.getList())){
			return pagiNation;
		}
		convertBaseRegionEntityList(pagiNation.getList());
		return pagiNation;
	}

	/**
	 * 进行基础数据转换
	 * @author IT-71664-Zhangxingwang
	 * @date 2016-9-8 13:26:51
	 * @param list
	 */
	private void convertBaseRegionEntityList(List<BaseRegionEntity> list) {
		//判定数据集合是否为空
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		for(BaseRegionEntity baseRegionEntity : list){
			convertBaseRegionEntity(baseRegionEntity);
		}
	}

	private void convertBaseRegionEntity(BaseRegionEntity baseRegionEntity) {
		//基础网点缓存
		ICache<String, BaseSiteEntity> baseSiteCache = CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
		//人员缓存
		ICache<String, EmployeeEntity> empCache = CacheManager.getInstance().getCache(DpapConstants.EMPLOYEE_CACHE_UUID);
		ICache<String, BaseRegionEntity> baseRegionCache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_CATCHE_UUID);
		ICache<String, String> dictCache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
		EmployeeEntity createUser = null;
		EmployeeEntity modifyUser = null;
		BaseSiteEntity createSite = null;
		BaseSiteEntity modifySite = null;
		BaseRegionEntity parentRegion = null;
		//创建人
		if(StringUtils.isNotEmpty(baseRegionEntity.getCreateUserCode())){
			createUser = empCache.get(baseRegionEntity.getCreateUserCode());
			baseRegionEntity.setCreateUserName(createUser == null ? baseRegionEntity.getCreateUserCode() : createUser.getEmployeeName());
			if(createUser != null && StringUtils.isNotEmpty(createUser.getOwnerSite())){
				createSite = baseSiteCache.get(createUser.getOwnerSite());
				baseRegionEntity.setCreateOrgName(createSite == null ? createUser.getOwnerSite() : createSite.getSiteName());
			}
		}
		//修改人
		if(StringUtils.isNotEmpty(baseRegionEntity.getModifyUserCode())){
			modifyUser = empCache.get(baseRegionEntity.getModifyUserCode());
			baseRegionEntity.setModifyUserName(modifyUser == null ? baseRegionEntity.getModifyUserCode() : modifyUser.getEmployeeName());
			if(createUser != null && StringUtils.isNotEmpty(modifyUser.getOwnerSite())){
				modifySite = baseSiteCache.get(modifyUser.getOwnerSite());
				baseRegionEntity.setModifyOrgName(modifySite == null ? modifyUser.getOwnerSite() : modifySite.getSiteName());
			}
		}
		//上级父级编码名称
		if(StringUtils.isNotEmpty(baseRegionEntity.getRegionParent())){
			parentRegion = baseRegionCache.get(baseRegionEntity.getRegionParent());
			baseRegionEntity.setRegionParentName(parentRegion == null ? baseRegionEntity.getRegionParent() : parentRegion.getRegionName());
		}
		//区域等级
		if(StringUtils.isNotEmpty(baseRegionEntity.getRegionLevel())){
			String regionLevelName = dictCache.get(BaseOrgConstant.TYPE_ALIAS_REGION_LEVEL+BaseOrgConstant.SPLIT_SEPARATOR+baseRegionEntity.getRegionLevel());
			baseRegionEntity.setRegionLevelName(StringUtils.isEmpty(regionLevelName) ? baseRegionEntity.getRegionLevel() : regionLevelName);
		}
	}

	@Override
	public int insert(BaseRegionEntity baseRegionEntity) {
		//判定传入的数据是否为空
		if(baseRegionEntity == null){
			throw new BusinessException("传入的参数为空");
		}
		if(StringUtils.isEmpty(baseRegionEntity.getRegionCode())){
			throw new BusinessException("传入的行政区域编码为空");
		}
		//校验有效的区域编码是否存在
		List<BaseRegionEntity> list = getBaseRegionByCode(baseRegionEntity.getRegionCode());
		if(CollectionUtils.isNotEmpty(list)){
			throw new BusinessException("传入的行政区域已经存在");
		}
		Date nowDate = new Date();
		baseRegionEntity.setRegionId(BasicEntityIdentityGenerator.getInstance().generateId());
		baseRegionEntity.setCreateTime(nowDate);
		baseRegionEntity.setModifyTime(nowDate);
		return baseRegionDao.insert(baseRegionEntity);
	}

	@Override
	@Transactional
	public int update(BaseRegionEntity baseRegionEntity) {
		// 判定数据是否为空
		if (baseRegionEntity == null) {
			throw new BusinessException("对应的行政区域数据为空");
		}
		// 判定对应的ID是否为空
		if (baseRegionEntity.getRegionId() == null) {
			throw new BusinessException("对应的行政区域ID为空");
		}
		// 判定对应的CODE是否为空
		if (baseRegionEntity.getRegionCode() == null) {
			throw new BusinessException("对应的行政区域CODE为空");
		}
		//校验有效的区域编码是否存在
		List<BaseRegionEntity> list = getBaseRegionByCode(baseRegionEntity.getRegionCode());
		if(CollectionUtils.isNotEmpty(list)){
			for(BaseRegionEntity baseRegion : list){
				if(baseRegion != null && baseRegionEntity.getRegionId() != baseRegion.getRegionId()
						&& baseRegionEntity.getRegionCode().equals(baseRegion.getRegionCode())){
					throw new BusinessException("传入的行政区域已经存在");
				}
			}
		}
		Date nowDate = new Date();
		baseRegionEntity.setModifyTime(nowDate);

		// 进行数据的更新
		int num = baseRegionDao.update(baseRegionEntity);
		if (num < 1) {
			throw new BusinessException("更新应用系统数据失败");
		}
		// 使缓存失效
		invalidGetRegionCodeByRegionNameCache(baseRegionEntity);
		return num;
	}

	@Override
	@Deprecated
	@Transactional
	public int updateStatusById(Long id, int status) {
		return baseRegionDao.updateStatusById(id, status);
	}

	@Override
	public List<BaseRegionEntity> getProvinceList() {
		return baseRegionDao.getProvinceList();
	}

	@Override
	public List<BaseRegionEntity> getSubRegionListByRegionParent(String regionParent) {
		return baseRegionDao.getSubRegionListByRegionParent(regionParent);
	}

	@Override
	public BaseRegionEntity getByRegionCode(String regionCode) {
		return baseRegionDao.getByRegionCode(regionCode);
	}

	@Transactional
	@Override
	public int deleteBaseRegionById(Long id, String currentUserCode) {
		// 判定ID是否为空
		if (id == null) {
			throw new BusinessException("对应区域ID为空");
		}
		BaseRegionEntity baseRegionEntityTem = baseRegionDao.getById(id);
		if(baseRegionEntityTem == null){
			throw new BusinessException("根据ID查询到对应的行政区域为空,ID为:"+id);
		}
		BaseRegionEntity baseRegionEntity = new BaseRegionEntity();
		baseRegionEntity.setRegionId(id);
		baseRegionEntity.setDelFlag(1);
		baseRegionEntity.setModifyUserCode(currentUserCode);
		baseRegionEntity.setModifyTime(new Date());
		int num = baseRegionDao.update(baseRegionEntity);
		if (num < 1) {
			throw new BusinessException("根据ID删除对应的行政区域失败");
		}
		//进行缓存数据的作废
		invalidGetRegionCodeByRegionNameCache(baseRegionEntityTem);
		return num;
	}

/**
 * 获得热门城市的方法
 */
	@Override
	public List<BaseRegionListVo> getHotCityList() {
		List<BaseRegionListVo> list = baseRegionDao.getHotCityList();
		return list;
	}
	/**
	 *拼音或者汉字查询
	 *@author zb
	 * @param name
	 * @return List<BaseRegionListVo>
	 */
	@Override
	public List<BaseRegionListVo> getCityVoListByPingyingAndHanzi(String name) {
		return baseRegionDao.getCityVoListByPingyingAndHanzi(name);
	}

	/**
	 * 分页查询行政区域
	 * @author zb
	 * @param queryPageVo
	 * @return Pagination<BaseRegionListVo>
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseRegionListVo> getVoPagination(QueryPageVo queryPageVo) {
		return baseRegionDao.getVoPagination(queryPageVo.getParaMap(),queryPageVo.getPage(),queryPageVo.getSorts());
	}

	@Override
	public List<BaseRegionEntity> getRegionListCascade(String regionCode) {
		return baseRegionDao.getRegionListCascade(regionCode);
	}

	private List<BaseRegionEntity> getBaseRegionByCode(String regionCode){
		if(StringUtils.isEmpty(regionCode)){
			return null;
		}
		Map<String, Object> arg0 = new HashMap<String, Object>();
		arg0.put("regionCode", regionCode);
		arg0.put("delFlag", 0);
		return baseRegionDao.get(arg0);
	}

	/**
	 * 缓存数据的作废
	 * @author IT-71664-Zhangxingwang
	 * @date 2016-9-18 13:06:44
	 * @param baseRegionEntity
	 */
	private void invalidGetRegionCodeByRegionNameCache(BaseRegionEntity baseRegionEntity) {
		// 使缓存失效
		if(baseRegionEntity == null || StringUtils.isEmpty(baseRegionEntity.getRegionCode()) || StringUtils.isEmpty(baseRegionEntity.getRegionName())){
			return;
		}
		//按照编码查询缓存
		if(StringUtils.isNotEmpty(baseRegionEntity.getRegionCode())){
			ICache<String, BaseRegionEntity> regionCache = CacheManager.getInstance().getCache(BaseCacheConstant.REGION_CATCHE_UUID);
			regionCache.invalid(baseRegionEntity.getRegionCode());
		}
		//按照名称查询缓存
		if(StringUtils.isNotEmpty(baseRegionEntity.getRegionName())){
			ICache<String, String> cache = CacheManager.getInstance().getCache(BaseCacheConstant.GET_REGION_CODE_BY_REGION_NAME_CACHE_UUID);
			cache.invalid(baseRegionEntity.getRegionName());
		}
	}

	@Override
	public List<BaseRegionEntity> getCityByProvinceAndCity(String provice, String city) {
		Map<String, Object> map = new HashMap<>();
		map.put("province", provice);
		map.put("city", city);

		return baseRegionDao.getCityByProvinceAndCity(map);
	}

	@Override
	public List<BaseRegionEntity> getDistrictByProvinceAndCityAndDistrict(String provice, String city, String district) {
		Map<String, Object> map = new HashMap<>();
		map.put("province", provice);
		map.put("city", city);
		map.put("district", district);

		return baseRegionDao.getDistrictByProvinceAndCityAndDistrict(map);
	}

	@Override
		public List<String> querySeq(String regionName) {
			List<String> seqs = baseRegionDao.getAllSeq("%" + regionName + "%");
			/*for (int i = 0; i < seqs.size(); i++) {
				for (int j = 0; j < seqs.size(); j++) {
					if (seqs.get(i).startsWith(seqs.get(j))&& !seqs.get(i).equals(seqs.get(j))) {
						seqs.remove(j);
						i--;
						break;
					}
				}
			}*/
			return seqs;
		}

	@Override
	public List<BaseRegionEntity> getBaseRegionEntity(Map<String, Object> paramMap) {
		return baseRegionDao.getBaseRegionEntity(paramMap);
	}

}
