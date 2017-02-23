package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

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

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ycgwl.cache.CacheManager;
import com.ycgwl.cache.base.ICache;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.common.shared.define.DpapConstants;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseClassesDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseClassesDetailDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseClassesService;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseClassesConstant;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseOrgConstant;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseClassesDetailEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseClassesEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseRegionEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesDetailVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesVo;

@Service("baseClassesService")
public class BaseClassesService implements IBaseClassesService {

    @Autowired
    private IBaseClassesDao baseClassesDao;
    @Autowired
    private IBaseClassesDetailDao baseClassesDetailDao;
    // 缓存接口
    @Autowired
    private IBaseRedisCacheService baseRedisCacheService;
    @Transactional
    @Override
    public int insert(BaseClassesEntity baseClassesEntity) {
        return baseClassesDao.insert(baseClassesEntity);
    }

    @Transactional
    @Override
    public int update(BaseClassesEntity baseClassesEntity) {
        // 判定数据是否为空
        if (baseClassesEntity == null) {
            throw new BusinessException("对应的应用系统数据为空");
        }
        // 判定对应的ID是否为空
        if (baseClassesEntity.getId() == null) {
            throw new BusinessException("对应的应用系统数据ID为空");
        }
        int num = baseClassesDao.update(baseClassesEntity);
        if (num < 1) {
            throw new BusinessException("更新应用系统数据失败");
        }
        return num;
    }
    @Transactional
    @Override
    public BaseClassesEntity getById(Long id) {
        if (id == null) {
            throw new BusinessException("传入的参数为空");
        }
        BaseClassesEntity baseClassesEntity= baseClassesDao.getById(id);
        //baseClassesEntity.setClassesDetailLists(queryClassesDetailByClassesId(id));
        return baseClassesEntity;
    }

    @Transactional
    @Override
    public List<BaseClassesEntity> get(Map<String, Object> params) {
        return baseClassesDao.get(params);
    }

    @Transactional
    @Override
    public List<BaseClassesEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {
        return baseClassesDao.getPage(params, pageNum, pageSize);
    }

    @Transactional
    @Override
    public int getPageTotalCount(Map<String, Object> params) {
        return 0;
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return baseClassesDao.deleteById(id);
    }

    @Transactional
    @Override
    public int updateStatusById(Long id, int status) {
        return 0;
    }

    @Transactional
    @Override
    public Pagination<BaseClassesEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {
        return baseClassesDao.getPagination(params, page, sorts);
    }

    /**
     *
     * @Title:批量查找
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月28日下午3:09:12
     *　@param paraMap
     *　@param page
     *　@param sorts
     *　@return
     */
    @Transactional
    @Override
    public Pagination<BaseClassesVo> queryClassesBySomeIf(Map<String, Object> paraMap, Page page,
            Sort[] sorts) {
        Pagination<BaseClassesVo> baseClassesVos=baseClassesDao.queryClassesBySomeIf(paraMap, page, sorts);
        if(baseClassesVos != null && CollectionUtils.isNotEmpty(baseClassesVos.getList())){
            ICache<String, String> cache = getDictDataValueNameCache();
            for (BaseClassesVo baseClassesVo : baseClassesVos.getList()) {
                //出发周期-BASE_CLASSES_CYCLE
                if(baseClassesVo.getCycle() != null){
                    baseClassesVo.setCycleName(cache.get(BaseOrgConstant.BASE_CLASSES_CYCLE + BaseOrgConstant.SPLIT_SEPARATOR + baseClassesVo.getCycle()));
                }
            }
        }

        return baseClassesVos;
    }

