package cn.ncut.entity.wechat.pojo;

import java.math.BigDecimal;

/**
 * 用户储值卡账户
 * */
public class WeChatUserStoredCard {
	private String phone;
	private Integer uId;
	private Integer cardId;
	private BigDecimal remainMoney;
	private BigDecimal remainPoints;
	private String name;
	private Integer status;
	private String password;
	private WeChatUser user;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public WeChatUser getUser() {
		return user;
	}
	public void setUser(WeChatUser user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "WeChatUserStoredCard [phone=" + phone + ", uId=" + uId
				+ ", cardId=" + cardId + ", remainMoney=" + remainMoney
				+ ", remainPoints=" + remainPoints + ", name=" + name
				+ ", status=" + status + ", password=" + password + ", user="
				+ user + "]";
	}
}
