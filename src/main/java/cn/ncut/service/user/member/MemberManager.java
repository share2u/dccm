package cn.ncut.service.user.member;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.QueryUser;
import cn.ncut.entity.system.QueryUserDiscount;
import cn.ncut.util.PageData;

/** 
 * 说明： 会员管理接口
 * 创建人：FH Q313596790
 * 创建时间：2016-12-18
 * @version
 */
public interface MemberManager{

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
	public PageData findById(PageData pd)throws Exception;
	
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
	public PageData findMemberAndGroupById(PageData pd)throws Exception;

	public PageData findByuid(Object object)throws Exception;

	public PageData findName(PageData pd)throws Exception;

	
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
	public List<PageData> queryUsernameByPhone(String phone)throws Exception;


	/**
	 * 
	 *<p>Tittle:findCityByPhone</p>
	 *<p>Description:</p>
	 *@param pHONE
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-1-7下午3:04:57
	 */
	public String findCityByPhone(String pHONE) throws Exception;

	/**
	 * 
	 *<p>Tittle:findCityByAreaCode</p>
	 *<p>Description:通过区号查询地址</p>
	 *@param pHONE
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-1-7下午4:21:41
	 */
	public String findCityByAreaCode(String pHONE) throws Exception;

	/**
	 * 
	 *<p>Tittle:isPhoneNumExist</p>
	 *<p>Description:号码是否存在</p>
	 *@param pHONE
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-1-7下午5:36:31
	 */
	public boolean isPhoneNumExist(String pHONE) throws Exception;


	public List<QueryUser> menberandgroup(Page page)throws Exception;
	
	/**
	 * 根据用户微信用户名称和微信名获取uid
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> selectUsersByName(PageData pd) throws Exception;

	public List<PageData> querystoredDetail(Page page)throws Exception;

	public List<PageData> queryprestoredDetail(Page page)throws Exception;

	public List<PageData> querydiscountDetail(Page page)throws Exception;

	public List<PageData> querycreditDetail(Page page)throws Exception;

	public List<QueryUser> selectUserAll(PageData pd) throws Exception;

	public List<PageData> selectUsersBy(PageData pd)throws Exception;

	public List<PageData> selectstoredAll(PageData pd)throws Exception;
	
	public List<PageData> selectAll(PageData pd) throws Exception;
	
	public int selectCount(PageData pd) throws Exception;

	int selectMaxUid(PageData pd) throws Exception;
	public int getUserAgeCount(PageData pd)throws Exception;

	PageData findByuid(int uid) throws Exception;
	
	public List<PageData> selectAllOldUser(PageData pd) throws Exception;

}

