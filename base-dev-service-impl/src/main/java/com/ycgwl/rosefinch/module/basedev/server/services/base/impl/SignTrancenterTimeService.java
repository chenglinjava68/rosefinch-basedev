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

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.support.id.BasicEntityIdentityGenerator;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.ISignTrancenterTimeDao;
import com.ycgwl.rosefinch.module.basedev.server.dao.base.ISignTrancenterTimeReleDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.ISignTrancenterTimeService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignTrancenterTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignTrancenterTimeReleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignTrancenterTimeReleVo;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignTrancenterTimeVo;

/**
 *
 * Title: Description: 派件 时效 转运分拨 ServiceImpl
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 下午1:58:15
 *
 */
@Service("signTrancenterTimeService")
public class SignTrancenterTimeService implements ISignTrancenterTimeService {
    @Autowired
    private ISignTrancenterTimeDao signTrancenterTimeDao;
    @Autowired
    private ISignTrancenterTimeReleDao signTrancenterTimeReleDao;

    @Override
    public int insert(SignTrancenterTimeEntity t) {

        return signTrancenterTimeDao.insert(t);
    }

    @Override
    public int update(SignTrancenterTimeEntity t) {

        return signTrancenterTimeDao.update(t);
    }

    @Override
    public SignTrancenterTimeEntity getById(Long id) {

        return signTrancenterTimeDao.getById(id);
    }

    @Override
    public List<SignTrancenterTimeEntity> get(Map<String, Object> params) {

        return signTrancenterTimeDao.get(params);
    }

