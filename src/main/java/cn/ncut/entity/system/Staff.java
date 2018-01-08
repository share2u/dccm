package cn.ncut.entity.system;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.ncut.entity.Page;

/**
 * 
*
*<p>Title:Staff</p>
*<p>Description:用户po</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-9下午10:17:39
*
 */
public class Staff {
	private String STAFF_ID;		//员工id
	private String STAFF_NAME;	//员工姓名
	private String RANK_ID;
	private String USERNAME;	//用户名
	private String PASSWORD; 	//密码
	private String STORE_ID;
	private Integer SEX;
	private String ID_NUMBER;
	private String TELEPHONE;
	private String SPECIAL;
	private String INFO;
	private String DEPART_ID;
	private String STAFF_IMG;
	private Integer DEP_HEADER;
	private Integer STATUS;
	private String RIGHTS;		//权限
	private String ROLE_ID;		//角色id
	private String LAST_LOGIN;	//最后登录时间
	private String IP;			//用户登录ip地址
	private String EMAIL;
	private Role role;			//角色对象
	private Page page;			//分页对象
	private String SKIN;		//皮肤
	
	private List<MultipartFile> uploadimages;
	
	
	public List<MultipartFile> getUploadimages() {
		return uploadimages;
	}
	public void setUploadimages(List<MultipartFile> uploadimages) {
		this.uploadimages = uploadimages;
	}
	public String getSTAFF_ID() {
		return STAFF_ID;
	}
	public void setSTAFF_ID(String sTAFF_ID) {
		STAFF_ID = sTAFF_ID;
	}
	public String getSTAFF_NAME() {
		return STAFF_NAME;
	}
	public void setSTAFF_NAME(String sTAFF_NAME) {
		STAFF_NAME = sTAFF_NAME;
	}

	public String getRANK_ID() {
		return RANK_ID;
	}
	public void setRANK_ID(String rANK_ID) {
		RANK_ID = rANK_ID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	
	public String getSTORE_ID() {
		return STORE_ID;
	}
	public void setSTORE_ID(String sTORE_ID) {
		STORE_ID = sTORE_ID;
	}
	public Integer getSEX() {
		return SEX;
	}
	public void setSEX(Integer sEX) {
		SEX = sEX;
	}
	public String getID_NUMBER() {
		return ID_NUMBER;
	}
	public void setID_NUMBER(String iD_NUMBER) {
		ID_NUMBER = iD_NUMBER;
	}
	public String getTELEPHONE() {
		return TELEPHONE;
	}
	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}
	public String getSPECIAL() {
		return SPECIAL;
	}
	public void setSPECIAL(String sPECIAL) {
		SPECIAL = sPECIAL;
	}
	public String getINFO() {
		return INFO;
	}
	public void setINFO(String iNFO) {
		INFO = iNFO;
	}
	
	public String getDEPART_ID() {
		return DEPART_ID;
	}
	public void setDEPART_ID(String dEPART_ID) {
		DEPART_ID = dEPART_ID;
	}
	public String getSTAFF_IMG() {
		return STAFF_IMG;
	}
	public void setSTAFF_IMG(String sTAFF_IMG) {
		STAFF_IMG = sTAFF_IMG;
	}
	public Integer getDEP_HEADER() {
		return DEP_HEADER;
	}
	public void setDEP_HEADER(Integer dEP_HEADER) {
		DEP_HEADER = dEP_HEADER;
	}
	public Integer getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}
	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	
	
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getSKIN() {
		return SKIN;
	}
	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}
	
	
	
}
