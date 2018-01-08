package cn.ncut.service.finance.discount;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.entity.system.Discount;
import cn.ncut.entity.system.DiscountGroup;
import cn.ncut.entity.system.QueryUserDiscount;
import cn.ncut.entity.system.UserDiscount;
import cn.ncut.entity.system.UserDiscountGroup;
import cn.ncut.util.PageData;


/** 
 * 说明： 优惠券接口
 * 创建人：ljj
 * 创建时间：2016-12-20
 * @version
 */
public interface DiscountManager{

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
	 * 插入 用户-优惠券 表
	 * @param arrayDATA_IDS
	 */
	public void sendDiscountToUser(PageData pd) throws Exception;
	
	/**
	 * 插入 用户-优惠券组合 表
	 */
	public void addDiscountGroupToUser(PageData pd) throws Exception;
	
	/**
	 * 根据优惠券组合group_id查询哪些用户有这个优惠券组合
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryUserByGroupid(Page page) throws Exception;
	
	
	/**
	 * 查询该用户有什么优惠券
	 * 
	 * @param parseInt
	 * @return
	 */
	public List<UserDiscount> queryDiscountByUidAndGroupid(UserDiscountGroup ug) throws Exception;

	/**
	 * 查询某个用户有什么优惠券组合
	 * @param parseInt
	 * @return
	 */
	public List<UserDiscountGroup> queryDiscountGroupByUid(int uid) throws Exception;
	
	/**
	 * 查询统计模块
	 * 查询优惠券
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<QueryUserDiscount> selectUserDiscounts(Page page) throws Exception;
	
	public List<PageData> selectDisCountsByName(PageData pd)throws Exception;

	/**
	 * 查询优惠劵下载全部
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<QueryUserDiscount> selectUserDiscountsAll(PageData pd)
			throws Exception;

	public List<PageData> findByUidAndDiscountId(PageData pd)throws Exception;
	
	/**
	 * 客服和优惠组为主题，查询相应的发放记录
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryGrantDiscountsListPage(Page page) throws Exception;

	/**
	 * 导入优惠组查询excel
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryGrantDiscountsAll(PageData pd) throws Exception;
}

