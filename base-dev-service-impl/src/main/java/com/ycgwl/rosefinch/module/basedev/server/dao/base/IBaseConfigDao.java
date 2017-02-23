package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Pagination;

import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseConfigEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseConfigVo;


public interface IBaseConfigDao extends IBaseDao<BaseConfigEntity,Long> {
		//新增
		BaseConfigEntity insertConfig(BaseConfigVo baseConfigVo, String currentUserCode);
		//删除
		void logicalDeleteById(Long configId);
		//修改
		BaseConfigEntity editConfig(BaseConfigVo baseConfigVo , String currentUserCode);
		//查询
		Pagination<BaseConfigEntity> getConfig(QueryPageVo queryPageVo);

		//检测配置名称的唯一性
		int uniqueCheckByConfigName(Map<String, Object> map);
		//检测配置编码的唯一性
		int uniqueCheckByConfigCode(String configCode);

		//批量删除
		void deleteBatchBaseConfigByIdList(List<Long> list);

}
