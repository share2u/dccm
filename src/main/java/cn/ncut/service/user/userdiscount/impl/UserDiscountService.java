package cn.ncut.service.user.userdiscount.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.user.userdiscount.UserDiscountManager;

/** 
 * 说明： 用户优惠券
 * 创建人：FH Q313596790
 * 创建时间：2017-02-17
 * @version
 */
@Service("userdiscountService")
public class UserDiscountService implements UserDiscountManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UserDiscountMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UserDiscountMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UserDiscountMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UserDiscountMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserDiscountMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserDiscountMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UserDiscountMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	public List<PageData> findByUId(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UserDiscountMapper.findByUIdlistPage", page);
	}
	
	public void deleteDiscounGroupByid(String groupid) throws Exception {
		
		 dao.findForList("UserDiscountGroupMapper.deleteDiscounGroupByid", groupid);
	}

	@Override
	public List<PageData> selectDiscountGroups(PageData pd) throws Exception {
		 return (List<PageData>) dao.findForList("StaticticsDiscountMapper.selectDiscountGroups", pd);
		
	}

	@Override
	public List<PageData> selectIsUsedDiscountByGroupId(PageData pd) throws Exception {
		 return (List<PageData>) dao.findForList("StaticticsDiscountMapper.selectIsUsedDiscountByGroupId", pd);
		
	}
}

