package cn.ncut.entity.wechat.pojo;

import java.math.BigDecimal;

public class WeChatStoredDetail {
	private String storedDetailId;
	private Integer uId;
	private Integer storedCategoryId;
	private String storeId;
	private String staffId;
	private String createTime;
	private BigDecimal weChatPayMoney;
	private BigDecimal aliPayMoney;
	private BigDecimal bankPayMoney;
	private BigDecimal cashPayMoney;
	private String remark;
	private Integer status;
	public String getStoredDetailId() {
		return storedDetailId;
	}
	public void setStoredDetailId(String storedDetailId) {
		this.storedDetailId = storedDetailId;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public Integer getStoredCategoryId() {
		return storedCategoryId;
	}
	public void setStoredCategoryId(Integer storedCategoryId) {
		this.storedCategoryId = storedCategoryId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getWeChatPayMoney() {
		return weChatPayMoney;
	}
	public void setWeChatPayMoney(BigDecimal weChatPayMoney) {
		this.weChatPayMoney = weChatPayMoney;
	}
	public BigDecimal getAliPayMoney() {
		return aliPayMoney;
	}
	public void setAliPayMoney(BigDecimal aliPayMoney) {
		this.aliPayMoney = aliPayMoney;
	}
	public BigDecimal getBankPayMoney() {
		return bankPayMoney;
	}
	public void setBankPayMoney(BigDecimal bankPayMoney) {
		this.bankPayMoney = bankPayMoney;
	}
	public BigDecimal getCashPayMoney() {
		return cashPayMoney;
	}
	public void setCashPayMoney(BigDecimal cashPayMoney) {
		this.cashPayMoney = cashPayMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "WeChatStoredDetail [storedDetailId=" + storedDetailId
				+ ", uId=" + uId + ", storedCategoryId=" + storedCategoryId
				+ ", storeId=" + storeId + ", staffId=" + staffId
				+ ", createTime=" + createTime + ", weChatPayMoney="
				+ weChatPayMoney + ", aliPayMoney=" + aliPayMoney
				+ ", bankPayMoney=" + bankPayMoney + ", cashPayMoney="
				+ cashPayMoney + ", remark=" + remark + ", status=" + status
				+ "]";
	}
	
	
}
