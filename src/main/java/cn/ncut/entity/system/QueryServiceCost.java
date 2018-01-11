package cn.ncut.entity.system;

import java.math.BigDecimal;
import java.util.Date;

import cn.ncut.entity.wechat.pojo.WeChatServiceProject;

public class QueryServiceCost {
	private int serviceCostId;
	// 服务板块
	private int mId;
	private String storeId;
	// 分类
	private int categoryId;
	//第二大分类
	private ServiceCategory f2serviceCategory;
	//第一大分类
	private ServiceCategory f1serviceCategory;
	// 项目id
	private int pId;
	private String staffId;
	// 生效日期
	private Date effecitiveDate;
	// 价格
	private BigDecimal price;
	// 是否是初诊 0 初诊;1复诊
	private int isFirst;
	private int status;

	private WeChatServiceProject serviceProject;

	public ServiceCategory getF2serviceCategory() {
		return f2serviceCategory;
	}

	public void setF2serviceCategory(ServiceCategory f2serviceCategory) {
		this.f2serviceCategory = f2serviceCategory;
	}

	public ServiceCategory getF1serviceCategory() {
		return f1serviceCategory;
	}

	public void setF1serviceCategory(ServiceCategory f1serviceCategory) {
		this.f1serviceCategory = f1serviceCategory;
	}

	public int getServiceCostId() {
		return serviceCostId;
	}

	public void setServiceCostId(int serviceCostId) {
		this.serviceCostId = serviceCostId;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public Date getEffecitiveDate() {
		return effecitiveDate;
	}

	public void setEffecitiveDate(Date effecitiveDate) {
		this.effecitiveDate = effecitiveDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public WeChatServiceProject getServiceProject() {
		return serviceProject;
	}

	public void setServiceProject(WeChatServiceProject serviceProject) {
		this.serviceProject = serviceProject;
	}

}
