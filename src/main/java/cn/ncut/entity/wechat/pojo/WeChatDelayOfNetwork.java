package cn.ncut.entity.wechat.pojo;

import java.sql.Timestamp;

public class WeChatDelayOfNetwork {
	private int delayId;
	private long delayStart;
	private long delayMid;
	private long delayEnd;
	private Timestamp delayCreateTime;
	private String delayIp;
	private String delayComment;
	private String delayStaff;
	private long delayFinish;
	public int getDelayId() {
		return delayId;
	}
	public void setDelayId(int delayId) {
		this.delayId = delayId;
	}
	public long getDelayStart() {
		return delayStart;
	}
	public void setDelayStart(long delayStart) {
		this.delayStart = delayStart;
	}
	public long getDelayMid() {
		return delayMid;
	}
	public void setDelayMid(long delayMid) {
		this.delayMid = delayMid;
	}
	public long getDelayEnd() {
		return delayEnd;
	}
	public void setDelayEnd(long delayEnd) {
		this.delayEnd = delayEnd;
	}
	public Timestamp getDelayCreateTime() {
		return delayCreateTime;
	}
	public void setDelayCreateTime(Timestamp delayCreateTime) {
		this.delayCreateTime = delayCreateTime;
	}
	public String getDelayIp() {
		return delayIp;
	}
	public void setDelayIp(String delayIp) {
		this.delayIp = delayIp;
	}
	public String getDelayComment() {
		return delayComment;
	}
	public void setDelayComment(String delayComment) {
		this.delayComment = delayComment;
	}
	public String getDelayStaff() {
		return delayStaff;
	}
	public void setDelayStaff(String delayStaff) {
		this.delayStaff = delayStaff;
	}	
	public long getDelayFinish() {
		return delayFinish;
	}
	public void setDelayFinish(long delayFinish) {
		this.delayFinish = delayFinish;
	}
	public static WeChatDelayOfNetwork getInstance(long delayStart, long delayMid, long delayEnd, 
			String delayIp, String delayComment, String delayStaff) {
		WeChatDelayOfNetwork weChatDelayOfNetwork = new WeChatDelayOfNetwork();
		weChatDelayOfNetwork.setDelayStart(delayStart);
		weChatDelayOfNetwork.setDelayMid(delayMid);
		weChatDelayOfNetwork.setDelayEnd(delayEnd);
		weChatDelayOfNetwork.setDelayFinish(System.currentTimeMillis());
		weChatDelayOfNetwork.setDelayCreateTime(new Timestamp(System.currentTimeMillis()));
		
		if (delayIp == null) {
			weChatDelayOfNetwork.setDelayIp("0:0:0:0");			
		} else {
			weChatDelayOfNetwork.setDelayIp(delayIp);
		}
		if (delayComment == null) {
			weChatDelayOfNetwork.setDelayComment("N/A");
		} else {
			weChatDelayOfNetwork.setDelayComment(delayComment);
		}
		if (delayStaff == null) {
			weChatDelayOfNetwork.setDelayStaff("N/A");
		} else {
			weChatDelayOfNetwork.setDelayStaff(delayStaff);
		}
		
		return weChatDelayOfNetwork;
	}
}