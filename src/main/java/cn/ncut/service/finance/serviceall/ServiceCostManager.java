package cn.ncut.service.finance.serviceall;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.util.PageData;


/** 
 * 说明： 定价接口
 * 创建人：ljj
 * 创建时间：2016-12-19
 * @version
 */
public interface ServiceCostManager{

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
	
	/**列表有效的(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listValidAll(PageData pd)throws Exception;
	
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
	 * 自己写的 根据员工编号查询项目和收费价格
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findServiceAndCostByStaff_id(PageData pd)throws Exception;
	
	/**
	 * 自己写的 根据PID查询价格
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryPriceByPID(PageData pd)throws Exception;
	public List<PageData> queryPriceByPIDAndStaff(PageData pd)throws Exception;
	public PageData findById2(PageData pd)throws Exception;
	
	/**
	 * 根据该记录的STORE_ID、STAFF_ID、PID查出之前生效的相同的项目
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryProjectByThreeParameters(PageData pd) throws Exception;
		
	
	/**
	 * 批量置为无效
	 * @param list
	 * @throws Exception
	 */
	public void batchUpdateStatusToInvalid(List<PageData> list) throws Exception;
	//以下是课题加的
	public int findmaxid(PageData pd)throws Exception;
	public String selectIscollByUid(Integer UID)throws Exception;
	public String selectservicecost_ids(PageData pd)throws Exception;
	public String selecttop10(PageData pd)throws Exception;
	public PageData selectPnameAndStaffName(PageData pd)throws Exception;
}

