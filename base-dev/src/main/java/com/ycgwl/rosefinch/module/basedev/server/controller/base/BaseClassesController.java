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
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseClassesService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseClassesEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesDetailVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesVo;


/**
 *
 * @Title 班次维护Controller
 * @Description TODO
 *　@Company　远成快运
 * @author panxiaoling
 * @date 2016年11月24日下午2:45:36
 */
@Controller
@RequestMapping("/basedev")
public class BaseClassesController extends AbstractController {
    @Autowired
    private IBaseClassesService baseClassesService;

   /**
    *
    * @Title 班次维护页面
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:50:25
    * @return
    */
    @RequestMapping("/classes.do")
    public String indexPage(){
        return "/basedev/classes";
    }


   /**
    *
    * @Title 插入一条班次信息
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:50:16
    * @param baseClassesVo
    * @return
    */
    @RequestMapping(value = "/insertBaseClasses.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity insertBaseClasses(@RequestBody BaseClassesVo baseClassesVo) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            String currentUser = UserContext.getCurrentUser().getUserName();
            baseClassesService.insertBaseClasses(baseClassesVo, currentUser);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

   /**
    *
    * @Title 根据ID删除一条班次信息
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:50:36
    * @param id
    * @return
    */
    @RequestMapping("/deleteBaseClasses.do")
    @ResponseBody
    public ResultEntity deleteBaseClasses(Long id) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            baseClassesService.deleteClassesById(id);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

   /**
    *
    * @Title 批量删除班次信息
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:51:14
    * @param ids
    * @return
    */
    @RequestMapping("/deleteSomeClasses.do")
    @ResponseBody
    public ResultEntity deleteSomeClasses(@RequestBody long[] ids) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            baseClassesService.deleteSomeClasses(ids);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }


    /**
     *
     * @Title 批量启用班次信息
     * @Description TODO
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月24日下午2:51:41
     * @param ids
     * @return
     */
    @RequestMapping("/startSomeClasses.do")
    @ResponseBody
    public ResultEntity startSomeClasses(@RequestBody long[] ids) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseClassesService.startSomeClasses(ids,currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }

