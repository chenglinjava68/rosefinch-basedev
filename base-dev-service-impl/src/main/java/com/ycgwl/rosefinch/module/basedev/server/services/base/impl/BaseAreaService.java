

package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseAreaDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseAreaService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseAreaConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAreaEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseAreaVo;
/**
 * @Title:片区维护服务类
 * @Description:
 * @Company: 远成快运
 * @author HXL
 * @date 2016年10月26日 上午9:40:26
 */
@Service("baseAreaService")
public class BaseAreaService implements IBaseAreaService {

	@Autowired
	private IBaseAreaDao baseAreaDao;
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int insert(BaseAreaEntity t) {

		return baseAreaDao.insert(t);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int update(BaseAreaEntity t) {

		return baseAreaDao.update(t);
	}

	@Override
	public BaseAreaEntity getById(Long id) {

		return baseAreaDao.getById(id);
	}

	@Override
	public List<BaseAreaEntity> get(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return baseAreaDao.get(params);
	}

	@Override
	public List<BaseAreaEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		return baseAreaDao.getPage(params, pageNum, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public int getPageTotalCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return baseAreaDao.getPageTotalCount(params);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		return baseAreaDao.deleteById(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int updateStatusById(Long id, int status) {
		// TODO Auto-generated method stub
		return baseAreaDao.updateStatusById(id, status);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Pagination<BaseAreaEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {
		String areaName = (String) params.get("areaName");
		String areaCode = (String)params.get("areaCode");
		if(StringUtils.isNotEmpty(areaCode)){
			params.put("areaCode", "%"+areaCode+"%");
		}
		if(StringUtils.isNotEmpty(areaName)){
			params.put("areaName", "%"+areaName+"%");
		}
		params.put("delFlag", 0);
		sorts = new Sort[1];
		Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
		sorts[0] = sort;
		return baseAreaDao.getPagination(params, page, sorts);
	}

	/**
	 * @Title:插入片区数据
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月26日 下午4:10:09
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int insertArea(BaseAreaVo baseAreaVo , String currentUser) {
		if(StringUtils.isEmpty(currentUser.trim())){
			throw new BusinessException("当前登录人为空");
		}
		if(StringUtils.isEmpty(baseAreaVo.getAreaCode().trim()) ){
			throw new BusinessException("传入的片区编号为空");
		}
		if(StringUtils.isEmpty(baseAreaVo.getAreaName().trim())){
			throw new BusinessException("传入的片区名称为空");
		}
		BaseAreaEntity baseAreaEntity = new BaseAreaEntity();
		VoToEntity(baseAreaVo,baseAreaEntity,currentUser,BaseAreaConstant.METHODTYPE_ADD);
		return baseAreaDao.insert(baseAreaEntity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int updateArea(BaseAreaVo baseAreaVo, String currentUser) {
		if(StringUtils.isEmpty(currentUser.trim())){
			throw new BusinessException("当前登录人为空");
		}
		BaseAreaEntity baseAreaEntity = new BaseAreaEntity();
		VoToEntity(baseAreaVo,baseAreaEntity,currentUser,BaseAreaConstant.METHODTYPE_UPDATE);
		return baseAreaDao.update(baseAreaEntity);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int deleteAreaById(Long areaId) {
		return  baseAreaDao.deleteAreaById(areaId);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int stopAndstartAreaById(Long[] areaId,String typeValue, String currentUser) {
		Map<String ,Object> params = new HashMap<String ,Object>();
		params.put("areaId", areaId);
		if( "stopArea".equals(typeValue)){
			params.put("blFlag", 0L);
		}else{
			params.put("blFlag", 1L);
		}
		params.put("modifyUserCode", currentUser);
		params.put("modifyTime", new Date());
		return  baseAreaDao.stopAndstartAreaById(params);
	}
	/**
	 * @Title:将Vo中的数据设置到Entity中
	 * @Description:
	 * @Company: 远成快运
	 * @author HXL
	 * @date 2016年10月26日 下午4:39:39
	 */
	private void VoToEntity(BaseAreaVo baseAreaVo , BaseAreaEntity baseAreaEntity, String currentUser,String methodType){
		baseAreaEntity.setAreaCode(baseAreaVo.getAreaCode());
		baseAreaEntity.setAreaName(baseAreaVo.getAreaName());
		//1为停用 ，0为启用
		baseAreaEntity.setBlFlag(baseAreaVo.getBlFlag());
		baseAreaEntity.setDelFlag(0);
		baseAreaEntity.setRemark(baseAreaVo.getRemark());
		//增加方法类型判断
		if(BaseAreaConstant.METHODTYPE_ADD.equals(methodType)){
			Long id =BasicEntityIdentityGenerator.getInstance().generateId();
			Date createTime = new Date();
			baseAreaEntity.setAreaId(id);
			baseAreaEntity.setCreateUserCode(currentUser);
			baseAreaEntity.setCreateTime(createTime);
			baseAreaEntity.setModifyUserCode(currentUser);
			baseAreaEntity.setModifyTime(createTime);
			//1为禁用 ，0为启用
			//baseAreaEntity.setDelFlag(0);
		}
		//修改方法类型判断
		if(BaseAreaConstant.METHODTYPE_UPDATE.equals(methodType)){
			Date modifyTime = new Date();
			baseAreaEntity.setAreaId(baseAreaVo.getAreaId());
			baseAreaEntity.setModifyUserCode(currentUser);
			baseAreaEntity.setModifyTime(modifyTime);
			//baseAreaEntity.setCreateUserCode(baseAreaVo.getCreateUserCode());
			//baseAreaEntity.setCreateTime(baseAreaVo.getCreateTime());

		}
	}

	/**
	 *校验编号是否重复
	 */
	@Override
	public List<BaseAreaVo> getAreaCodeByInsert(String areaCode) {

		return baseAreaDao.getAreaCodeByInsert(areaCode);
	}

	/**
	 *校验名称是否重复
	 */
	@Override
	public List<BaseAreaVo> getAreaNameByInsert(String areaName) {

		return  baseAreaDao.getAreaNameByInsert(areaName);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int batchDeleteById(Long[] areaId) {
			int length = areaId.length;
			List<Long> list = null;
			int result = 0;
		if(length<=0){
			return 0;
		}else{
			list = new ArrayList<Long>();
			for(int i = 0; i<length;i++){
				//result = baseAreaDao.deleteAreaById(areaId[i]);
				list.add(areaId[i]);
			}
			result = baseAreaDao.batchDeleteById(list);
		}
		return result;
	}


}
