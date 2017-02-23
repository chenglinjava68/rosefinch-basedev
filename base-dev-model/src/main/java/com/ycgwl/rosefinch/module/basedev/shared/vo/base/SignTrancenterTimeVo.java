package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SignTrancenterTimeVo implements Serializable {

    private static final long serialVersionUID = -6359594384963636988L;
    // ID
    private Long id;
    // 时效编号
    private String configCode;
    // 时效名称
    private String configName;
    // 到达1开始时间
    private String oneArrivalTimeS;
    // 到达1结束时间
    private String oneArrivalTimeE;
    // 到达1日常合格签收时间日常合格签收时间
    private String oneDispatchEndTime;
    // 一派月度合格签收时间
    private String oneDispatchMonthEndTime;
    // 到达2开始时间
    private String twoArrivalTimeS;
    // 到达2结束时间
    private String twoArrivalTimeE;
    // 到达2日常合格签收时间段日常合格签收时间
    private String twoDispatchEndTime;
    //二派月度合格签收时间
    private String twoDispatchMonthEndTime;

    // 一派比率
    private Double oneDispatchRate;
    // 二派比率
    private Double twoDispatchRate;
    // 是否启用
    private Integer blFlag;
    // 备注
    private String remark;
    // 创建人
    private String createUserCode;
    // 修改人
    private String modifyUserCode;
    // 修改时间
    private Date createTime;
    // 修改时间
    private Date modifyTime;
    // 状态
    private Integer delFlag;
    // 关联表
    private List<SignTrancenterTimeReleVo> siteCodeList;

    private String siteNames;

    private String siteCodes;




    public String getTwoDispatchMonthEndTime() {
        return twoDispatchMonthEndTime;
    }

    public void setTwoDispatchMonthEndTime(String twoDispatchMonthEndTime) {
        this.twoDispatchMonthEndTime = twoDispatchMonthEndTime;
    }

    public List<SignTrancenterTimeReleVo> getSiteCodeList() {
        return siteCodeList;
    }

    public void setSiteCodeList(List<SignTrancenterTimeReleVo> siteCodeList) {
        this.siteCodeList = siteCodeList;
    }

    public String getSiteNames() {
        return siteNames;
    }

    public void setSiteNames(String siteNames) {
        this.siteNames = siteNames;
    }

    public String getSiteCodes() {
        return siteCodes;
    }

    public void setSiteCodes(String siteCodes) {
        this.siteCodes = siteCodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getOneArrivalTimeS() {
        return oneArrivalTimeS;
    }

    public void setOneArrivalTimeS(String oneArrivalTimeS) {
        this.oneArrivalTimeS = oneArrivalTimeS;
    }

    public String getOneArrivalTimeE() {
        return oneArrivalTimeE;
    }

    public void setOneArrivalTimeE(String oneArrivalTimeE) {
        this.oneArrivalTimeE = oneArrivalTimeE;
    }

    public String getOneDispatchEndTime() {
        return oneDispatchEndTime;
    }

    public void setOneDispatchEndTime(String oneDispatchEndTime) {
        this.oneDispatchEndTime = oneDispatchEndTime;
    }

    public String getOneDispatchMonthEndTime() {
        return oneDispatchMonthEndTime;
    }

    public void setOneDispatchMonthEndTime(String oneDispatchMonthEndTime) {
        this.oneDispatchMonthEndTime = oneDispatchMonthEndTime;
    }

    public String getTwoArrivalTimeS() {
        return twoArrivalTimeS;
    }

    public void setTwoArrivalTimeS(String twoArrivalTimeS) {
        this.twoArrivalTimeS = twoArrivalTimeS;
    }

    public String getTwoArrivalTimeE() {
        return twoArrivalTimeE;
    }

    public void setTwoArrivalTimeE(String twoArrivalTimeE) {
        this.twoArrivalTimeE = twoArrivalTimeE;
    }

    public String getTwoDispatchEndTime() {
        return twoDispatchEndTime;
    }

    public void setTwoDispatchEndTime(String twoDispatchEndTime) {
        this.twoDispatchEndTime = twoDispatchEndTime;
    }

    public Double getOneDispatchRate() {
        return oneDispatchRate;
    }

    public void setOneDispatchRate(Double oneDispatchRate) {
        this.oneDispatchRate = oneDispatchRate;
    }

    public Double getTwoDispatchRate() {
        return twoDispatchRate;
    }

    public void setTwoDispatchRate(Double twoDispatchRate) {
        this.twoDispatchRate = twoDispatchRate;
    }

    public Integer getBlFlag() {
        return blFlag;
    }

    public void setBlFlag(Integer blFlag) {
        this.blFlag = blFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

}
