package com.ycgwl.rosefinch.module.basedev.server.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycgwl.framework.springmvc.annotation.QueryPage;
import com.ycgwl.framework.springmvc.controller.AbstractController;
import com.ycgwl.framework.springmvc.entity.ResultEntity;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.common.shared.context.UserContext;
import com.ycgwl.rosefinch.module.authorization.shared.domain.UserEntity;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseEmployeeService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCommonConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseEmployeeVo;
import com.ycgwl.rosefinch.module.organization.shared.domain.EmployeeEntity;


/**
*
* Title: 员工信息Controller
* @Company: 远成快运
* @author panxiaoling
* @date 2016年11月1日  下午4:58
*
*/
@Controller
@RequestMapping("/basedev")
public class BaseEmployeeController extends AbstractController {
    @Autowired
    private IBaseEmployeeService baseEmployeeService;

    /**
     *  员工资料管理页面
     *
     */
    @RequestMapping("/baseEmployee.do")
    public String indexPage() {
        return "/basedev/baseEmployee";
    }

    /**
     * @Title:新增一条员工记录
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping(value = "/insertBaseEmployee.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity insertBaseEmployee(@RequestBody BaseEmployeeVo baseEmployeeVo) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentName = UserContext.getCurrentUser().getUserName();
            baseEmployeeService.insertEmployee(baseEmployeeVo, currentName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * @Title:删除一条员工记录
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping("/deleteEmployee.do")
    @ResponseBody
    public ResultEntity deleteEployee(Long id) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            baseEmployeeService.deleteEmployeeById(id);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     * @Title:批量删除
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping("/deleteSomeEmployee.do")
    @ResponseBody
    public ResultEntity deleteSomeEmployee(@RequestBody long[] ids) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            baseEmployeeService.deleteSomeEmployee(ids);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }


    /**
     * @Title:批量启用
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping("/startSomeEmployee.do")
    @ResponseBody
    public ResultEntity startSomeEmployee(@RequestBody long[] ids) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentName = UserContext.getCurrentUser().getUserName();
            baseEmployeeService.startSomeEmployee(ids,currentName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }


    /**
     * @Title:批量停用
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping("/stopSomeEmployee.do")
    @ResponseBody
    public ResultEntity stopSomeEmployee(@RequestBody long[] ids) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentName = UserContext.getCurrentUser().getUserName();
            baseEmployeeService.stopSomeEmployee(ids,currentName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }
    /**
     * @Title:修改一条员工记录
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping(value = "/updateBaseEmployee.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateBaseEmployee(@RequestBody BaseEmployeeVo baseEmployeeVo) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentName = UserContext.getCurrentUser().getUserName();
            baseEmployeeService.updateEmployee(baseEmployeeVo, currentName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }

        return resultEntity;
    }

    /**
     * @Title:根据条件分页查询所有员工
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping("/getPaginationBaseEmployeeList.do")
    @ResponseBody
    public Pagination<BaseEmployeeVo> getPaginationUserInfoList(@QueryPage QueryPageVo queryPageVo) {
    	UserEntity userEntity = (UserEntity)UserContext.getCurrentUser();
		EmployeeEntity currentEmployee = userEntity.getEmpEntity();
		String orgCode = currentEmployee.getOrgCode();
		
		Map<String, Object> map = queryPageVo.getParaMap();
		if (userEntity.getUserName().equals(BaseCommonConstant.ADMIN)) {
			// 系统管理员可以看见所有员工
			map.put("ownerOrg", BaseOrgConstant.YC_GROUP_CODE);
		} else {
			map.put("ownerOrg", orgCode);
		}
    	
		
    	Pagination<BaseEmployeeVo> pageInfo = baseEmployeeService.queryEmployeeBySomeIf(queryPageVo.getParaMap(),
                queryPageVo.getPage(), queryPageVo.getSorts());
        return pageInfo;
    }

    /**
     * @Title:得到一条员工记录
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping("/getBaseEmployeeById.do")
    @ResponseBody
    public ResultEntity getById(Long id) {
        BaseEmployeeEntity data = baseEmployeeService.getById(id);
        return returnSuccess(data);
    }

    /**
     * @Title:根据输入的员工编号检查是否已经存在该编号
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping(value = "/uniqueCheckByEmployeeCode.do")
    @ResponseBody
    public ResultEntity uniqueCheckByConfigCode(String employeeCode) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            int count = baseEmployeeService.uniqueCheckByEployeeCode(employeeCode);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }

        return resultEntity;
    }

    /**
     * @Title:根据输入的员工名字检查是否已经存在该名字
     * @Description:
     * @Company: 远成快运
     * @author panxiaoling
     * @date 2016年11月8日 下午3:35:44
     */
    @RequestMapping(value = "/uniqueCheckByEmployeeName.do")
    @ResponseBody
    public ResultEntity uniqueCheckByConfigName(String employeeCode, String employeeName, String state) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("employeeCode", employeeCode);
            map.put("employeeName", employeeName);
            map.put("state", state);

            int count = baseEmployeeService.uniqueCheckByEmployeeName(map);
            resultEntity.setSuccess(true);
            resultEntity.setData(count);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }

        return resultEntity;
    }


    /**
     * 查询员工信息关联类型
     *
     * @author panxiaoling
     */
    @RequestMapping(value="/getEmployeeTypeByEmployeeCode.do")
    @ResponseBody
    public ResultEntity getEmployeeTypeByEmployeeCode(String employeeCode){
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(baseEmployeeService.getEmployeeTypeByEmployeeCode(employeeCode));
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }
    
    
    @RequestMapping(value="/getEmployeesByOwnerSite.do")
    @ResponseBody
    public ResultEntity getEmployeesByOwnerSite(String siteCode){
        ResultEntity resultEntity = new ResultEntity();
        try {
        	Map<String, Object> map = new HashMap<>();
        	map.put("ownerSite", siteCode);
        	
        	List<BaseEmployeeEntity> list = baseEmployeeService.get(map);
        	resultEntity.setData(list);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
        }
        return resultEntity;
    }
    
    
    
    
    @RequestMapping("/queryEmployeeByNameOrCode.do")
    @ResponseBody
    public Pagination<BaseEmployeeVo> queryEmployeeByNameOrCode(@QueryPage QueryPageVo queryPageVo) {
    	UserEntity userEntity = (UserEntity)UserContext.getCurrentUser();
		EmployeeEntity currentEmployee = userEntity.getEmpEntity();
		String orgCode = currentEmployee.getOrgCode();
		
		Map<String, Object> map = queryPageVo.getParaMap();
		if (userEntity.getUserName().equals(BaseCommonConstant.ADMIN)) {
			// 系统管理员可以看见所有员工
			map.put("ownerOrg", BaseOrgConstant.YC_GROUP_CODE);
		} else {
			map.put("ownerOrg", orgCode);
		}
		
    	Pagination<BaseEmployeeVo> pageInfo = baseEmployeeService.queryEmployeeByNameOrCode(queryPageVo.getParaMap(),
                queryPageVo.getPage(), queryPageVo.getSorts());
        return pageInfo;
    }
    

}
