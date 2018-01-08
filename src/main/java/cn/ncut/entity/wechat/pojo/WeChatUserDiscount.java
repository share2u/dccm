package cn.ncut.entity.wechat.pojo;


/**
 * 用户拥有优惠券类
 * */
public class WeChatUserDiscount {
	private int id;
	// 用户主键
	private int uId;
	// 优惠券表编号
	private String discountId;
	// 创建时间
	private String createTime;
	// 优惠券是否被用户看过 默认没有看过为0
	private int isScan;
	// 优惠券是否使用过 默认么有使用过为0
	private int isUsed;
	// 
	private WeChatDiscount discount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getuId() {
		return uId;
	}
	public void setuId(int uId) {
		this.uId = uId;
	}
	public String getDiscountId() {
		return discountId;
	}
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getIsScan() {
		return isScan;
	}
	public void setIsScan(int isScan) {
		this.isScan = isScan;
	}
	public int getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}
	public WeChatDiscount getDiscount() {
		return discount;
	}
	public void setDiscount(WeChatDiscount discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		return "WeChatUserDiscount [id=" + id + ", uId=" + uId
				+ ", discountId=" + discountId + ", createTime=" + createTime
				+ ", isScan=" + isScan + ", isUsed=" + isUsed + ", discount="
				+ discount + "]";
	}
}
