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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseCarLineService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCarLineEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseCarLineVo;
@Controller
@RequestMapping("/basedev")
public class BaseCarLineController extends AbstractController {

	@Autowired
	private IBaseCarLineService baseCarLineService;


	/**
	 * 车线管理页面
	 * @return
	 */
	@RequestMapping("/baseCarLineIndex.do")
	public String areaIndex(){

		return "/basedev/baseCarLineIndex";
	}
	 /**
     * 车线维护列表
     *
     * @param queryPageVo
     * @return
     * @author
     */
    @RequestMapping("/getPaginationbaseCarLineList.do")
    @ResponseBody
    public Pagination<BaseCarLineEntity> getPaginationbaseCarLineList(@QueryPage QueryPageVo queryPageVo) {
    	  Sort[] sorts = new Sort[1];
          Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
          sorts[0] = sort;
          Map<String, Object> map = queryPageVo.getParaMap();
          map.put("delFlag", 0);
          Pagination<BaseCarLineEntity> pageInfo = baseCarLineService.getPagination(map, queryPageVo.getPage(), sorts);
          return pageInfo;
    }
    /**
     * 保存车线信息
     *
     * @param
     * @return
     * @author
     */
    @RequestMapping(value="/insertbaseCarLine.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity insertbaseCarLine(@RequestBody BaseCarLineVo baseCarLineVo){
    	if(baseCarLineVo.getBeginRegionCode() == null||baseCarLineVo.getEndRegionCode()==null){
            return returnException("行政区域不能为空");
        }
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseCarLineService.insertCarLine(baseCarLineVo, currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }
    /**
     * 校验车线名称唯一信息
     * @param vehicelNo
     * @param id
     * @return
     */
    @RequestMapping(value="/uniqueCheckbaseCarLineBylineName.do")
    @ResponseBody
    public ResultEntity uniqueCheckbaseCarLineBylineCode(Long id, String lineName){
    	 ResultEntity resultEntity = new ResultEntity();
         try {
             int count = baseCarLineService.uniqueCheckByLineName(id, lineName);
             resultEntity.setSuccess(true);
             resultEntity.setData(count);
         } catch (Exception e) {
             e.printStackTrace();
             resultEntity.setSuccess(false);
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
    @RequestMapping(value="/uniqueCheckByLineCode.do")
    @ResponseBody
    public ResultEntity uniqueCheckByConfigCode(String lineCode){
        ResultEntity resultEntity = new ResultEntity();

        try {
            int count = baseCarLineService.uniqueCheckBylineCode(lineCode);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }

        return resultEntity;
    }
    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/deletebaseCarLine.do")
    @ResponseBody
    public ResultEntity deleteBaseCarLine(Long id) {
        ResultEntity resultEntity = new ResultEntity();
        if(id == null){
            return returnException("传入的参数不能为空");
        }
        try {
            baseCarLineService.removeBaseCarLine(id);
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
     * @param baseCarLineVo
     * @return
     */
    @RequestMapping(value="/batchDeletebaseCarLineById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchDeleteBaseRouteById(@RequestBody BaseCarLineVo BaseCarLineVo) {
        if(BaseCarLineVo == null || CollectionUtils.isEmpty(BaseCarLineVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            baseCarLineService.removeBaseCarLineByIdList(BaseCarLineVo.getIdList());
            return returnSuccess("删除成功!");
        } catch (BusinessException e) {
            return returnException("删除失败!异常信息:"+e.getMessage());
        }
    }


    @RequestMapping("/querybaseCarLineDetailList.do")
    @ResponseBody
    public ResultEntity queryBaseCarLineDetailList(String lineCode){
        ResultEntity resultEntity = new ResultEntity();
        if(StringUtils.isBlank(lineCode)){
            return resultEntity;
        }
        try {
            resultEntity.setSuccess(true);
            resultEntity.setData(baseCarLineService.findBaseCarLineDetailListByLineId(lineCode));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }
    /**
     * 编辑车线信息
     *
     * @param baseCarLineVo
     * @return
     * @author
     */
    @RequestMapping(value="/updatebaseCarLine.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateBaseCarLine(@RequestBody BaseCarLineVo baseCarLineVo){
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseCarLineService.modifyBaseCarLine(baseCarLineVo, currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }
    /**
     * 批量启用/停用
     * @author
     * @param baseCarLineVo
     * @return
     */
    @RequestMapping(value="/batchUpdateCarLineBlFlagById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchUpdateRouteBlFlagById(@RequestBody BaseCarLineVo baseCarLineVo) {
        if(baseCarLineVo == null || CollectionUtils.isEmpty(baseCarLineVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("blFlag", baseCarLineVo.getBlFlag());
            paramMap.put("list", baseCarLineVo.getIdList());
            paramMap.put("modifyUserCode", UserContext.getCurrentUser().getUserName());
            baseCarLineService.modifyBlFlagByIdList(paramMap);
            return returnSuccess(baseCarLineVo.getBlFlag() == 1 ? "启用成功" : "停用成功");
        } catch (BusinessException e) {
            return returnException("修改失败!异常信息:"+e.getMessage());
        }
    }



}
