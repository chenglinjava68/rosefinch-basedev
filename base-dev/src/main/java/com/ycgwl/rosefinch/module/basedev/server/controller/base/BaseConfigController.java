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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseConfigService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseConfigEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseConfigVo;

/**
 *
 * Title: 配置Controller
 * Description:表T_BASE_CONFIG  CRUD
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年8月11日  下午2:58:48
 *
 */
@Controller
@RequestMapping("/basedev")
public class BaseConfigController extends AbstractController {
	@Autowired
	private IBaseConfigService baseConfigService;
	/**
	 *  配置管理页面
	 *
	 */
	@RequestMapping("/baseConfigIndex.do")
	public String indexPage(){
		return "/basedev/baseConfigIndex";
	}
	/**
	 * 配置列表
	 *
	 * @param queryPageVo
	 * @return
	 * @author
	 */
	@RequestMapping("/getPaginationBaseConfigList.do")
	@ResponseBody
	public Pagination<BaseConfigEntity> getPaginationBaseConfigtList(@QueryPage QueryPageVo queryPageVo) {
		Sort[] sorts = new Sort[1];
		Sort sort = new Sort("CREATE_TIME", Sort.DESC);
		sorts[0] = sort;
		queryPageVo.setSorts(sorts);

		Pagination<BaseConfigEntity> pageInfo = baseConfigService.getConfig(queryPageVo);
		return pageInfo;
	}
	/**
	 * 通过ID删除
	 *
	 * @param id
	 * @return
	 * @author
	 */
	@RequestMapping("/deleteConfig.do")
	@ResponseBody
	public ResultEntity deleteConfig(Long configId) {
		ResultEntity resultEntity = new ResultEntity();

		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			baseConfigService.deleteConfig(configId, currentUserName);
			resultEntity.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity.setSuccess(false);
			resultEntity.setMsg(e.getMessage());
		}
		return resultEntity;
	}

	/**
	 * 保存配置
	 *
	 * @param baseConfigVo
	 * @return
	 * @author
	 */
	@RequestMapping(value="/insertBaseConfig.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity insertBaseConfig(@RequestBody BaseConfigVo baseConfigVo){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			baseConfigService.insertConfig(baseConfigVo, currentUserName);
			resultEntity.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity.setSuccess(false);
			resultEntity.setMsg(e.getMessage());
		}
		return resultEntity;
	}
	/**
	 * 编辑产品
	 *
	 * @param baseConfigVo
	 * @return
	 * @author
	 */
	@RequestMapping(value="/updateBaseConfig.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity updateBaseConfig(@RequestBody BaseConfigVo baseConfigVo){
		ResultEntity resultEntity = new ResultEntity();

		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			baseConfigService.editConfig(baseConfigVo, currentUserName);
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
	@RequestMapping(value="/uniqueCheckByConfigCode.do")
	@ResponseBody
	public ResultEntity uniqueCheckByConfigCode(String configCode){
		ResultEntity resultEntity = new ResultEntity();

		try {
			int count = baseConfigService.uniqueCheckByConfigCode(configCode);
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
	@RequestMapping(value="/uniqueCheckByConfigName.do")
	@ResponseBody
	public ResultEntity uniqueCheckByConfigName(String configCode, String configName, String state){
		ResultEntity resultEntity = new ResultEntity();

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("configCode", configCode);
			map.put("configName", configName);
			map.put("state", state);

			int count = baseConfigService.uniqueCheckByConfigName(map);
			resultEntity.setSuccess(true);
			resultEntity.setData(count);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity.setSuccess(false);
		}

		return resultEntity;
	}

    /**
     * 批量删除配置
     * @param baseConfigVo
     * @return ResultEntity
     */
    @RequestMapping(value="/batchDeleteBaseConfigById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchDeleteBaseConfigById(@RequestBody BaseConfigVo baseConfigVo) {
        if(baseConfigVo == null || CollectionUtils.isEmpty(baseConfigVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            baseConfigService.removeBaseConfigByIdList(baseConfigVo.getIdList());
            return returnSuccess("删除成功!");
        } catch (BusinessException e) {
            return returnException("删除失败!异常信息:"+e.getMessage());
        }
    }

}
