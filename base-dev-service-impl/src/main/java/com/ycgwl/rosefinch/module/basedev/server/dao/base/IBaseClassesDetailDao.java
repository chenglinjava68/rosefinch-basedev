package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseClassesDetailEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesDetailVo;

public interface IBaseClassesDetailDao extends IBaseDao<BaseClassesDetailEntity, Long>{
    List<BaseClassesDetailVo> queryClassesDetailByClassesId(String classesCode);//classesId查找班次详情
    List<BaseClassesDetailVo> queryCarLineDetailByLineCode(String lineCode);//lineId查找车线详情
    int deleteClassesDetailByClassesId(String classesCode);//删除
    BaseClassesDetailEntity queryByOrder(Map<String, Object> map);//根据条件查找BaseClassesDetailEntity
    String queryByLineId(String lineCode);
}
