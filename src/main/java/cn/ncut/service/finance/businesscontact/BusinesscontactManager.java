package cn.ncut.service.finance.businesscontact;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/** 
 * 说明： 往来规则接口
 * 创建人：FH Q313596790
 * 创建时间：2016-12-26
 * @version
 */
public interface BusinesscontactManager{

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

	public Double findTostaff()throws Exception;
	
	/**查询最新的业务往来规则
	 * @param pd
	 * @throws Exception
	 */
	public PageData findnew(PageData pd)throws Exception;
	
}

