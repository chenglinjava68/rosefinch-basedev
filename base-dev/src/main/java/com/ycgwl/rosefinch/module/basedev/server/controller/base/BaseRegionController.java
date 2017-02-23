package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.annotation.QueryPage;
import com.ycgwl.framework.springmvc.controller.AbstractController;
import com.ycgwl.framework.springmvc.entity.ResultEntity;
import com.ycgwl.framework.springmvc.entity.TreeNode;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.common.shared.context.UserContext;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRegionService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRegionListVo;

/**
 *
 * 行政区域Controller
 *
 * @author guoh.mao
 */
@Controller
@RequestMapping("/basedev")
public class BaseRegionController extends AbstractController {
	@Autowired
	private IBaseRegionService baseRegionService;

	@RequestMapping("/baseRegionIndex.do")
	public String indexPage(){
		return "/basedev/baseRegion";
	}

	/**
	 * 获取省份列表
	 *
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("getProvinceList.do")
	@ResponseBody
	public List<BaseRegionEntity> getProvinceList() {
		return baseRegionService.getProvinceList();
	}

	@RequestMapping("getSubRegionListByRegionParent.do")
	@ResponseBody
	public List<BaseRegionEntity> getSubRegionListByRegionParent(String regionParent) {
		//判定父区域编码是否为空
		if(StringUtils.isEmpty(regionParent)){
			return null;
		}
		return baseRegionService.getSubRegionListByRegionParent(regionParent);
	}


	// FIXME
	/**
	 * 获取市列表
	 *
	 * @param provinceCode 省份Code
	 * @return
	 * @author guoh.mao
	 */
	/*@RequestMapping("getCityListByProvinceCode.do")
	@ResponseBody
	public List<BaseRegionEntity> getCityListByProvinceCode(String provinceCode) {
		List<BaseRegionEntity> list = new ArrayList<BaseRegionEntity>();
		List<BaseRegionEntity> provinceList = baseRegionService.getCityListByProvinceCode(provinceCode);

		if (!CollectionUtils.isEmpty(provinceList)) {
			list = provinceList;
		}

		return list;
	}
	*//**
	 *
	 * 获取区（县）列表
	 *
	 * @param cityCode
	 * @return
	 * @author guoh.mao
	 *//*
	@RequestMapping("getDistrictListByCityCode.do")
	@ResponseBody
	public List<BaseRegionEntity> getDistrictListByCityCode(String cityCode) {
		List<BaseRegionEntity> list = new ArrayList<BaseRegionEntity>();
		List<BaseRegionEntity> provinceList = baseRegionService.getDistrictListByCityCode(cityCode);

		if (!CollectionUtils.isEmpty(provinceList)) {
			list = provinceList;
		}

		return list;
	}*/

