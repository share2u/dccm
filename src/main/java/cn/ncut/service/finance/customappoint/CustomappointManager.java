package cn.ncut.service.finance.customappoint;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.util.PageData;


/** 
 * 说明： 预约表接口
 * 创建人：FH Q313596790
 * 创建时间：2017-01-03
 * @version
 */
public interface CustomappointManager{

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
	
	/**查询指定预约数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCount(PageData pd)throws Exception;
	
	public List<PageData> listToday(Page page)throws Exception;
	
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
	
	public List<PageData> querySum(PageData pd)throws Exception;

	/**
	 * 
	 *<p>Tittle:findByOrderId</p>
	 *<p>Description:通过ORDER_ID找到所有的customappoint内容</p>
	 *@param pd
	 *@throws Exception
	 *@author lph
	 *@date 2017-4-24下午4:57:25
	 */
	public PageData findByOrderId(PageData pd) throws Exception;
	
}

