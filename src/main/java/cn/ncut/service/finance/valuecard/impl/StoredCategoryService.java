package cn.ncut.service.finance.valuecard.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.finance.valuecard.StoredCategoryManager;

/**
 * 
*
*<p>Title:StoredCategoryService</p>
*<p>Description:储值卡管理</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-20下午7:37:33
*
 */
@Service("storedcategoryService")
public class StoredCategoryService implements StoredCategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("StoredCategoryMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("StoredCategoryMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("StoredCategoryMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StoredCategoryMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoredCategoryMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoredCategoryMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StoredCategoryMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 通过pd从储值卡详情表中找到对应员工的信息
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findStafflistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StoredCategoryMapper.findStafflistPage",page);
	}
	
	/**
	 * 根据条件查询会员
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryMember(Page page) throws Exception {
		return (List<PageData>) dao.findForList("MemberMapper.datalistPage", page);
	}

	
}

