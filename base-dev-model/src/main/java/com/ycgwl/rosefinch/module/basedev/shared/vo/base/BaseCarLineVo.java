package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
*
* @Title:车线VO
* @Description:
* @Company: 远成快运
* @author yzn
*
*
*/
public class BaseCarLineVo implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = 8779665936356446745L;
	private Long id;
	private String lineName;//车线名称
	private String lineCode;//车线编码
	private Integer lineType;//车线类型
	private String beginRegionCode;//起始行政区域
	private String endRegionCode;//到达行政区域
	private String beginSiteCode;//起始网点
	private String endSiteCode;//目的网点
	private String ownerOrg;//所属机构
	private String loaderDetial;//装载范围明细
	private Integer totalStayTime;//总停留时间
	private Integer totalMileage;//总路程
	private Integer siteCount;//途径站总数
	private Integer totalTime;//总时间
	private String createUserCode;//创建人
	private Date createTime;//创建时间
	private String modifyUserCode;//修改人
	private Date modifyTime;//修改时间
	private String remark;//备注
	private Integer delFlag;//是否删除
	private Integer blFlag;//启用标识
	private List<Long> idList;
	//车线明细记录
    private List<BaseCarLineDetailVo> detailList;

	public Integer getBlFlag() {
		return blFlag;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}
	public List<BaseCarLineDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<BaseCarLineDetailVo> detailList) {
		this.detailList = detailList;
	}
	public List<Long> getIdList() {
		return idList;
	}
	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	public Integer getLineType() {
		return lineType;
	}
	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
	public String getBeginRegionCode() {
		return beginRegionCode;
	}
	public void setBeginRegionCode(String beginRegionCode) {
		this.beginRegionCode = beginRegionCode;
	}
	public String getEndRegionCode() {
		return endRegionCode;
	}
	public void setEndRegionCode(String endRegionCode) {
		this.endRegionCode = endRegionCode;
	}
	public String getBeginSiteCode() {
		return beginSiteCode;
	}
	public void setBeginSiteCode(String beginSiteCode) {
		this.beginSiteCode = beginSiteCode;
	}
	public String getEndSiteCode() {
		return endSiteCode;
	}
	public void setEndSiteCode(String endSiteCode) {
		this.endSiteCode = endSiteCode;
	}
	public String getOwnerOrg() {
		return ownerOrg;
	}
	public void setOwnerOrg(String ownerOrg) {
		this.ownerOrg = ownerOrg;
	}
	public String getLoaderDetial() {
		return loaderDetial;
	}
	public void setLoaderDetial(String loaderDetial) {
		this.loaderDetial = loaderDetial;
	}
	public Integer getTotalStayTime() {
		return totalStayTime;
	}
	public void setTotalStayTime(Integer totalStayTime) {
		this.totalStayTime = totalStayTime;
	}
	public Integer getTotalMileage() {
		return totalMileage;
	}
	public void setTotalMileage(Integer totalMileage) {
		this.totalMileage = totalMileage;
	}
	public Integer getSiteCount() {
		return siteCount;
	}
	public void setSiteCount(Integer siteCount) {
		this.siteCount = siteCount;
	}
	public Integer getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}
