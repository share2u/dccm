package cn.ncut.service.datamining;

import java.util.List;

import cn.ncut.entity.Page;
//import cn.ncut.entity.system.QueryUser;
import cn.ncut.util.PageData;

public interface ClusterManager {
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
	
	/**更新手机号和姓名
	 * @param pd
	 * @throws Exception
	 */
	public void updatenameandphone(PageData pd)throws Exception;
	
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
	public PageData findById(Page page)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	
	/**用户管理-- 用户查询用户信息
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listmenberandgroup(Page page)throws Exception;
	
	/**优惠券下发 -- 列出所有在线用户信息
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> DiscountlistmemberlistPage(Page page)throws Exception;
	
	/**自己写的 编辑时用
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */


	
	/**
	 * 储值卡售卖：查询客户的基本信息
	 * @param pd
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listCompleteMemberlistPage(Page page) throws Exception;
	
	/**
	 * 根据uid查询用户的储值卡和余额信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData findUserStorededAndPrestoreByUid(int uid) throws Exception;
	

	/**
	 * 用户缴费：查询客户的基本信息
	 * @param pd
	 * @return 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> userPayQueryMemberlistPage(Page page) throws Exception;
	
	
	/**
	 * 根据用户电话查询用户记录，如果是多条则返回List集合
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByLabel(Page pd)throws Exception;

	//查询聚类算法的结果
	public List<PageData> listcluster(Page page)throws Exception;

	public List<PageData> findVIP(Page page)throws Exception;

	public List<PageData> listLiushi(Page page)throws Exception;

	public List<PageData> listClassifier(Page page)throws Exception;

	public List<PageData> findLiushi(Page page)throws Exception;

	


	

	
	
	
}
