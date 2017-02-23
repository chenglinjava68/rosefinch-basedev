package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import org.mybatis.spring.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.framework.springmvc.annotation.QueryPage;
import com.ycgwl.framework.springmvc.controller.AbstractController;
import com.ycgwl.framework.springmvc.entity.ResultEntity;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseInterfaceService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseInterfaceInfoEntity;
/**
 * @Title 接口管理 Controller
 * @Description 关于数据库表 T_BASE_INTERFACE_INFO 的 CRUD
 * @Company: 远成快运
 * @author DongQL
 * @date 
 *
 */
@Controller
@RequestMapping("/basedev")
public class BaseInterfaceInfoController extends AbstractController {
      @Autowired
      private IBaseInterfaceService baseInterfaceService;
	
    /**
  	 * 接口管理主页
  	 *
  	 * @return
  	 * @author guoh.mao
  	 */
  	@RequestMapping("/baseInterfaceIndex.do")
  	public String indexPage(){
  		return "/basedev/baseInterfaceIndex";
  	}
	/**
	 * 根据 接口名字 查询
	 * @param interfaceName
	 * @return
	 */
      @RequestMapping("/baseInterface.do")
      @ResponseBody
		public Pagination<BaseInterfaceInfoEntity> queryByInterfaceName(@QueryPage QueryPageVo queryPageVo) {
	        
			return baseInterfaceService.getPagination(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
		}
  
      @RequestMapping("/baseInsertInterface.do")
      @ResponseBody
      public ResultEntity insert2InterfaceInfo(BaseInterfaceInfoEntity baseInterfaceInfoEntity){    
    	  baseInterfaceService.insert(baseInterfaceInfoEntity);
    	  
    	  return returnSuccess("增加OK！", baseInterfaceInfoEntity);
      }
      
     @RequestMapping("/baseDelInterfaceById.do")
     @ResponseBody     
      public ResultEntity delById(long interfaceId,String appKey){
    	 baseInterfaceService.deleteInterById(interfaceId,appKey);
    	 return returnSuccess("删除OK！");
     } 
    
     @RequestMapping("/baseUpdateInterface.do")
     @ResponseBody
     public ResultEntity updateInterface(BaseInterfaceInfoEntity baseInterfaceInfoEntity){
    	  baseInterfaceService.update(baseInterfaceInfoEntity);
    	  return returnSuccess("更新OK！",baseInterfaceInfoEntity);
     }
     
}
