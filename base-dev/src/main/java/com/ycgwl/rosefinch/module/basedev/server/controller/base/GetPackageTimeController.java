package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IGetPackageTimeService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.GetPackageTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.GetPackageTimeVo;

/**
 *
 * Title: 揽件时效Controller
 * Description:表T_GET_PACKAGE_TIME  CRUD
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月1日  下午4:58
 *
 */
@Controller
@RequestMapping("/basedev")
public class GetPackageTimeController extends AbstractController{

    @Autowired
    private IGetPackageTimeService getPackageTimeService;

    @Autowired
    private IBaseSiteService baseSiteEntityService;

    /**
     *  揽件时效管理页面
     *
     */
    @RequestMapping("/getPackageTimeIndex.do")
    public String indexPage(){
        return "/basedev/getPackageTimeIndex";
    }

    /**
     * 揽件时效列表
     *
     * @param queryPageVo
     * @return
     * @author caijue
     */
    @RequestMapping("/getPaginationPackageTimeList.do")
    @ResponseBody
    public Pagination<GetPackageTimeEntity> getPaginationPckageTimeList(@QueryPage QueryPageVo queryPageVo) {
        Sort[] sorts = new Sort[1];
        Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
        sorts[0] = sort;
        Map<String, Object> map = queryPageVo.getParaMap();
        map.put("delFlag", 0);
        Pagination<GetPackageTimeEntity> pageInfo = getPackageTimeService.getPagination(map, queryPageVo.getPage(), sorts);
        return pageInfo;
    }

    /**
     * 保存揽件时效
     *
     * @param GetPackageTimeVo
     * @return
     * @author
     */
    @RequestMapping(value="/insertPackageTime.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity insertPackageTime(@RequestBody GetPackageTimeVo packageTimeVo){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            getPackageTimeService.insertPackageTime(packageTimeVo, currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * 编辑揽件时效
     *
     * @param GetPackageTimeVo
     * @return
     * @author
     */
    @RequestMapping(value="/updatePackageTime.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updatePackageTime(@RequestBody GetPackageTimeVo packageTimeVo){
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            getPackageTimeService.editPackageTime(packageTimeVo, currentUserName);
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
     * @author caijue
     */
    @RequestMapping("/deletePackageTime.do")
    @ResponseBody
    public ResultEntity deletePackageTime(Long configId) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            getPackageTimeService.deleteGetPackage(configId);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * 配置编号唯一校验
     *
     * @param configCode
     * @return
     * @author
     */
    @RequestMapping(value="/uniqueCheckByPackageTimeCode.do")
    @ResponseBody
    public ResultEntity uniqueCheckByConfigCode(String configCode){
        ResultEntity resultEntity = new ResultEntity();

        try {
            int count = getPackageTimeService.uniqueCheckByConfigCode(configCode);
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
    @RequestMapping(value="/uniqueCheckByPackageTimeName.do")
    @ResponseBody
    public ResultEntity uniqueCheckByConfigName(String configCode, String configName, String state){
        ResultEntity resultEntity = new ResultEntity();

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("configCode", configCode);
            map.put("configName", configName);
            map.put("state", state);

            int count = getPackageTimeService.uniqueCheckByConfigName(map);
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
     * @author caijue
     * @date 2016-11-09
     * @param GetPackageTimeVo
     * @return
     */
    @RequestMapping(value="/batchDeletePackageTimeById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchDeleteByConfigId(@RequestBody GetPackageTimeVo getPackageTimeVo) {
        if(getPackageTimeVo == null || CollectionUtils.isEmpty(getPackageTimeVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            //进行数据的
            getPackageTimeService.batchlogicalDeleteById(getPackageTimeVo.getIdList());
            return returnSuccess("删除成功!");
        } catch (BusinessException e) {
            return returnException("删除失败!异常信息:"+e.getMessage());
        }
    }

    /**
     * 批量启用/停用
     * @author caijue
     * @date 2016-11-09
     * @param GetPackageTimeVo
     * @return
     */
    @RequestMapping(value="/batchUpdatePackageStateById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchUpdatePackageStateById(@RequestBody GetPackageTimeVo getPackageTimeVo) {
        if(getPackageTimeVo == null || CollectionUtils.isEmpty(getPackageTimeVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("blFlag", getPackageTimeVo.getBlFlag());
            paramMap.put("list", getPackageTimeVo.getIdList());
            paramMap.put("modifyUserCode", UserContext.getCurrentUser().getUserName());
            getPackageTimeService.batchUpdateBlFlagById(paramMap);
            return returnSuccess(getPackageTimeVo.getBlFlag() == 1 ? "启用成功" : "停用成功");
        } catch (BusinessException e) {
            return returnException("修改失败!异常信息:"+e.getMessage());
        }
    }

    /**
    *
    * Description: 获取树形结构
    * @return
    * Created by caijue
    */
   @SuppressWarnings("rawtypes")
   @RequestMapping("/getPackageTimeTree.do")
   @ResponseBody
   public  List<TreeNode<BaseSiteEntity, TreeNode>> getPackageTimeTree(String node, String id) {
       if(StringUtils.isBlank(node)) return new ArrayList<TreeNode<BaseSiteEntity, TreeNode>>();
       Map<String, Object> paramMap = new HashMap<String, Object>();
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
      Set<String> set = getPackageTimeService.getChildSites(upSiteCode);
      if(!StringUtil.isEmpty(configCode)){
          releSites = getPackageTimeService.getByConfigCodeAndSites(configCode, baseSiteEntities);
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
          if(set != null && set.contains(baseSiteEntity.getSiteCode())){
              treeNode.setLeaf(false);
          } else {
              treeNode.setLeaf(true);
          }
          treeNode.setEntity(baseSiteEntity);
          treeNode.setText(baseSiteEntity.getSiteNameShort());
          treeNode.setChildren(null);
          nodes.add(treeNode);
      }
      return nodes;
  }


  /**
   * 查询揽件时效关联的门店
   *
   * @param configCode
   * @return
   * @author
   */
  @RequestMapping(value="/getOrgsByPackageTime.do")
  @ResponseBody
  public ResultEntity getOrgsByPackageTime(String configCode){
      if(StringUtils.isEmpty(configCode)){
          return returnException("传入的参数不能为空");
      }
      ResultEntity resultEntity = new ResultEntity();
      try {
          resultEntity.setSuccess(true);
          resultEntity.setData(getPackageTimeService.getSitesByPackageTime(configCode));
      } catch (Exception e) {
          e.printStackTrace();
          resultEntity.setSuccess(false);
      }
      return resultEntity;
  }
}



