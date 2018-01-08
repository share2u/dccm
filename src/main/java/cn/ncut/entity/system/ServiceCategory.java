package cn.ncut.entity.system;

import java.util.List;

public class ServiceCategory {
	private String CATEGORY_NAME;
	private Integer F_CATEGORY_ID;	
	private String DESCRIPTION;
	private Integer STATUS;
	private Integer SERVICECATEGORY_ID;
	
	private List<ServiceCategory> subCategorylist;
	
	public List<ServiceCategory> getSubCategorylist() {
		return subCategorylist;
	}
	public void setSubCategorylist(List<ServiceCategory> subCategorylist) {
		this.subCategorylist = subCategorylist;
	}
	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}
	public void setCATEGORY_NAME(String cATEGORY_NAME) {
		CATEGORY_NAME = cATEGORY_NAME;
	}
	public Integer getF_CATEGORY_ID() {
		return F_CATEGORY_ID;
	}
	public void setF_CATEGORY_ID(Integer f_CATEGORY_ID) {
		F_CATEGORY_ID = f_CATEGORY_ID;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public Integer getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Integer sTATUS) {
		STATUS = sTATUS;
	}
	public Integer getSERVICECATEGORY_ID() {
		return SERVICECATEGORY_ID;
	}
	public void setSERVICECATEGORY_ID(Integer sERVICECATEGORY_ID) {
		SERVICECATEGORY_ID = sERVICECATEGORY_ID;
	}
	
	
	
}
