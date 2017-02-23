package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignTrancenterTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignTrancenterTimeVo;

/**
 *
 * Title: Description: 派件时效转运Service
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 下午1:55:37
 *
 */
public interface ISignTrancenterTimeService extends IBaseService<SignTrancenterTimeEntity, Long> {
    // 增
    int insertSignTrancenterTime(SignTrancenterTimeVo vo, String currentUser) throws BusinessException;

    // 删
    int deleteSignTrancenterTimeById(Long id);

    // 改
    int updateSignTrancenterTime(SignTrancenterTimeVo vo, String currentUser) throws BusinessException;

    // 分页查询
    Pagination<SignTrancenterTimeVo> getPage(Map<String, Object> params, Page page, Sort... sorts);

    // 查询时效编号是否存在
    String getConfigCodeByCentreInsert(String configCode);

    // 查询时效名称是否存在
    String getConfigNameByCentreInsert(String configName);
    // 查询时效名称是否存在
    int getConfigNameByCentreInsert2(Map<String, Object> map);

    // 根据id 得到修改前的Vo实体
    SignTrancenterTimeVo preUpdateCentreData(Long id) throws BusinessException;

    // 批量启用设置
    boolean batchStart(long ids[]);

    // 批量禁用设置
    boolean batchClose(long ids[]);

    // 组织
    List<BaseOrgEntity> getByConfigCodeAndSites(String code, List<BaseOrgEntity> baseOrgEntities);

    // 查询siteCodes和configName的map集合
    Map<String, Object> getOrgsBySignTrancenterTime(String configCode);
    //批量删除
    boolean batchDeleteCentreSendTimeByIdValue(long ids[]);
}
