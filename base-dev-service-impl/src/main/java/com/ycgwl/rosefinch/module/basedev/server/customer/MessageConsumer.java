package com.ycgwl.rosefinch.module.basedev.server.customer;

import java.io.UnsupportedEncodingException;

//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.ycgwl.kafka.server.constant.LogConstant;
import com.ycgwl.kafka.server.logger.Logger;
import com.ycgwl.kafka.server.logger.LoggerFactory;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseCustomerService;

/**
 *
 * Title:
 * Description:客户资料同步
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月29日  上午10:06:14
 *
 */
public class MessageConsumer /*implements MessageListener*/ {/*
    @Autowired
    private IBaseCustomerService baseCustomerService;
    *//**日志*//*
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public void onMessage(Message message) {
		String str = null;
		try {
			byte[] bs = message.getBody();
			str =  new String(bs,"UTF-8");
			log.info("客户资料同步入参为："+str);

			baseCustomerService.synCustomerData(str);

		} catch (UnsupportedEncodingException e) {

			log.error("客户资料同步出现异常:"+str,LogConstant.LOG_TYPE_DATA_EXCEPTION,e);
		} catch (Exception e) {

			log.error("客户资料同步出现异常:"+str,LogConstant.LOG_TYPE_DATA_EXCEPTION,e);
		}
	}


*/}
