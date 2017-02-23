package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.common.shared.context.UserContext;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRouteService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRouteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseRouteVo;

/**
 * @Title:路由维护控制器
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月28日 下午5:18
 *
 */
@Controller
@RequestMapping("/basedev")
public class BaseRouteController extends AbstractController {

    @Autowired
    private IBaseRouteService baseRouteService;

    /**
     * 路由维护页面
     * @author caijue
     * @return
     */
    @RequestMapping("/baseRouteIndex.do")
    public String indexPage(){
        return "/basedev/baseRouteIndex";
    }

    /**
     * 路由查询列表
     * @author caijue
     * @param queryPageVo
     * @return
     */
    @RequestMapping("/getPaginationBaseRouteList.do")
    @ResponseBody
    public Pagination<BaseRouteEntity> getPaginationBaseRouteList(@QueryPage QueryPageVo queryPageVo) {
        Sort[] sorts = new Sort[1];
        Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
        sorts[0] = sort;
        Map<String, Object> map = queryPageVo.getParaMap();
        map.put("delFlag", 0);
        Pagination<BaseRouteEntity> pageInfo = baseRouteService.getPagination(map, queryPageVo.getPage(), sorts);
        return pageInfo;
    }

    /**
     * 保存路由信息
     * @author caijue
     * @param baseRouteVo
     * @return
     */
    @RequestMapping(value="/insertBaseRoute.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity insertBaseRoute(@RequestBody BaseRouteVo baseRouteVo){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseRouteService.createBaseRoute(baseRouteVo, currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * 编辑路由信息
     * @author caijue
     * @param baseRouteVo
     * @return
     */
    @RequestMapping(value="/updateBaseRoute.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateBaseRoute(@RequestBody BaseRouteVo baseRouteVo){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseRouteService.modifyBaseRoute(baseRouteVo, currentUserName);
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
     * @author caijue
     * @param id
     * @return
     */
    @RequestMapping("/deleteBaseRoute.do")
    @ResponseBody
    public ResultEntity deleteBaseRoute(Long id) {
        ResultEntity resultEntity = new ResultEntity();
        if(id == null){
            return returnException("传入的参数不能为空");
        }
        try {
            baseRouteService.removeBaseRoute(id);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * 批量删除
     * @author caijue
     * @param baseRouteVo
     * @return
     */
    @RequestMapping(value="/batchDeleteBaseRouteById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchDeleteBaseRouteById(@RequestBody BaseRouteVo baseRouteVo) {
        if(baseRouteVo == null || CollectionUtils.isEmpty(baseRouteVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            baseRouteService.removeBaseRouteByIdList(baseRouteVo.getIdList());
            return returnSuccess("删除成功!");
        } catch (BusinessException e) {
            return returnException("删除失败!异常信息:"+e.getMessage());
        }
    }

    /**
     * 批量启用/停用
     * @author caijue
     * @date 2016-11-28
     * @param baseRouteVo
     * @return
     */
    @RequestMapping(value="/batchUpdateRouteBlFlagById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchUpdateRouteBlFlagById(@RequestBody BaseRouteVo baseRouteVo) {
        if(baseRouteVo == null || CollectionUtils.isEmpty(baseRouteVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("blFlag", baseRouteVo.getBlFlag());
            paramMap.put("list", baseRouteVo.getIdList());
            paramMap.put("modifyUserCode", UserContext.getCurrentUser().getUserName());
            baseRouteService.modifyBlFlagByIdList(paramMap);
            return returnSuccess(baseRouteVo.getBlFlag() == 1 ? "启用成功" : "停用成功");
        } catch (BusinessException e) {
            return returnException("修改失败!异常信息:"+e.getMessage());
        }
    }

    /**
     * 获取路由详情列表
     * @author caijue
     * @param routeCode路由编号
     * @return 路由详情列表
     */
    @RequestMapping("/queryBaseRouteDetailList.do")
    @ResponseBody
    public ResultEntity queryBaseRouteDetailList(String routeCode){
        ResultEntity resultEntity = new ResultEntity();
        if(StringUtils.isBlank(routeCode)){
            return resultEntity;
        }
        try {
            resultEntity.setSuccess(true);
            resultEntity.setData(baseRouteService.findBaseRouteDetailListByRouteCode(routeCode));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }

    /**
     * 校验路由名称是否存在
     * @author caijue
     * @param id 路由ID
     * @param routeName 路由名称
     * @return
     */
    @RequestMapping("/uniqueCheckbaseRouteByRouteName.do")
    @ResponseBody
    public ResultEntity uniqueCheckbaseRouteByRouteName(Long id, String routeName){
        ResultEntity resultEntity = new ResultEntity();
        try {
            int count = baseRouteService.uniqueCheckByRouteName(id, routeName);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }

        return resultEntity;
    }

    /**
     * 路由编号唯一校验
     *
     * @param routeCode
     * @return
     * @author
     */
    @RequestMapping(value="/uniqueCheckByRouteCode.do")
    @ResponseBody
    public ResultEntity uniqueCheckByRouteCode(String routeCode){
        ResultEntity resultEntity = new ResultEntity();

        try {
            int count = baseRouteService.uniqueCheckByRouteCode(routeCode);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }
}
