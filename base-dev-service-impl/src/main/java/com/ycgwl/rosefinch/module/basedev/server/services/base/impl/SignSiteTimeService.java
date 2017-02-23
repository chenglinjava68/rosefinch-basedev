package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.IBaseSiteDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.ISignSiteTimeDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.ISignSiteTimeReleDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.ISignSiteTimeService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignSiteTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignSiteTimeReleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignSiteTimeReleVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignSiteTimeVo;

/**
 *
 * Title: Description:门店serviceImp
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 下午1:30:23
 *
 */
@Service("signSiteTimeService")
public class SignSiteTimeService implements ISignSiteTimeService {
    @Autowired
    private ISignSiteTimeDao signSiteTimeDao;
    @Autowired
    private ISignSiteTimeReleDao signSiteTimeReleDao;

    @Autowired
    private IBaseSiteDao baseSiteDao;

    @Override
    public int insert(SignSiteTimeEntity t) {

        return signSiteTimeDao.insert(t);
    }

    @Override
    public int update(SignSiteTimeEntity t) {

        return signSiteTimeDao.update(t);
    }

    @Override
    public SignSiteTimeEntity getById(Long id) {

        return signSiteTimeDao.getById(id);
    }

    @Override
    public List<SignSiteTimeEntity> get(Map<String, Object> params) {

        return signSiteTimeDao.get(params);
    }

