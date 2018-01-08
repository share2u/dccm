package cn.ncut.entity.wechat.pojo;

public class WeChatStore {
	String storeId;
	String storeName;
	String address;
	String trafficMessage;
	String workTime;
	String telephone;
	String mId;
	String staffId;
	String storeSmallImg;
	String storeBigImg;
	Integer status;
	String bussinPattern;
	String cityId;
	String isQualification;
	String storeNumber;
	String storeInfo;
	
	public String getStoreInfo() {
		return storeInfo;
	}
	public void setStoreInfo(String storeInfo) {
		this.storeInfo = storeInfo;
	}
	public WeChatStore() {
		super();
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTrafficMessage() {
		return trafficMessage;
	}
	public void setTrafficMessage(String trafficMessage) {
		this.trafficMessage = trafficMessage;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStoreSmallImg() {
		return storeSmallImg;
	}
	public void setStoreSmallImg(String storeSmallImg) {
		this.storeSmallImg = storeSmallImg;
	}
	public String getStoreBigImg() {
		return storeBigImg;
	}
	public void setStoreBigImg(String storeBigImg) {
		this.storeBigImg = storeBigImg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBussinPattern() {
		return bussinPattern;
	}
	public void setBussinPattern(String bussinPattern) {
		this.bussinPattern = bussinPattern;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getIsQualification() {
		return isQualification;
	}
	public void setIsQualification(String isQualification) {
		this.isQualification = isQualification;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	@Override
	public String toString() {
		return "WeChatStore [storeId=" + storeId + ", storeName=" + storeName
				+ ", address=" + address + ", trafficMessage=" + trafficMessage
				+ ", workTime=" + workTime + ", telephone=" + telephone
				+ ", mId=" + mId + ", staffId=" + staffId + ", storeSmallImg="
				+ storeSmallImg + ", storeBigImg=" + storeBigImg + ", status="
				+ status + ", bussinPattern=" + bussinPattern + ", cityId="
				+ cityId + ", isQualification=" + isQualification
				+ ", storeNumber=" + storeNumber + "]";
	}
	
	
	
	
}
