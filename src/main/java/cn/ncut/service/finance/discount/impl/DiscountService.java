package cn.ncut.service.finance.discount.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.Discount;
import cn.ncut.entity.system.DiscountGroup;
import cn.ncut.entity.system.QueryUserDiscount;
import cn.ncut.entity.system.UserDiscount;
import cn.ncut.entity.system.UserDiscountGroup;
import cn.ncut.service.finance.discount.DiscountManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 优惠券
 * 创建人：ljj
 * 创建时间：2016-12-20
 * @version
 */
@Service("discountService")
public class DiscountService implements DiscountManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DiscountMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DiscountMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DiscountMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DiscountMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DiscountMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DiscountMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DiscountMapper.deleteAll", ArrayDATA_IDS);
	}

	
	/**
	 * 插入 用户-优惠券组合 表
	 */
	@Override
	public void addDiscountGroupToUser(PageData pd) throws Exception {
		
		dao.save("UserDiscountGroupMapper.addDiscountGroupToUser", pd);
	}
	

	/**
	 * 插入 用户-优惠券 表
	 */
	@Override
	public void sendDiscountToUser(PageData pd) throws Exception {
		
		dao.save("UserDiscountMapper.addDiscountToUser", pd);
	}
	
	
	
	/**
	 * 根据优惠券组合group_id查询哪些用户有这个优惠券组合
	 * @param group_id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryUserByGroupid(Page page) throws Exception{
		return (List<PageData>)dao.findForList("UserDiscountGroupMapper.queryUserByGroupidlistPage", page);
	}

	@Override
	public List<UserDiscount> queryDiscountByUidAndGroupid(UserDiscountGroup ug) throws Exception {
		return (List<UserDiscount>)dao.findForList("UserDiscountMapper.queryDiscountByUidAndGroupid", ug);
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDiscountGroup> queryDiscountGroupByUid(int uid) throws Exception {
		return (List<UserDiscountGroup>)dao.findForList("UserDiscountGroupMapper.queryDiscountGroupByUid", uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QueryUserDiscount> selectUserDiscounts(Page page)
			throws Exception {
		return (List<QueryUserDiscount>) dao.findForList("QueryDiscountMapper.queryDiscountlistPage", page);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryUserDiscount> selectUserDiscountsAll(PageData pd)
			throws Exception {
		return (List<QueryUserDiscount>) dao.findForList("QueryDiscountMapper.queryDiscountAll", pd);
	}

	@Override
	public List<PageData> selectDisCountsByName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("QueryDiscountMapper.selectDisCountsByName", pd);
	}

	@Override
	public List<PageData> findByUidAndDiscountId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserDiscountMapper.findByUidAndDiscountId", pd);
	}

	@Override
	public List<PageData> queryGrantDiscountsListPage(Page page)
			throws Exception {
		return (List<PageData>)dao.findForList("QueryDiscountMapper.selectGrantDiscountGrouplistPage", page);
	}

	@Override
	public List<PageData> queryGrantDiscountsAll(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("QueryDiscountMapper.selectGrantDiscountGroupAll", pd);
	}
}

