package cn.ncut.service.system.buttonrights;

import java.util.List;
import cn.ncut.util.PageData;

/**
 * 
*
*<p>Title:ButtonrightsManager</p>
*<p>Description:按钮权限接口</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-6上午10:06:46
*
 */
public interface ButtonrightsManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**通过(角色ID和按钮ID)获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列表(全部)左连接按钮表,查出安全权限标识
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllBrAndQxname(PageData pd)throws Exception;
	
}

