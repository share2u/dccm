package cn.ncut.entity.system;

import java.io.Serializable;

public class QueryUser implements Serializable{
	/**
	 * 用户id
	 */
	private Integer uId;
	
	private Integer sex;
	/**
	 * 用户名
	 */
	private String name;
	
	/**
	 * 用户昵称
	 */
	private String userName;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	private String city;
	/**
	 * 个人打折比例
	 */
	private Double proportion;
	/**
	 * 会员类型
	 */
	private String categoryName;
	
	/**
	 * 用户组优惠比例categoryProportioncategoryProporation
	 */
	private Double categoryProportion;
	/**
	 * 储值账户余额    ---储值明细
	 */
	private Double remainMoney;
	/**
	 * 储值账户返点   ---储值明细
	 */
	private Double remainPoints;
	/**
	 * 钱包余额（预存）--预存明细
	 */
	private Double sumMoney;
	
	/**
	 * 积分（订单饭积分）---订单明细
	 */
	private Integer credit;

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}
	

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getCategoryProportion() {
		return categoryProportion;
	}

	public void setCategoryProportion(Double categoryProporation) {
		this.categoryProportion = categoryProporation;
	}

	public Double getRemainMoney() {
		return remainMoney;
	}

	public void setRemainMoney(Double remainMoney) {
		this.remainMoney = remainMoney;
	}

	public Double getRemainPoints() {
		return remainPoints;
	}

	public void setRemainPoints(Double remainPoints) {
		this.remainPoints = remainPoints;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return "QueryUser [uId=" + uId + ", name=" + name + ", userName="
				+ userName + ", phone=" + phone + ", proportion=" + proportion
				+ ", categoryname=" + categoryName + ", categoryProporation="
				+ categoryProportion + ", remainMoney=" + remainMoney
				+ ", remainPoints=" + remainPoints + ", sumMoney=" + sumMoney
				+ ", credit=" + credit + "]";
	}
	
}
