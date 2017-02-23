package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
import com.ycgwl.rosefinch.module.authorization.shared.domain.UserEntity;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseEmployeeService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseSiteService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseSiteVo;
import com.ycgwl.rosefinch.module.organization.shared.domain.EmployeeEntity;

/**
* @Title: 网点Controller
* @Description:
* @Company: 远成快运
* @author guoh.mao
* @date 2016年7月26日 下午6:43:21
 */
@Controller
@RequestMapping("/basedev")
public class BaseSiteController extends AbstractController {
	@Autowired
	private IBaseSiteService baseSiteService;
	@Autowired
    private IBaseEmployeeService baseEmployeeService;


	/**
	 * 门店管理首页
	 *
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/baseSiteIndex.do")
	public String indexPage(){
		return "/basedev/baseSiteIndex";
	}




	/**
	 *
	 * 分页查询网点
	 *
	 * @param queryPageVo 封装了分页及查询参数
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getUpFinanceCenterList.do")
	@ResponseBody
	public Pagination<BaseSiteEntity> getUpFinanceCenterList(@QueryPage QueryPageVo queryPageVo) {
		Pagination<BaseSiteEntity> pageInfo = baseSiteService.getUpFinanceCenterList(queryPageVo);
		return pageInfo;
	}

	/**
	 * TODO: DOCUMENT ME!
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getUpSettleCenterList.do")
	@ResponseBody
	public Pagination<BaseSiteEntity> getUpSettleCenterList(
			@QueryPage QueryPageVo queryPageVo) {
		Pagination<BaseSiteEntity> pageInfo = baseSiteService
				.getUpSettleCenterList(queryPageVo);
		return pageInfo;
	}

	/**
	 * 网点列表（网点combobox公用组件）
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getPaginationBaseSiteList.do")
	@ResponseBody
	public Pagination<BaseSiteEntity> getPaginationBaseSiteList(@QueryPage QueryPageVo queryPageVo) {
		Pagination<BaseSiteEntity> pageInfo = baseSiteService.getPaginationBaseSiteList(queryPageVo);
		return pageInfo;
	}

	/**
	 * 级联查询当前登录用户所属网点及所有下级网点
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/queryOwnerSiteCascade.do")
	@ResponseBody
	public Pagination<BaseSiteEntity> queryOwnerSiteCascade(@QueryPage QueryPageVo queryPageVo) {
		String siteCode = null;
		Map<String, Object> map = queryPageVo.getParaMap();
		Object siteCodeObj = map.get("siteCode");
		if (null != siteCodeObj) {
			siteCode = (String)siteCodeObj;
		} else {
			UserEntity userEntity = (UserEntity)UserContext.getCurrentUser();
			siteCode = userEntity.getEmpEntity().getOwnerSite();
		}

		Pagination<BaseSiteEntity> pageInfo = baseSiteService.queryOwnerSiteCascade(queryPageVo, siteCode);
		return pageInfo;
	}

	/**
	 * 网点列表（网点panel公用组件）
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getSitePageListByCondition.do")
	@ResponseBody
	public Pagination<BaseSiteEntity> getSitePageListByCondition(@QueryPage QueryPageVo queryPageVo) {
		String userName = UserContext.getCurrentUser().getUserName();
		Pagination<BaseSiteEntity> pageInfo = baseSiteService.getSitePageListByCondition(queryPageVo,userName);
		return pageInfo;
	}
	/**
	 * 查询显示财务中心空间
	 *
	 * @param
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping("/getUpFinaceCenterPage.do")
	@ResponseBody
	public Pagination<BaseSiteEntity> getUpFinaceCenterPage(@QueryPage QueryPageVo queryPageVo) {
		String userName = UserContext.getCurrentUser().getUserName();
		Pagination<BaseSiteEntity> pageInfo = baseSiteService.getUpFinaceCenterPage(queryPageVo,userName);
		return pageInfo;
	}


	//-------------------------------------
	@RequestMapping(value="/insertBaseSite.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity insertBaseSite(@RequestBody BaseSiteVo baseSiteVo) {
		try {
			String currentUserName = UserContext.getCurrentUser().getUserName();
			baseSiteService.insertBaseSite(baseSiteVo, currentUserName);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("新增门店信息报错！异常信息:"+e.getMessage());
		}
		return returnSuccess();
	}


	@RequestMapping(value="/updateBaseSite.do",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultEntity updateaBaseSite(@RequestBody BaseSiteVo baseSiteVo) {
		try {
			String createUserCode = UserContext.getCurrentUser().getUserName();
			baseSiteService.updateBaseSite(baseSiteVo, createUserCode);
		} catch (BusinessException e) {
			e.printStackTrace();
			return returnException("修改门店信息报错！异常信息:"+e.getMessage());
		}

		return returnSuccess();
	}

	@RequestMapping(value="/uniqueCheckBySiteCode.do")
	@ResponseBody
	public ResultEntity uniqueCheckBySiteCode(String siteCode) {
		int count = baseSiteService.uniqueCheckBySiteCode(siteCode);
		return returnSuccess(count);
	}

	@RequestMapping(value="/uniqueCheckBySiteName.do")
	@ResponseBody
	public ResultEntity uniqueCheckBySiteName(String siteCode, String siteName,
			String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteCode", siteCode);
		map.put("siteName", siteName);
		map.put("state", state);

		int count = baseSiteService.uniqueCheckBySiteName(map);
		return returnSuccess(count);
	}

	@RequestMapping(value="/uniqueCheckBySiteNameShort.do")
	@ResponseBody
	public ResultEntity uniqueCheckBySiteNameShort(String siteCode,
			String siteNameShort, String state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteCode", siteCode);
		map.put("siteNameShort", siteNameShort);
		map.put("state", state);

		int count = baseSiteService.uniqueCheckBySiteNameShort(map);
		return returnSuccess(count);
	}


	@RequestMapping("/queryBaseSite.do")
	@ResponseBody
	public Pagination<BaseSiteEntity> queryBaseSite(@QueryPage QueryPageVo queryPageVo) {
		UserEntity userEntity = (UserEntity)UserContext.getCurrentUser();
		EmployeeEntity currentEmployee = userEntity.getEmpEntity();

		// 所属门店
		String ownerSite = currentEmployee.getOwnerSite();
		if (StringUtils.isBlank(ownerSite)) {
			String orgCode = currentEmployee.getOrgCode();
			if (StringUtils.isBlank(orgCode)) {
				// 设置一个不存在的值
				ownerSite = "-1";
			}else {
				ownerSite = orgCode;
			}
		}


		Map<String, Object> map = queryPageVo.getParaMap();
		map.put("ownerSite", ownerSite);

		Pagination<BaseSiteEntity> pageInfo = baseSiteService.queryBaseSite(queryPageVo.getParaMap(), queryPageVo.getPage(), queryPageVo.getSorts());
		return pageInfo;
	}

	/**
	 * 批量修改门店启用状态
	 *
	 * @param codes
	 * @param blFlag 启用：1，停用：0
	 * @return
	 * @author guoh.mao
	 */
	@RequestMapping(value="/batchUpdateBlFlag.do")
	@ResponseBody
	public ResultEntity batchUpdateBlFlag(String codes, Integer blFlag){
		List<String> codeList = new ArrayList<>();
		if (StringUtils.isNotBlank(codes)) {
			String[] codesArr = codes.split(",");
			codeList = Arrays.asList(codesArr);

			String currentUserCode = UserContext.getCurrentUser().getUserName();

			baseSiteService.batchUpdateBlFlag(blFlag, codeList, currentUserCode);
		}

		return returnSuccess();
	}


