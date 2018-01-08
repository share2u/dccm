package cn.ncut.service.system.staff;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Staff;
import cn.ncut.entity.system.User;
import cn.ncut.util.PageData;

/**
 * 
*
*<p>Title:StaffManager</p>
*<p>Description:员工模块接口</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-9下午9:24:03
*
 */
public interface StaffManager{

	
	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getStaffByNameAndPwd(PageData pd)throws Exception;
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd)throws Exception;
	
	
	/**通过用户ID获取用户信息和角色信息
	 * @param STAFF_ID
	 * @return
	 * @throws Exception
	 */
	public Staff getStaffAndRoleById(String STAFF_ID) throws Exception;
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception;
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllStaffByRoldId(PageData pd) throws Exception;
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd)throws Exception;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listStaffs(Page page)throws Exception;
	
	/**用户列表(弹窗选择用)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listStaffsBystaff(Page page)throws Exception;
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd)throws Exception;
	
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(Staff staff)throws Exception;
	
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(Staff staff)throws Exception;
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] STAFF_IDS)throws Exception;
	
	/**用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllStaff(PageData pd)throws Exception;
	
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getStaffCount(String value)throws Exception;
	
	
	/**
	 * 根据员工id查询该员工信息，保存在PageData中
	 */
	public PageData queryById(String id) throws Exception;
	
	/**
	 * 
	 *<p>Tittle:findStoreStaffByStaff</p>
	 *<p>Description:显示所有的员工并现实对应的门店</p>
	 *@param pd
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2016-12-12下午4:58:02
	 */
	public List<PageData> findStoreDepartStaffByStaff(Page page) throws Exception;
	
	/**
	 * 
	 *<p>Tittle:findStoreDepartById</p>
	 *<p>Description:通过id获取数据</p>
	 *@param pd
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2016-12-12下午10:07:39
	 */
	public List findStoreDepartById(PageData pd)throws Exception;
	/**
	 * 
	 *<p>Tittle:listAll</p>
	 *<p>Description:</p>
	 *@param pd
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2016-12-18下午5:47:13
	 */
	public List<PageData> listAll(PageData pd)throws Exception;

	/**
	 * <!--根据员工id查询其门店编号-->
	 * @param STAFF_ID
	 * @return
	 * @throws Exception
	 */
	public String queryStoreBySID(String STAFF_ID) throws Exception;

	/**
	 * 
	 *<p>Tittle:getStaffNameById</p>
	 *<p>Description:根据员工ID查找其姓名</p>
	 *@param STAFF_ID
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-1-2下午3:32:42
	 */
	public String getStaffNameById(String STAFF_ID) throws Exception;

	
	public List<PageData> listSelfStoreStaff(PageData pd)throws Exception;
	
	public List<PageData> listSelfStoreKefu(PageData pd)throws Exception;
	
	
	//查询统计start--
	
	/**
	 * 根据莫一些内容查询员工s
	 */

	public List<PageData> findstaffsBySome(Page page)throws Exception;
	
	public List<PageData> findstaffsByStoreId(PageData pd) throws Exception;
	/**
	 * 根据员工名称获取员工ids
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findstaffsBySome(PageData pd) throws Exception;

	/**
	 * 根据一些条件查询所有结果不分页
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findstaffsBySomeALL(PageData pd) throws Exception;
	
	//查询统计end--

}

