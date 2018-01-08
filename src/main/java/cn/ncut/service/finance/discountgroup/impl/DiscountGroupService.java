package cn.ncut.service.finance.discountgroup.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.discountgroup.DiscountGroupManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 优惠券组
 * 创建人：FH Q313596790
 * 创建时间：2017-02-16
 * @version
 */
@Service("discountgroupService")
public class DiscountGroupService implements DiscountGroupManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DiscountGroupMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DiscountGroupMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DiscountGroupMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DiscountGroupMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DiscountGroupMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DiscountGroupMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DiscountGroupMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 根据group_id 查找group_id相同的记录
	 * @param string
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByGroupId(String groupid) throws Exception{
		return (List<PageData>)dao.findForList("DiscountGroupMapper.findByGroupId", groupid);
	}
}

