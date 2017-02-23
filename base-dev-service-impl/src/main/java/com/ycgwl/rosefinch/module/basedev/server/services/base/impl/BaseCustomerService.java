package com.ycgwl.rosefinch.module.basedev.server.services.base.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
//import com.ycgwl.rosefinch.module.basedev.server.dao.base.ICustomerDao;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseCustomerService;
//import com.ycgwl.rosefinch.module.basedev.shared.entity.base.CustomerEntity;
import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseCustomerVo;

/**
 *
 * Title: Description:客户服务Service
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年10月25日 下午3:13:29
 *
 */
@Service("baseCustomerService")
public class BaseCustomerService /*implements IBaseCustomerService*/ {/*
    @Autowired
    private ICustomerDao customerDaoImp;
    @Override
    public void synCustomerData(String str) throws Exception {
     BaseCustomerVo vo=JSON.parseObject(str, BaseCustomerVo.class);
   // 根据ActionType的值做相应的操作
     Integer ActionType= vo.getActionType();
     CustomerEntity data=vo.getData();
//对实体CustomerEntity的空校验
     //判断类型增删改查
     if(ActionType==1){//新增
         addCustomerEntity(data);
     }
     if(ActionType==2){//更新
         updateCustomerEntity(data);
     }
     if(ActionType==3){//删除
         delCustomerEntity(data);
     }

    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int addCustomerEntity(CustomerEntity customerEntity) {

        return customerDaoImp.addCustomerEntity(customerEntity);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int delCustomerEntity(CustomerEntity customerEntity) {
        // TODO
        return customerDaoImp.delCustomerEntity(customerEntity);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class,
            RuntimeException.class })
    public int updateCustomerEntity(CustomerEntity customerEntity) {
        // TODO
        return customerDaoImp.updateCustomerEntity(customerEntity);
    }
   public CustomerEntity  isNullSet(CustomerEntity customerEntity){
//       if(StringUtils.isEmpty ())
       if(StringUtils.isEmpty (customerEntity.getCustomerCode())){
           customerEntity.setCustomerCode("111");
       }


       customerEntity.setCustomerName("000");
       customerEntity.setCustomerFullName("000");
       customerEntity.setCustomerBalanceMode("000");
       customerEntity.setCustomerLinkman("00");
       customerEntity.setCustomerPhone1("00");
       customerEntity.setCustomerPhone2("222");
       customerEntity.setCustomerFax("55");
       customerEntity.setCustomerAddress("999");
       customerEntity.setCusSendAddress("5515");
       customerEntity.setOwnerSiteCode("555");
       customerEntity.setCustomerRebate(0.3);
       customerEntity.setBalancePassword("6225");
       customerEntity.setCustomerType("52");
       customerEntity.setOperationEmployee("44");
       customerEntity.setEmployeeCode("666");
       customerEntity.setPostCode("555");
       customerEntity.setCustomerId("555");
       customerEntity.setInterfacePassword("555");
       customerEntity.setProv("555");
       customerEntity.setCity("666");
       customerEntity.setCounty("6555");
       customerEntity.setUrl("555");
       customerEntity.setEmail("555");
       customerEntity.setRemark("5545");
       customerEntity.setBank("999");
       customerEntity.setAccount("6266");
       customerEntity.setHolder("999");
       customerEntity.setIdCode("666");
       customerEntity.setBankAddress("151");
       customerEntity.setMarketMan("665");
       customerEntity.setCustomerSiteType("555");
       customerEntity.setCreateDate(new Date());
       customerEntity.setCreateMan("666");
       customerEntity.setCreateCode("3663");
       customerEntity.setModifyDate(new Date());
       customerEntity.setModifyMan("525252");
       customerEntity.setModifyCode("55");
       customerEntity.setModifySite("55");
       customerEntity.setCustomerPhone("555");
       customerEntity.setCustomerRang("dd");

       customerEntity.setCustomerRebate(0.3);
       customerEntity.setCustomerOwnerType("555");
       customerEntity.setCustomerLinkman2("55");
       customerEntity.setSignstatementsflag("66565");
       customerEntity.setRegionalcompanies("55");
       customerEntity.setBranch("55");
       customerEntity.setMembershipchannels("555");
       customerEntity.setMembervip("52552");
       BigDecimal aDouble =new BigDecimal(1.22);
       customerEntity.setTotaCost(aDouble);
       customerEntity.setBlOpen(1);
       customerEntity.setIndustryDetails("555");
       customerEntity.setIndustryClass("2525");
       customerEntity.setBlBagCustomer(1);
       customerEntity.setRebateNumber(1);
       customerEntity.setBlRebateNumber(0);
       customerEntity.setBlMoneyOpen(null);
       customerEntity.setCashCustomer(0);


    return customerEntity;

    }

*/}
