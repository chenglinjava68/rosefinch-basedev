package com.test.dubbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ycgwl.cache.CacheManager;
import com.ycgwl.cache.base.ICache;
import com.ycgwl.rosefinch.module.basedev.shared.constant.base.BaseCacheConstant;
/**
* 
* @Title: 
* @Description: 刷新缓存
* @Company: 远成快运 
* @author xsh
 * @param <V>
* @date 2016-7-19 下午7:34:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class RefreshBaseCacheTest {
	@Test
	public void refreshCacheTest(){
		String cacheUUID = BaseCacheConstant.PRODUCT_CATCHE_UUID;
		// 账户缓存
		ICache<Object, Object> cache = CacheManager.getInstance().getCache(cacheUUID);
		cache.invalid();
	}

}
