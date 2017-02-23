package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.framework.springmvc.annotation.QueryPage;
import com.ycgwl.framework.springmvc.controller.AbstractController;
import com.ycgwl.framework.springmvc.entity.ResultEntity;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.common.shared.context.UserContext;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseAreaService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAreaEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseAreaVo;

@Controller
@RequestMapping("/basedev")
public class BaseAreaController extends AbstractController{

	@Autowired
	private IBaseAreaService baseAreaService;
	@Autowired
	private IBaseSiteService baseSiteService;

	@RequestMapping("/baseArea.do")
	public String areaIndex(){

		return "/basedev/baseArea";
	}
	/**
	 * @Title:根据条件分页查询所有片区
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月26日 下午3:35:44
	 */
	@RequestMapping("/getBaseAreaList.do")
	@ResponseBody
	public Pagination<BaseAreaEntity> getBaseAreaList(@QueryPage QueryPageVo queryPageVo){
		Pagination<BaseAreaEntity> baseAreaList=baseAreaService.getPagination(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
		 if(baseAreaList == null){
			 baseAreaList = new Pagination<BaseAreaEntity>();
			 baseAreaList.setCount(0);
			 baseAreaList.setPageSize(10);
			 baseAreaList.setPageNum(0);
		 }
		return baseAreaList;
	}
	/**
	 * @Title:新增一条片区记录
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月26日 下午5:24:42
	 */
	@RequestMapping("/addArea.do")
	@ResponseBody
	public ResultEntity addArea(BaseAreaVo baseAreaVo){
		String currentUser = UserContext.getCurrentUser().getUserName();
		int result = baseAreaService.insertArea(baseAreaVo , currentUser);
		if(result >0){
			return returnSuccess("插入片区成功");
		}
		return returnException("插入片区失败");
	}
	/**
	 * @Title:修改之前查询数据
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月28日 上午11:27:11
	 */
	@RequestMapping("/preUpdateArea.do")
	@ResponseBody
	public ResultEntity preUpdateArea(Long areaId){
		BaseAreaEntity  baseAreaEntity = null ;
		try {
			baseAreaEntity  = baseAreaService.getById(areaId);
				return returnSuccess(baseAreaEntity);
		} catch (Exception e) {
			return returnException(e.getMessage());
		}
	}
	/**
	 * @Title:修改一条片区记录
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月26日 下午5:26:11
	 */
	@RequestMapping("/updateArea.do")
	@ResponseBody
	public ResultEntity updateArea(BaseAreaVo baseAreaVo){
		String currentUser = UserContext.getCurrentUser().getUserName();
		int result = baseAreaService.updateArea(baseAreaVo , currentUser);
		if(result >0){
			return returnSuccess("修改片区成功");
		}
		return returnException("修改片区失败");
	}
	/**
	 * @Title:删除一条片区记录（假删除）
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月26日 下午5:27:20
	 */
	@RequestMapping("/deleteArea.do")
	@ResponseBody
	public ResultEntity deleteArea(Long areaId,String areaCode){
		List<String> areaCodeList = new ArrayList<String>();
		if(StringUtils.isEmpty(areaCode)){
			return returnException("删除片区失败");
		}
		areaCodeList.add(areaCode);
		if(preDeleteGetAreaBaseSite(areaCodeList)){
			return  returnException("删除片区失败,在门店中有引用不能删除");
		}
		int result = baseAreaService.deleteAreaById(areaId);
		if(result >0){
			return returnSuccess("删除片区成功");
		}
		return returnException("删除片区失败");
	}
	@RequestMapping("/stopAndstartAreaById.do")
	@ResponseBody
	public ResultEntity stopAndstartAreaById(Long[] areaId,String typeValue){
		if(StringUtils.isEmpty(typeValue) ){
			typeValue = "startArea";
		}
		String currentUser = UserContext.getCurrentUser().getUserName();
		int result = baseAreaService.stopAndstartAreaById(areaId,typeValue,currentUser);
		if(result >0){
			if("stopArea".equals(typeValue)){
				return returnSuccess("停用片区成功");
			}else if( "startArea".equals(typeValue)){
				return returnSuccess("启用片区成功");
			}
		}else if("stopArea".equals(typeValue)){
			return returnException("停用片区失败");
		}else if("startArea".equals(typeValue)){
			return returnException("启用片区失败");
		}
		return returnException("数据异常");

	}
	/**
	 * @Title:批量删除数据根据ID
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年12月22日 下午2:49:32
	 */
	@RequestMapping("/batchDeleteById.do")
	@ResponseBody
	public ResultEntity batchDeleteById(Long[] areaId,String[] areaCode){
		List<String> areaCodeList = new ArrayList<String>();
		if(areaCode!= null && areaCode.length>0){
			for(int i = 0;i<areaCode.length ; i++)
			areaCodeList.add(areaCode[i]);
		}else{
			return returnException("数据异常");
		}
		if(preDeleteGetAreaBaseSite(areaCodeList)){
			return  returnException("删除片区失败,在门店中有引用不能删除");
		}
		int result = baseAreaService.batchDeleteById(areaId);
		if(result==0){
			return returnException("批量删除失败");
		}
		return returnSuccess("批量删除成功");
	}
	/**
	 * @Title:根据输入的片区编号检查是否已经存在该编号
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月27日 上午8:31:49
	 */
	@RequestMapping("/getAreaCodeByInsert.do")
	@ResponseBody
	public ResultEntity getAreaCodeByInsert(String areaCode){
		ResultEntity re = new ResultEntity();
		List<BaseAreaVo> result = baseAreaService.getAreaCodeByInsert(areaCode);
		 if(result !=null && result.size()>0){
			re.setData(result.get(0));
			 re.setCode("0");
			 re.setMsg("片区编号已经存在");
			 re.setSuccess(true);
			 return re;
		 }
		return returnSuccess(null);

	}
	/**
	 * @Title:根据输入的片区编号检查是否已经存在该编号
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月27日 上午8:31:49
	 */
	@RequestMapping("/getAreaNameByInsert.do")
	@ResponseBody
	public ResultEntity getAreaNameByInsert(String areaName){
		ResultEntity re = new ResultEntity();
		List<BaseAreaVo> result = baseAreaService.getAreaNameByInsert(areaName);
		 if(result !=null && result.size()>0){
			 re.setCode("0");
			 re.setData(result.get(0));
			 re.setMsg("片区名称已经存在");
			 re.setSuccess(true);
			 return re;
		 }
		return returnSuccess(null);

	}

	/**
	 * @Title:通过片区编号查询门店信息
	 * @Description:门店信息不为空，返回true。
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2017年1月6日 上午9:55:13
	 */
	public Boolean preDeleteGetAreaBaseSite(List<String> areaCodeList){
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("areaCodeList", areaCodeList);
		List<BaseSiteEntity> siteList= baseSiteService.getBaseSiteByAreaCode(params);
		if(siteList != null && siteList.size()>0){
			return true;
		}else{
			 return false;
		}
	}
}
