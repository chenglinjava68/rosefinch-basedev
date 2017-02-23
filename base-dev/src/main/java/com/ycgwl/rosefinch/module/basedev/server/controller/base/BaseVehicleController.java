package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseVehicleService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseVehicleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseVehicleVo;

/**
 *
 * Title: 车辆维护Controller Description:表T_BASE_VEHICLE CRUD
 *
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月21日 下午3:48
 *
 */
@Controller
@RequestMapping("/basedev")
public class BaseVehicleController extends AbstractController {

    @Autowired
    private IBaseVehicleService baseVehicleService;

    /**
     * 车辆管理页面
     * @return
     */
    @RequestMapping("/baseVehicleIndex.do")
    public String indexPage(){
        return "/basedev/baseVehicleIndex";
    }

    /**
     * 车辆维护列表
     *
     * @param queryPageVo
     * @return
     * @author caijue
     */
    @RequestMapping("/getPaginationBaseVehicleList.do")
    @ResponseBody
    public Pagination<BaseVehicleEntity> getPaginationBaseVehicleList(@QueryPage QueryPageVo queryPageVo) {
        Sort[] sorts = new Sort[1];
        Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
        sorts[0] = sort;
        Map<String, Object> map = queryPageVo.getParaMap();
        map.put("delFlag", 0);
        Pagination<BaseVehicleEntity> pageInfo = baseVehicleService.getPagination(map, queryPageVo.getPage(), sorts);
        return pageInfo;
    }

    /**
     * 保存车辆信息
     *
     * @param GetPackageTimeVo
     * @return
     * @author
     */
    @RequestMapping(value="/insertBaseVehicle.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity insertBaseVehicle(@RequestBody BaseVehicleVo baseVehicleVo){
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseVehicleService.insertVehicle(baseVehicleVo, currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * 编辑车辆信息
     *
     * @param BaseVehicleVo
     * @return
     * @author
     */
    @RequestMapping(value="/updateBaseVehicle.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateBaseVehicle(@RequestBody BaseVehicleVo baseVehicleVo){
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseVehicleService.modifyVehicle(baseVehicleVo, currentUserName);
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
    @RequestMapping("/deleteBaseVehicle.do")
    @ResponseBody
    public ResultEntity deleteBaseVehicle(Long id) {
        ResultEntity resultEntity = new ResultEntity();
        if(id == null){
            return returnException("传入的参数不能为空");
        }
        try {
            baseVehicleService.deleteVehicle(id);
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
     * @param baseVehicleVo
     * @return
     */
    @RequestMapping(value="/batchDeleteBaseVehicleById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchDeleteBaseVehicleById(@RequestBody BaseVehicleVo baseVehicleVo) {
        if(baseVehicleVo == null || CollectionUtils.isEmpty(baseVehicleVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            baseVehicleService.batchlogicalDeleteById(baseVehicleVo.getIdList());
            return returnSuccess("删除成功!");
        } catch (BusinessException e) {
            return returnException("删除失败!异常信息:"+e.getMessage());
        }
    }

    /**
     * 校验车辆唯一信息
     * @param vehicelNo
     * @param id
     * @return
     */
    @RequestMapping(value="/uniqueCheckBaseVehicleByVehicleNo.do")
    @ResponseBody
    public ResultEntity uniqueCheckBaseVehicleByVehicleNo(String vehicelNo, Long id){
        ResultEntity resultEntity = new ResultEntity();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("vehicelNo", vehicelNo);
            map.put("id", id);

            int count = baseVehicleService.uniqueCheckByVehicelNo(map);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }

        return resultEntity;
    }

    /**
     *
     * @Title 批量停用、启用
     *　@Company　远成快运
     * @author caijue
     * @date 2017年1月12日上午9:36:44
     * @param baseVehicleVo
     * @return
     */
    @RequestMapping(value="/batchUpdateVehicleBlFlagById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchUpdateVehicleBlFlagById(@RequestBody BaseVehicleVo baseVehicleVo) {
        if(baseVehicleVo == null || CollectionUtils.isEmpty(baseVehicleVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("blFlag", baseVehicleVo.getBlFlag());
            paramMap.put("list", baseVehicleVo.getIdList());
            paramMap.put("modifyUserCode", UserContext.getCurrentUser().getUserName());
            baseVehicleService.modifyBlFlagByIdList(paramMap);
            return returnSuccess(baseVehicleVo.getBlFlag() == 1 ? "启用成功" : "停用成功");
        } catch (BusinessException e) {
            return returnException("修改失败!异常信息:"+e.getMessage());
        }
    }

    /**
     * 车辆编号唯一校验
     *
     * @param vechicleCode
     * @return
     * @author
     */
    @RequestMapping(value="/uniqueCheckByVehicleCode.do")
    @ResponseBody
    public ResultEntity uniqueCheckByVehicleCode(String vehicelCode){
        ResultEntity resultEntity = new ResultEntity();

        try {
            int count = baseVehicleService.uniqueCheckByVehicleCode(vehicelCode);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }

}
