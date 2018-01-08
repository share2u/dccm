package cn.ncut.service.finance.valuecard;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/**
 * 
*
*<p>Title:StoredCategoryManager</p>
*<p>Description:储值卡管理接口</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-20下午7:36:46
*
 */
public interface StoredCategoryManager{

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

	/**
	 * 
	 *<p>Tittle:list</p>
	 *<p>Description:通过pd从储值卡详情表中找到对应员工的信息</p>
	 *@param pd
	 *@return
	 *@author lph
	 *@date 2016-12-20下午8:03:16
	 */
	public List<PageData> findStafflistPage(Page page) throws Exception;

	/**
	 * 
	 *<p>Tittle:listmenberlistPage</p>
	 *<p>Description:根据条件查询会员</p>
	 *@param page
	 *@return
	 *@author xyh
	 *@date 2016-12-20下午10:14:40
	 */
	public List<PageData> queryMember(Page page) throws Exception;
	
	
}

