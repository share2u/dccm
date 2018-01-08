package cn.ncut.entity.wechat.pojo;

import java.util.List;
import java.util.Map;

public class WeChatDiscountGroup {
	private Integer discountGroupId;
	private String groupId;
	private String groupName;
	private String discountId;
	private String discountNumber;
	private String createTime;
	private String staffId;
	private String rmark;
	private Map<String, List<Integer>> map;
	private String ids;
	public Integer getDiscountGroupId() {
		return discountGroupId;
	}
	public void setDiscountGroupId(Integer discountGroupId) {
		this.discountGroupId = discountGroupId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	public String getDiscountNumber() {
		return discountNumber;
	}
	public void setDiscountNumber(String discountNumber) {
		this.discountNumber = discountNumber;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getRmark() {
		return rmark;
	}
	public void setRmark(String rmark) {
		this.rmark = rmark;
	}
	public Map<String, List<Integer>> getMap() {
		return map;
	}
	public void setMap(Map<String, List<Integer>> map) {
		this.map = map;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
