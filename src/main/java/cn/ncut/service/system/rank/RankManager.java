package cn.ncut.service.system.rank;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/**
 * 
*
*<p>Title:rank表接口</p>
*<p>Description:</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-15下午9:52:19
*
 */
public interface RankManager{

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
	 *<p>Tittle:listAllRankOne</p>
	 *<p>Description:查出一个rank</p>
	 *@param pd
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-1-17下午9:26:52
	 */
	public List<PageData> listAllRankOne(PageData pd) throws Exception;
	
}

