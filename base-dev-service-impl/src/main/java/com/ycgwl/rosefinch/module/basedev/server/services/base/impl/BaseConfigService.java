package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.vo.QueryPageVo;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseConfigDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseConfigService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseConfigEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseConfigVo;
@Service
public class BaseConfigService implements IBaseConfigService{
	@Autowired
	private IBaseConfigDao baseConfigDao;

	@Transactional
	@Override
	public int insert(BaseConfigEntity t) {

		return baseConfigDao.insert(t);
	}

	@Transactional
	@Override
	public int update(BaseConfigEntity t) {

		return baseConfigDao.update(t);
	}

	@Override
	public BaseConfigEntity getById(Long id) {

		return baseConfigDao.getById(id);
	}

	@Override
	public List<BaseConfigEntity> get(Map<String, Object> params) {

		return baseConfigDao.get(params);
	}

	@Override
	public List<BaseConfigEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {

		return baseConfigDao.getPage(params, pageNum, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public int getPageTotalCount(Map<String, Object> params) {

		return 0;
	}

	@Override
	public int deleteById(Long id) {

		return 0;
	}

	@Override
	public int updateStatusById(Long id, int status) {

		return 0;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public Pagination<BaseConfigEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {

		return null;
	}
	//增
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public BaseConfigEntity insertConfig(BaseConfigVo baseConfigVo, String currentUserCode) {
		BaseConfigEntity baseConfigEntity=new BaseConfigEntity();

		if (null != baseConfigVo) {
			BeanUtils.copyProperties(baseConfigVo, baseConfigEntity);
			baseConfigEntity.setConfigId(BasicEntityIdentityGenerator.getInstance().generateId());
			baseConfigEntity.setDelFlag(0);
			Date date = new Date();
			baseConfigEntity.setCreateTime(date);
			baseConfigEntity.setCreateUserCode(currentUserCode);
			baseConfigEntity.setModifyTime(date);
			baseConfigEntity.setModifyUserCode(currentUserCode);
			baseConfigDao.insert(baseConfigEntity);
			refreshCache(baseConfigEntity.getConfigCode());
			}

		return baseConfigEntity;
	}
	//删

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public void deleteConfig(Long configId, String currentUserCode) {
		// 判定ID是否为空
				if (configId == null) {
					throw new BusinessException("对应配置ID为空");
				}
				 baseConfigDao.logicalDeleteById(configId);
				BaseConfigEntity oldBaseConfigEntity=baseConfigDao.getById(configId);
				refreshCache(oldBaseConfigEntity.getConfigCode());
	}
	//改
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public BaseConfigEntity editConfig(BaseConfigVo baseConfigVo, String currentUserCode) {
		BaseConfigEntity oldBaseConfigEntity=baseConfigDao.getById(baseConfigVo.getConfigId());
		BeanUtils.copyProperties(baseConfigVo, oldBaseConfigEntity);
		oldBaseConfigEntity.setModifyTime(new Date());
		oldBaseConfigEntity.setModifyUserCode(currentUserCode);
		baseConfigDao.update(oldBaseConfigEntity);
		refreshCache(oldBaseConfigEntity.getConfigCode());
		return oldBaseConfigEntity;
	}
	//查
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	@Override
	public Pagination<BaseConfigEntity> getConfig(QueryPageVo queryPageVo) {
		Map<String, Object> map = queryPageVo.getParaMap();
		// delFlag=0
		map.put("delFlag", 0);
		Pagination<BaseConfigEntity> pageInfo  = baseConfigDao.getPagination(map,queryPageVo.getPage(),queryPageVo.getSorts());
		if (null == pageInfo) {
			pageInfo = new Pagination<BaseConfigEntity>();
			pageInfo.setCount(0);
		}
		return pageInfo;
	}

	/**
	 * 批量删除
	 * @param id集合列表
	 */
    @Transactional
    @Override
    public void removeBaseConfigByIdList(List<Long> list) {
        baseConfigDao.deleteBatchBaseConfigByIdList(list);
    }

	@Override
	public int uniqueCheckByConfigName(Map<String, Object> map) {

		return baseConfigDao.uniqueCheckByConfigName(map);
	}

	@Override
	public int uniqueCheckByConfigCode(String configCode) {

		return baseConfigDao.uniqueCheckByConfigCode(configCode);
	}


	/**
	 * 清除系统配置缓存
	 * @param key
	 */
	private void refreshCache(String key){
		//系统配置
        CacheManager.getInstance().getCache(BaseCacheConstant.BASE_CONFIG_CATCHE_UUID).invalid(key);
	}
}
