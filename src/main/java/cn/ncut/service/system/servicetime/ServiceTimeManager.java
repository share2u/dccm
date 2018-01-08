package cn.ncut.service.system.servicetime;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/**
 * 
*
*<p>Title:ServiceTimeManager</p>
*<p>Description:医生排班接口</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-18下午3:56:08
*
 */
public interface ServiceTimeManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public List<PageData> listStaffNameAndIdById(String sTORE_ID) throws Exception;

	/**
	 * 
	 *<p>Tittle:findByStaffAndWeek</p>
	 *<p>Description:通过员工ID以及当前是本年第几周找到servicetime表中的记录</p>
	 *@param pd
	 *@return
	 *@author lph
	 *@date 2016-12-28上午9:29:02
	 */
	public List<PageData> findByStaffAndWeek(PageData pd) throws Exception;

	/**
	 * 
	 *<p>Tittle:deleteByStaffAndWeek</p>
	 *<p>Description:在更新排班信息之前删除数据库中存在本员工该周的所有记录</p>
	 *@param pd
	 *@throws Exception
	 *@author lph
	 *@date 2016-12-28下午8:16:45
	 */
	public void deleteByStaffAndWeek(PageData pd) throws Exception;

	/**
	 * 
	 *<p>Tittle:addEveryDay</p>
	 *<p>Description:把每天的排班记录都插入进去</p>
	 *@param pd
	 *@throws Exception
	 *@author lph
	 *@date 2016-12-28下午9:47:58
	 */
	public void addEveryDay(PageData pd) throws Exception;
	
	/**
	 * 
	 *<p>Tittle:findByYearAndWeekOfYear</p>
	 *<p>Description:根据年和本年第几周找到大表List</p>
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2016-12-30下午5:24:53
	 */
	public List<PageData> findByYearAndWeekOfYearStaff(PageData pd) throws Exception;
	
	
}

