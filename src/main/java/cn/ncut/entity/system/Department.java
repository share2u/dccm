package cn.ncut.entity.system;

import java.util.List;

/**
 * 
*
*<p>Title:Department</p>
*<p>Description:组织机构,预留模板,不使用</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-5下午8:37:25
*
 */
public class Department {

  private String DEP_ID;
  private String DEP_NAME;
  private String DEP_DESC;
  private int STATUS;
public String getDEP_ID() {
	return DEP_ID;
}
public void setDEP_ID(String dEP_ID) {
	DEP_ID = dEP_ID;
}
public String getDEP_NAME() {
	return DEP_NAME;
}
public void setDEP_NAME(String dEP_NAME) {
	DEP_NAME = dEP_NAME;
}
public String getDEP_DESC() {
	return DEP_DESC;
}
public void setDEP_DESC(String dEP_DESC) {
	DEP_DESC = dEP_DESC;
}
public int getSTATUS() {
	return STATUS;
}
public void setSTATUS(int sTATUS) {
	STATUS = sTATUS;
}
  
}