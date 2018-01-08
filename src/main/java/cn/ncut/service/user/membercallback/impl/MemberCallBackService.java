package cn.ncut.service.user.membercallback.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.user.membercallback.MemberCallBackManager;

/** 
 * 说明： 用户回电
 * 创建人：FH Q313596790
 * 创建时间：2016-12-19
 * @version
 */
@Service("membercallbackService")
public class MemberCallBackService implements MemberCallBackManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MemberCallBackMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MemberCallBackMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MemberCallBackMapper.edit", pd);
	}
	public void updatestatus(PageData pd)throws Exception{
		dao.update("MemberCallBackMapper.updatestatus", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberCallBackMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MemberCallBackMapper.listAll", pd);
	}
	/**
	 * 自己写的列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listStaffAndStore(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberCallBackMapper.listStaffAndStorelistPage", page);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberCallBackMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MemberCallBackMapper.deleteAll", ArrayDATA_IDS);
	}
/**
 * 自己写的
 */
	public PageData findAllById(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("MemberCallBackMapper.findAllById", pd);
	}
	
}

