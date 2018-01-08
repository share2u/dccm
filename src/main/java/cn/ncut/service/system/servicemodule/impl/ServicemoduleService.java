package cn.ncut.service.system.servicemodule.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.system.servicemodule.ServicemoduleManager;

/** 
 * 说明： 版块管理
 * 创建人：FH Q313596790
 * 创建时间：2016-12-09
 * @version
 */
@Service("servicemoduleService")
public class ServicemoduleService implements ServicemoduleManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ServicemoduleMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ServicemoduleMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ServicemoduleMapper.edit", pd);
	}
	
	/**修改不上传图片
	 * @param pd
	 * @throws Exception
	 */
	public void editnoimg(PageData pd)throws Exception{
		dao.update("ServicemoduleMapper.editnoimg", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ServicemoduleMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ServicemoduleMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServicemoduleMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ServicemoduleMapper.deleteAll", ArrayDATA_IDS);
	}

	
	/**
	 * 根据板块id查询该板块
	 */
	@Override
	public PageData queryModuleById(String id) throws Exception {
		return (PageData) dao.findForObject("ServicemoduleMapper.querymoduleById", id);
	}
	
	/**列表(有效的板块)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll0(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ServicemoduleMapper.listAll0", pd);
	}
	
}

