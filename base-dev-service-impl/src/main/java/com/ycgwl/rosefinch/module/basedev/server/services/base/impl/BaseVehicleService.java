package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.cache.base.ICache;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseVehicleDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseOrgService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseVehicleService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseVehicleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseVehicleVo;

/**
*
* @Title:T_BASE_VEHICLE表Service的实现
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月21日  下午3:31
*
*/
@Service
public class BaseVehicleService implements IBaseVehicleService {

    @Autowired
    private IBaseVehicleDao baseVehicleDao;

    @Autowired
    private IBaseOrgService baseOrgService;

    @Transactional
    @Override
    public int insert(BaseVehicleEntity paramT) throws BusinessException {
        return baseVehicleDao.insert(paramT);
    }

    @Transactional
    @Override
    public int update(BaseVehicleEntity paramT) throws BusinessException {
        return baseVehicleDao.update(paramT);
    }

    @Override
    public BaseVehicleEntity getById(Long paramPK) throws BusinessException {
        return baseVehicleDao.getById(paramPK);
    }

    @Override
    public List<BaseVehicleEntity> get(Map<String, Object> paramMap)
            throws BusinessException {
        return baseVehicleDao.get(paramMap);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
    @Override
    public List<BaseVehicleEntity> getPage(Map<String, Object> paramMap,
            int paramInt1, int paramInt2) throws BusinessException {
        return baseVehicleDao.getPage(paramMap, paramInt1, paramInt2);
    }

    @Override
    public int getPageTotalCount(Map<String, Object> paramMap)
            throws BusinessException {
        return baseVehicleDao.getPageTotalCount(paramMap);
    }

    @Override
    public int deleteById(Long paramPK) throws BusinessException {
        return baseVehicleDao.deleteById(paramPK);
    }

    @Transactional
    @Override
    public int updateStatusById(Long paramPK, int paramInt)
            throws BusinessException {
        return baseVehicleDao.updateStatusById(paramPK, paramInt);
    }

    private ICache<String, String> getDictDataValueNameCache() {
        return CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
    }

    private ICache<String, BaseOrgEntity> getBaseOrgEntityCache() {
        return CacheManager.getInstance().getCache(BaseCacheConstant.ORG_INFO_CATCHE_UUID);
    }

    /**
     * 分页查询车辆信息
     */
    @Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
    @Override
    public Pagination<BaseVehicleEntity> getPagination(
            Map<String, Object> paramMap, Page paramPage,
            Sort... paramArrayOfSort) throws BusinessException {
        Pagination<BaseVehicleEntity> pageInfo = baseVehicleDao.getPagination(paramMap, paramPage, paramArrayOfSort);
        if(pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())){
            ICache<String, String> cache = getDictDataValueNameCache();
            for (BaseVehicleEntity entity : pageInfo.getList()) {
                //所有类型-BASE_VEHICLE_ALL_TYPE
                if(entity.getOwnerType() != null){
                    entity.setOwerTypeName(cache.get(BaseOrgConstant.TYPE_ALIAS_BASE_VEHICLE_ALL_TYPE + BaseOrgConstant.SPLIT_SEPARATOR + entity.getOwnerType()));
                }
                //车型-BASE_VEHICLE_MODEL_TYPE
                if(entity.getVehicelModel() != null){
                   entity.setVehicelModelName(cache.get(BaseOrgConstant.TYPE_ALIAS_BASE_VEHICLE_MODEL_TYPE + BaseOrgConstant.SPLIT_SEPARATOR + entity.getVehicelModel()));
                }
                //车厢类型-BASE_VEHICLE_BOX_TYPE
                if(entity.getBoxType() != null){
                    entity.setBoxTypeName(cache.get(BaseOrgConstant.TYPE_ALIAS_BASE_VEHICLE_BOX_TYPE + BaseOrgConstant.SPLIT_SEPARATOR + entity.getBoxType()));
                }
                if(StringUtils.isNotBlank(entity.getOwnerOrg())){
                    //从缓存获取组织机构
                    BaseOrgEntity org = getBaseOrgEntityCache().get(entity.getOwnerOrg());
                    if(org != null){
                        entity.setOwnerOrgName(org.getOrgName());
                    }
//                    Map<String, Object> param = new HashMap<String, Object>();
//                    param.put("orgCode", entity.getOwnerOrg());
//                    List<BaseOrgEntity> orgList = baseOrgService.get(param);
//                    if(orgList != null){
//                        entity.setOwnerOrgName(orgList.get(0).getOrgName());
//                    }
                }
                //根据班次编号获取班次名称
    /*            if(StringUtils.isNotBlank(entity.getRunNo())){
                    entity.setRunName(baseVehicleDao.queryVehicleName(entity.getRunNo()));
                }*/
            }
        }
        return pageInfo;
    }

    /**
     * 修改车辆信息
     */
    @Transactional
    @Override
    public BaseVehicleEntity modifyVehicle(BaseVehicleVo vo,
            String currentUserCode) {
        BaseVehicleEntity oldEntity = baseVehicleDao.getById(vo.getId());
        BeanUtils.copyProperties(vo, oldEntity);
        oldEntity.setModifyTime(new Date());
        oldEntity.setModifyUserCode(currentUserCode);
        baseVehicleDao.update(oldEntity);
        return oldEntity;
    }

    /**
     * 新增一条车辆信息
     */
    @Transactional
    @Override
    public BaseVehicleEntity insertVehicle(BaseVehicleVo vo,
            String currentUserCode) {
        BaseVehicleEntity vehicle = new BaseVehicleEntity();
        if (null != vo) {
            BeanUtils.copyProperties(vo, vehicle);
            vehicle.setId(BasicEntityIdentityGenerator.getInstance().generateId());
            Date date = new Date();
            vehicle.setCreateTime(date);
            vehicle.setCreateUserCode(currentUserCode);
            vehicle.setModifyTime(date);
            vehicle.setModifyUserCode(currentUserCode);
            baseVehicleDao.insert(vehicle);
        }
        return vehicle;
    }

    /**
     * 校验是否存在相同的车牌号
     */
    @Override
    public int uniqueCheckByVehicelNo(Map<String, Object> map) {
        return baseVehicleDao.uniqueCheckByVehicelNo(map);
    }

    /**
     * 删除单个车辆信息
     */
    @Transactional
    @Override
    public void deleteVehicle(Long id) {
        baseVehicleDao.logicalDeleteById(id);
    }

    /**
     *批量删除车辆信息
     */
    @Transactional
    @Override
    public void batchlogicalDeleteById(List<Long> list) {
        baseVehicleDao.batchlogicalDeleteById(list);
    }

    /**
     * 获取所有班次信息
     */
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
    public Pagination<Map<String, Object>> queryVehicleRuns(Map<String, Object> paraMap, Page page, Sort[] sorts) {
        return baseVehicleDao.queryVehicleRuns(paraMap, page, sorts);
    }

    /**
     * 批量修改路由状态：停用、启用
     * @author caijue
     * @param list要修改的id集合， blFlag操作：启用、禁用
     */
    @Transactional
    @Override
    public void modifyBlFlagByIdList(Map<String, Object> paramMap) {
        baseVehicleDao.updateBlFlagByMap(paramMap);
    }

    /**
     * 根据车辆编号判断是否存在
     */
    @Override
    public int uniqueCheckByVehicleCode(String vehicelCode) {
        return baseVehicleDao.uniqueCheckByVehicleCode(vehicelCode);
    }

}
