package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseOrgEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignTrancenterTimeReleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignTrancenterTimeReleVo;

/**
 *
 * Title: Description:派件 转运 关联 dao
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 下午2:10:20
 *
 */
public interface ISignTrancenterTimeReleDao extends IBaseDao<SignTrancenterTimeReleEntity, Long> {
    public void deleteAllReles(String configCode);

    List<SignTrancenterTimeReleVo> findSignTrancenterTimeVoList(String configCode);

    public List<BaseOrgEntity> getByConfigCodeAndSites(Map<String, Object> paramMap);

    // 根据 configCode查询出names和sitecodes的map集合
    public List<BaseOrgEntity> getOrgByConfigCode(String configCode);
}
