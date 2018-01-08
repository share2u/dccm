package cn.ncut.service.user.usercategory.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.user.usercategory.UsercategoryManager;

/** 
 * 说明： 会员类型
 * 创建人：FH Q313596790
 * 创建时间：2016-12-21
 * @version
 */
@Service("usercategoryService")
public class UsercategoryService implements UsercategoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("UsercategoryMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("UsercategoryMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("UsercategoryMapper.edit", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editProportion(PageData pd)throws Exception{
		dao.update("UsercategoryMapper.editProportion", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UsercategoryMapper.datalistPagenew", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UsercategoryMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllCategory(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UsercategoryMapper.listAllCategory", pd);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllParentCategory(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UsercategoryMapper.listAllParentCategory", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listChildCategoryByParent(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UsercategoryMapper.listChildCategoryByParent", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UsercategoryMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UsercategoryMapper.deleteAll", ArrayDATA_IDS);
	}

	public Double findProportion(PageData pd)throws Exception {
		// TODO Auto-generated method stub
		return (Double)dao.findForObject("UsercategoryMapper.queryProportion", pd);
	}

	
}

