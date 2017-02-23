package com.ycgwl.rosefinch.module.basedev.server.controller.refresh;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.controller.AbstractController;
import com.ycgwl.framework.springmvc.entity.ResultEntity;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.refresh.RefreshCacheConstant;

@Controller
@RequestMapping("/basedev")
public class RefreshCacheController extends AbstractController{

	@Autowired
	private IBaseRedisCacheService baseRedisCacheService;
	@RequestMapping("/refreshCacheIndex.do")
	public String refreshCacheIndex(){
			return "/basedev/refreshCache";
	}
	@RequestMapping("/refeshCache.do")
	@ResponseBody
	public ResultEntity refeshCacheAll(String cacheType,String keyValue){
		if(StringUtils.isEmpty(cacheType)){
			throw new BusinessException("没有要刷新的缓存");
		}
			try {
				switch(cacheType){
				//1网点缓存标识
				case RefreshCacheConstant.SITE_INFO_CACHE:
					baseRedisCacheService.refreshBaseSiteFromCache(keyValue);
					break;
					//2组织缓存标识
				case RefreshCacheConstant.ORG_INFO_CACHE:
					baseRedisCacheService.refreshBaseOrgFromCache(keyValue);
					break;
					//3应用系统信息表标识
				case RefreshCacheConstant.APP_INFO_CACHE:
					baseRedisCacheService.refreshBaseAppInfoEntityFromCache(keyValue);
					break;
					//4产品缓存标识
				case RefreshCacheConstant.PRODUCT_CACHE:
					baseRedisCacheService.refreshBaseProductFromCache(keyValue);
					break;
					//5配置信息缓存标识
				case RefreshCacheConstant.BASE_CONFIG_CACHE:
					baseRedisCacheService.refreshBaseConfigEntityFromCache(keyValue);
					break;
					//6通过产品名称获取产品编码缓存标识
				case RefreshCacheConstant.GET_PRODUCT_CODE_BY_PRODUCT_NAME_CACHE:
					baseRedisCacheService.refreshProductCodeByProductNameFromCache(keyValue);
					break;
					//7通过区域名称获取区域编码缓存标识
				case RefreshCacheConstant.GET_REGION_CODE_BY_REGION_NAME_CACHE:
					baseRedisCacheService.refreshRegionCodeByRegionNameFromCache(keyValue);
					break;
					//8接口缓存标识
				case RefreshCacheConstant.INTERFACE_CACHE:
					baseRedisCacheService.refreshBaseInterfaceInfoFromCache(keyValue);
					break;
					//9行政区域缓存标识
				case RefreshCacheConstant.REGION_CACHE:
					baseRedisCacheService.refreshBaseRegionFromCache(keyValue);
					break;
					//10级联获取行政区域缓存（按省、市、区降序排列）标识
				case RefreshCacheConstant.REGION_LIST_CASCADE_CATCHE:
					baseRedisCacheService.refreshBaseRegionListCascadeFromCache(keyValue);
					break;
					//11资金路由缓存标识
				case RefreshCacheConstant.SITE_FIN_LINE_CACHE:
					baseRedisCacheService.refreshBaseSiteFinLineFromCache(keyValue);
					break;
					//12资金路由缓存（根据网点编号获取）标识
				case RefreshCacheConstant.SITE_FIN_LINE_CACHE__CODE_LEVETYPE:
					baseRedisCacheService.refreshSiteFinLineCodeAndLineTypeFromCache(keyValue);
					break;
					//13网点简称获取网点缓存标识
				case RefreshCacheConstant.SITE_INFO_NAME_CACHE:
					baseRedisCacheService.refreshBaseSiteEntityNameFromCache(keyValue);
					break;
					//14 功能实体缓存前缀
				case RefreshCacheConstant.FUNCTION_ENTITY_CACHE:
					baseRedisCacheService.refreshFunctionFromCache(keyValue);
					break;
					// 15菜单缓存前缀
				case RefreshCacheConstant.MENU_CACHE:
					baseRedisCacheService.refreshFunctionMenuFromCache(keyValue);
					break;
					//16刷新用户缓存
				case RefreshCacheConstant.USER_CACHE:
					baseRedisCacheService.refreshUserCache(keyValue);
					break;
					//17刷新用户部门数据权限缓存
				case RefreshCacheConstant.USER_DEPT_AUTH_CACHE:
					baseRedisCacheService.refreshUserDeptAuthorityCache(keyValue);
					break;
					//18数据字典缓存前缀
				case RefreshCacheConstant.DICT_DATA_CACHE:
					baseRedisCacheService.refreshDictDataCache(keyValue);
					break;
					//19数据字典项名称缓存
				case RefreshCacheConstant.DICT_DATA_VALUE_NAME_CACHE:
					baseRedisCacheService.refreshDictDataValueNameCache(keyValue);
					break;
					//20数据字典实体缓存前缀
				case RefreshCacheConstant.DICT_ENTITY_CACHE:
					baseRedisCacheService.refreshDictEntityCache(keyValue);
					break;
					//21部门缓存前缀
				case RefreshCacheConstant.DEPARTMENT_CACHE:
					baseRedisCacheService.refreshDepartmentCache(keyValue);
					break;
					//22员工缓存前缀
				case RefreshCacheConstant.EMPLOYEE_CACHE:
					baseRedisCacheService.refreshEmployeeCache(keyValue);
					break;
					//23部门子部门集合缓存前缀
				case RefreshCacheConstant.DEPARTMENT_DIRECT_CHILD_CACHE:
					baseRedisCacheService.refreshDepartmentDirectChildCache(keyValue);
					break;
					//刷新全部缓存
				case "ALL":
					 refreshAll();
					 break;
					default:
					throw new BusinessException("没有要刷新的缓存数据");
				}
			} catch (Exception e) {
				//DPAP-CacheManager-getCache：CacheId:[ORG_INFO_CATCHE_UUID] not found
				if(e!=null){
					 String getCache = e.toString();
					 if(getCache.contains("CacheId") && getCache.contains("not found")){
						 return returnException("没有找到要刷新的缓存数据");
					 }
				}
				return returnException("刷新缓存失败");
			}
			return returnSuccess("刷新缓存成功");
	}
	private void refreshAll() {
		String keyValue = null;
		baseRedisCacheService.refreshBaseSiteFromCache(keyValue);
		baseRedisCacheService.refreshBaseOrgFromCache(keyValue);
		baseRedisCacheService.refreshBaseAppInfoEntityFromCache(keyValue);
		baseRedisCacheService.refreshBaseProductFromCache(keyValue);
		baseRedisCacheService.refreshBaseConfigEntityFromCache(keyValue);
		baseRedisCacheService.refreshProductCodeByProductNameFromCache(keyValue);
		baseRedisCacheService.refreshRegionCodeByRegionNameFromCache(keyValue);
		baseRedisCacheService.refreshBaseInterfaceInfoFromCache(keyValue);
		baseRedisCacheService.refreshBaseRegionFromCache(keyValue);
		baseRedisCacheService.refreshBaseRegionListCascadeFromCache(keyValue);
		baseRedisCacheService.refreshBaseSiteFinLineFromCache(keyValue);
		baseRedisCacheService.refreshSiteFinLineCodeAndLineTypeFromCache(keyValue);
		baseRedisCacheService.refreshBaseSiteEntityNameFromCache(keyValue);
		baseRedisCacheService.refreshFunctionFromCache(keyValue);
		baseRedisCacheService.refreshFunctionMenuFromCache(keyValue);
		baseRedisCacheService.refreshUserCache(keyValue);
		baseRedisCacheService.refreshUserDeptAuthorityCache(keyValue);
		baseRedisCacheService.refreshDictDataCache(keyValue);
		baseRedisCacheService.refreshDictDataValueNameCache(keyValue);
		baseRedisCacheService.refreshDictEntityCache(keyValue);
		baseRedisCacheService.refreshDepartmentCache(keyValue);
		baseRedisCacheService.refreshEmployeeCache(keyValue);
		baseRedisCacheService.refreshDepartmentDirectChildCache(keyValue);
	}
}
