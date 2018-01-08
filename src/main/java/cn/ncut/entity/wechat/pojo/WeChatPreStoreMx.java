package cn.ncut.entity.wechat.pojo;

/**
 * 预存明细
 * */
public class WeChatPreStoreMx {
	private String preStoreMxId;
	private String preStoreId;
	private Integer uId;
	private String phone;
	private String userName;
	private String remitNo;	// 汇款单号
	private String bank;
	private String address;
	private Double preStoreMoney;
	private String money;	// 作为钱数的显示,Double精度不能显示到小数点后2位
	private String staffId;
	private String createTime;
	private Integer type;	// 0:收入即电汇,2:支出即线下支付订单,3:退款(取消订单与退还钱包(预存)余额)
	public String getPreStoreMxId() {
		return preStoreMxId;
	}
	public void setPreStoreMxId(String preStoreMxId) {
		this.preStoreMxId = preStoreMxId;
	}
	public String getPreStoreId() {
		return preStoreId;
	}
	public void setPreStoreId(String preStoreId) {
		this.preStoreId = preStoreId;
	}
	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRemitNo() {
		return remitNo;
	}
	public void setRemitNo(String remitNo) {
		this.remitNo = remitNo;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getPreStoreMoney() {
		return preStoreMoney;
	}
	public void setPreStoreMoney(Double preStoreMoney) {
		this.preStoreMoney = preStoreMoney;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
}
