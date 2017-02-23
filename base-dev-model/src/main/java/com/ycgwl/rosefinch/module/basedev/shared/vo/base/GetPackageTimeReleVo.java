package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

/**
 *
 * Title:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月16日 上午10:33:50
 *
 */
public class GetPackageTimeReleVo implements Serializable {

    private static final long serialVersionUID = 4196325153318121449L;
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
