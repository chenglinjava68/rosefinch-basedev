package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseOrgService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.ISignSiteTimeService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.ISignTrancenterTimeService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignSiteTimeVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignTrancenterTimeVo;

/**
 *
 * Title: Description:派件是时效设置Controller
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月9日 下午1:45:09
 *
 */
@Controller
@RequestMapping("/basedev")
public class SendTimeController extends AbstractController {
    @Autowired
    private ISignSiteTimeService signSiteTimeService;

    @Autowired
    private ISignTrancenterTimeService signTrancenterTimeService;
    // 组织机构服务
    @Autowired
    private IBaseOrgService baseOrgEntityService;

    // 组织机构服务
    @Autowired
    private IBaseSiteService baseSiteEntityService;

    /**
     * 派件时效(门店)首页
     *
     * @return
     */
    @RequestMapping("/storeSendTime.do")
    public String indexStorePage() {
        return "/basedev/storeSendTime";
    }

    /**
     * 分页查询所有数据(派件时效门店类型)
     *
     * @param queryPageVo
     * @return josn
     */
    @RequestMapping("/getStoreSendTimeList.do")
    @ResponseBody
    public Pagination<SignSiteTimeVo> getStoreSendTimePagination(@QueryPage QueryPageVo queryPageVo) {
        Sort[] sorts = new Sort[1];
        Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
        sorts[0] = sort;
        queryPageVo.setSorts(sorts);
        Pagination<SignSiteTimeVo> pageInfo = signSiteTimeService.getPage(queryPageVo.getParaMap(),
                queryPageVo.getPage(), sorts);
        return pageInfo;

    }

    //  ++
    /**
     * 查询派件时效关联的门店(门店)
     *
     * @param configCode
     * @return
     * @author
     */
    @RequestMapping(value="/getOrgsByStoreTime.do")
    @ResponseBody
    public ResultEntity getOrgsByStoreTime(String configCode){
        if(StringUtils.isEmpty(configCode)){
            return returnException("传入的参数不能为空");
        }
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setSuccess(true);
            resultEntity.setData(signSiteTimeService.getSitesBySignSiteTime(configCode));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }
    /**
     * 查询派件时效关联的门店(zhongxin)
     *
     * @param configCode
     * @return
     * @author
     */
    @RequestMapping(value="/getOrgsByCentreTime.do")
    @ResponseBody
    public ResultEntity getOrgsByCentreTime(String configCode){
        if(StringUtils.isEmpty(configCode)){
            return returnException("传入的参数不能为空");
        }
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setSuccess(true);
            resultEntity.setData(signTrancenterTimeService.getOrgsBySignTrancenterTime(configCode));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }


    /**
     * 验证时效编号是否存在(门店)
     */
    @RequestMapping("/getConfigCodeByInsert.do")
    @ResponseBody
    public ResultEntity getConfigCodeByInsert(String configCode) {
        ResultEntity re = new ResultEntity();
        String configCode1 = signSiteTimeService.getConfigCodeByInsert(configCode);
        if (StringUtils.isNotEmpty(configCode1)) {
            re.setCode("0");
            re.setData(configCode1);
            re.setMsg("时效编号已经存在");
            re.setSuccess(true);
            return re;
        }
        return returnSuccess(null);

    }

    /**
     * 验证时效名称是否存在(门店)
     */
    @RequestMapping("/getConfigNameByInsert.do")
    @ResponseBody
    public ResultEntity getConfigNameByInsert(String configName) {
        ResultEntity re = new ResultEntity();
        String name = signSiteTimeService.getConfigNameByInsert(configName);
        if (StringUtils.isNotEmpty(name)) {
            re.setCode("0");
            re.setData(name);
            re.setMsg("时效名称已经存在");
            re.setSuccess(true);
            return re;
        }
        return returnSuccess(null);

    }
    /**
     * 验证时效名称是否存在(门店  修改)
     */
    @RequestMapping("/getConfigNameByInsert2.do")
    @ResponseBody
    public ResultEntity getConfigNameByInsert2(String configName,String configCode) {
        ResultEntity re = new ResultEntity();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("configName", configName);
        paramMap.put("configCode", configCode);

        try {
            int flag = signSiteTimeService.getConfigNameByInsert2(paramMap);
            re.setSuccess(true);
            re.setData(flag);
        } catch (Exception e) {
            e.printStackTrace();
            re.setSuccess(false);
        }

        return re;

    }

