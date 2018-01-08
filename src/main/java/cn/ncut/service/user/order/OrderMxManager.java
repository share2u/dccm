package cn.ncut.service.user.order;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.util.PageData;


/** 
 * 说明： 订单(明细)接口
 * 创建人：FH Q313596790
 * 创建时间：2016-12-30
 * @version
 */
public interface OrderMxManager{

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
	
	/**查询明细总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception;

	/**
	 * 
	 *<p>Tittle:findByOrderId</p>
	 *<p>Description:通过订单ID找到明细信息</p>
	 *@param pd
	 *@return
	 *@author lph
	 *@date 2017-2-22下午3:01:31
	 */
	public List<PageData> findAllByOrderId(PageData pd) throws Exception;
	
	public PageData findFandianByOrderId(PageData pd) throws Exception;
	
	
}

