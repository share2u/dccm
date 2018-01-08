package cn.ncut.entity.system;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 门店实体类
 * @author ljj
 *
 */
public class Store {
	private String STORE_NAME;	
	private String ADDRESS;
	private String TRAFFIC_MESSAGE;
	private String WORKTIME;	
	private String TELEPHONE;	
	private String M_ID;  //'服务板块编号',	
	private String STAFF_ID; //'门店负责人'	
	private String STORE_SMALL_IMG;	
	private String STORE_BIG_IMG;	
	private int STATUS;	
	private String BUSSINPATTERN;	  //经营模式
	private String CITY_ID;  //所属城市id
	private String CITY_NAME;
	private String IS_QUALIFICATION;  //是否有资质	
	private String STORE_NUMBER;	 //门店编号
	private String STORE_ID;  //主键
	
	private List<MultipartFile> uploadimages;  //上传的图片
	
	public String getCITY_NAME() {
		return CITY_NAME;
	}
	public void setCITY_NAME(String cITY_NAME) {
		CITY_NAME = cITY_NAME;
	}
	public String getSTORE_NAME() {
		return STORE_NAME;
	}
	public void setSTORE_NAME(String sTORE_NAME) {
		STORE_NAME = sTORE_NAME;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getTRAFFIC_MESSAGE() {
		return TRAFFIC_MESSAGE;
	}
	public void setTRAFFIC_MESSAGE(String tRAFFIC_MESSAGE) {
		TRAFFIC_MESSAGE = tRAFFIC_MESSAGE;
	}
	public String getWORKTIME() {
		return WORKTIME;
	}
	public void setWORKTIME(String wORKTIME) {
		WORKTIME = wORKTIME;
	}
	public String getTELEPHONE() {
		return TELEPHONE;
	}
	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}
	public String getM_ID() {
		return M_ID;
	}
	public void setM_ID(String m_ID) {
		M_ID = m_ID;
	}
	public String getSTAFF_ID() {
		return STAFF_ID;
	}
	public void setSTAFF_ID(String sTAFF_ID) {
		STAFF_ID = sTAFF_ID;
	}
	public String getSTORE_SMALL_IMG() {
		return STORE_SMALL_IMG;
	}
	public void setSTORE_SMALL_IMG(String sTORE_SMALL_IMG) {
		STORE_SMALL_IMG = sTORE_SMALL_IMG;
	}
	public String getSTORE_BIG_IMG() {
		return STORE_BIG_IMG;
	}
	public void setSTORE_BIG_IMG(String sTORE_BIG_IMG) {
		STORE_BIG_IMG = sTORE_BIG_IMG;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public String getBUSSINPATTERN() {
		return BUSSINPATTERN;
	}
	public void setBUSSINPATTERN(String bUSSINPATTERN) {
		BUSSINPATTERN = bUSSINPATTERN;
	}
	public String getCITY_ID() {
		return CITY_ID;
	}
	public void setCITY_ID(String cITY_ID) {
		CITY_ID = cITY_ID;
	}
	public String getIS_QUALIFICATION() {
		return IS_QUALIFICATION;
	}
	public void setIS_QUALIFICATION(String iS_QUALIFICATION) {
		IS_QUALIFICATION = iS_QUALIFICATION;
	}
	public String getSTORE_NUMBER() {
		return STORE_NUMBER;
	}
	public void setSTORE_NUMBER(String sTORE_NUMBER) {
		STORE_NUMBER = sTORE_NUMBER;
	}
	public String getSTORE_ID() {
		return STORE_ID;
	}
	public void setSTORE_ID(String sTORE_ID) {
		STORE_ID = sTORE_ID;
	}
	public List<MultipartFile> getUploadimages() {
		return uploadimages;
	}
	public void setUploadimages(List<MultipartFile> uploadimages) {
		this.uploadimages = uploadimages;
	}
	
	
}
