package cn.ncut.service.finance.serviceall.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.serviceall.ServiceProjectManager;
import cn.ncut.util.PageData;

/** 
 * 说明： 服务项目
 * 创建人：ljj
 * 创建时间：2016-12-16
 * @version
 */
@Service("serviceprojectService")
public class ServiceProjectService implements ServiceProjectManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ServiceProjectMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ServiceProjectMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ServiceProjectMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ServiceProjectMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ServiceProjectMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServiceProjectMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ServiceProjectMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据项目Id查询项目名称
	 * @param p
	 * @return
	 * @throws Exception 
	 */
	public String findNameById(PageData p) throws Exception {
		return (String) dao.findForObject("ServiceProjectMapper.findNameById", p);
	}

	@SuppressWarnings("unchecked")
	public List<PageData> queryProjectByCID(String cid) throws Exception{
		return (List<PageData>) dao.findForList("ServiceProjectMapper.queryProjectByCID", cid);
	}
	
}

