package cn.ncut.service.system.staff.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Staff;
import cn.ncut.util.PageData;
import cn.ncut.service.system.staff.StaffManager;

/** 
 * 说明： 员工模块
 * 创建人：FH Q313596790
 * 创建时间：2016-12-09
 * @version
 */
@Service("staffService")
public class StaffService implements StaffManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStaffByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.getStaffInfo", pd);
	}
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("StaffMapper.updateLastLogin", pd);
	}
	
	/**通过用户ID获取用户信息和角色信息
	 * @param Staff_ID
	 * @return
	 * @throws Exception
	 */
	public Staff getStaffAndRoleById(String STAFF_ID) throws Exception {
		return (Staff) dao.findForObject("StaffMapper.getStaffAndRoleById", STAFF_ID);
	}
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByUsername", pd);
	}
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllStaffByRoldId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StaffMapper.listAllStaffByRoldId", pd);
		
	}
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd)throws Exception{
		dao.update("StaffMapper.saveIP", pd);
	}
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listStaffs(Page page)throws Exception{
		return (List<PageData>) dao.findForList("StaffMapper.datalistPage", page);
	}
	
	/**用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listStaffsBystaff(Page page)throws Exception{
		return (List<PageData>) dao.findForList("StaffMapper.staffBystafflistPage", page);
	}
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByUE", pd);
	}
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByUN", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findById", pd);
	}
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(Staff staff)throws Exception{
		dao.save("StaffMapper.saveU", staff);
	}
	 
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(Staff staff)throws Exception{
		dao.update("StaffMapper.editU", staff);
	}
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("StaffMapper.deleteU", pd);
	}
	
	/**批量删除用户
	 * @param STAFF_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] STAFF_IDS)throws Exception{
		dao.delete("StaffMapper.deleteAllU", STAFF_IDS);
	}
	
	/**用户列表(全部)
	 * @param STAFF_IDS
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllStaff(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("StaffMapper.listAllStaff", pd);
	}
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getStaffCount(String value)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.getStaffCount", value);
	}

	/**
	 * 根据员工id查询该员工信息，保存在PageData中
	 */
	@Override
	public PageData queryById(String id) throws Exception {
		return (PageData) dao.findForObject("StaffMapper.queryStaff", id);
	}

	/**
	 * 显示所有的员工并现实对应的门店
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findStoreDepartStaffByStaff(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StaffMapper.findStoreDepartStafflistPage", page);
	}

	@Override
	public List findStoreDepartById(PageData pd) throws Exception {
		return (List) dao.findForObject("StaffMapper.findStoreDepartById",pd);
	}

	
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.listAll", pd);
	}

	/**
	 * <!--根据员工id查询其门店编号-->
	 */
	@Override
	public String queryStoreBySID(String STAFF_ID) throws Exception {
		return (String) dao.findForObject("StaffMapper.queryStoreBySId", STAFF_ID);
	}

	@Override
	public String getStaffNameById(String STAFF_ID) throws Exception {
		return  (String) dao.findForObject("StaffMapper.getStaffNameById", STAFF_ID);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> listSelfStoreStaff(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.listSelfStoreStaff", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listSelfStoreKefu(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.listSelfStoreKefu", pd);
	}

	@Override
	public List<PageData> findstaffsBySome(Page page) throws Exception {
		return (List<PageData>)dao.findForList("StaffMapper.findstaffsBySomelistPage", page);
	}

	@Override
	public List<PageData> findstaffsBySomeALL(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StaffMapper.findstaffsBySomeAll", pd);
	}
	@Override
	public List<PageData> findstaffsByStoreId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StaffMapper.findstaffsByStoreId", pd);
	}

	@Override
	public List<PageData> findstaffsBySome(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StaffMapper.findstaffsBySome", pd);
	}

}