    @Override
    public List<SignTrancenterTimeEntity> getPage(Map<String, Object> params, int pageNum, int pageSize) {

        return signTrancenterTimeDao.getPage(params, pageNum, pageSize);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public int getPageTotalCount(Map<String, Object> params) {

        return signTrancenterTimeDao.getPageTotalCount(params);
    }

    @Override
    public int deleteById(Long id) {

        return signTrancenterTimeDao.deleteById(id);
    }

    @Override
    public int updateStatusById(Long id, int status) {

        return signTrancenterTimeDao.updateStatusById(id, status);
    }

    @Override
    public Pagination<SignTrancenterTimeEntity> getPagination(Map<String, Object> params, Page page, Sort... sorts) {

        return signTrancenterTimeDao.getPagination(params, page, sorts);
    }

    /************************* my start ************************/

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int insertSignTrancenterTime(SignTrancenterTimeVo vo, String currentUser) throws BusinessException {
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

        SignTrancenterTimeEntity signTrancenterTimeEntity = new SignTrancenterTimeEntity();
        myVo2Entity(vo, signTrancenterTimeEntity);
        // 设置相应的值
        signTrancenterTimeEntity.setId(BasicEntityIdentityGenerator.getInstance().generateId());
        signTrancenterTimeEntity.setCreateUserCode(currentUser);
        signTrancenterTimeEntity.setCreateTime(new Date());
        signTrancenterTimeEntity.setModifyTime(new Date());
        int flag1 = signTrancenterTimeDao.insert(signTrancenterTimeEntity);

        List<SignTrancenterTimeReleVo> siteCodeList = vo.getSiteCodeList();
        String configCode = vo.getConfigCode();
        batchInsertReles(siteCodeList, configCode);
        if (flag1 > 0) {
            return 1;
        }
        return -1;
    }

    private void batchInsertReles(List<SignTrancenterTimeReleVo> siteCodeList, String configCode) {
        if (CollectionUtils.isNotEmpty(siteCodeList)) {
            for (SignTrancenterTimeReleVo vo : siteCodeList) {
                SignTrancenterTimeReleEntity signTrancenterTimeReleEntity = new SignTrancenterTimeReleEntity();
                signTrancenterTimeReleEntity.setrId(BasicEntityIdentityGenerator.getInstance().generateId());
                signTrancenterTimeReleEntity.setConfigCode(configCode);
                signTrancenterTimeReleEntity.setSiteCode(vo.getSiteCode());
                int flag2 = signTrancenterTimeReleDao.insert(signTrancenterTimeReleEntity);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int deleteSignTrancenterTimeById(Long id) {

        SignTrancenterTimeEntity signTrancenterTimeEntity = signTrancenterTimeDao.getById(id);
        int flag1 = signTrancenterTimeDao.logicalDeleteById(id);
        signTrancenterTimeReleDao.deleteAllReles(signTrancenterTimeEntity.getConfigCode());
        if (flag1 > 0) {
            return 1;
        }
        return -1;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int updateSignTrancenterTime(SignTrancenterTimeVo vo, String currentUser) throws BusinessException {
        if (StringUtils.isEmpty(currentUser.trim())) {
            throw new BusinessException("当前登录人为空");
        }
        SignTrancenterTimeEntity signTrancenterTimeEntity = signTrancenterTimeDao.getById(vo.getId());
        myVo2Entity(vo, signTrancenterTimeEntity);
        // 设置相应的值
        signTrancenterTimeEntity.setModifyUserCode(currentUser);
        signTrancenterTimeEntity.setModifyTime(new Date());
        int flag1 = signTrancenterTimeDao.update(signTrancenterTimeEntity);

        signTrancenterTimeReleDao.deleteAllReles(signTrancenterTimeEntity.getConfigCode());

        batchInsertReles(vo.getSiteCodeList(), signTrancenterTimeEntity.getConfigCode());
        if (flag1 > 0) {
            return 1;
        }
        return -1;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Pagination<SignTrancenterTimeVo> getPage(Map<String, Object> params, Page page, Sort... sorts) {

        Pagination<SignTrancenterTimeVo> pagin = signTrancenterTimeDao.querySignTrancenterTimeVoPagination2(params, page,
                sorts);
        List<SignTrancenterTimeVo> listVo = pagin.getList();
        if (!CollectionUtils.isEmpty(listVo)) {
            for (SignTrancenterTimeVo vo : listVo) {
                String configCode = vo.getConfigCode();
                Map<String, Object> map = getOrgsBySignTrancenterTime(configCode);
                String str = (String) map.get("siteNames");
                vo.setSiteNames(str);
            }
        }

        return pagin;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public String getConfigCodeByCentreInsert(String configCode) {

        return signTrancenterTimeDao.getConfigCodeByCentreInsert(configCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public String getConfigNameByCentreInsert(String configName) {

        return signTrancenterTimeDao.getConfigNameByCentreInsert(configName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public SignTrancenterTimeVo preUpdateCentreData(Long id) throws BusinessException {

        SignTrancenterTimeVo signTrancenterTimeVo = new SignTrancenterTimeVo();
        try {
            SignTrancenterTimeEntity signTrancenterTimeEntity = signTrancenterTimeDao.getById(id);

            myEntity2Vo(signTrancenterTimeEntity, signTrancenterTimeVo);

            String configCode = signTrancenterTimeEntity.getConfigCode();
            // 查询出name
            Map<String, Object> map = getOrgsBySignTrancenterTime(configCode);
            String str = (String) map.get("siteNames");
            signTrancenterTimeVo.setSiteNames(str);
            // siteCodes

            List<SignTrancenterTimeReleVo> siteCodeList = (List<SignTrancenterTimeReleVo>) map.get("siteCodes");
            signTrancenterTimeVo.setSiteCodeList(siteCodeList);

        } catch (Exception e) {
            throw new BusinessException("封装修改前的数据出错");
        }

        return signTrancenterTimeVo;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public boolean batchStart(long[] ids) {
        for (long id : ids) {
            if (id != 0) {// 判断long 类型的id是否为空
                int flag = signTrancenterTimeDao.openBlFlagById(id);
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
                int flag = signTrancenterTimeDao.closeBlFlagById(id);
                if (flag < 0) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * VO转实体的方法
     *
     * @param SignTrancenterTimeVo
     * @param SignTrancenterTimeEntity
     */
    private void myVo2Entity(SignTrancenterTimeVo signTrancenterTimeVo,
            SignTrancenterTimeEntity signTrancenterTimeEntity) {
        signTrancenterTimeEntity.setConfigCode(signTrancenterTimeVo.getConfigCode());
        signTrancenterTimeEntity.setConfigName(signTrancenterTimeVo.getConfigName());
        signTrancenterTimeEntity.setBlFlag(signTrancenterTimeVo.getBlFlag());
        signTrancenterTimeEntity.setOneArrivalTimeS(signTrancenterTimeVo.getOneArrivalTimeS());
        signTrancenterTimeEntity.setOneArrivalTimeE(signTrancenterTimeVo.getOneArrivalTimeE());
        signTrancenterTimeEntity.setOneDispatchEndTime(signTrancenterTimeVo.getOneDispatchEndTime());
        signTrancenterTimeEntity.setOneDispatchMonthEndTime(signTrancenterTimeVo.getOneDispatchMonthEndTime());
        signTrancenterTimeEntity.setTwoArrivalTimeS(signTrancenterTimeVo.getTwoArrivalTimeS());
        signTrancenterTimeEntity.setTwoArrivalTimeE(signTrancenterTimeVo.getTwoArrivalTimeE());
        signTrancenterTimeEntity.setTwoDispatchEndTime(signTrancenterTimeVo.getTwoDispatchEndTime());
        signTrancenterTimeEntity.setTwoDispatchMonthEndTime(signTrancenterTimeVo.getTwoDispatchMonthEndTime());
        signTrancenterTimeEntity.setOneDispatchRate(signTrancenterTimeVo.getOneDispatchRate());
        signTrancenterTimeEntity.setTwoDispatchRate(signTrancenterTimeVo.getTwoDispatchRate());
        signTrancenterTimeEntity.setRemark(signTrancenterTimeVo.getRemark());

    }

    /**
     * 实体转VO
     */
    private void myEntity2Vo(SignTrancenterTimeEntity signTrancenterTimeEntity,
            SignTrancenterTimeVo signTrancenterTimeVo) {
        signTrancenterTimeVo.setId(signTrancenterTimeEntity.getId());
        signTrancenterTimeVo.setConfigCode(signTrancenterTimeEntity.getConfigCode());
        signTrancenterTimeVo.setConfigName(signTrancenterTimeEntity.getConfigName());
        signTrancenterTimeVo.setOneArrivalTimeS(signTrancenterTimeEntity.getOneArrivalTimeS());
        signTrancenterTimeVo.setOneArrivalTimeE(signTrancenterTimeEntity.getOneArrivalTimeE());
        signTrancenterTimeVo.setOneDispatchEndTime(signTrancenterTimeEntity.getOneDispatchEndTime());
        signTrancenterTimeVo.setOneDispatchMonthEndTime(signTrancenterTimeEntity.getOneDispatchMonthEndTime());
        signTrancenterTimeVo.setTwoArrivalTimeS(signTrancenterTimeEntity.getTwoArrivalTimeS());
        signTrancenterTimeVo.setTwoArrivalTimeE(signTrancenterTimeEntity.getTwoArrivalTimeE());
        signTrancenterTimeVo.setTwoDispatchEndTime(signTrancenterTimeEntity.getTwoDispatchEndTime());
        signTrancenterTimeVo.setTwoDispatchMonthEndTime(signTrancenterTimeEntity.getTwoDispatchMonthEndTime());

        signTrancenterTimeVo.setOneDispatchRate(signTrancenterTimeEntity.getOneDispatchRate());
        signTrancenterTimeVo.setTwoDispatchRate(signTrancenterTimeEntity.getTwoDispatchRate());
        signTrancenterTimeVo.setBlFlag(signTrancenterTimeEntity.getBlFlag());
        signTrancenterTimeVo.setRemark(signTrancenterTimeEntity.getRemark());
        signTrancenterTimeVo.setCreateUserCode(signTrancenterTimeEntity.getCreateUserCode());
        signTrancenterTimeVo.setCreateTime(signTrancenterTimeEntity.getCreateTime());
        signTrancenterTimeVo.setModifyTime(signTrancenterTimeEntity.getModifyTime());
        signTrancenterTimeVo.setModifyUserCode(signTrancenterTimeEntity.getModifyUserCode());
        signTrancenterTimeVo.setDelFlag(signTrancenterTimeEntity.getDelFlag());
    }

    @Override
    public List<BaseOrgEntity> getByConfigCodeAndSites(String code, List<BaseOrgEntity> baseOrgEntities) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("configCode", code);
        paramMap.put("list", baseOrgEntities);
        return signTrancenterTimeReleDao.getByConfigCodeAndSites(paramMap);
    }

    @Override
    public Map<String, Object> getOrgsBySignTrancenterTime(String configCode) {
        Map<String, Object> orgMap = new HashMap<String, Object>();
        List<BaseOrgEntity> orgList = signTrancenterTimeReleDao.getOrgByConfigCode(configCode);
        if (CollectionUtils.isNotEmpty(orgList)) {
            String orgNames = "";
            List<SignTrancenterTimeReleVo> siteCodeList = new ArrayList<SignTrancenterTimeReleVo>();
            for (BaseOrgEntity baseOrgEntity : orgList) {
                orgNames += "," + baseOrgEntity.getOrgName();
                SignTrancenterTimeReleVo vo = new SignTrancenterTimeReleVo();
                vo.setSiteCode(baseOrgEntity.getOrgCode());
                vo.setSiteName(baseOrgEntity.getOrgName());
                siteCodeList.add(vo);
            }
            orgMap.put("siteNames", orgNames.substring(1));
            orgMap.put("siteCodes", siteCodeList);
        }
        return orgMap;

    }
    // 批量删除
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public boolean batchDeleteCentreSendTimeByIdValue(long[] ids) {
        if (ids == null) {
            throw new BusinessException("传入ID列表为空!");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
       int flag= signTrancenterTimeDao.batchDeleteCentreSendTimeByIdValue(map);
       if(flag>0){
           return true;
       }
        return false;

    }

    @Override
    public int getConfigNameByCentreInsert2(Map<String, Object> map) {

        return signTrancenterTimeDao.getConfigNameByCentreInsert2(map);
    }

    /************************* my end ***************************/

}