    /**
     *
     * @Title:启用单个
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:15:40
     *　@param id
     *　@return
     */
    @Transactional
    @Override
    public int startOneClasses(Map<String, Object> map) {
        return baseClassesDao.startOneClasses(map);
    }
    /**
     *
     * @Title:停用单个
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:15:26
     *　@param id
     *　@return
     */
    @Transactional
    @Override
    public int stopOneClasses(Map<String, Object> map) {
        return baseClassesDao.stopOneClasses(map);
    }
    /**
     *
     * @Title:根据id删除多个
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:15:09
     *　@param ids
     *　@return
     */
    @Transactional
    @Override
    public int deleteSomeClasses(long[] ids) {
        int count = 0;
        for(long id : ids){
            count  = count + deleteClassesById(id);
        }
        return count;
    }
    /**
     *
     * @Title:根据id删除单个
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:14:42
     *　@param id
     *　@return
     */
    @Transactional
    @Override
    public int deleteClassesById(long id) {
      int flag=baseClassesDao.deleteClassesById(id);
      return flag;
    }
    /**
     *
     * @Title:查询车线
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:14:26
     *　@param paraMap
     *　@param page
     *　@param sorts
     *　@return
     */
    @Transactional
    @Override
    public Pagination<BaseClassesVo> queryCarLine(Map<String, Object> paraMap, Page page, Sort[] sorts) {
        return baseClassesDao.queryCarLine(paraMap, page, sorts);
    }
    /**
     *
     * @Title:批量启用
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:14:05
     *　@param ids
     *　@return
     */
    @Transactional
    @Override
    public int startSomeClasses(long[] ids,String currentUserCode) {
        int count = 0;
        for(long id : ids){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("currentUserCode", currentUserCode);
            count  = count + startOneClasses(map);
        }
        return count;
    }
    /**
     *
     * @Title:批量停用
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:13:43
     *　@param ids
     *　@return
     */
    @Transactional
    @Override
    public int stopSomeClasses(long[] ids,String currentUserCode) {
        int count = 0;
        for(long id : ids){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            map.put("currentUserCode", currentUserCode);
            count  = count + stopOneClasses(map);
        }
        return count;
    }
    /**
     *
     * @Title:添加
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:13:04
     *　@param baseClassesVo
     *　@param currentUser
     *　@return
     *　@throws BusinessException
     */
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
    @Override
    public BaseClassesEntity insertBaseClasses(BaseClassesVo baseClassesVo, String currentUser)
            throws BusinessException {
        if(StringUtils.isEmpty(currentUser.trim())){
            throw new BusinessException("当前登录人为空");
        }
        BaseClassesEntity baseClassesEntity = new BaseClassesEntity();
        if (null != baseClassesVo) {
            baseClassesEntityCope(baseClassesEntity,baseClassesVo,currentUser,BaseClassesConstant.METHODTYPE_ADD);
            baseClassesDao.insert(baseClassesEntity);
            batchInsert(baseClassesEntity);
        }
        return baseClassesEntity;
    }
    /**
     *
     * @Title:修改(根据条件删除从表进行主表的载入)
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月24日下午5:13:27
     *　@param baseClassesVo
     *　@param currentUser
     *　@return
     *　@throws BusinessException
     */
    @Transactional(propagation=Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class,RuntimeException.class})
    @Override
    public BaseClassesEntity updateBaseClasses(BaseClassesVo baseClassesVo, String currentUser)
            throws BusinessException {
        BaseClassesEntity baseClassesEntity = baseClassesDao.getById(baseClassesVo.getId());
        String lineCode=baseClassesEntity.getLineCode();
        String departtrueTime=baseClassesEntity.getDepartureTime();
        baseClassesEntityCope(baseClassesEntity,baseClassesVo,currentUser,BaseClassesConstant.METHODTYPE_UPDATE);
        baseClassesDao.update(baseClassesEntity);
        if(baseClassesVo.getLineCode()!=null&&lineCode!=baseClassesVo.getClassesCode()){
            baseClassesDetailDao.deleteClassesDetailByClassesId(baseClassesEntity.getClassesCode());
            batchInsert(baseClassesEntity);
        }
        if(departtrueTime.equals(baseClassesVo.getDepartureTime())==false){
            baseClassesDetailDao.deleteClassesDetailByClassesId(baseClassesEntity.getClassesCode());
            batchInsert(baseClassesEntity);
        }
        return baseClassesEntity;
    }
    /**
     *
     * @Title get/set进行匹配
     * @Description 对象复制-类型的不匹配
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月24日下午2:34:54
     * @param baseClassesEntity
     * @param baseClassesVo
     * @param currentUser
     * @param methodType
     */
    private  void baseClassesEntityCope(BaseClassesEntity baseClassesEntity,BaseClassesVo baseClassesVo, String currentUser,String methodType){
        int time=0;//时间点
        String arrayTime="";//时间字符串
        String ids=null;
        if(baseClassesVo.getLineCode()==null){
            BaseClassesEntity baseClassesEntity1 = baseClassesDao.getById(baseClassesVo.getId());
            ids=baseClassesEntity1.getLineCode();
            baseClassesEntity.setLineCode(baseClassesEntity1.getLineCode());
        }
        else{
            ids=baseClassesVo.getLineCode();
            baseClassesEntity.setLineCode(baseClassesVo.getLineCode());
        }
        List<BaseClassesDetailVo> baseClassesDetailVos = baseClassesDetailDao.queryCarLineDetailByLineCode(ids);
        for(int i=0;i<baseClassesDetailVos.size();i++){
            time=time+baseClassesDetailVos.get(i).getArriveTime()+baseClassesDetailVos.get(i).getStayTime();
            if(i==baseClassesDetailVos.size()-1){
                time=time-baseClassesDetailVos.get(i).getStayTime();
            }
            arrayTime= batchTime(baseClassesVo.getDepartureTime(),time);
        }
        baseClassesEntity.setClassesCode(baseClassesVo.getClassesCode());
        baseClassesEntity.setBlFlag(baseClassesVo.getBlFlag());
        baseClassesEntity.setArriveTime(arrayTime);
        baseClassesEntity.setDelFlag(0);
        baseClassesEntity.setClassesName(baseClassesVo.getClassesName());
        baseClassesEntity.setCycle(baseClassesVo.getCycle());
        baseClassesEntity.setDepartureTime(baseClassesVo.getDepartureTime());
        baseClassesEntity.setRemark(baseClassesVo.getRemark());
        baseClassesEntity.setVehicelCode(baseClassesVo.getVehicelCode());
        Integer carType=baseClassesDao.queryCarTypeByvehicelNo(baseClassesVo.getVehicelCode());
        baseClassesEntity.setCarType(carType);
      //增加方法类型判断
        if(BaseClassesConstant.METHODTYPE_ADD.equals(methodType)){
            Long id =BasicEntityIdentityGenerator.getInstance().generateId();
            Date createTime = new Date();
            baseClassesEntity.setId(id);
            baseClassesEntity.setCreateUserCode(currentUser);
            baseClassesEntity.setCreateTime(createTime);
        }
        //修改方法类型判断
        if(BaseClassesConstant.METHODTYPE_UPDATE.equals(methodType)){
            baseClassesEntity.setId(baseClassesVo.getId());
        }
        baseClassesEntity.setModifyTime(new Date());
        baseClassesEntity.setModifyUserCode(currentUser);
    }
    /**
     *
     * @Title:车次名字详细判断
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月29日下午3:39:02
     *　@param classesName
     *　@return
     */
    @Transactional
    @Override
    public int uniqueCheckByClassesName(Map<String, Object> map) {
        return baseClassesDao.uniqueCheckByClassesName(map);
    }
    /**
     *
     * @Title:查找全部车牌号
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月29日下午3:38:40
     *　@param paraMap
     *　@param page
     *　@param sorts
     *　@return
     */
    @Transactional
    @Override
    public Pagination<BaseClassesVo> queryVehicelNo(Map<String, Object> paraMap, Page page, Sort[] sorts) {
        return baseClassesDao.queryVehicelCode(paraMap, page, sorts);
    }

    /**
     *
     * @Title:查找指定车次的详细信息
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月29日下午3:38:10
     *　@param paraMap
     *　@param page
     *　@param sorts
     *　@return
     */
    @Transactional
    @Override
    public List<BaseClassesDetailVo> queryClassesDetailByClassesId(String classesCode) {
        List<BaseClassesDetailVo> baseClassesDetailVos = baseClassesDetailDao.queryClassesDetailByClassesId(classesCode);
        if(baseClassesDetailVos != null && CollectionUtils.isNotEmpty(baseClassesDetailVos)){
            ICache<String, String> cache = getDictDataValueNameCache();
            for (BaseClassesDetailVo baseClassesDetailVo : baseClassesDetailVos) {
                //所有类型-BASE_VEHICLE_ALL_TYPE
                if(baseClassesDetailVo.getViaType() != null){
                    baseClassesDetailVo.setViaTypeName(cache.get(BaseOrgConstant.BASE_VEHICLE_VIA_TYPE + BaseOrgConstant.SPLIT_SEPARATOR + baseClassesDetailVo.getViaType()));
                }
                //运输方式-BASE_ROUTE_CLASSTYPE
                if(baseClassesDetailVo.getClassType() != null){
                    baseClassesDetailVo.setClassTypeName(cache.get(BaseOrgConstant.BASE_ROUTE_CLASSTYPE + BaseOrgConstant.SPLIT_SEPARATOR + baseClassesDetailVo.getClassType()));
                }
                if(StringUtils.isNotBlank(baseClassesDetailVo.getCurrSiteCode())){
                    //从缓存获取当前网点名称
                    BaseSiteEntity site = getBaseSiteEntityCache().get(baseClassesDetailVo.getCurrSiteCode());
                    if(site != null){
                        baseClassesDetailVo.setCurrSiteName(site.getSiteName());
                    }
                }
                if(StringUtils.isNotBlank(baseClassesDetailVo.getNextSiteCode())){
                    //从缓存获取下个网点名称
                    BaseSiteEntity site = getBaseSiteEntityCache().get(baseClassesDetailVo.getNextSiteCode());
                    if(site != null){
                        baseClassesDetailVo.setNextSiteName(site.getSiteNameShort());
                    }
                }
                String[] currRegions = getRegionInfo(baseClassesDetailVo.getCurrRegionCode());
                if(currRegions!=null){

                        baseClassesDetailVo.setCurrRegionName(currRegions[0]);
                        baseClassesDetailVo.setCurrRegionCodeStr(currRegions[1]);
                }
                String[] nextRegions = getRegionInfo(baseClassesDetailVo.getNextRegionCode());
                if(nextRegions!=null){
                    baseClassesDetailVo.setNextRegionName(nextRegions[0]);
                    baseClassesDetailVo.setNextRegionCodeStr(nextRegions[1]);
                }
            }
        }
        return baseClassesDetailVos;
    }
    /**
     * 根据regionCode拼接行政区域名称
     * @param regionCode
     * @return
     */
    private String[] getRegionInfo(String regionCode) {
        if(StringUtils.isNotBlank(regionCode)){
            List<BaseRegionEntity> regionList = baseRedisCacheService.getBaseRegionListCascadeFromCache(regionCode);

            if (CollectionUtils.isNotEmpty(regionList)) {
                String regionName = "";
                String regionCodeStr = "";
                int i = 0;

                for (BaseRegionEntity baseRegionEntity : regionList) {
                    if (i > 0) {
                        regionName += "-";
                        regionName += baseRegionEntity.getRegionName();

                        regionCodeStr += "-";
                        regionCodeStr += baseRegionEntity.getRegionCode();
                    }else {
                        regionName += baseRegionEntity.getRegionName();
                        regionCodeStr += baseRegionEntity.getRegionCode();
                    }
                    i++;
                }
                return new String[]{regionName, regionCodeStr};
            }
        }
        return null;
    }
    //获得缓存
    private ICache<String, String> getDictDataValueNameCache() {
        ICache<String, String> cache = CacheManager.getInstance().getCache(DpapConstants.DICT_DATA_VALUE_NAME_CACHE_UUID);
        return cache;
    }
    // 网点
    private ICache<String, BaseSiteEntity> getBaseSiteEntityCache() {
        return CacheManager.getInstance().getCache(BaseCacheConstant.SITE_INFO_CATCHE_UUID);
    }
    //行政区域
    private ICache<String, BaseRegionEntity> getBaseRegionEntityCache() {
        return CacheManager.getInstance().getCache(BaseCacheConstant.REGION_CATCHE_UUID);
    }
    /**
     *
     * @Title:指定车线详细信息
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年11月29日下午3:36:39
     *　@param lineId
     *　@return
     */
    @Transactional
    @Override
    public List<BaseClassesDetailVo> queryCarLineDetailByLineId(String lineCode) {
        List<BaseClassesDetailVo> baseClassesDetailVos = baseClassesDetailDao.queryCarLineDetailByLineCode(lineCode);
        if(baseClassesDetailVos != null && CollectionUtils.isNotEmpty(baseClassesDetailVos)){
            ICache<String, String> cache = getDictDataValueNameCache();
            for (BaseClassesDetailVo baseClassesDetailVo : baseClassesDetailVos) {
                //所有类型-BASE_VEHICLE_ALL_TYPE
                if(baseClassesDetailVo.getViaType() != null){
                    baseClassesDetailVo.setViaTypeName(cache.get(BaseOrgConstant.BASE_VEHICLE_VIA_TYPE + BaseOrgConstant.SPLIT_SEPARATOR + baseClassesDetailVo.getViaType()));
                }
                //运输方式-BASE_ROUTE_CLASSTYPE
                if(baseClassesDetailVo.getClassType() != null){
                    baseClassesDetailVo.setClassTypeName(cache.get(BaseOrgConstant.BASE_ROUTE_CLASSTYPE + BaseOrgConstant.SPLIT_SEPARATOR + baseClassesDetailVo.getClassType()));
                }
                if(StringUtils.isNotBlank(baseClassesDetailVo.getCurrSiteCode())){
                    //从缓存获取当前网点名称
                    BaseSiteEntity site = getBaseSiteEntityCache().get(baseClassesDetailVo.getCurrSiteCode());
                    if(site != null){
                        baseClassesDetailVo.setCurrSiteName(site.getSiteNameShort());
                    }
                }
                if(StringUtils.isNotBlank(baseClassesDetailVo.getNextSiteCode())){
                    //从缓存获取下个网点名称
                    BaseSiteEntity site = getBaseSiteEntityCache().get(baseClassesDetailVo.getNextSiteCode());
                    if(site != null){
                        baseClassesDetailVo.setNextSiteName(site.getSiteNameShort());
                    }
                }
                String[] currRegions = getRegionInfo(baseClassesDetailVo.getCurrRegionCode());
                if(currRegions!=null){

                        baseClassesDetailVo.setCurrRegionName(currRegions[0]);
                        baseClassesDetailVo.setCurrRegionCodeStr(currRegions[1]);
                }
                String[] nextRegions = getRegionInfo(baseClassesDetailVo.getNextRegionCode());
                if(nextRegions!=null){
                    baseClassesDetailVo.setNextRegionName(nextRegions[0]);
                    baseClassesDetailVo.setNextRegionCodeStr(nextRegions[1]);
                }
            }
        }
        return baseClassesDetailVos;
    }
    /**
     *
     * @Title 将指定车线详细插入车次详细
     * @Description TODO
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月29日下午3:37:25
     * @param lineId
     * @param classesId
     */
    private void batchInsert(BaseClassesEntity baseClassesEntity){
        List<BaseClassesDetailVo> baseClassesDetailVos=baseClassesDetailDao.queryCarLineDetailByLineCode(baseClassesEntity.getLineCode());
        if(CollectionUtils.isNotEmpty(baseClassesDetailVos)){
            for (int i=0;i<baseClassesDetailVos.size();i++) {
                BaseClassesDetailEntity  baseClassesDetailEntity =new BaseClassesDetailEntity();
                baseClassesDetailEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
                baseClassesDetailEntity.setClassesCode(baseClassesEntity.getClassesCode());
                baseClassesDetailEntity.setRemark(baseClassesDetailVos.get(i).getRemark());
                baseClassesDetailEntity.setArriveMileage(baseClassesDetailVos.get(i).getArriveMileage());
                baseClassesDetailEntity.setArriveTime(baseClassesDetailVos.get(i).getArriveTime());
                baseClassesDetailEntity.setClassType(baseClassesDetailVos.get(i).getClassType());
                baseClassesDetailEntity.setViaType(baseClassesDetailVos.get(i).getViaType());
                baseClassesDetailEntity.setOrderBy(baseClassesDetailVos.get(i).getOrderBy());
                baseClassesDetailEntity.setCurrRegionCode(baseClassesDetailVos.get(i).getCurrRegionCode());
                baseClassesDetailEntity.setCurrSiteCode(baseClassesDetailVos.get(i).getCurrSiteCode());
                baseClassesDetailEntity.setNextRegionCode(baseClassesDetailVos.get(i).getNextRegionCode());
                baseClassesDetailEntity.setNextSiteCode(baseClassesDetailVos.get(i).getNextSiteCode());
                baseClassesDetailEntity.setStayTime(baseClassesDetailVos.get(i).getStayTime());
                if(baseClassesDetailVos.get(i).getOrderBy()==1){
                    baseClassesDetailEntity.setDepartTime(baseClassesEntity.getDepartureTime());
                    baseClassesDetailEntity.setArrivalTime(batchTime(baseClassesEntity.getDepartureTime(),baseClassesDetailVos.get(i).getArriveTime()));
                }
                else{
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("classesCode",baseClassesEntity.getClassesCode());
                    map.put("orderBy",baseClassesDetailVos.get(i-1).getOrderBy());
                    BaseClassesDetailEntity  baseClassesDetailEntity1 =queryByOrder(map);
                    baseClassesDetailEntity.setDepartTime(batchTime(baseClassesDetailEntity1.getArrivalTime(),baseClassesDetailVos.get(i-1).getStayTime()));
                    baseClassesDetailEntity.setArrivalTime(batchTime(baseClassesDetailEntity1.getArrivalTime(),(baseClassesDetailVos.get(i-1).getStayTime()+baseClassesDetailVos.get(i).getArriveTime())));
                }
                baseClassesDetailDao.insert(baseClassesDetailEntity);
            }
        }
     }
   /**
    *
    * @Title 调整时间参数
    * @Description TODO
    *　@Company　远成快运
    * @author panxiaoling
    * @date 2016年12月6日下午2:52:19
    * @param time
    * @param time1
    * @return
    */
    private String batchTime(String time,int time1){
        String[] times = time.split(":");
        if(Integer.parseInt(times[0])>23){
            times[0]=((Integer.parseInt(times[0])+((Integer.parseInt(times[1])+time1)/60)))/24-1+"";
        }
        times[0]=(Integer.parseInt(times[0])+((Integer.parseInt(times[1])+time1)/60))+"";
        times[1]=((Integer.parseInt(times[1])+time1)%60)+"";
        if(0<=Integer.parseInt(times[1]) && Integer.parseInt(times[1])<10){
            times[1]="0"+times[1];
        }
    return   times[0]+":"+times[1];
}

    /**
     *
     * @Title 修改明细
     * @Description TODO
     *　@Company　远成快运
     * @author panxiaoling
     * @date 2016年11月30日下午1:28:57
     * @param baseClassesDetailVos
     * @return
     */
    @Transactional
    @Override
    public int updateSomeClassesDetail(List<BaseClassesDetailVo> baseClassesDetailVos) {
        int count=0;
        if(CollectionUtils.isNotEmpty(baseClassesDetailVos)){
            for (BaseClassesDetailVo baseClassesDetailVo : baseClassesDetailVos) {
                BaseClassesDetailEntity baseClassesDetailEntity = new BaseClassesDetailEntity();
                baseClassesDetailEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
                baseClassesDetailEntity.setClassesCode(baseClassesDetailVo.getClassesCode());
                baseClassesDetailEntity.setRemark(baseClassesDetailVo.getRemark());
                baseClassesDetailEntity.setArriveMileage(baseClassesDetailVo.getArriveMileage());
                baseClassesDetailEntity.setArriveTime(baseClassesDetailVo.getArriveTime());
                baseClassesDetailEntity.setClassType(baseClassesDetailVo.getClassType());
                baseClassesDetailEntity.setViaType(baseClassesDetailVo.getViaType());
                baseClassesDetailEntity.setOrderBy(baseClassesDetailVo.getOrderBy());
                baseClassesDetailEntity.setCurrRegionCode(baseClassesDetailVo.getCurrRegionCode());
                baseClassesDetailEntity.setCurrSiteCode(baseClassesDetailVo.getCurrSiteCode());
                baseClassesDetailEntity.setNextRegionCode(baseClassesDetailVo.getNextRegionCode());
                baseClassesDetailEntity.setNextSiteCode(baseClassesDetailVo.getNextSiteCode());
                baseClassesDetailEntity.setStayTime(baseClassesDetailVo.getStayTime());
                baseClassesDetailEntity.setArrivalTime(baseClassesDetailVo.getArrivalTime());
                baseClassesDetailEntity.setDepartTime(baseClassesDetailVo.getDepartTime());
               count=count+baseClassesDetailDao.update(baseClassesDetailEntity);
            }
        }
        return count;
    }
    /**
     *
     * @Title:根据条件查找
     * @Description:
     *　@Company:远成快运
     * @author:panxiaoling
     * @date:2016年12月7日上午8:47:17
     *　@param map
     *　@return
     */
    @Transactional
    @Override
    public BaseClassesDetailEntity queryByOrder(Map<String, Object> map) {
      return baseClassesDetailDao.queryByOrder(map);
    }

    //查找唯一编号
    @Transactional
    @Override
    public int uniqueCheckByClassesCode(String classesCode) {
        return baseClassesDao.uniqueCheckByClassesCode(classesCode);
    }
}
