package cn.ncut.entity.wechat.pojo;

import cn.ncut.entity.Page;
import cn.ncut.entity.system.Role;

/**
 * 员工类 -- 对应数据库员工表
 * */
public class WeChatStaff {
	private String staffId;		//员工id
	private String staffName;	//员工姓名
	private String rankId;
	private String userName;	//用户名
	private String password; 	//密码
	private String storeId;
	private Integer sex;
	private String idNumber;
	private String telephone;
	private String special;
	private String info;
	private String departmentId;
	private String staffImg;
	private Integer departmentHeader;
	private Integer status;
	private String rights;		//权限
	private String roleId;		//角色id
	private String lastLogin;	//最后登录时间
	private String ip;			//用户登录ip地址
	private String email;
	private Role role;			//角色对象
	private Page page;			//分页对象
	private String skin;		//皮肤
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getRankId() {
		return rankId;
	}
	public void setRankId(String rankId) {
		this.rankId = rankId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getStaffImg() {
		return staffImg;
	}
	public void setStaffImg(String staffImg) {
		this.staffImg = staffImg;
	}
	public Integer getDepartmentHeader() {
		return departmentHeader;
	}
	public void setDepartmentHeader(Integer departmentHeader) {
		this.departmentHeader = departmentHeader;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
}
