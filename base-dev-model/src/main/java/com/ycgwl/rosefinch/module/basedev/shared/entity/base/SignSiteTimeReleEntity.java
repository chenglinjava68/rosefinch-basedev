package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

/**
 *
 * Title: Description:派件/签收时效设置关联表（门店）实体
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 上午11:06:50
 *
 */
@Table(value = "T_SIGN_SITE_TIME_RELE")
public class SignSiteTimeReleEntity implements Serializable {

    private static final long serialVersionUID = 464669039688876284L;
    // ID
    @Id
    @Column("ID")
    private Long rId;
    // 卸车时效编码
    @Column("CONFIG_CODE")
    private String configCode;
    // 网点编码
    @Column("SITE_CODE")
    private String siteCode;

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

}
