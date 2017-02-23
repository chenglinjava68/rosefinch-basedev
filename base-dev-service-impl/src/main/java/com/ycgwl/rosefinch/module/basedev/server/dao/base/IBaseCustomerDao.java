package com.ycgwl.rosefinch.module.basedev.server.dao.base;

import java.util.List;

import org.mybatis.spring.dao.IBaseDao;
import org.springframework.stereotype.Repository;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseCustomerEntity;
/**
 *
 * Title:
 * Description:客户Dao
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年10月25日  下午3:11:02
 *
 */
@Repository
public interface IBaseCustomerDao extends IBaseDao<BaseCustomerEntity, Long>{
            int delteAllBaseCustomer();
            //批量删除客户表数据
            int  batchDeleteCustomer();
             //批量插入客户表数据
            int batchInsertCustomer(List<BaseCustomerEntity> lists);
}
