package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;
import org.mybatis.spring.support.Page;
import org.mybatis.spring.support.Pagination;
import org.mybatis.spring.support.Sort;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignTrancenterTimeEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignTrancenterTimeVo;

/**
 *
 * Title: Description: 派件 时效 分拨转运 Dao
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 下午2:00:38
 *
 */
public interface ISignTrancenterTimeDao extends IBaseDao<SignTrancenterTimeEntity, Long> {

    Pagination<SignTrancenterTimeVo> querySignTrancenterTimeVoPagination(Map<String, Object> params, Page page,
            Sort... sorts);

    Pagination<SignTrancenterTimeVo> querySignTrancenterTimeVoPagination2(Map<String, Object> params, Page page,
            Sort... sorts);

    String getConfigCodeByCentreInsert(String configCode);

    String getConfigNameByCentreInsert(String configName);


    int getConfigNameByCentreInsert2(Map<String, Object> params);

    // 设置启用
    int openBlFlagById(long id);

    // 设置禁用
    int closeBlFlagById(long id);
    //逻辑删除
    int  logicalDeleteById(long id);
    //批量删除
    int batchDeleteCentreSendTimeByIdValue(Map<String,Object> map);
}
