package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseProductService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseProductEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseProductVo;

/**
 *
 * 产品管理Controller
 *
 * @author guoh.mao
 */
@Controller
@RequestMapping("/basedev")
public class BaseProductController extends AbstractController {
	@Autowired
	private IBaseProductService baseProductService;

	/**
	 * 产品管理主页
	 *
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/baseProductIndex.do")
	public String indexPage(){
		return "/basedev/baseProductIndex";
	}

	/**
	 * 产品列表
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getBaseProductList.do")
	@ResponseBody
	public Pagination<BaseProductEntity> getBaseProductList(@QueryPage QueryPageVo queryPageVo) {
		Sort[] sorts = new Sort[1];
		Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
		sorts[0] = sort;
		queryPageVo.setSorts(sorts);

		Pagination<BaseProductEntity> pageInfo = baseProductService.getPage(queryPageVo);

		List<BaseProductEntity> list = pageInfo.getList();
		List<BaseProductEntity> resultList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(list)) {
			for (BaseProductEntity baseProductEntity : list) {
				BaseProductEntity productEntity = baseProductService.constructFullBaseProductEntity(baseProductEntity);
				resultList.add(productEntity);
			}

			// set list
			pageInfo.setList(resultList);
		}

		return pageInfo;
	}

	/**
	 * 产品列表（产品公共选择器）
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getComboProductList.do")
	@ResponseBody
	public Pagination<BaseProductEntity> getComboProductList(@QueryPage QueryPageVo queryPageVo) {
		Pagination<BaseProductEntity> pageInfo = baseProductService.getPaginationBaseProductList(queryPageVo);
		return pageInfo;
	}


	/**
	 * 产品编号唯一校验
	 *
	 * @param productCode
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/uniqueCheckByProductCode.do")
	@ResponseBody
	public ResultEntity uniqueCheckByProductCode(String productCode){
		ResultEntity resultEntity = new ResultEntity();

		try {
			int count = baseProductService.uniqueCheckByProductCode(productCode);
			resultEntity.setSuccess(true);
			resultEntity.setData(count);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity.setSuccess(false);
			resultEntity.setMsg("校验失败！详细信息："+e.getMessage());
		}

		return resultEntity;
	}

	/**
	 * 产品名称唯一校验
	 *
	 * @param productCode 产品编号
	 * @param productName 产品名称
	 * @param state 新增/修改
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/uniqueCheckByProductName.do")
	@ResponseBody
	public ResultEntity uniqueCheckByProductName(String productCode, String productName, String state){
		ResultEntity resultEntity = new ResultEntity();

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("productCode", productCode);
			map.put("productName", productName);
			map.put("state", state);

			int count = baseProductService.uniqueCheckByProductName(map);
			resultEntity.setSuccess(true);
			resultEntity.setData(count);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity.setSuccess(false);
			resultEntity.setMsg("校验失败！详细信息："+e.getMessage());
		}

		return resultEntity;
	}

	/**
	 * 保存产品
	 *
	 * @param baseProductVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/insertBaseProduct.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity insertBaseProduct(@RequestBody BaseProductVo baseProductVo){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			baseProductService.insertBaseProduct(baseProductVo, currentUserName);
			resultEntity.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity.setSuccess(false);
			resultEntity.setMsg("保存失败！详细信息："+e.getMessage());
		}
		return resultEntity;
	}

	/**
	 * 编辑产品
	 *
	 * @param baseProductVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/updateBaseProduct.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity updateBaseProduct(@RequestBody BaseProductVo baseProductVo){
		ResultEntity resultEntity = new ResultEntity();

		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			baseProductService.updateBaseProduct(baseProductVo,currentUserName);
			resultEntity.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultEntity.setSuccess(false);
			resultEntity.setMsg("修改失败！详细信息："+e.getMessage());
		}

		return resultEntity;
	}

	 /**
     * 批量启用/停用
     * @author
     * @param baseProductVo
     * @return
     */
    @RequestMapping(value="/batchUpdateProductBlFlagById.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity batchUpdateRouteBlFlagById(@RequestBody BaseProductVo baseProductVo) {
        if(baseProductVo == null || CollectionUtils.isEmpty(baseProductVo.getIdList())){
            return returnException("传入的参数不能为空");
        }
        try {
            //进行数据的
        	baseProductService.modifyBlFlagByIdList(baseProductVo.getIdList(), baseProductVo.getBlFlag());
            return returnSuccess("修改成功!");
        } catch (BusinessException e) {
            return returnException("修改失败!异常信息:"+e.getMessage());
        }
    }

}
