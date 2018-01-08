package cn.ncut.entity.wechat.pojo;

public class WeChatPreStore {
	private Integer UID;
	private String PHONE;	
	private String USERNAME;
	private Double SUM_MONEY;	
	private String PRESTORE_ID;
	public Integer getUID() {
		return UID;
	}
	public void setUID(Integer uID) {
		UID = uID;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public Double getSUM_MONEY() {
		return SUM_MONEY;
	}
	public void setSUM_MONEY(Double sUM_MONEY) {
		SUM_MONEY = sUM_MONEY;
	}
	public String getPRESTORE_ID() {
		return PRESTORE_ID;
	}
	public void setPRESTORE_ID(String pRESTORE_ID) {
		PRESTORE_ID = pRESTORE_ID;
	}
}
