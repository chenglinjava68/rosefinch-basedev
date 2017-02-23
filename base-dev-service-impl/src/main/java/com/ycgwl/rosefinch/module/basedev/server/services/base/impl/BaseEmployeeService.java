package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseEmployeeDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseEmployeeTypeDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseEmployeeService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseAreaConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseEmployeeConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseEmployeeTypeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseEmployeeVo;

@Service("baseEmployeeService")
public class BaseEmployeeService implements IBaseEmployeeService {

    @Autowired
    private IBaseEmployeeDao baseEmployeeDao;
    @Autowired
    private IBaseEmployeeTypeDao baseEmployeeTypeDao;

    /*
     * 插入员工信息(non-Javadoc)
     *
     * @see com.ycgwl.framework.springmvc.service.IBaseService#insert(java.lang.
     * Object)
     */
    @Transactional
    @Override
    public int insert(BaseEmployeeEntity baseEmployeeEntity) {
        long id = BasicEntityIdentityGenerator.getInstance().generateId();// 用户id
        baseEmployeeEntity.setId(id);
        Date date = new Date();
        baseEmployeeEntity.setCreateTime(date);// 创建时间
        baseEmployeeEntity.setDelFlag(0);// 删除标示
        return baseEmployeeDao.insert(baseEmployeeEntity);
    }

    /*
     * 修改员工信息 (non-Javadoc)
     *
     * @see com.ycgwl.framework.springmvc.service.IBaseService#update(java.lang.
     * Object)
     */
    @Transactional
    @Override
    public int update(BaseEmployeeEntity baseEmployeeEntity) {
        // 判定数据是否为空
        if (baseEmployeeEntity == null) {
            throw new BusinessException("对应的应用系统数据为空");
        }
        // 判定对应的ID是否为空
        if (baseEmployeeEntity.getId() == null) {
            throw new BusinessException("对应的应用系统数据ID为空");
        }

        Date nowDate = new Date();
        baseEmployeeEntity.setModifyTime(nowDate);

        // 进行数据的更新
        int num = baseEmployeeDao.update(baseEmployeeEntity);
        refreshCache(baseEmployeeEntity.getEmployeeName());
        if (num < 1) {
            throw new BusinessException("更新应用系统数据失败");
        }
        return num;
    }

    /**
     * 根据id获取员工信息
     */
    @Transactional
    @Override
    public BaseEmployeeEntity getById(Long id) {
        if (id == null) {
            throw new BusinessException("传入的参数为空");
        }
        return baseEmployeeDao.getById(id);
    }

    /**
     * 根据Code获取员工信息
     */
    @Transactional
    @Override
    public BaseEmployeeEntity getByCode(String employeeCode) {
        if (StringUtils.isBlank(employeeCode)) {
            throw new BusinessException("传入的参数为空");
        }
        return baseEmployeeDao.getByCode(employeeCode);
    }

    @Transactional
    @Override
    public List<BaseEmployeeEntity> get(Map<String, Object> params) {
        return baseEmployeeDao.get(params);
    }