	/**
	 * 获取市列表
	 *
	 * @param 热门 城市Code
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("getHotCityList.do")
	@ResponseBody
	public List<BaseRegionListVo> getHotCityList() {
		return baseRegionService.getHotCityList();
	}

	@RequestMapping("/getBaseRegionList.do")
	@ResponseBody
	public Pagination<BaseRegionEntity> queryBaseRegion(@QueryPage QueryPageVo queryPageVo){
		return baseRegionService.getPagination(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
	}

	@RequestMapping("/deleteBaseRegionById.do")
	@ResponseBody
	public ResultEntity deleteBaseRegion(String regionId){
		if(StringUtils.isEmpty(regionId)){
			return returnException("传入的行政区域ID为空");
		}
		try {
			String currentUserCode = UserContext.getCurrentUser().getUserName();
			baseRegionService.deleteBaseRegionById(Long.valueOf(regionId), currentUserCode);
			return returnSuccess("删除行政成功!");
		} catch (BusinessException e) {
			return returnException("删除行政区域失败!");
		}
	}

	@RequestMapping("/addBaseRegion.do")
	@ResponseBody
	public ResultEntity addBaseRegion(BaseRegionEntity baseRegionEntity){
		if(baseRegionEntity == null){
			return returnException("传入的参数为空");
		}
		if(StringUtils.isEmpty(baseRegionEntity.getRegionCode())){
			return returnException("传入的行政区域编码为空");
		}
		try {
			String currentUserCode = UserContext.getCurrentUser().getUserName();
			baseRegionEntity.setCreateUserCode(currentUserCode);
			baseRegionEntity.setModifyUserCode(currentUserCode);
			baseRegionService.insert(baseRegionEntity);
			return returnSuccess("增加行政区域成功!",baseRegionEntity);
		} catch (BusinessException e) {
			return returnException("增加行政区域失败!"+e.getMessage());
		}
	}


	@RequestMapping("/getBaseRegionByRegionId.do")
	@ResponseBody
	public ResultEntity getById(String regionId) {
		if(StringUtils.isEmpty(regionId)){
			return returnException("传入的行政区域ID为空");
		}
		try {
			BaseRegionEntity data  = baseRegionService.getById(Long.valueOf(regionId));
			return returnSuccess(data);
		} catch (BusinessException e) {
			return returnException("查询数据失败,异常详情:"+e.getMessage());
		}
	}


	@RequestMapping("/updateBaseRegion.do")
	@ResponseBody
	public ResultEntity updateBaseRegion(BaseRegionEntity baseRegionEntity){
		if(baseRegionEntity == null){
			return returnException("传入的参数不能为空");
		}
		try {
			String currentUserCode = UserContext.getCurrentUser().getUserName();
			baseRegionEntity.setModifyUserCode(currentUserCode);
			baseRegionService.update(baseRegionEntity);
			return returnSuccess("修改行政区域成功!",baseRegionEntity);
		} catch (BusinessException e) {
			return returnException("修改行政区域失败!错误详情"+e.getMessage());
		}
	}
	/**
	 *
	 *
	 * 获取省份的Vo对象
	 * @return
	 */
	@RequestMapping("getProvinceVoList.do")
	@ResponseBody
	public List<BaseRegionListVo> getProvinceVoList() {
		List<BaseRegionEntity> provinceList = baseRegionService.getProvinceList();
		List<BaseRegionListVo> provinceVoList = this.convertRegionEntityListToVoList(provinceList);
		return provinceVoList;
	}

	// FIXME
	/**
	 *
	 *
	 * 获取城市的Vo对象
	 * @return
	 */
	/*@RequestMapping("getCityVoList.do")
	@ResponseBody
	public List<BaseRegionListVo> getCityVoList(String provincecode) {
		List<BaseRegionEntity> list = new ArrayList<BaseRegionEntity>();
		List<BaseRegionEntity> cityList = baseRegionService.getCityListByProvinceCode(provincecode);

		if (!CollectionUtils.isEmpty(cityList)) {
			list = cityList;
		}
		List<BaseRegionListVo> cityVoList = this.RegionEntityListToVoList(list,2);

		return cityVoList;
	}*/


	// FIXME
	/**
	 *
	 *
	 * 获取区县的Vo对象
	 * @return
	 */
	/*@RequestMapping("getDistrictVoList.do")
	@ResponseBody
	public List<BaseRegionListVo> getDistrictVoList(String citycode) {
		List<BaseRegionEntity> list = new ArrayList<BaseRegionEntity>();
		List<BaseRegionEntity> districtList = baseRegionService.getDistrictListByCityCode(citycode);

		if (!CollectionUtils.isEmpty(districtList)) {
			list = districtList;
		}
		List<BaseRegionListVo> districtVoList = this.RegionEntityListToVoList(list,3);

		return districtVoList;
	}*/

	/**
	 *
	 * 将Entity转化一下成为Vo对象
	 * @param list
	 * @param type
	 * @return
	 */
	private List<BaseRegionListVo> convertRegionEntityListToVoList(List<BaseRegionEntity> provinceList) {
		List<BaseRegionListVo> voList = new ArrayList<BaseRegionListVo>();
		if(CollectionUtils.isEmpty(provinceList)) return voList;
		BaseRegionListVo baseRegionListVo =null;
		for (int i = 0; i < provinceList.size(); i++) {
			baseRegionListVo =  new BaseRegionListVo();
			BaseRegionEntity entity =  provinceList.get(i);
			BeanUtils.copyProperties(entity, baseRegionListVo);
			voList.add(baseRegionListVo);
		}
		return voList;
	}
/********************************************************************************************新增方法*******************************************************************************/

