/*
 * Copyright (C) 2016 YcExpress.
 * All rights reserved.
 */
package com.ycgwl.rosefinch.module.basedev.server.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.ycgwl.framework.quartz.jobgrid.GridJob;
import com.ycgwl.framework.quartz.jobgrid.impl.GridJobListener;

/**
* @Title: 
* @Description: 
* @Company: 远成快运 
* @author guoh.mao
* @date 2016年12月12日 下午2:10:39
*/
public class SyncActiveDirectoryJob extends GridJob {

	@Override
	protected void doExecute(JobExecutionContext context)
			throws JobExecutionException {
		ApplicationContext acontext = (ApplicationContext) context
				.get(GridJobListener.APPLICATION_CONTEXT);
		
		SyncActiveDirectoryBean syncActiveDirectoryBean = (SyncActiveDirectoryBean) acontext
				.getBean("syncActiveDirectoryBean");
		
		// 执行数据同步
		syncActiveDirectoryBean.execute();
	}

}
