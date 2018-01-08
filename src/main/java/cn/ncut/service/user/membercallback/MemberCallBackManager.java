package cn.ncut.service.user.membercallback;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/** 
 * 说明： 用户回电接口
 * 创建人：FH Q313596790
 * 创建时间：2016-12-19
 * @version
 */
public interface MemberCallBackManager{

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
	public void updatestatus(PageData pd)throws Exception;
	
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
	
	/**
	 * 自己写的列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listStaffAndStore(Page page)throws Exception;
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	/**
	 * 自己写的 获取全部数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findAllById(PageData pd)throws Exception;
	
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
}

