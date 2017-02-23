package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Pagination;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRegionListVo;

/**
 *
 * @author guoh.mao
 *
 */
public interface IBaseRegionService extends IBaseService<BaseRegionEntity, Long>{
	/**
	 * 获取省列表
	 *
	 * @return
	 * @author guoh.mao
	 */
	List<BaseRegionEntity> getProvinceList();

	/**
	 * 获取子行政区域
	 *
	 * @param regionParent
	 * @return
	 * @author guoh.mao
	 */
	List<BaseRegionEntity> getSubRegionListByRegionParent(String regionParent);

	/**
	 * 获得行政区域
	 *
	 * @param regionCode
	 * @return
	 * @author guoh.mao
	 */
	BaseRegionEntity getByRegionCode(String regionCode);

	int deleteBaseRegionById(Long id, String currentUserCode) throws BusinessException;

	List<BaseRegionListVo> getHotCityList();
	/**
	 *拼音或者汉字查询
	 *@author zb
	 * @param name
	 * @return List<BaseRegionListVo>
	 */
	List<BaseRegionListVo> getCityVoListByPingyingAndHanzi(String name);
	/**
	 * 分页查询行政区域
	 * @author zb
	 * @param queryPageVo
	 * @return Pagination<BaseRegionListVo>
	 */
	Pagination<BaseRegionListVo> getVoPagination(QueryPageVo queryPageVo);

	/**
	 * 级联获取行政区域List
	 *
	 * @param regionCode
	 * @return
	 * @author guoh.mao
	 */
	List<BaseRegionEntity> getRegionListCascade(String regionCode);

	/**
	 * 用途：通过区域名称获得区域编码 缓存
	 * 通过省、市两级名称获得市，示例：湖北省-武汉市
	 *
	 * @param map
	 * @return
	 * @author guoh.mao
	 */
	List<BaseRegionEntity> getCityByProvinceAndCity(String provice, String city);

	/**
	 * 用途：通过区域名称获得区域编码 缓存
	 * 通过省、市、区（县）三级名称获得区（县），示例：湖北省-武汉市-洪山区
	 *
	 * @param map
	 * @return
	 * @author guoh.mao
	 */
	List<BaseRegionEntity> getDistrictByProvinceAndCityAndDistrict(String provice, String city, String district);

	List<String> querySeq(String regionName);

	List<BaseRegionEntity> getBaseRegionEntity(Map<String, Object> paramMap);

}