    @Transactional
    @Override
    public List<BaseEmployeeEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
        return baseEmployeeDao.getPage(params, pageNum, pageSize);
    }

    @Transactional
    @Override
    public int getPageTotalCount(Map<String, Object> params) {
        return 0;
    }

    /*
     * 删除员工信息(non-Javadoc)
     *
     * @see
     * com.ycgwl.framework.springmvc.service.IBaseService#deleteById(java.lang.
     * Object)
     */
    @Transactional
    @Override
    public int deleteById(Long id) {
        return baseEmployeeDao.deleteById(id);
    }

    @Transactional
    @Override
    public int updateStatusById(Long id, int status) {
        return 0;
    }

    @Transactional
    @Override
    public Pagination<BaseEmployeeEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {
        return baseEmployeeDao.getPagination(params, page, sorts);
    }

    /**
     * 清除系统配置缓存
     *
     * @param key
     */
    private void refreshCache(String key) {
        CacheManager.getInstance().getCache(BaseCacheConstant.BASE_CONFIG_CATCHE_UUID).invalid(key);
    }

    /*
     * 判断员工名字是否唯一(non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int uniqueCheckByEmployeeName(Map<String, Object> map) {
        return baseEmployeeDao.uniqueCheckByEmployeeName(map);
    }

    /*
     * 判断员工编号是否唯一(non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int uniqueCheckByEployeeCode(String configCode) {
        return baseEmployeeDao.uniqueCheckByEployeeCode(configCode);
    }

    /*
     * 分页显示员工信息(non-Javadoc)
     *
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public Pagination<BaseEmployeeVo> queryEmployeeBySomeIf(Map<String, Object> paraMap, Page page, Sort[] sorts) {
        Pagination<BaseEmployeeVo> empPa = baseEmployeeDao.queryEmployeeBySomeIf(paraMap, page, sorts);
        return empPa;
    }

    /*
     * 停用修改 (non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int stopOneEmployee(Map<String, Object> map) {
        return baseEmployeeDao.stopOneEmployee(map);
    }

    /*
     * 启用修改 (non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int startOneEmployee(Map<String, Object> map) {
        return baseEmployeeDao.startOneEmployee(map);
    }

    /*
     * 批量删除 (non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int deleteSomeEmployee(long[] ids) {
        int count = 0;
        for(long id : ids){
            count  = count + deleteEmployeeById(id);
        }
        return count;
    }

    /*
     * 删除员工信息(non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int deleteEmployeeById(long id) {
        return baseEmployeeDao.deleteEmployeeById(id);
    }

    /*
     * 批量启用(non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int startSomeEmployee(long[] ids,String currentUserCode) {
        int count = 0;
        for(long id : ids){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("currentUserCode", currentUserCode);
            count  = count + startOneEmployee(map);
        }
        return count;
    }

    /*
     * 批量停用 (non-Javadoc)
     *
     */
    @Transactional
    @Override
    public int stopSomeEmployee(long[] ids,String currentUserCode) {
        int count = 0;
        for(long id : ids){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("currentUserCode", currentUserCode);
            count  = count + stopOneEmployee(map);
        }
        return count;
    }

    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
    @Override
    public BaseEmployeeEntity insertEmployee(BaseEmployeeVo baseEmployeeVo, String currentUser) throws BusinessException {
        if(StringUtils.isEmpty(currentUser.trim())){
            throw new BusinessException("当前登录人为空");
        }
        BaseEmployeeEntity baseEmployeeEntity = new BaseEmployeeEntity();
        if (null != baseEmployeeVo) {
            employeeEntityCope(baseEmployeeEntity,baseEmployeeVo,currentUser,BaseEmployeeConstant.METHODTYPE_ADD);
            baseEmployeeDao.insert(baseEmployeeEntity);
            batchInsertEmployeeTypes(baseEmployeeVo.getEmployeeTypeList(),baseEmployeeEntity.getEmployeeCode());
        }
        return baseEmployeeEntity;
    }

    /*
     * 循环得到员工类型并插入
     */
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
    private void batchInsertEmployeeTypes(List<Integer> employeeTypeList, String employeeCode){
        if(CollectionUtils.isNotEmpty(employeeTypeList)){
            for (Integer  employeeType: employeeTypeList) {
                BaseEmployeeTypeEntity employeeTypeEntity = new BaseEmployeeTypeEntity();
                employeeTypeEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
                employeeTypeEntity.setEmployeeCode(employeeCode);
                employeeTypeEntity.setEmployeeType(employeeType);
                baseEmployeeTypeDao.insert(employeeTypeEntity);
            }
        }
    }


    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
    @Override
    public BaseEmployeeEntity updateEmployee(BaseEmployeeVo baseEmployeeVo, String currentUser) throws BusinessException {
        BaseEmployeeEntity baseEmployeeEntity = baseEmployeeDao.getById(baseEmployeeVo.getId());
        employeeEntityCope(baseEmployeeEntity,baseEmployeeVo,currentUser,BaseAreaConstant.METHODTYPE_UPDATE);
        baseEmployeeDao.update(baseEmployeeEntity);
        baseEmployeeTypeDao.deleteEmployeeTypeByEmployeeCode(baseEmployeeEntity.getEmployeeCode());
        batchInsertEmployeeTypes(baseEmployeeVo.getEmployeeTypeList(),baseEmployeeEntity.getEmployeeCode());
        return baseEmployeeEntity;
    }
    /**
     * 对象复制-类型的不匹配只能set|get
     * @param baseEmployeeEntity
     * @param baseEmployeeVo
     */
    private static void employeeEntityCope(BaseEmployeeEntity baseEmployeeEntity,BaseEmployeeVo baseEmployeeVo, String currentUser,String methodType){
        baseEmployeeEntity.setAddress(baseEmployeeVo.getAddress());
        baseEmployeeEntity.setEmployeeType(0);
        baseEmployeeEntity.setBlFlag(baseEmployeeVo.getBlFlag());
        baseEmployeeEntity.setPhone(baseEmployeeVo.getPhone());
        baseEmployeeEntity.setDelFlag(0);
        baseEmployeeEntity.setEmployeeName(baseEmployeeVo.getEmployeeName());
        baseEmployeeEntity.setEmployeeCode(baseEmployeeVo.getEmployeeCode());
        baseEmployeeEntity.setOwnerSite(baseEmployeeVo.getOwnerSite());
        baseEmployeeEntity.setOrgCode(baseEmployeeVo.getOrgCode());
      //增加方法类型判断
        if(BaseEmployeeConstant.METHODTYPE_ADD.equals(methodType)){
            Long id =BasicEntityIdentityGenerator.getInstance().generateId();
            Date createTime = new Date();
            baseEmployeeEntity.setId(id);
            baseEmployeeEntity.setCreateUserCode(currentUser);
            baseEmployeeEntity.setCreateTime(createTime);
        }
        //修改方法类型判断
        if(BaseAreaConstant.METHODTYPE_UPDATE.equals(methodType)){
            baseEmployeeEntity.setId(baseEmployeeVo.getId());
        }
        baseEmployeeEntity.setModifyTime(new Date());
        baseEmployeeEntity.setModifyUserCode(currentUser);
    }

    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
    @Override
    public List<Integer> getEmployeeTypeByEmployeeCode(String employeeCode) {
        List<Integer>  employeeTypeList=baseEmployeeTypeDao.getEmployeeTypeByEmployeeCode(employeeCode);
        return employeeTypeList;
    }



    //----------------guoh.mao--------------------
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void batchInsertEmployee(List<BaseEmployeeEntity> empList) {
		baseEmployeeDao.batchInsertEmployee(empList);
	}

    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	@Override
	public void batchInsertOrUpdateEmployee(List<BaseEmployeeEntity> empList, BaseOrgEntity parent) {
		if (CollectionUtils.isNotEmpty(empList)) {
			for (BaseEmployeeEntity baseEmployeeEntity : empList) {
				// 不同步
				if (baseEmployeeEntity.getIgnore()) {
					return;
				}

				boolean isExist = baseEmployeeDao.isExist(baseEmployeeEntity.getEmployeeCode());

				if (null != parent) {
					// 组织架构编码
					baseEmployeeEntity.setOrgCode(parent.getOrgCode());

					// 非分公司下级部门员工
					if (!baseEmployeeEntity.getFlag()) {
						// 如果父组织为“分公司”，那么员工所属门店就是该分公司
						if (BaseOrgConstant.ORG_TYPE_COMPANY.toString().equals(parent.getOrgType())) {
							baseEmployeeEntity.setOwnerSite(parent.getOrgCode());
						}
					}
				}

				if (isExist) {
					baseEmployeeDao.updateByCode(baseEmployeeEntity);
				}else {
					baseEmployeeDao.insert(baseEmployeeEntity);
				}
			}
		}
	}

    
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
	public Pagination<BaseEmployeeVo> queryEmployeeByNameOrCode(
			Map<String, Object> paraMap, Page page, Sort[] sorts) {
		Pagination<BaseEmployeeVo> empPa = baseEmployeeDao.queryEmployeeByNameOrCode(paraMap, page, sorts);
        return empPa;
	}

}