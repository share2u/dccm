package cn.ncut.service.user.storeddetail.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.user.storeddetail.StoredDetailManager;

/** 
 * 说明： 储值卡明细
 * 创建人：FH Q313596790
 * 创建时间：2016-12-26
 * @version
 */
@Service("storeddetailService")
public class StoredDetailService implements StoredDetailManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("StoredDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("StoredDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("StoredDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StoredDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StoredDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StoredDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StoredDetailMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> listdetail(Page page) throws Exception {
	
		return (List<PageData>)dao.findForList("StoredDetailMapper.storeddetaillistPage", page);
	}

	@Override
	public List<PageData> selectstoredAll(PageData pd) throws Exception {
		
		return (List<PageData>)dao.findForList("StoredDetailMapper.selectstoreddetail", pd);
	}

	@Override
	public List<PageData> selectstoreddetailGroups(PageData pd)
			throws Exception {
		 return (List<PageData>) dao.findForList("StaticticsStoreddetailMapper.selectStoreddetail", pd);
			
		}

	@Override
	public List countstored(PageData pd) throws Exception {
		
		return (List) dao.findForList("StaticticsStoreddetailMapper.countCategory", pd);
		
	}

	@Override
	public List<PageData> selectStoredCategory(PageData pd) throws Exception {
       return (List<PageData>) dao.findForList("StaticticsStoreddetailMapper.selectStoredCategory", pd);
		
	}



	
}