    @Override
    public List<SignSiteTimeEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {

        return signSiteTimeDao.getPage(params, pageNum, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public int getPageTotalCount(Map<String, Object> params) {

        return signSiteTimeDao.getPageTotalCount(params);
    }

    @Override
    public int deleteById(Long id) {
        //逻辑删除
        return signSiteTimeDao.logicalDeleteById(id);
    }

    @Override
    public int updateStatusById(Long id, int status) {

        return signSiteTimeDao.updateStatusById(id, status);
    }

    @Override
    public Pagination<SignSiteTimeEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {

        return signSiteTimeDao.getPagination(params, page, sorts);
    }

    /******************* 自己的服务*************start *******/
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int insertSignSiteTime(SignSiteTimeVo vo, String currentUser) throws BusinessException {
        if (StringUtils.isEmpty(currentUser.trim())) {
            throw new BusinessException("当前登录人为空");
        }
        if (StringUtils.isEmpty(vo.getConfigCode().trim())) {
            throw new BusinessException("传入的时效编号为空");
        }
        if (StringUtils.isEmpty(vo.getConfigName().trim())) {
            throw new BusinessException("传入的时效名称为空");
        }
        if (vo.getSiteCodeList() == null) {
            throw new BusinessException("传入的使用网点为空");
        }

        SignSiteTimeEntity signSiteTimeEntity = new SignSiteTimeEntity();
        myVo2Entity(vo, signSiteTimeEntity);
        // 设置相应的值
        signSiteTimeEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
        signSiteTimeEntity.setCreateUserCode(currentUser);
        signSiteTimeEntity.setCreateTime(new Date());
        signSiteTimeEntity.setModifyTime(new Date());
        int flag1 = signSiteTimeDao.insert(signSiteTimeEntity);
        List<SignSiteTimeReleVo> siteCodeList = vo.getSiteCodeList();
        String configCode = vo.getConfigCode();
        batchInsertReles(siteCodeList, configCode);
        if (flag1 > 0) {
            return 1;
        }
        return -1;
    }

    private void batchInsertReles(List<SignSiteTimeReleVo> siteCodeList, String configCode) {
        if (CollectionUtils.isNotEmpty(siteCodeList)) {
            for (SignSiteTimeReleVo vo : siteCodeList) {
                SignSiteTimeReleEntity signSiteTimeReleEntity = new SignSiteTimeReleEntity();
                signSiteTimeReleEntity.setrId(BasicEntityIdentityGenerator.getInstance().generateId());
                signSiteTimeReleEntity.setConfigCode(configCode);
                signSiteTimeReleEntity.setSiteCode(vo.getSiteCode());
                int flag2 = signSiteTimeReleDao.insert(signSiteTimeReleEntity);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int deleteSignSiteTimeById(Long id) {

        SignSiteTimeEntity signSiteTimeEntity = signSiteTimeDao.getById(id);
        int flag1 = signSiteTimeDao.logicalDeleteById(id);
        signSiteTimeReleDao.deleteAllReles(signSiteTimeEntity.getConfigCode());
        if (flag1 > 0) {
            return 1;
        }
        return -1;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int updateSignSiteTime(SignSiteTimeVo vo, String currentUser) throws BusinessException {
        if (StringUtils.isEmpty(currentUser.trim())) {
            throw new BusinessException("当前登录人为空");
        }
        SignSiteTimeEntity signSiteTimeEntity = signSiteTimeDao.getById(vo.getId());
        myVo2Entity(vo, signSiteTimeEntity);
        // 设置相应的值
        signSiteTimeEntity.setConfigName(vo.getConfigName());
        signSiteTimeEntity.setModifyUserCode(currentUser);
        signSiteTimeEntity.setModifyTime(new Date());
        int flag1 = signSiteTimeDao.update(signSiteTimeEntity);

        signSiteTimeReleDao.deleteAllReles(signSiteTimeEntity.getConfigCode());

        batchInsertReles(vo.getSiteCodeList(), signSiteTimeEntity.getConfigCode());
        if (flag1 > 0) {
            return 1;
        }
        return -1;

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Pagination<SignSiteTimeVo> getPage(Map<String, Object> params, Page page, Sort... sorts) {
        Pagination<SignSiteTimeVo> pagin = signSiteTimeDao.querySignSiteTimeVoPagination2(params, page, sorts);
        List<SignSiteTimeVo> listVo = pagin.getList();

        if (!CollectionUtils.isEmpty(listVo)) {
            for (SignSiteTimeVo vo : listVo) {
                String configCode = vo.getConfigCode();
                Map<String, Object> map = getSitesBySignSiteTime(configCode);
                String str = (String) map.get("siteNames");
                vo.setSiteNames(str);
            }
        }

        return pagin;
    }

    /**
     * VO转实体的方法
     *
     * @param signSiteTimeVo
     * @param signSiteTimeEntity
     */
    private void myVo2Entity(SignSiteTimeVo signSiteTimeVo, SignSiteTimeEntity signSiteTimeEntity) {
        signSiteTimeEntity.setConfigCode(signSiteTimeVo.getConfigCode());
        signSiteTimeEntity.setConfigName(signSiteTimeVo.getConfigName());
        signSiteTimeEntity.setBlFlag(signSiteTimeVo.getBlFlag());
        signSiteTimeEntity.setOneArrivalTimeS(signSiteTimeVo.getOneArrivalTimeS());
        signSiteTimeEntity.setOneArrivalTimeE(signSiteTimeVo.getOneArrivalTimeE());
        signSiteTimeEntity.setOneDispatchEndTime(signSiteTimeVo.getOneDispatchEndTime());
        signSiteTimeEntity.setOneDispatchMonthEndTime(signSiteTimeVo.getOneDispatchMonthEndTime());
        signSiteTimeEntity.setTwoArrivalTimeS(signSiteTimeVo.getTwoArrivalTimeS());
        signSiteTimeEntity.setTwoArrivalTimeE(signSiteTimeVo.getTwoArrivalTimeE());
        signSiteTimeEntity.setTwoDispatchEndTime(signSiteTimeVo.getTwoDispatchEndTime());
        signSiteTimeEntity.setTwoDispatchMonthEndTime(signSiteTimeVo.getTwoDispatchMonthEndTime());
        signSiteTimeEntity.setOneDispatchRate(signSiteTimeVo.getOneDispatchRate());
        signSiteTimeEntity.setTwoDispatchRate(signSiteTimeVo.getTwoDispatchRate());
        signSiteTimeEntity.setRemark(signSiteTimeVo.getRemark());

    }

    /**
     * 实体转VO
     */
    private void myEntity2Vo(SignSiteTimeEntity signSiteTimeEntity, SignSiteTimeVo signSiteTimeVo) {
        signSiteTimeVo.setId(signSiteTimeEntity.getId());
        signSiteTimeVo.setConfigCode(signSiteTimeEntity.getConfigCode());
        signSiteTimeVo.setConfigName(signSiteTimeEntity.getConfigName());
        signSiteTimeVo.setOneArrivalTimeS(signSiteTimeEntity.getOneArrivalTimeS());
        signSiteTimeVo.setOneArrivalTimeE(signSiteTimeEntity.getOneArrivalTimeE());
        signSiteTimeVo.setOneDispatchEndTime(signSiteTimeEntity.getOneDispatchEndTime());
        signSiteTimeVo.setOneDispatchMonthEndTime(signSiteTimeEntity.getOneDispatchMonthEndTime());
        signSiteTimeVo.setTwoArrivalTimeS(signSiteTimeEntity.getTwoArrivalTimeS());
        signSiteTimeVo.setTwoArrivalTimeE(signSiteTimeEntity.getTwoArrivalTimeE());
        signSiteTimeVo.setTwoDispatchEndTime(signSiteTimeEntity.getTwoDispatchEndTime());
        signSiteTimeVo.setTwoDispatchMonthEndTime(signSiteTimeEntity.getTwoDispatchMonthEndTime());
        signSiteTimeVo.setOneDispatchRate(signSiteTimeEntity.getOneDispatchRate());
        signSiteTimeVo.setTwoDispatchRate(signSiteTimeEntity.getTwoDispatchRate());
        signSiteTimeVo.setBlFlag(signSiteTimeEntity.getBlFlag());
        signSiteTimeVo.setRemark(signSiteTimeEntity.getRemark());
        signSiteTimeVo.setCreateUserCode(signSiteTimeEntity.getCreateUserCode());
        signSiteTimeVo.setCreateTime(signSiteTimeEntity.getCreateTime());
        signSiteTimeVo.setModifyTime(signSiteTimeEntity.getModifyTime());
        signSiteTimeVo.setModifyUserCode(signSiteTimeEntity.getModifyUserCode());
        signSiteTimeVo.setDelFlag(signSiteTimeEntity.getDelFlag());

    }

    @Override
    public String getConfigCodeByInsert(String configCode) {

        return signSiteTimeDao.getConfigCodeByInsert(configCode);
    }

    @Override
    public String getConfigNameByInsert(String configName) {

        return signSiteTimeDao.getConfigNameByInsert(configName);
    }
    ///修改
    @Override
    public int getConfigNameByInsert2(Map<String, Object> map) {

        return signSiteTimeDao.getConfigNameByInsert2(map);
    }


    @Override
    public SignSiteTimeVo preUpdateStoreData(Long id) throws BusinessException {

        SignSiteTimeVo signSiteTimeVo = new SignSiteTimeVo();
        try {
            SignSiteTimeEntity signSiteTimeEntity = signSiteTimeDao.getById(id);
            myEntity2Vo(signSiteTimeEntity, signSiteTimeVo);
            String configCode = signSiteTimeEntity.getConfigCode();
            // //根据configCode查询出所有SignSiteTimeReleVo
            // List<SignSiteTimeReleVo>siteCodeList=
            // signSiteTimeReleDao.findSignSiteTimeReleVoList(configCode);
            // signSiteTimeVo.setSiteCodeList(siteCodeList);
            // 查询出name
            Map<String, Object> map = getSitesBySignSiteTime(configCode);
            String str = (String) map.get("siteNames");
            signSiteTimeVo.setSiteNames(str);
            // siteCodes

            List<SignSiteTimeReleVo> siteCodeList = (List<SignSiteTimeReleVo>) map.get("siteCodes");
            signSiteTimeVo.setSiteCodeList(siteCodeList);

        } catch (Exception e) {
            throw new BusinessException("封装修改前的数据出错");
        }

        return signSiteTimeVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public boolean batchStart(long[] ids) {
        for (long id : ids) {
            if (id != 0) {// 判断long 类型的id是否为空
                int flag = signSiteTimeDao.openBlFlagById(id);
                if (flag < 0) {
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public boolean batchClose(long[] ids) {
        for (long id : ids) {
            if (id != 0) {// 判断long 类型的id是否为空
                int flag = signSiteTimeDao.closeBlFlagById(id);
                if (flag < 0) {
                    return false;
                }
            }

        }
        return true;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public boolean batchDeleteStoreSendTimeByIdValue(long[] ids) {
        if (ids == null) {
            throw new BusinessException("传入ID列表为空!");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
       int flag= signSiteTimeDao.batchDeleteStoreSendTimeByIdValue(map);
       if(flag>0){
           return true;
       }
        return false;
    }

    @Override
    public List<BaseSiteEntity> getByConfigCodeAndSites(String code, List<BaseSiteEntity> baseSiteEntities) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("configCode", code);
        paramMap.put("list", baseSiteEntities);
        return signSiteTimeReleDao.getByConfigCodeAndSites(paramMap);

    }

    @Override
    public Map<String, Object> getSitesBySignSiteTime(String configCode) {
        Map<String, Object> siteMap = new HashMap<String, Object>();
        List<BaseSiteEntity> siteList = signSiteTimeReleDao.getOrgByConfigCode(configCode);
        if (CollectionUtils.isNotEmpty(siteList)) {
            String siteNames = "";
            List<SignSiteTimeReleVo> siteCodeList = new ArrayList<SignSiteTimeReleVo>();
            for (BaseSiteEntity baseSiteEntity : siteList) {
                siteNames += "," + baseSiteEntity.getSiteNameShort();
                SignSiteTimeReleVo vo = new SignSiteTimeReleVo();
                vo.setSiteCode(baseSiteEntity.getSiteCode());
                vo.setSiteName(baseSiteEntity.getSiteNameShort());
                siteCodeList.add(vo);
            }
            siteMap.put("siteNames", siteNames.substring(1));
            siteMap.put("siteCodes", siteCodeList);
        }
        return siteMap;
    }

    @Override
    public Set<String> getChildSites(String siteCode){
        List<String> list = baseSiteDao.queryChildSitesBySiteCode(siteCode);
        Set<String> set = new HashSet<String>();
        if(CollectionUtils.isNotEmpty(list)){
            set.addAll(list);
        }
        return set;
    }







    /******************* 自己的服务*************end *******/

}
