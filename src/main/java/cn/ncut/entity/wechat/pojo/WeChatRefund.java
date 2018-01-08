package cn.ncut.entity.wechat.pojo;

public class WeChatRefund {
	private Integer UID;
	private String TIME;
	private Double PRECARD_ADD;
	private Double PRECARD_POINTS_ADD;
	private Double PAY_MONEY;
	private String SERVICE_ID;
	private String REFUND_ID;
	private String ORDER_ID;
	public Integer getUID() {
		return UID;
	}
	public void setUID(Integer uID) {
		UID = uID;
	}
	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	public Double getPRECARD_ADD() {
		return PRECARD_ADD;
	}
	public void setPRECARD_ADD(Double pRECARD_ADD) {
		PRECARD_ADD = pRECARD_ADD;
	}
	public Double getPRECARD_POINTS_ADD() {
		return PRECARD_POINTS_ADD;
	}
	public void setPRECARD_POINTS_ADD(Double pRECARD_POINTS_ADD) {
		PRECARD_POINTS_ADD = pRECARD_POINTS_ADD;
	}
	public Double getPAY_MONEY() {
		return PAY_MONEY;
	}
	public void setPAY_MONEY(Double pAY_MONEY) {
		PAY_MONEY = pAY_MONEY;
	}
	public String getSERVICE_ID() {
		return SERVICE_ID;
	}
	public void setSERVICE_ID(String sERVICE_ID) {
		SERVICE_ID = sERVICE_ID;
	}
	public String getREFUND_ID() {
		return REFUND_ID;
	}
	public void setREFUND_ID(String rEFUND_ID) {
		REFUND_ID = rEFUND_ID;
	}
	public String getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}
}
