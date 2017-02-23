package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;

/**
 *
 * Title: Description:派件/签收时效设置表（转运、分拨）实体
 *
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年11月7日 上午11:12:17
 *
 */
@Table(value = "T_SIGN_TRANCENTER_TIME")
public class SignTrancenterTimeEntity extends BizBaseEntity implements Serializable {

    private static final long serialVersionUID = 3084602074620968486L;
    // ID
    @Id
    @Column("ID")
    private Long id;
    // 时效编号
    @Column("CONFIG_CODE")
    private String configCode;
    // 时效名称
    @Column("CONFIG_NAME")
    private String configName;
    // 一派到达开始时间
    @Column("ONE_ARRIVAL_TIME_S")
    private String oneArrivalTimeS;
    // 一派到达结束时间
    @Column("ONE_ARRIVAL_TIME_E")
    private String oneArrivalTimeE;
    // 一派日常合格签收时间日常合格签收时间
    @Column("ONE_DISPATCH_END_TIME")
    private String oneDispatchEndTime;
    // 一派月度合格签收时间
    @Column("ONE_DISPATCH_MONTH_END_TIME")
    private String oneDispatchMonthEndTime;
    // 到达2开始时间
    @Column("TWO_ARRIVAL_TIME_S")
    private String twoArrivalTimeS;
    // 到达2结束时间
    @Column("TWO_ARRIVAL_TIME_E")
    private String twoArrivalTimeE;
    // 到达2日常合格签收时间段日常合格签收时间
    @Column("TWO_DISPATCH_END_TIME")
    private String twoDispatchEndTime;
    //二派月度合格签收时间
    @Column("TWO_DISPATCH_MONTH_END_TIME")
    private String twoDispatchMonthEndTime;

    // 一派比率
    @Column("ONE_DISPATCH_RATE")
    private Double oneDispatchRate;
    // 二派比率
    @Column("TWO_DISPATCH_RATE")
    private Double twoDispatchRate;
    // 是否启用
    @Column("BL_FLAG")
    private Integer blFlag;
    // 备注
    @Column("REMARK")
    private String remark;


    public String getTwoDispatchMonthEndTime() {
        return twoDispatchMonthEndTime;
    }

    public void setTwoDispatchMonthEndTime(String twoDispatchMonthEndTime) {
        this.twoDispatchMonthEndTime = twoDispatchMonthEndTime;
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

}