	/**
	 *
	 *
	 * 获取区县的Vo对象
	 * @return
	 */
	@RequestMapping("getCityVoListByPingyingAndHanzi.do")
	@ResponseBody
	public List<BaseRegionListVo> getCityVoListByPingyingAndHanzi(String name) {
		if(StringUtils.isEmpty(name)){
			return null;
		}
		return baseRegionService.getCityVoListByPingyingAndHanzi(name);
	}
	/**
	 *
	 * Description: 分页获取计费条目数
	 * @author IT-71664-Zhangxingwang
	 */
	@RequestMapping("/geVoListByQueryPageVo.do")
	@ResponseBody
	public Pagination<BaseRegionListVo> geVoListByQueryPageVo(Integer page,Integer limit,Integer start,Integer isHot,String regionParent,String regionLevel) {
		QueryPageVo queryPageVo =  new QueryPageVo();
		Page pageInfo = new Page();
		pageInfo.setPageNum(page);
		pageInfo.setPageSize(limit);
		HashMap<String,Object> paraMap  = new HashMap<String,Object>();
		paraMap.put("regionParent", regionParent);
		paraMap.put("isHot", isHot);
		paraMap.put("regionLevel", regionLevel);
		queryPageVo.setParaMap(paraMap);
		queryPageVo.setPage(pageInfo);
		Pagination<BaseRegionListVo> pagination = null;
		try{
		pagination = baseRegionService.getVoPagination(queryPageVo);
		}catch(Exception e){
			e.printStackTrace();
			return new Pagination<BaseRegionListVo>();
		}
		return pagination ;
	}
	/**
	 *
	 * Description: 分页获取计费条目数
	 * @author IT-71664-Zhangxingwang
	 */
	@RequestMapping("/geBaseRegionByRegionlevel.do")
	@ResponseBody
	public Pagination<BaseRegionListVo> geBaseRegionByRegionlevel(@QueryPage QueryPageVo queryPageVo) {
		Pagination<BaseRegionListVo> pagination = null;
		try{
			pagination = baseRegionService.getVoPagination(queryPageVo);
		}catch(Exception e) {
			e.printStackTrace();
			return pagination;
		}
		return pagination ;
	}

	@RequestMapping(value="/querySeq.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<String> querySeq(@RequestBody Map<String,String> map){
		String regionName = map.get("regionName");
        if (StringUtils.isEmpty(regionName)) {
        	regionName = "中国";
        }
		 List<String> seqList = baseRegionService.querySeq(regionName);
		return seqList;
	}
	/**
	 * @Title:
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月10日 下午4:40:06
	 */
	@RequestMapping("/loadTree.do")
	@ResponseBody
	@SuppressWarnings("all")
	public List<TreeNode<BaseRegionEntity, TreeNode>> getTreeNodes(String node){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("regionParent", node);
		//paramMap.put("orderBy", "ASC");
		List<BaseRegionEntity> baseOrgEntities = baseRegionService.getBaseRegionEntity(paramMap);
		return baseRegionEntitiesToTreenodes(baseOrgEntities,node);
	}
	@SuppressWarnings("all")
	private List<TreeNode<BaseRegionEntity, TreeNode>> baseRegionEntitiesToTreenodes(List<BaseRegionEntity> baseRegionEntities, String regionParent) {
		List<TreeNode<BaseRegionEntity, TreeNode>> nodes = new ArrayList();
		Map<String, Object> paramMap = new HashMap<String,Object>();
		if (!CollectionUtils.isEmpty(baseRegionEntities)) {
			for (BaseRegionEntity baseRegionEntity : baseRegionEntities) {
				TreeNode treeNode = new TreeNode();
				paramMap.put("regionParent", baseRegionEntity.getRegionCode());
				List<BaseRegionEntity> baseOrgEntities = baseRegionService.getBaseRegionEntity(paramMap);
				if(baseOrgEntities != null && baseOrgEntities.size()>0){
					treeNode.setLeaf(false);
				}else{
					treeNode.setLeaf(true);
				}
				treeNode.setId(baseRegionEntity.getRegionCode());
				treeNode.setParentId(regionParent);
				treeNode.setChecked(null);
				treeNode.setEntity(baseRegionEntity);
				treeNode.setText(baseRegionEntity.getRegionName());
				treeNode.setChildren(null);
				nodes.add(treeNode);
			}
		}
		return nodes;
	}
}