    /**
     * 新增数据(门店)
     */
    @RequestMapping(value = "/addStoreSendTime.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity addtStoreSendTime(@RequestBody SignSiteTimeVo signSiteTimeVo) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUser = UserContext.getCurrentUser().getUserName();
            int success = signSiteTimeService.insertSignSiteTime(signSiteTimeVo, currentUser);
            if (success > 0) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("新增成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("新增失败");
            }

        } catch (Exception e) {
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;

    }

    /**
     * 删除数据(门店)
     *
     */
    @RequestMapping(value = "/deleteStoreSendTime.do")
    @ResponseBody
    public ResultEntity deleteStoreSendTime(Long id) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            int success = signSiteTimeService.deleteSignSiteTimeById(id);
            if (success > 0) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("删除成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("删除失败");
            }

        } catch (Exception e) {
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;

    }

    /**
     * 修改之前通过id 获取修改前的数据(门店)
     */
    @RequestMapping("/preUpdateStoreSendTime.do")
    @ResponseBody
    public ResultEntity preUpdateStoreSendTime(Long id) {
        ResultEntity resultEntity = new ResultEntity();
        SignSiteTimeVo signSiteTimeVo = null;
        try {
            signSiteTimeVo = signSiteTimeService.preUpdateStoreData(id);
            if (signSiteTimeVo == null) {
                return returnException("没有查询到要修改的数据");
            }
        } catch (BusinessException e) {
            return returnException(e);
        }
        return returnSuccess(signSiteTimeVo);
    }

    /**
     * 执行修改的操作(门店)
     */
    @RequestMapping(value = "/updateStoreSendTime.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateStoreSendTime(@RequestBody SignSiteTimeVo signSiteTimeVo) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUser = UserContext.getCurrentUser().getUserName();
            int success = signSiteTimeService.updateSignSiteTime(signSiteTimeVo, currentUser);
            if (success > 0) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("修改成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("修改失败");
            }

        } catch (Exception e) {
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;

    }

    /**
     * 批量启用选择的数据(门店)
     */
    @RequestMapping("/batchStartById.do")
    @ResponseBody
    public ResultEntity batchStartValue(long ids[]) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            boolean flag = signSiteTimeService.batchStart(ids);
            if (flag == true) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("启用成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("启用失败");
            }

        } catch (Exception e) {

            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg("启用失败");

        }

        return resultEntity;

    }

    /**
     * 批量禁用选择的数据(门店)
     */
    @RequestMapping("/batchCloseById.do")
    @ResponseBody
    public ResultEntity batchCloseValue(long ids[]) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            boolean flag = signSiteTimeService.batchClose(ids);
            if (flag == true) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("停用成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("停用失败");
            }

        } catch (Exception e) {

            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg("停用失败");

        }

        return resultEntity;

    }

    /**
     * 批量删除选择的数据(门店)
     */
    @RequestMapping("/batchDeleteStoreSendTimeById.do")
    @ResponseBody
    public ResultEntity batchDeleteStoreSendTimeByIdValue(long ids[]) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            boolean flag = signSiteTimeService.batchDeleteStoreSendTimeByIdValue(ids);
            if (flag == true) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("删除成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("删除失败");
            }

        } catch (Exception e) {

            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg("删除失败");

        }

        return resultEntity;

    }
    //batchDeleteCentreSendTimeById.do

    /**
     * 批量删除选择的数据(转运/分拨)
     */
    @RequestMapping("/batchDeleteCentreSendTimeById.do")
    @ResponseBody
    public ResultEntity batchDeleteCentreSendTimeByIdValue(long ids[]) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            boolean flag = signTrancenterTimeService.batchDeleteCentreSendTimeByIdValue(ids);
            if (flag == true) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("删除成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("删除失败");
            }

        } catch (Exception e) {

            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg("删除失败");

        }

        return resultEntity;

    }


    /**
     *
     * Description: 获取树形结构
     *
     * @return Created by zb 2016年3月28日
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/getPriceRegionTree.do")
    @ResponseBody
    public List<TreeNode<BaseSiteEntity, TreeNode>> getPriceRegionTree(String node, String id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        if (node == null || node.equals(""))
            return new ArrayList<TreeNode<BaseSiteEntity, TreeNode>>();
        paramMap.put("blFlag", 1);
        paramMap.put("upSite", node);
        List<BaseSiteEntity> baseSiteEntities = baseSiteEntityService.get(paramMap);

        if (CollectionUtils.isEmpty(baseSiteEntities))
            return new ArrayList<TreeNode<BaseSiteEntity, TreeNode>>();

        return this.baseOrgEntitiesToTreenodes(baseSiteEntities, node, id);
    }

    /**
     *
     * 树的获取方法
     */
    public List<TreeNode<BaseSiteEntity, TreeNode>> baseOrgEntitiesToTreenodes(List<BaseSiteEntity> baseSiteEntities,
            String upSiteCode, String configCode) {
        List<TreeNode<BaseSiteEntity, TreeNode>> nodes = new ArrayList();
        List<BaseSiteEntity> priceSites = null;
        if (CollectionUtils.isEmpty(baseSiteEntities))
            return nodes;
        Set<String> set = signSiteTimeService.getChildSites(upSiteCode);
        if (!StringUtil.isEmpty(configCode)) {
            priceSites = signSiteTimeService.getByConfigCodeAndSites(configCode, baseSiteEntities);

        } else {
            for (BaseSiteEntity baseSiteEntity : baseSiteEntities) {
                TreeNode treeNode = new TreeNode();
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
        if (CollectionUtils.isEmpty(priceSites))
            return nodes;
        for (BaseSiteEntity baseSiteEntity : priceSites) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(baseSiteEntity.getSiteCode());
            treeNode.setParentId(upSiteCode);
            if (baseSiteEntity.getSiteId() == null) {
                treeNode.setChecked(false);
            } else {
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

    /************************* 派件时效转运/分拨 **********************************/
    /**
     * 派件时效(转运/分拨)首页
     *
     * @return
     */
    @RequestMapping("/centreSendTime.do")
    public String indexCentrePage() {
        return "/basedev/centreSendTime";
    }

    /**
     * 分页查询所有数据(转运/分拨)
     *
     * @param queryPageVo
     * @return josn
     */
    @RequestMapping("/getCentreSendTimeList.do")
    @ResponseBody
    public Pagination<SignTrancenterTimeVo> getCentreSendTimePagination(@QueryPage QueryPageVo queryPageVo) {
        Sort[] sorts = new Sort[1];
        Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
        sorts[0] = sort;
        queryPageVo.setSorts(sorts);
        Pagination<SignTrancenterTimeVo> pageInfo = signTrancenterTimeService.getPage(queryPageVo.getParaMap(),
                queryPageVo.getPage(), sorts);
        return pageInfo;

    }

    /**
     * 验证时效编号是否存在(转运/分拨)
     */
    @RequestMapping("/getConfigCodeByCentreInsert.do")
    @ResponseBody
    public ResultEntity getConfigCodeByCentreInsert(String configCode) {
        ResultEntity re = new ResultEntity();
        String configCode1 = signTrancenterTimeService.getConfigCodeByCentreInsert(configCode);
        if (StringUtils.isNotEmpty(configCode1)) {
            re.setCode("0");
            re.setData(configCode1);
            re.setMsg("时效编号已经存在");
            re.setSuccess(true);
            return re;
        }
        return returnSuccess(null);

    }

    /**
     * 验证时效名称是否存在(转运/分拨)
     */
    @RequestMapping("/getConfigNameByCentreInsert.do")
    @ResponseBody
    public ResultEntity getConfigNameByCentreInsert(String configName) {
        ResultEntity re = new ResultEntity();
        String name = signTrancenterTimeService.getConfigNameByCentreInsert(configName);
        if (StringUtils.isNotEmpty(name)) {
            re.setCode("0");
            re.setData(name);
            re.setMsg("时效名称已经存在");
            re.setSuccess(true);
            return re;
        }
        return returnSuccess(null);

    }

    /**
     * 验证时效名称是否存在(转运/分拨)  修改
     */
    @RequestMapping("/getConfigNameByCentreInsert2.do")
    @ResponseBody
    public ResultEntity getConfigNameByCentreInsert2(String configName,String configCode) {
        ResultEntity re = new ResultEntity();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("configName", configName);
        paramMap.put("configCode", configCode);
        try {
            int flag = signTrancenterTimeService.getConfigNameByCentreInsert2(paramMap);
            re.setSuccess(true);
            re.setData(flag);
        } catch (Exception e) {
            e.printStackTrace();
            re.setSuccess(false);
        }

        return re;

    }

    /**
     * 新增数据(转运/分拨)
     */
    @RequestMapping(value = "/addCentreSendTime.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity addtCentreSendTime(@RequestBody SignTrancenterTimeVo signTrancenterTimeVo) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUser = UserContext.getCurrentUser().getUserName();
            int success = signTrancenterTimeService.insertSignTrancenterTime(signTrancenterTimeVo, currentUser);
            if (success > 0) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("新增成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("新增失败");
            }

        } catch (Exception e) {
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * 删除数据(转运/分拨)
     *
     */
    @RequestMapping(value = "/deleteCentreSendTime.do")
    @ResponseBody
    public ResultEntity deleteCentreSendTime(Long id) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            int success = signTrancenterTimeService.deleteSignTrancenterTimeById(id);
            if (success > 0) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("删除成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("删除失败");
            }

        } catch (Exception e) {
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;

    }

    /**
     * 修改之前通过id 获取修改前的数据(转运/分拨)
     */
    @RequestMapping("/preUpdateCentreSendTime.do")
    @ResponseBody
    public ResultEntity preUpdateCentreSendTime(Long id) {
        ResultEntity resultEntity = new ResultEntity();
        SignTrancenterTimeVo signTrancenterTimeVo = null;
        try {
            signTrancenterTimeVo = signTrancenterTimeService.preUpdateCentreData(id);
            if (signTrancenterTimeVo == null) {
                return returnException("没有查询到要修改的数据");
            }
        } catch (BusinessException e) {
            return returnException(e);
        }
        return returnSuccess(signTrancenterTimeVo);
    }

    /**
     * 执行修改的操作(转运/分拨)
     */
    @RequestMapping(value = "/updateCentreSendTime.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateCentreSendTime(@RequestBody SignTrancenterTimeVo signTrancenterTimeVo) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUser = UserContext.getCurrentUser().getUserName();
            int success = signTrancenterTimeService.updateSignTrancenterTime(signTrancenterTimeVo, currentUser);
            if (success > 0) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("修改成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("修改失败");
            }

        } catch (Exception e) {
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;

    }

    /**
     * 批量启用选择的数据(转运/分拨)
     */
    @RequestMapping("/batchStartCentreById.do")
    @ResponseBody
    public ResultEntity batchStartCentreValue(long ids[]) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            boolean flag = signTrancenterTimeService.batchStart(ids);
            if (flag == true) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("启用成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("启用失败");
            }

        } catch (Exception e) {

            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg("启用失败");

        }

        return resultEntity;

    }

    /**
     * 批量禁用选择的数据(转运/分拨)
     */
    @RequestMapping("/batchCloseCentreById.do")
    @ResponseBody
    public ResultEntity batchCloseCentreValue(long ids[]) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            boolean flag = signTrancenterTimeService.batchClose(ids);
            if (flag == true) {
                resultEntity.setSuccess(true);
                resultEntity.setMsg("停用成功");
            } else {
                resultEntity.setSuccess(false);
                resultEntity.setMsg("停用失败");
            }

        } catch (Exception e) {

            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg("停用失败");

        }

        return resultEntity;

    }

    /**
     *
     * Description: 获取树形结构（分拨转运）
     *
     * @return Created by zb 2016年3月28日
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping("/getPriceRegionTree2.do")
    @ResponseBody
    public List<TreeNode<BaseOrgEntity, TreeNode>> getPriceRegionTree2(String node, String id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        if (node == null || node.equals(""))
            return new ArrayList<TreeNode<BaseOrgEntity, TreeNode>>();
        paramMap.put("upOrgCode", node);
        List<BaseOrgEntity> baseOrgEntities = baseOrgEntityService.get(paramMap);

        if (CollectionUtils.isEmpty(baseOrgEntities))
            return new ArrayList<TreeNode<BaseOrgEntity, TreeNode>>();

        return this.baseOrgEntitiesToTreenodes2(baseOrgEntities, node, id);
    }

    /**
     *
     * 树的获取方法（分拨）
     */
    public List<TreeNode<BaseOrgEntity, TreeNode>> baseOrgEntitiesToTreenodes2(List<BaseOrgEntity> baseOrgEntities,
            String upOrgCode, String configCode) {
        List<TreeNode<BaseOrgEntity, TreeNode>> nodes = new ArrayList();
        List<BaseOrgEntity> priceOrgs = null;
        if (CollectionUtils.isEmpty(baseOrgEntities))
            return nodes;
        if (!StringUtil.isEmpty(configCode)) {
            priceOrgs = signTrancenterTimeService.getByConfigCodeAndSites(configCode, baseOrgEntities);

        } else {
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
        if (CollectionUtils.isEmpty(priceOrgs))
            return nodes;
        for (BaseOrgEntity baseOrgEntity : priceOrgs) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(baseOrgEntity.getOrgCode());
            treeNode.setParentId(upOrgCode);
            if (baseOrgEntity.getOrgId() == null) {
                treeNode.setChecked(false);
            } else {
                treeNode.setChecked(true);
            }
            treeNode.setEntity(baseOrgEntity);
            treeNode.setLeaf(false);
            treeNode.setText(baseOrgEntity.getOrgName());
            treeNode.setChildren(null);
            nodes.add(treeNode);
        }
        return nodes;
    }

}
