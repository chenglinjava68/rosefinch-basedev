package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Pagination;

import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseOrgVo;

/**
 *
 * @author guoh.mao
 *
 */
public interface IBaseOrgService extends IBaseService<BaseOrgEntity, Long>{
	/**
	 * 组织机构名称查询
	 * @param orgName
	 * @return
	 * @author guoh.mao
	 */
	List<BaseOrgEntity> queryByOrgName(String orgName);

	/**
	 * 分页查询组织机构
	 *
	 * @param queryPageVo
	 * @return
	 * @author guoh.mao
	 */
	Pagination<BaseOrgEntity> getPage(QueryPageVo queryPageVo);

	/**
	 * 保存 组织机构
	 *
	 * @param baseOrgVo
	 * @return
	 * @author guoh.mao
	 */
	BaseOrgEntity insertBaseOrgEntity(BaseOrgVo baseOrgVo, String currentUserName);

	/**
	 * 修改 组织机构
	 *
	 * @param baseOrgVo
	 * @return
	 * @author guoh.mao
	 */
	BaseOrgEntity updateBaseOrgEntity(BaseOrgVo baseOrgVo, String currentUserName);

	/**
	 * 修改组织机构“启用”状态
	 *
	 * @param baseOrgEntity
	 * @author guoh.mao
	 */
	void updateBlFlag(BaseOrgEntity baseOrgEntity);

	/**
	 * orgCode唯一校验
	 *
	 * @param orgCode
	 * @return
	 * @author guoh.mao
	 */
	int uniqueCheckByOrgCode(String orgCode);

	/**
	 * 组织全名唯一校验
	 *
	 * @param map
	 * @return
	 * @author guoh.mao
	 */
	int uniqueCheckByOrgName(Map<String, Object> map);

	/**
	 * 组织简称唯一校验
	 *
	 * @param map
	 * @return
	 * @author guoh.mao
	 */
	int uniqueCheckByOrgNameShort(Map<String, Object> map);

	/**
	 * 设置临时属性（非数据库字段）值
	 *
	 * @param baseOrgEntity
	 * @return
	 * @author guoh.mao
	 */
	BaseOrgEntity constructFullBaseOrgEntity(BaseOrgEntity baseOrgEntity);

	/**
	 * 批量修改启用状态
	 *
	 * @param blFlag 1：启用，0：停用
	 * @param codes 组织机构编码（逗号分隔）
	 * @return
	 * @author guoh.mao
	 */
	int batchUpdateBlFlag(Integer blFlag, List<String> codes);
	
	
	/**
	 * 用于AD域同步数据
	 * 将父组织机构下的一级子组织结构 和 员工批量插入数据库
	 *
	 * @param orgList
	 * @param empList
	 * @author guoh.mao
	 */
	void batchInsertOrUpdateOneLevelOuAndUser(List<BaseOrgEntity> orgList, List<BaseEmployeeEntity> empList);
	
	
	/**
	 * 批量插入或更新组织
	 *
	 * @param orgList
	 * @author guoh.mao
	 */
	void batchInsertOrUpdateOrg(List<BaseOrgEntity> orgList, BaseOrgEntity parent);
	
	/**
	 * 插入或更新“远成集团”
	 *
	 * @param ycg
	 * @author guoh.mao
	 */
	void insertOrUpdateRootOu(BaseOrgEntity ycg);
	
	
	/**
	 * 获得子节点
	 *
	 * @param upOrgCode
	 * @return
	 * @author guoh.mao
	 */
	List<BaseOrgEntity> getChildren(String upOrgCode);
	
}
