package cn.ncut.service.system.ncutlog;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/**
 * 
*
*<p>Title:NcutlogManager</p>
*<p>Description:操作日志接口</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-6上午10:08:53
*
 */
public interface NcutlogManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(String USERNAME, String CONTENT,String IP)throws Exception;
	
	/**
	 * 
	 *<p>Tittle:save</p>
	 *<p>Description:只要content的日志</p>
	 *@param CONTENT
	 *@throws Exception
	 *@author lph
	 *@date 2017-1-12上午11:47:39
	 */
	public void save(String CONTENT,String IP)throws Exception;
	
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
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
	
}

