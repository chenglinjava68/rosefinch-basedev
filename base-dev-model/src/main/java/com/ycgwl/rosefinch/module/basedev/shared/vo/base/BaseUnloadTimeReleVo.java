package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

public class BaseUnloadTimeReleVo implements Serializable {

    /**
	 *
	 */
	private static final long serialVersionUID = 6552247940905564033L;
	private String configCode;
    private String siteCode;
    private String siteName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

