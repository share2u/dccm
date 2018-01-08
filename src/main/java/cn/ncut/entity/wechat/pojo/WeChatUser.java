package cn.ncut.entity.wechat.pojo;

import java.util.Date;

public class WeChatUser {

	/**
	 * 用户编号，自增
	 */
	Integer uId;
	/**
	 * openid
	 */
	String openId;
	/**
	 * 姓名
	 */
	String name;
	/**
	 * 用户名，昵称的意思？
	 */
	String userName;
	
	/**
	 * 用户头像的链接
	 */
	String  userImg;
	
	/**
	 *  性别（1是男性，2是女性，0是未知）
	 */
	Integer sex;
	
	 /**
	 * 生日
	 */
	String birthday;
	/**
	 * 手机号
	 */
	String  phone;
	
	 /**
	 * 身份证号
	 */
	String idCode;
	/**
	 * 根据手机号获得
	 */
	String city;
	/**
	 * 初诊时间
	 */
	String firstDate;
	
	

	/**
	 * 0代表微信端，1代表客服录入',
	 */
	Integer  source;
	
	/**
	 * 
	 */
	Integer userCategory;
	
	/**
	 * 病种
	 * */
	String illness;
	
	/**
	 * 科室
	 * */
	String section;
	
	/**
	 * 邮箱
	 * */
	String email;
	
	/**
	 * 打折比例
	 */
	Double proportion;
	
	/**
	 * 关注时间
	 */
	String attentionTime;
	
	
	/**
	 * 详细地址
	 */
	String address;

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	public String getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(Integer userCategory) {
		this.userCategory = userCategory;
	}

	public String getIllness() {
		return illness;
	}

	public void setIllness(String illness) {
		this.illness = illness;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	public String getAttentionTime() {
		return attentionTime;
	}

	public void setAttentionTime(String attentionTime) {
		this.attentionTime = attentionTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "WeChatUser [uId=" + uId + ", openId=" + openId + ", name="
				+ name + ", userName=" + userName + ", userImg=" + userImg
				+ ", sex=" + sex + ", birthday=" + birthday + ", phone="
				+ phone + ", idCode=" + idCode + ", city=" + city
				+ ", firstDate=" + firstDate + ", source=" + source
				+ ", userCategory=" + userCategory + ", illness=" + illness
				+ ", section=" + section + ", email=" + email + ", proportion="
				+ proportion + ", attentionTime=" + attentionTime
				+ ", address=" + address + "]";
	}
	
}
