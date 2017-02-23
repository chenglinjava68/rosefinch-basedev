package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseOrgService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseOrgVo;

/**
 * 组织机构Controller
 * 
 * @author guoh.mao
 */
@Controller
@RequestMapping("/basedev")
public class BaseOrgController extends AbstractController {
	@Autowired
	private IBaseOrgService baseOrgEntityService;
	
	/**
	 * 
	 * 组织机构首页
	 *
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/baseOrgIndex.do")
	public String indexPage(){
		return "/basedev/baseOrgIndex";
	}
	
	/**
	 * 
	 * 获取父子点下所有子节点
	 *
	 * @param node ExtJs树节点ID
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getBaseOrgList.do")
	@ResponseBody
	@SuppressWarnings("all")
	public List<TreeNode<BaseOrgEntity, TreeNode>> getTreeNodes(String node){
		/*Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("upOrgCode", node);
		List<BaseOrgEntity> baseOrgEntities = baseOrgEntityService.get(paramMap);*/
		
		List<BaseOrgEntity> baseOrgEntities = baseOrgEntityService.getChildren(node);
		
		return baseOrgEntitiesToTreenodes(baseOrgEntities,node);
	}
	
	
	/**
	 * 
	 * 通过组织机构名称模糊查询
	 *
	 * @param orgName 组织机构名称
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/queryByOrgName.do")
	@ResponseBody
	public List<String> queryByOrgName(String orgName){
		List<BaseOrgEntity> baseOrgEntities = baseOrgEntityService.queryByOrgName(orgName);
		List<String> paths = getNodePaths(baseOrgEntities);
		
		return paths;
	}
	
	
	/**
	 * 组织机构List转换为ExtJs树节点List
	 *
	 * @param baseOrgEntities
	 * @param upOrgCode 上级组织机构Code
	 * @return
	 * @author guoh.mao
	 */
	@SuppressWarnings("all")
	private List<TreeNode<BaseOrgEntity, TreeNode>> baseOrgEntitiesToTreenodes(List<BaseOrgEntity> baseOrgEntities, String upOrgCode) {
		List<TreeNode<BaseOrgEntity, TreeNode>> nodes = new ArrayList();
		
		if (!CollectionUtils.isEmpty(baseOrgEntities)) {
			for (BaseOrgEntity baseOrgEntity : baseOrgEntities) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(baseOrgEntity.getOrgCode());
				treeNode.setParentId(upOrgCode);
				treeNode.setChecked(null);
				treeNode.setEntity(baseOrgEntity);
				treeNode.setLeaf(!baseOrgEntity.getHasChildren());
				treeNode.setText(baseOrgEntity.getOrgName());
				treeNode.setChildren(null);
				nodes.add(treeNode);
			}
		}
		return nodes;
	}
	
	/**
	 * 
	 * 按照组织机构的父子关系，递归获取当前组织机构及其直接间接父节点
	 *
	 * @param org
	 * @param codeList
	 * @author guoh.mao
	 * @see #getNodeExpandPath(BaseOrgEntity)
	 */
	private void getNodePath(BaseOrgEntity org, List<String> codeList){
		codeList.add(org.getOrgCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgCode", org.getUpOrgCode());
		
		List<BaseOrgEntity> list = baseOrgEntityService.get(param);
		
		if (!CollectionUtils.isEmpty(list)) {
			// 递归调用
			getNodePath(list.get(0), codeList);
		}
	}
	
	
	/**
	 * 获取List中每个组织机构节点的展开路径
	 * 示例：[/YCG/100/101,/YCG/100/102]
	 *
	 * @param baseOrgEntities
	 * @return
	 * @author guoh.mao
	 */
	private List<String> getNodePaths(List<BaseOrgEntity> baseOrgEntities) {
		List<String> paths = new ArrayList<String>();
		
		if (!CollectionUtils.isEmpty(baseOrgEntities)) {
			for (BaseOrgEntity baseOrgEntity : baseOrgEntities) {
				List<String> codeList = new ArrayList<String>();
				getNodePath(baseOrgEntity, codeList);
				Collections.reverse(codeList);
				
				StringBuilder sb = new StringBuilder();
//				sb.append("/"+BaseOrgConstant.YC_GROUP_CODE);
				for (String orgCode : codeList) {
					sb.append("/");
					sb.append(orgCode);
				}
				
				paths.add(sb.toString());
			}
		}
		return paths;
	}
	
	/**
	 * 
	 * 获取组织节点展开路径
	 * 示例：/YCG/100/101,
	 * 101：当前节点ID;100：当前节点直接父节点ID;YCG:是根节点ID
	 *
	 * @param baseOrgEntity
	 * @return
	 * @author guoh.mao
	 */
	private String getNodeExpandPath(BaseOrgEntity baseOrgEntity) {
		List<BaseOrgEntity> list = new ArrayList<BaseOrgEntity>();
		list.add(baseOrgEntity);
		List<String> pathList = getNodePaths(list);
		String path = pathList.get(0);
		return path;
	}
	
	/**
	 * 分页查询组织机构
	 *
	 * @param queryPageVo 
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getPaginationBaseOrgList.do")
	@ResponseBody
	public Pagination<BaseOrgEntity> getPaginationBaseOrgList(@QueryPage QueryPageVo queryPageVo) {
		Pagination<BaseOrgEntity> pageInfo = baseOrgEntityService.getPage(queryPageVo);
		return pageInfo;
	}
	
	/**
	 * 保存组织机构
	 *
	 * @param baseOrgVo 封装了组织机构属性
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/insertBaseOrg.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity insertBaseOrg(@RequestBody BaseOrgVo baseOrgVo) {
		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			BaseOrgEntity baseOrgEntity = baseOrgEntityService
					.insertBaseOrgEntity(baseOrgVo, currentUserName);

			// get node expand path
			String path = getNodeExpandPath(baseOrgEntity);

			List<String> data = new ArrayList<String>();
			data.add(baseOrgEntity.getOrgCode());
			data.add(path);

			return returnSuccess(data);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("保存组织机构失败！异常信息:"+e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * 更新组织机构
	 *
	 * @param baseOrgVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/updateBaseOrg.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity updateBaseOrg(@RequestBody BaseOrgVo baseOrgVo) {
		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			BaseOrgEntity baseOrgEntity = baseOrgEntityService
					.updateBaseOrgEntity(baseOrgVo, currentUserName);

			String path = getNodeExpandPath(baseOrgEntity);

			List<String> data = new ArrayList<String>();
			data.add(baseOrgEntity.getOrgCode());
			data.add(path);

			return returnSuccess(data);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("更新组织机构失败！异常信息:"+e.getMessage());
		}
	}
	
	
	/**
	 * 启用 组织机构
	 *
	 * @param orgCode
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/enableBaseOrg.do")
	@ResponseBody
	public ResultEntity enableBaseOrg(String orgCode) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orgCode", orgCode);

			List<BaseOrgEntity> list = baseOrgEntityService.get(params);
			if (!CollectionUtils.isEmpty(list)) {
				BaseOrgEntity oldBaseOrgEntity = list.get(0);
				oldBaseOrgEntity.setBlFlag(1);
				baseOrgEntityService.updateBlFlag(oldBaseOrgEntity);

				String path = getNodeExpandPath(oldBaseOrgEntity);

				List<String> data = new ArrayList<String>();
				data.add(oldBaseOrgEntity.getOrgCode());
				data.add(path);
				
				return returnSuccess(data);
			}
			return returnException("启用失败，找不到对应组织机构！");
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("启用失败！异常信息:" + e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * 停用 组织机构
	 *
	 * @param orgCode
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/disableBaseOrg.do")
	@ResponseBody
	public ResultEntity disableBaseOrg(String orgCode) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orgCode", orgCode);

			List<BaseOrgEntity> list = baseOrgEntityService.get(params);
			if (!CollectionUtils.isEmpty(list)) {
				BaseOrgEntity oldBaseOrgEntity = list.get(0);
				oldBaseOrgEntity.setBlFlag(0);
				baseOrgEntityService.updateBlFlag(oldBaseOrgEntity);

				String path = getNodeExpandPath(oldBaseOrgEntity);

				List<String> data = new ArrayList<String>();
				data.add(oldBaseOrgEntity.getOrgCode());
				data.add(path);

				return returnSuccess(data);
			}

			return returnException("停用失败，找不到对应组织机构！");
		} catch (BusinessException e) {
			e.printStackTrace();

			return returnException("停用失败！异常信息:" + e.getMessage());
		}
	}
	
	
	/**
	 * 获取组织机构Entity
	 *
	 * @param orgCode 组织编码
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/getBaseOrgEntity.do")
	@ResponseBody
	public ResultEntity getBaseOrgEntity(String orgCode) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orgCode", orgCode);

			List<BaseOrgEntity> list = baseOrgEntityService.get(paramMap);

			BaseOrgEntity baseOrgEntity = new BaseOrgEntity();

			if (!CollectionUtils.isEmpty(list)) {
				baseOrgEntity = list.get(0);

				// 构造完整网点Entity
				baseOrgEntity = baseOrgEntityService
						.constructFullBaseOrgEntity(baseOrgEntity);
			}

			return returnSuccess(baseOrgEntity);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("获取组织机构失败！异常信息:" + e.getMessage());
		}
	}
	
	
	/**
	 * orgCode唯一校验
	 * 修改组织机构时排除当前组织机构
	 *
	 * @param orgCode
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/uniqueCheckByOrgCode.do")
	@ResponseBody
	public ResultEntity uniqueCheckByOrgCode(String orgCode) {
		try {
			int count = baseOrgEntityService.uniqueCheckByOrgCode(orgCode);
			return returnSuccess(count);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("校验失败！异常信息:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * orgName唯一校验
	 * 修改组织机构时排除当前组织机构
	 *
	 * @param orgCode
	 * @param orgName
	 * @param state
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/uniqueCheckByOrgName.do")
	@ResponseBody
	public ResultEntity uniqueCheckByOrgName(String orgCode, String orgName,
			String state, String upOrgCode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgCode", orgCode);
			map.put("orgName", orgName);
			map.put("state", state);
			map.put("upOrgCode", upOrgCode);

			int count = baseOrgEntityService.uniqueCheckByOrgName(map);
			return returnSuccess(count);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("校验失败！异常信息:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * orgNameShort唯一校验
	 * 修改组织机构时排除当前组织机构
	 *
	 * @param orgCode
	 * @param orgNameShort
	 * @param state
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/uniqueCheckByOrgNameShort.do")
	@ResponseBody
	public ResultEntity uniqueCheckByOrgNameShort(String orgCode,
			String orgNameShort, String state, String upOrgCode) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgCode", orgCode);
			map.put("orgNameShort", orgNameShort);
			map.put("state", state);
			map.put("upOrgCode", upOrgCode);

			int count = baseOrgEntityService.uniqueCheckByOrgNameShort(map);
			return returnSuccess(count);

		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("校验失败！异常信息:" + e.getMessage());
		}
	}
	
	/***********************************************树形菜单组件************************************************/
	@RequestMapping("/getBaseOrgCompList.do")
	@ResponseBody
	@SuppressWarnings("all")
	public List<TreeNode<BaseOrgEntity, TreeNode>> getBaseOrgCompList(String node){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("upOrgCode", node);
		List<BaseOrgEntity> baseOrgEntities = baseOrgEntityService.get(paramMap);
		return baseOrgEntitiesToTreenodesInComp(baseOrgEntities,node);
	}
	/**
	 *
	 * 组织机构List转换为ExtJs树节点List
	 *
	 * @param baseOrgEntities
	 * @param upOrgCode 上级组织机构Code
	 * @return
	 * @author guoh.mao
	 */
	@SuppressWarnings("all")
	private List<TreeNode<BaseOrgEntity, TreeNode>> baseOrgEntitiesToTreenodesInComp(List<BaseOrgEntity> baseOrgEntities, String upOrgCode) {
		List<TreeNode<BaseOrgEntity, TreeNode>> nodes = new ArrayList();
		if (!CollectionUtils.isEmpty(baseOrgEntities)) {
			for (BaseOrgEntity baseOrgEntity : baseOrgEntities) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(baseOrgEntity.getOrgCode());
				treeNode.setParentId(upOrgCode);
				treeNode.setChecked(false);
				treeNode.setEntity(baseOrgEntity);
				treeNode.setLeaf(false);
				treeNode.setText(baseOrgEntity.getOrgName());
				treeNode.setChildren(null);
				nodes.add(treeNode);
			}
		}
		return nodes;
	}
}