    /**
     *
     * @Title 批量停止班次信息
     * @Description TODO
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月24日下午2:52:28
     * @param ids
     * @return
     */
    @RequestMapping("/stopSomeClasses.do")
    @ResponseBody
    public ResultEntity stopSomeClasses(@RequestBody long[] ids) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUserName = UserContext.getCurrentUser().getUserName();
            baseClassesService.stopSomeClasses(ids,currentUserName);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }
    /**
     * 修改之前
     */

  /**
   *
   * @Title 修改一条班次信息
   * @Description TODO
   *　@Company　远成快运
   * @author panxiaoling
   * @date 2016年11月24日下午2:52:40
   * @param BaseClassesVo
   * @return
   */
    @RequestMapping(value = "/updateBaseClasses.do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultEntity updateBaseClasses(@RequestBody BaseClassesVo baseClassesVo) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            String currentUser = UserContext.getCurrentUser().getUserName();
            baseClassesService.updateBaseClasses(baseClassesVo, currentUser);
            resultEntity.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }

        return resultEntity;
    }

   /**
    *
    * @Title 根据条件分页得到不同的数据
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:53:15
    * @param queryPageVo
    * @return
    */
    @RequestMapping("/queryClassesBySomeIf.do")
    @ResponseBody
    public Pagination<BaseClassesVo> queryClassesBySomeIf(@QueryPage QueryPageVo queryPageVo) {
        Pagination<BaseClassesVo> pageInfo = baseClassesService.queryClassesBySomeIf(queryPageVo.getParaMap(), queryPageVo.getPage(),queryPageVo.getSorts());
        return pageInfo;
    }

   /**
    *
    * @Title 根据id得到一个班次
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:54:50
    * @param id
    * @return
    */
    @RequestMapping("/getById.do")
    @ResponseBody
    public ResultEntity getById(Long id) {
       BaseClassesEntity data = baseClassesService.getById(id);
        return returnSuccess(data);
    }

   /**
    *
    * @Title 判断唯一班次编码
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:57:09
    * @param employeeCode
    * @return
    */
    @RequestMapping(value = "/uniqueCheckByClassesName.do")
    @ResponseBody
    public ResultEntity uniqueCheckByRunCode(String classesName, String id, String state) {
        ResultEntity resultEntity = new ResultEntity();

        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("classesName", classesName);
            map.put("id", id);
            map.put("state", state);
            int count = baseClassesService.uniqueCheckByClassesName(map);
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
     * @Title 查询路线
     * @Description TODO
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月24日下午3:00:44
     * @param queryPageVo
     * @return
     */
    @RequestMapping("/queryCarLineForClasses.do")
    @ResponseBody
    public Pagination<BaseClassesVo> queryCarLine(@QueryPage QueryPageVo queryPageVo) {
        Pagination<BaseClassesVo> pageInfo = baseClassesService.queryCarLine(queryPageVo.getParaMap(), queryPageVo.getPage(),queryPageVo.getSorts());
        return pageInfo;
    }
    /**
     *
     * @Title 查询车辆名字
     * @Description TODO
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月28日下午3:19:59
     * @param queryPageVo
     * @return
     */
    @RequestMapping("/queryVehicelNo.do")
    @ResponseBody
    public Pagination<BaseClassesVo> queryVehicelNo(@QueryPage QueryPageVo queryPageVo) {
        Pagination<BaseClassesVo> pageInfo = baseClassesService.queryVehicelNo(queryPageVo.getParaMap(), queryPageVo.getPage(),queryPageVo.getSorts());
        return pageInfo;
    }

    /**
     *
     * @Title TODO
     * @Description TODO
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月30日上午8:53:51
     * @param baseClassesVo
     * @return
     */
    @RequestMapping("/queryClassesDetailByClassesId.do")
    @ResponseBody
    public ResultEntity queryClassesDetailByClassesId(String classesCode) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setSuccess(true);
            resultEntity.setData(baseClassesService.queryClassesDetailByClassesId(classesCode));
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setSuccess(false);
            resultEntity.setMsg(e.getMessage());
        }
        return resultEntity;
    }
    /**
    *
    * @Title 批量修改
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年11月24日下午2:51:41
    * @param ids
    * @return
    */
   @RequestMapping("/updateSomeClassesDetail.do")
   @ResponseBody
   public ResultEntity updateSomeClassesDetail(@RequestBody List<BaseClassesDetailVo> baseClassesDetailVos) {
       ResultEntity resultEntity = new ResultEntity();

       try {
           baseClassesService.updateSomeClassesDetail(baseClassesDetailVos);
           resultEntity.setSuccess(true);
       } catch (Exception e) {
           e.printStackTrace();
           resultEntity.setSuccess(false);
           resultEntity.setMsg(e.getMessage());
       }
       return resultEntity;
   }
   /**
    *
    * @Title TODO
    * @Description 查找车线
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年12月6日上午11:16:19
    * @param classesId
    * @return
    */
   @RequestMapping("/queryCarLineDetailByLineId.do")
   @ResponseBody
   public ResultEntity queryCarLineDetailByLineId(String lineCode) {
       ResultEntity resultEntity = new ResultEntity();
       try {
           resultEntity.setSuccess(true);
           resultEntity.setData(baseClassesService.queryCarLineDetailByLineId(lineCode));
       } catch (Exception e) {
           e.printStackTrace();
           resultEntity.setSuccess(false);
           resultEntity.setMsg(e.getMessage());
       }
       return resultEntity;
   }

   /**
    *
    * @Title TODO
    * @Description 查找唯一编号
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2017年1月17日下午1:09:10
    * @param classesCode
    * @return
    */
   @RequestMapping(value = "/uniqueCheckByClassesCode.do")
   @ResponseBody
   public ResultEntity uniqueCheckByClassesCode(String classesCode) {
       ResultEntity resultEntity = new ResultEntity();

       try {
           int count = baseClassesService.uniqueCheckByClassesCode(classesCode);
           resultEntity.setSuccess(true);
           resultEntity.setData(count);
       } catch (Exception e) {
           e.printStackTrace();
           resultEntity.setSuccess(false);
       }

       return resultEntity;
   }
}
