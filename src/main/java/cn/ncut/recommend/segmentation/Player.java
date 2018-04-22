 package cn.ncut.recommend.segmentation;  
  
  /** 
   * 聚类对象
    *  
   * @author xth
   *  
   */  
 public class Player {  
  
private String uid;  
private String attribute2;  
  
private String attribute3;  
  
/* 聚类特征值1 */  
@KmeansField  
private double usercategoryid;  
  
/* 聚类特征值2 */  
@KmeansField  
private double proportion;  
/* 聚类特征值2 */  
@KmeansField  
private double attentiontime;  
  
public double getAttentiontime() {
	return attentiontime;
}



public void setAttentiontime(double attentiontime) {
	this.attentiontime = attentiontime;
}



public double getAvgordercount() {
	return avgordercount;
}



public void setAvgordercount(double avgordercount) {
	this.avgordercount = avgordercount;
}



public double getAvgordersum() {
	return avgordersum;
}



public void setAvgordersum(double avgordersum) {
	this.avgordersum = avgordersum;
}



/* 聚类特征值3 */  
@KmeansField  
private double ordercount;  
 
/*聚类特征值4 */  
@KmeansField  
private double ordersum;  
/* 聚类特征值3 */  
@KmeansField  
private double avgordercount;  
 
/*聚类特征值4 */  
@KmeansField  
private double avgordersum;  

/* 聚类特征值5 */ 
@KmeansField  
private double storedcount; 
/*
 聚类特征值6 */  
@KmeansField  
private double remain;

/*
聚类特征值7 */  
@KmeansField  
private double refundcount;

/*
聚类特征值8 */  
@KmeansField  
private double refundmoney;



public String getUid() {
	return uid;
}



public void setUid(String uid) {
	this.uid = uid;
}



public String getAttribute2() {
	return attribute2;
}



public void setAttribute2(String attribute2) {
	this.attribute2 = attribute2;
}



public String getAttribute3() {
	return attribute3;
}



public void setAttribute3(String attribute3) {
	this.attribute3 = attribute3;
}



public double getUsercategoryid() {
	return usercategoryid;
}



public void setUsercategoryid(double usercategoryid) {
	this.usercategoryid = usercategoryid;
}



public double getProportion() {
	return proportion;
}



public void setProportion(double proportion) {
	this.proportion = proportion;
}



public double getOrdercount() {
	return ordercount;
}



public void setOrdercount(double ordercount) {
	this.ordercount = ordercount;
}



public double getOrdersum() {
	return ordersum;
}



public void setOrdersum(double ordersum) {
	this.ordersum = ordersum;
}



public double getStoredcount() {
	return storedcount;
}



public void setStoredcount(double storedcount) {
	this.storedcount = storedcount;
}



public double getRemain() {
	return remain;
}



public void setRemain(double remain) {
	this.remain = remain;
}



public double getRefundcount() {
	return refundcount;
}



public void setRefundcount(double refundcount) {
	this.refundcount = refundcount;
}



public double getRefundmoney() {
	return refundmoney;
}



public void setRefundmoney(double refundmoney) {
	this.refundmoney = refundmoney;
}



@Override
public String toString() {
	return "Player [uid=" + uid + ", attribute2=" + attribute2
			+ ", attribute3=" + attribute3 + ", usercategoryid="
			+ usercategoryid + ", proportion=" + proportion
			+ ", attentiontime=" + attentiontime + ", ordercount=" + ordercount
			+ ", ordersum=" + ordersum + ", avgordercount=" + avgordercount
			+ ", avgordersum=" + avgordersum + ", storedcount=" + storedcount
			+ ", remain=" + remain + ", refundcount=" + refundcount
			+ ", refundmoney=" + refundmoney + "]";
}










  

 }
  
  
  
  