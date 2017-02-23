package com.ycgwl.rosefinch.module.basedev.server.services.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.framework.exception.BusinessException;
import com.ycgwl.framework.springmvc.service.IBaseService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignSiteTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignSiteTimeVo;

/**
 * ] Title: Description:门店时效设置service
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 下午1:26:56
 *
 */
public interface ISignSiteTimeService extends IBaseService<SignSiteTimeEntity, Long> {
    // 增
    int insertSignSiteTime(SignSiteTimeVo vo, String currentUser) throws BusinessException;

    // 删
    int deleteSignSiteTimeById(Long id);

    // 改
    int updateSignSiteTime(SignSiteTimeVo vo, String currentUser) throws BusinessException;

    // 分页查询
    Pagination<SignSiteTimeVo> getPage(Map<String, Object> params, Page page, Sort... sorts);

    // 查询时效编号是否存在
    String getConfigCodeByInsert(String configCode);

    // 查询时效名称是否存在
    String getConfigNameByInsert(String configName);

    // 查询时效名称是否存在
    int getConfigNameByInsert2(Map<String, Object> map);

    // 根据id 得到修改前的Vo实体
    SignSiteTimeVo preUpdateStoreData(Long id) throws BusinessException;

    // 批量禁用设置
    boolean batchStart(long ids[]);

    // 批量禁用设置
    boolean batchClose(long ids[]);

    // 组织
    List<BaseSiteEntity> getByConfigCodeAndSites(String code, List<BaseSiteEntity> baseSiteEntities);

    // 查询siteCodes和configName的map集合
    Map<String, Object> getSitesBySignSiteTime(String configCode);
    //批量删除
    boolean batchDeleteStoreSendTimeByIdValue(long ids[]);

    Set<String> getChildSites(String siteCode);

}
