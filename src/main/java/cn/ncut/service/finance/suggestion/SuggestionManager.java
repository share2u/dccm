package cn.ncut.service.finance.suggestion;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/** 
 * 说明： 投诉与建议接口
 * 创建人：FH Q313596790
 * 创建时间：2017-01-08
 * @version
 */
public interface SuggestionManager{

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
	
	/**通过id获取评价的数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData queryCommentById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**修改评价
	 * @param pd
	 * @throws Exception
	 */
	public void editComment(PageData pd)throws Exception;
	
	/**列表所有评价
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listAllComment(Page page)throws Exception;
	
}

