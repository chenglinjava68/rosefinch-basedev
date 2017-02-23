package com.ycgwl.rosefinch.module.basedev.server.interceptor.base;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ycgwl.framework.springmvc.util.SecurityUtil;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;



/**
 * 验证类
 * 
 */

public class Validator implements Serializable {


    private static final long       serialVersionUID = -1465368356350438221L;

    private static class SingletonHolder {
        private static Validator alpValidator = new Validator();
    }

    public static Validator getInstance() {
        return SingletonHolder.alpValidator;
    }
    
    public BaseAppInfoEntity validateAppkey(String appkey,IBaseRedisCacheService baseRedisCacheService) throws Exception{
    	
    	BaseAppInfoEntity basicAppInfo = baseRedisCacheService.getBaseAppInfoEntityFromCache(appkey);
    	if(basicAppInfo==null || basicAppInfo.getAppId()==null){
    		return null;
    	}
    	
    	return basicAppInfo;
    }



    /**
     * 摘要验证
     * 
     * @param src 源
     * @param digest 摘要
     * @return
     */
    public boolean validateDigest(String src, String digest,String appkey,String appSecret) {
        if (StringUtils.isEmpty(src) || StringUtils.isEmpty(digest)) {
            return false;
        }
        String digestTemp = SecurityUtil.getDigest(src + appkey + appSecret);
        if (digest.trim().equals(digestTemp.trim())) {
            return true;
        }
        return false;
    }
    
    public boolean validateTimestamp(String timestamp,String expiredTime) {
        if (StringUtils.isBlank(timestamp)) {
            return false;
        }
        long now = new Date().getTime();
        long time;
        long expiredTimeLong;
        try {
            time = Long.parseLong(timestamp);
            expiredTimeLong = Long.parseLong(expiredTime);
        } catch (Exception e) {
            return false;
        }
        if (time - now >= expiredTimeLong * 1000 * 60
                || now - time >= expiredTimeLong * 1000 * 60) {
            return false;
        }
        return true;
    }

}
