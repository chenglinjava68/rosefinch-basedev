package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.hsqldb.lib.StringUtil;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseUnloadTimeService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseUnloadTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseUnloadTimeVo;
/**
 * 卸车时效
 *
 * @author
 *
 */
@Controller
@RequestMapping("/basedev")
public class BaseUnloadTimeController extends AbstractController{

    @Autowired
    private IBaseUnloadTimeService baseUnloadTimeService;
  //组织机构服务
  	@Autowired
  	private IBaseSiteService baseSiteEntityService;
    /**
     *  卸车时效管理页面
     *
     */
    @RequestMapping("/getUnloadTimeIndex.do")
    public String indexPage(){
        return "/basedev/getUnloadTimeIndex";
    }
    /**
     * 卸车时效列表
     *
     * @param queryPageVo
     * @return
     * @author
     */
    @RequestMapping("/getPaginationgetUnloadTimeList.do")
    @ResponseBody
    public Pagination<BaseUnloadTimeEntity> getPaginationgetUnloadTimeList(@QueryPage QueryPageVo queryPageVo) {
        Sort[] sorts = new Sort[1];
        Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
        sorts[0] = sort;
        Map<String, Object> map = queryPageVo.getParaMap();
        map.put("delFlag", 0);
        Pagination<BaseUnloadTimeEntity> pageInfo = baseUnloadTimeService.getPagination(map, queryPageVo.getPage(), sorts);
        return pageInfo;
    }
    /**
     * 保存卸车时效
     *
     * @param BaseUnloadTimeVo
     * @return
     * @author
     */
    @RequestMapping(value="/insertGetUnloadTime.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity insertBaseConfig(@RequestBody BaseUnloadTimeVo baseUnloadTimeVo){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseUnloadTimeService.insertUnloadTime(baseUnloadTimeVo, currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }
    /**
     * 编辑卸车时效
     *
     * @param baseUnloadTimeVo
     * @return
     * @author
     */
    @RequestMapping(value="/updateGetUnloadTime.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateGetUnloadTime(@RequestBody BaseUnloadTimeVo baseUnloadTimeVo){
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseUnloadTimeService.editUnloadTime(baseUnloadTimeVo, currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }

        return resultEntity;
    }
    /**
     * 通过ID删除
     *
     * @param id
     * @return
     * @author
     */
    @RequestMapping("/deleteUnloadTime.do")
    @ResponseBody
    public ResultEntity deleteUnloadTime(Long id) {
        ResultEntity resultEntity = new ResultEntity();

        try {
        	baseUnloadTimeService.deleteUnloadTime(id);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }
    /**
     * 时效编号唯一校验
     *
     * @param configCode
     * @return
     * @author
     */
    @RequestMapping(value="/uniqueCheckByUnloadTimeCode.do")
    @ResponseBody
    public ResultEntity uniqueCheckByConfigCode(String configCode){
        ResultEntity resultEntity = new ResultEntity();

        try {
            int count = baseUnloadTimeService.uniqueCheckByConfigCode(configCode);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }

        return resultEntity;
    }
    /**
     * 配置名称唯一校验
     *
     * @param configCode 产品编号
     * @param configName 产品名称
     * @param state 新增/修改
     * @return
     * @author
     */
    @RequestMapping(value="/uniqueCheckByUnloadTimeName.do")
    @ResponseBody
    public ResultEntity uniqueCheckByConfigName(String configCode, String configName, String state){
        ResultEntity resultEntity = new ResultEntity();

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("configCode", configCode);
            map.put("configName", configName);
            map.put("state", state);

            int count = baseUnloadTimeService.uniqueCheckByConfigName(map);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }

        return resultEntity;
    }
    /**
     * 批量删除
     * @author yzn
     * @date 2016-11-11
     * @param baseUnloadTimeVo
     * @return
     */
    @RequestMapping(value="/batchDeleteUnloadTimeById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchDeleteByConfigId(@RequestBody BaseUnloadTimeVo baseUnloadTimeVo) {
        if(baseUnloadTimeVo == null || CollectionUtils.isEmpty(baseUnloadTimeVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            //进行数据的
        	baseUnloadTimeService.batchlogicalDeleteById(baseUnloadTimeVo.getIdList());
            return returnSuccess("删除成功!");
        } catch (BusinessException e) {
            return returnException("删除失败!异常信息:"+e.getMessage());
        }
    }
    /**
     * 批量启用/停用
     * @author yzn
     * @date 2016-11-11
     * @param baseUnloadTimeVo
     * @return
     */
    @RequestMapping(value="/batchUpdateUnloadTimeById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchUpdateUnloadTimeById(@RequestBody BaseUnloadTimeVo baseUnloadTimeVo) {
        if(baseUnloadTimeVo == null || CollectionUtils.isEmpty(baseUnloadTimeVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("blFlag", baseUnloadTimeVo.getBlFlag());
            paramMap.put("list", baseUnloadTimeVo.getIdList());
            paramMap.put("modifyUserCode", UserContext.getCurrentUser().getUserName());
            baseUnloadTimeService.batchUpdateBlFlagById(paramMap);
            return returnSuccess(baseUnloadTimeVo.getBlFlag() == 1 ? "启用成功" : "停用成功");
        } catch (BusinessException e) {
            return returnException("修改失败!异常信息:"+e.getMessage());
        }
    }

    /**
    *
    * Description: 获取树形结构
    * @return
    * Created
    */
   @SuppressWarnings("rawtypes")
   @RequestMapping("/getUnloadTimeTree.do")
   @ResponseBody
   public  List<TreeNode<BaseSiteEntity, TreeNode>> getUnloadTimeTree(String node,String id) {
       Map<String, Object> paramMap = new HashMap<String, Object>();

       if(node==null||node.equals("")) return new ArrayList<TreeNode<BaseSiteEntity, TreeNode>>();
       paramMap.put("blFlag", 1);
       paramMap.put("upSite", node);
       List<BaseSiteEntity> baseSiteEntities = baseSiteEntityService.get(paramMap);

       if(CollectionUtils.isEmpty(baseSiteEntities)) return new ArrayList<TreeNode<BaseSiteEntity, TreeNode>>();

       return this.baseOrgEntitiesToTreenodes(baseSiteEntities,node,id);
   }
   /**
   *
   * 树的获取方法
   */
  @SuppressWarnings("rawtypes")
  public List<TreeNode<BaseSiteEntity, TreeNode>> baseOrgEntitiesToTreenodes(List<BaseSiteEntity> baseSiteEntities, String upSiteCode,String configCode) {
      List<TreeNode<BaseSiteEntity, TreeNode>> nodes = new ArrayList<TreeNode<BaseSiteEntity, TreeNode>>();
      List<BaseSiteEntity> releSites  =  null;
      if(CollectionUtils.isEmpty(baseSiteEntities)) return nodes;
      Set<String> set = baseUnloadTimeService.getChildSites(upSiteCode);
      if(!StringUtil.isEmpty(configCode)){
          releSites = baseUnloadTimeService.getByConfigCodeAndSites(configCode, baseSiteEntities);
      }else{
          for (BaseSiteEntity baseSiteEntity : baseSiteEntities) {
              TreeNode<BaseSiteEntity, TreeNode> treeNode = new TreeNode<BaseSiteEntity, TreeNode>();
              treeNode.setId(baseSiteEntity.getSiteCode());
              treeNode.setParentId(upSiteCode);
              treeNode.setChecked(false);
              treeNode.setEntity(baseSiteEntity);
              if(set != null && set.contains(baseSiteEntity.getSiteCode())){
                  treeNode.setLeaf(false);
              } else {
                  treeNode.setLeaf(true);
              }
              treeNode.setText(baseSiteEntity.getSiteNameShort());
              treeNode.setChildren(null);
              nodes.add(treeNode);
          }
      }
      if(CollectionUtils.isEmpty(releSites)) return nodes;
      for (BaseSiteEntity baseSiteEntity : releSites) {
          TreeNode<BaseSiteEntity, TreeNode> treeNode = new TreeNode<BaseSiteEntity, TreeNode>();
          treeNode.setId(baseSiteEntity.getSiteCode());
          treeNode.setParentId(upSiteCode);
          if(baseSiteEntity.getSiteId() == null){
              treeNode.setChecked(false);
          }else{
              treeNode.setChecked(true);
          }
          treeNode.setEntity(baseSiteEntity);
          if(set != null && set.contains(baseSiteEntity.getSiteCode())){
              treeNode.setLeaf(false);
          } else {
              treeNode.setLeaf(true);
          }
          treeNode.setText(baseSiteEntity.getSiteNameShort());
          treeNode.setChildren(null);
          nodes.add(treeNode);
      }
      return nodes;
  }

  /**
   * 查询卸车时效关联的门店
   *
   * @param configCode
   * @return
   * @author
   */
  @RequestMapping(value="/getOrgsByUnloadTime.do")
  @ResponseBody
  public ResultEntity getOrgsByUnloadTime(String configCode){
      ResultEntity resultEntity = new ResultEntity();

      try {
          resultEntity.setSuccess(true);
          resultEntity.setData(baseUnloadTimeService.getOrgsByUnloadTime(configCode));
      } catch (Exception e) {
          e.printStackTrace();
          resultEntity.setSuccess(false);
      }
      return resultEntity;
  }




}
