package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

/**
 *
 * @Title:T_GET_PACKAGE_TIME_RELE配置表Entity实体
 * @Description:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月1日  下午4:28
 *
 */
@Table(value="T_GET_PACKAGE_TIME_RELE",dynamicInsert=true,dynamicUpdate=true)
public class GetPackageTimeReleEntity implements Serializable{

    private static final long serialVersionUID = 4848842416800290945L;

    //ID
    @Id
    @Column("ID")
    private Long configId;

    //揽件时效编码
    @Column("CONFIG_CODE")
    private String configCode;

    //网点编码
    @Column("SITE_CODE")
    private String siteCode;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
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
