package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

public class SignSiteTimeReleVo implements Serializable {

    private static final long serialVersionUID = -2511363123558092517L;
    private String configCode;
    private String siteCode;
    private String siteName;

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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

}
