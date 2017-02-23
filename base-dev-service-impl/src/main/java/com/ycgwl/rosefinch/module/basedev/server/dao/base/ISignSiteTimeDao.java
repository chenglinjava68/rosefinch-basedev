package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignSiteTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignSiteTimeVo;

/**
 *
 * Title: Description:
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 下午1:47:28
 *
 */
public interface ISignSiteTimeDao extends IBaseDao<SignSiteTimeEntity, Long> {

    Pagination<SignSiteTimeVo> querySignSiteTimeVoPagination(Map<String, Object> params, Page page, Sort... sorts);
    Pagination<SignSiteTimeVo> querySignSiteTimeVoPagination2(Map<String, Object> params, Page page, Sort... sorts);

    String getConfigCodeByInsert(String configCode);

    String getConfigNameByInsert(String configName);

    int  getConfigNameByInsert2(Map<String, Object> params);

    // 设置启用
    int openBlFlagById(long id);

    // 设置禁用
    int closeBlFlagById(long id);
    //逻辑删除
    int  logicalDeleteById(long id);
    //批量删除
    int batchDeleteStoreSendTimeByIdValue(Map<String,Object> map);
}
