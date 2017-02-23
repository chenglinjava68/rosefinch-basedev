package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

import com.ycgwl.rosefinch.module.basedev.shared.entity.base.DataEntity;


public class BaseCustomerVo implements Serializable{
    private static final long serialVersionUID = 4785505160136525772L;
    private String ClientID;
    private String SerialNumber;
    private Integer ActionType;
    private DataEntity  Data;

    public DataEntity getData() {
        return Data;
    }
    public void setData(DataEntity data) {
        Data = data;
    }
    //  data类型的类
    public String getClientID() {
        return ClientID;
    }
    public void setClientID(String clientID) {
        ClientID = clientID;
    }
    public String getSerialNumber() {
        return SerialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }
    public Integer getActionType() {
        return ActionType;
    }
    public void setActionType(Integer actionType) {
        ActionType = actionType;
    }

}
