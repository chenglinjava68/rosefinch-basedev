package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.annotation.QueryPage;
import com.ycgwl.framework.springmvc.controller.AbstractController;
import com.ycgwl.framework.springmvc.entity.ResultEntity;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.context.UserContext;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseMaterialService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseMaterialEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseMaterialVo;

@Controller
@RequestMapping("/basedev")
public class BaseMaterialController extends AbstractController{

	@Autowired
	private IBaseMaterialService baseMaterialService;

	@RequestMapping("/baseMaterial.do")
	public String materialIndex(){
		return "/basedev/baseMaterial";
	}

	/**
	 * @Title:根据条件获取物料信息(分页)
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月9日 下午1:56:36
	 */
	@RequestMapping("/getbaseMaterialList.do")
	@ResponseBody
	public Pagination<BaseMaterialEntity> getbaseMaterialList(@QueryPage QueryPageVo queryPageVo){
		Pagination<BaseMaterialEntity> materialList =  baseMaterialService.getPagination(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
		return materialList;
	}
	/**
	 * @Title:插入物料信息
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月9日 下午3:05:14
	 */
	@RequestMapping("/addGoods.do")
	@ResponseBody
	public ResultEntity addGoods(BaseMaterialEntity baseMaterialEntity){
		String currentUser = UserContext.getCurrentUser().getUserName();
		int result = 0;
		if(StringUtils.isEmpty(currentUser)){
			return returnException("当前登录人为空");
		}
		if(baseMaterialEntity == null){
			return returnException("插入的物料数据为空");
		}
		try {
			Long id = BasicEntityIdentityGenerator.getInstance().generateId();
		    Date date = new Date();
			baseMaterialEntity.setId(id);
			baseMaterialEntity.setCreateUserCode(currentUser);
			baseMaterialEntity.setCreateTime(date);
			baseMaterialEntity.setModifyTime(date);
			baseMaterialEntity.setModifyUserCode(currentUser);
			baseMaterialEntity.setDelFlag(0);
			result = baseMaterialService.insert(baseMaterialEntity);
			if(result>0){
				return returnSuccess("插入成功");
			}
		} catch (BusinessException e) {
			return returnException("插入失败");
		}
		return returnException("插入失败");
	}
	/**
	 * @Title:修改之前：根据传入的id查询要修改的信息
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月9日 下午2:21:48
	 */
	@RequestMapping("/preUpdateGoods.do")
	@ResponseBody
	public ResultEntity preUpdateGoods(Long id){
		BaseMaterialVo baseMaterialVo = null;
		try {
				baseMaterialVo = baseMaterialService.preUpdateGoods(id);
				if(baseMaterialVo == null){
					return returnException("没有查询到要修改的数据");
				}
		} catch (BusinessException e) {
			return returnException(e);
		}

		return returnSuccess(baseMaterialVo);
	}
	/**
	 * @Title:修改物料信息
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月9日 下午3:06:04
	 */
	@RequestMapping("/updateGoods.do")
	@ResponseBody
	public ResultEntity updateGoods(BaseMaterialEntity baseMaterialEntity){
		String currentUser = UserContext.getCurrentUser().getUserName();
		int result = 0;
		if(StringUtils.isEmpty(currentUser)){
			return returnException("当前登录人为空");
		}
		if(baseMaterialEntity == null){
			return returnException("修改的物料数据为空");
		}
		try {
			baseMaterialEntity.setModifyUserCode(currentUser);
			baseMaterialEntity.setModifyTime(new Date());
			result = baseMaterialService.update(baseMaterialEntity);
			if(result>0){
				return returnSuccess("修改成功");
			}
		} catch (BusinessException e) {
			return returnException("修改失败");
		}
		return returnException("修改失败");
	}
	/**
	 * @Title:删除物料信息（假删除）
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月9日 下午2:32:41
	 */
	@RequestMapping("/deletegoods.do")
	@ResponseBody
	public ResultEntity deletegoods(Long id){
		int result = 0;
		 result = baseMaterialService.deletegoods(id);
		if(result >0){
			return returnSuccess("删除成功");
		}
		return returnException("删除失败");
	}
	/**
	 * @Title:
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年12月22日 下午4:01:22
	 */
	@RequestMapping("/batchDeleteByIds.do")
	@ResponseBody
	public ResultEntity batchDeleteByIds(Long[] ids){
		int result = baseMaterialService.batchDeleteByIds(ids);
		if(result == 0){
			return returnException("批量删除失败");
		}
		return returnSuccess("批量删除成功");
	}
	/**
	 * @Title:根据编号查询是否存在该物料
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月9日 下午2:44:55
	 */
	@RequestMapping("/getMaterialCodeByInsert.do")
	@ResponseBody
	public ResultEntity getMaterialCodeByInsert(String goodsCode){
		ResultEntity re = new ResultEntity();
		List<BaseMaterialVo> goods= baseMaterialService.getMaterialCodeByInsert(goodsCode);
		if(goods !=null &&goods.size()>0){
			 re.setCode("0");
			 re.setData(goods.get(0));
			 re.setMsg("物料编号已经存在");
			 re.setSuccess(true);
			 return re;
		 }
		return returnSuccess(null);
	}
	/**
	 * @Title:根据名称查询是否存在该物料
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年11月9日 下午2:45:00
	 */
	@RequestMapping("/getMaterialNameByInsert.do")
	@ResponseBody
	public ResultEntity getMaterialNameByInsert(String goodsName){
		ResultEntity re = new ResultEntity();
		List<BaseMaterialVo> name= baseMaterialService.getMaterialNameByInsert(goodsName);
		if(CollectionUtils.isNotEmpty(name)){
			 re.setCode("0");
			 re.setData(name.get(0));
			 re.setMsg("物料名称已经存在");
			 re.setSuccess(true);
			 return re;
		 }
		return returnSuccess(null);
	}
}
