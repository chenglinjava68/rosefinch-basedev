package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.dao.IBaseDao;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseSiteEntity;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.SignSiteTimeReleEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.SignSiteTimeReleVo;

/**
 *
 * Title:
 * Description:
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日  下午1:46:32
 *
 */
public interface ISignSiteTimeReleDao extends IBaseDao<SignSiteTimeReleEntity, Long> {
    public void deleteAllReles(String configCode);
    List<SignSiteTimeReleVo> findSignSiteTimeReleVoList(String configCode);
    public List<BaseSiteEntity> getByConfigCodeAndSites(Map<String, Object> paramMap);
    //根据 configCode查询出names和sitecodes的map集合
    public List<BaseSiteEntity> getOrgByConfigCode(String configCode);
}
