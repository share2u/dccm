package cn.ncut.entity.wechat.pojo;

import java.math.BigDecimal;

public class WeChatStored {
	private String phone;
	private Integer uId;
	private Integer cardId;
	private BigDecimal remainMoney;
	private BigDecimal remainPoints;
	private String name;
	private Integer status;
	private String passWord;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public BigDecimal getRemainMoney() {
		return remainMoney;
	}
	public void setRemainMoney(BigDecimal remainMoney) {
		this.remainMoney = remainMoney;
	}
	public BigDecimal getRemainPoints() {
		return remainPoints;
	}
	public void setRemainPoints(BigDecimal remainPoints) {
		this.remainPoints = remainPoints;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Override
	public String toString() {
		return "WeChatStored [phone=" + phone + ", uId=" + uId + ", cardId="
				+ cardId + ", remainMoney=" + remainMoney + ", remainPoints="
				+ remainPoints + ", name=" + name + ", status=" + status
				+ ", passWord=" + passWord + "]";
	}
	
	
	
}
