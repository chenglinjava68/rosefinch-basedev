package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.ArrayList;
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

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseMaterialDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseMaterialService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseMaterialEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseMaterialVo;
import com.ycgwl.rosefinch.module.dict.shared.domain.DictionaryEntity;
@Service("baseMaterialService")
public class BaseMaterialService implements IBaseMaterialService {

	 @Autowired
	 private IBaseMaterialDao baseMaterialDao;
	 @Autowired
	 private IBaseRedisCacheService baseRedisCacheService;
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int insert(BaseMaterialEntity t) throws BusinessException {
		return baseMaterialDao.insert(t);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int update(BaseMaterialEntity t) throws BusinessException {

		return baseMaterialDao.update(t);
	}

	@Override
	public BaseMaterialEntity getById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return baseMaterialDao.getById(id);
	}

	@Override
	public List<BaseMaterialEntity> get(Map<String, Object> params) throws BusinessException {
		// TODO Auto-generated method stub
		return baseMaterialDao.get(params);
	}

	@Override
	public List<BaseMaterialEntity> getPage(Map<String, Object> params, int pageNum, int pageSize)
			throws BusinessException {
		// TODO Auto-generated method stub
		return baseMaterialDao.getPage(params, pageNum, pageSize);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public int getPageTotalCount(Map<String, Object> params) throws BusinessException {
		// TODO Auto-generated method stub
		return baseMaterialDao.getPageTotalCount(params);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int deleteById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return baseMaterialDao.deleteById(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int updateStatusById(Long id, int status) throws BusinessException {
		// TODO Auto-generated method stub
		return baseMaterialDao.updateStatusById(id, status);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Pagination<BaseMaterialEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts)
			throws BusinessException {
		 if(params == null){
			params = new HashMap<String , Object>();
		 }
		 params.put("delFlag", 0);
		 String goodsName = (String) params.get("goodsName");
		 String goodsCode = (String)params.get("goodsCode");
		 if(StringUtils.isNotEmpty(goodsName)){
			 params.put("goodsName", "%"+goodsName+"%");
		 }
		 if(StringUtils.isNotEmpty(goodsCode) && StringUtils.isNumeric(goodsCode)){
			 params.put("goodsCode", "%"+goodsCode+"%");
		 }
			sorts = new Sort[1];
			Sort sort = new Sort("MODIFY_TIME", Sort.DESC);
			sorts[0] = sort;
		 Pagination<BaseMaterialEntity> baseMaterialList =  baseMaterialDao.getPagination(params, page, sorts);
			if(baseMaterialList == null){
				baseMaterialList = new Pagination<BaseMaterialEntity>();
				baseMaterialList.setCount(1);
				baseMaterialList.setPageNum(1);
				baseMaterialList.setPageSize(10);
				baseMaterialList.setSortStr(null);
				baseMaterialList.setTotalPages(1);
			}else{
				//List<BaseMaterialEntity> materialList =;
				completionEntity( baseMaterialList.getList());
			}
		return baseMaterialList;
	}
	private void completionEntity(List<BaseMaterialEntity> materialList){
		List<DictionaryEntity>MaterialTypeList = baseRedisCacheService.getDictDataCache("MATERIAL_TYPE");
			if(CollectionUtils.isNotEmpty(materialList)){
				for(BaseMaterialEntity bme : materialList){
					if(bme.getCategory()!=null && CollectionUtils.isNotEmpty(MaterialTypeList)){
						String categoryName = getValueCodeByValueName(MaterialTypeList,bme.getCategory());
						bme.setCategoryName(categoryName);
					}
					bme.getCategory();
				}
			}
	}
	/**
	 * 根据数据字典查询该数据字典编号对应的名称
	 * @param dictList
	 * @param valueCode
	 * @return
	 */
	public  String getValueCodeByValueName(List<DictionaryEntity> dictList, Object valueCode){
		//校验传入的数据字典名称是否为空
	  String valueCodeStr = String.valueOf(valueCode);
		if(StringUtils.isEmpty(valueCodeStr)){
			return null;
		}
		//判定需进行寻找的数据字典是否为空
		if(CollectionUtils.isEmpty(dictList)){
			return null;
		}
		//进行FOR循环校验是否存在
		String valueName = null;
		for(DictionaryEntity dictionary : dictList){
			if(dictionary != null)
			if( StringUtils.isNotEmpty(dictionary.getValueCode())
					&& dictionary.getValueCode().contains(valueCodeStr)){
				valueName = dictionary.getValueName();
				break;
			}
		}
		return StringUtils.isEmpty(valueName) ? null : valueName;
	}
	/**
	 * 根据名称查询是否存在该物料
	 */
	@Override
	public List<BaseMaterialVo> getMaterialNameByInsert(String goodsName){
		if(StringUtils.isEmpty(goodsName)){
			throw new BusinessException("输入的物料名称为空");
		}
	return baseMaterialDao.getMaterialNameByInsert(goodsName);
	}
	/**
	 * 根据编号查询是否存在该物料
	 */
	@Override
	public List<BaseMaterialVo> getMaterialCodeByInsert(String goodsCode){
		if(goodsCode == null){
			throw new BusinessException("输入的物料编号为空");
		}
	return baseMaterialDao.getMaterialCodeByInsert(goodsCode);
	}

	/**
	 *修改之前：根据id查询物料信息
	 */
	@Override
	public BaseMaterialVo preUpdateGoods(Long id) {
		if(id == null){
			throw new BusinessException("要修改的数据传入的ID为空");
		}
		return baseMaterialDao.preUpdateGoods(id);
	}

	/**
	 *根据id 删除数据 （假删除）
	 */
	@Override
	public int deletegoods(Long id) {
		// TODO Auto-generated method stub
		return baseMaterialDao.deletegoods(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
	public int batchDeleteByIds(Long[] ids) {
			int length = ids.length;
			List<Long> list = null;
			int result = 0;
		if(length<=0){
			return 0;
		}else{
			list = new ArrayList<Long>();
			for(int i = 0; i<length;i++){
				//result = baseAreaDao.deleteAreaById(areaId[i]);
				list.add(ids[i]);
			}
			result = baseMaterialDao.batchDeleteByIds(list);
		}
		return result;
	   }
}
