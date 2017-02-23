package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.Date;

import org.mybatis.spring.support.Pagination;
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
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.context.UserContext;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseAppInfoService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;


@Controller
@RequestMapping("/basedev")
public class BaseAppInfoController extends AbstractController {

	@Autowired
	public IBaseAppInfoService baseAppInfoService;

	@RequestMapping("/baseAppInfo.do")
	public String indexPage(){
		return "/basedev/baseAppInfo";
	}

	@RequestMapping(value="/addBaseAppInfo.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity insert2BaseAppINfo(@RequestBody BaseAppInfoEntity baseAppInfoEntity){
		Long id = BasicEntityIdentityGenerator.getInstance().generateId();

		if(baseAppInfoEntity!=null){
			baseAppInfoEntity.setAppId(id);
			baseAppInfoEntity.setCreateUserCode(UserContext.getCurrentUser().getUserName());
			baseAppInfoEntity.setCreateTime(new Date()); //日期调用公用方法

			   try {
				   baseAppInfoService.insert(baseAppInfoEntity);
				  return returnSuccess("增加应用系统成功!",baseAppInfoEntity);
				} catch (BusinessException e) {
					return returnException("增加应用系统失败!"+e);
				}

		   }

		return returnException("增加应用系统失败!");
	}

	/**
	 * 分页查询
	 * @param queryPageVo
	 * @return
	 */
	@RequestMapping("/getBaseAppInfoList.do")
	@ResponseBody
	public Pagination<BaseAppInfoEntity> queryBaseAppInfo(@QueryPage QueryPageVo queryPageVo){
		return baseAppInfoService.getPagination(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
	}

	@RequestMapping("/deleteBaseAppInfoById.do")
	@ResponseBody
	public ResultEntity deleteBaseAppInfo(Long appId,String appKey){
		String currentUserCode = UserContext.getCurrentUser().getUserName();
		baseAppInfoService.deleteBaseAppInfoById(appId, appKey,currentUserCode);
		return returnSuccess("删除成功!");

	}

	@RequestMapping("/getByAppKey.do")
	@ResponseBody
	public ResultEntity getByAppKey(String appKey) {
		BaseAppInfoEntity data  = baseAppInfoService.getByAppKey(appKey);
		return returnSuccess(data);
	}

	@RequestMapping(value="/updateBaseAppInfoById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity updateBaseAppInfo(@RequestBody BaseAppInfoEntity baseAppInfoEntity){
       baseAppInfoEntity.setModifyUserCode(UserContext.getCurrentUser().getUserName());
       baseAppInfoEntity.setModifyTime(new Date());
       try{
    	   baseAppInfoService.update(baseAppInfoEntity);
       }catch(Exception e){
    	   return returnException("更新失败!");
       }
	   return returnSuccess("更新成功!");
	}
}