	/**
	 * 通过门店名称模糊查询
	 *
	 * @param siteName 门店名称
	 * @return
	 * @author caijue
	 */
	@RequestMapping("/getSiteListByName.do")
	@ResponseBody
	public List<String> getSiteListByName(String siteName, String rootNode){
		List<BaseSiteEntity> baseSiteEntities = baseSiteService.getSiteListByName(siteName);
		List<String> paths = getNodePaths(baseSiteEntities, rootNode);
        return paths;
	}

	   /**
     * 获取List中每个门店节点的展开路径
     * @param baseSiteEntities
     * @return
     * @author caijue
     */
    private List<String> getNodePaths(List<BaseSiteEntity> baseSiteEntities, String rootNode) {
        List<String> paths = new ArrayList<String>();

        if (!CollectionUtils.isEmpty(baseSiteEntities)) {
            for (BaseSiteEntity baseSiteEntity : baseSiteEntities) {
                List<String> codeList = new ArrayList<String>();
                getNodePath(baseSiteEntity, codeList, rootNode);
                Collections.reverse(codeList);

                StringBuilder sb = new StringBuilder();
//              sb.append("/"+BaseOrgConstant.YC_GROUP_CODE);
                for (String siteCode : codeList) {
                    sb.append("/");
                    sb.append(siteCode);
                }
                paths.add(sb.toString());
            }
        }
        return paths;
    }

    /**
     *
     * 按照门店的父子关系，递归获取当前门店及其直接间接父节点
     *
     * @param site
     * @param codeList
     * @author caijue
     */
    private void getNodePath(BaseSiteEntity site, List<String> codeList, String rootNode){
        codeList.add(site.getSiteCode());
        if(rootNode.equals(site.getSiteCode())){
            return;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("siteCode", site.getUpSite());

        List<BaseSiteEntity> list = baseSiteService.get(param);

        if (!CollectionUtils.isEmpty(list)) {
            // 递归调用
            getNodePath(list.get(0), codeList, rootNode);
        }
    }

	//-------------------------------------



    /**
    * Description:  动态获取根节点
    * @return
    * Created by caijue
    */
    @RequestMapping("/getSiteRootNode.do")
    @ResponseBody
    public ResultEntity getSiteRootNode(boolean flag) {
        String rootNode = BaseOrgConstant.YC_EXPRESS_CODE;
        if(!flag){
            BaseEmployeeEntity employee = baseEmployeeService.getByCode(UserContext.getCurrentUser().getUserName());
            if(employee == null) return returnException("员工信息获取异常");
            if(StringUtils.isBlank(employee.getOwnerSite())) return returnException("员工所属网点获取异常");
            rootNode = employee.getOwnerSite();
        }

        return returnSuccess(baseSiteService.getByCode(rootNode));
    }



    /**
     * 关联员工
     *
     * @param siteCode
     * @param empCodes
     * @return
     * @author guoh.mao
     */
    @RequestMapping("/assosiateToEmployee.do")
	@ResponseBody
	public ResultEntity assosiateToEmployee(String siteCode, String empCodes){
		try{
			baseSiteService.assosiateToEmployee(siteCode, empCodes);
			return returnSuccess();
		}catch(BusinessException e){
			e.printStackTrace();
			return returnException("保存失败！异常信息:"+e.getMessage());
		}
	}

}
