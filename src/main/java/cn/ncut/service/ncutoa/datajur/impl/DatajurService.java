package cn.ncut.service.ncutoa.datajur.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.util.PageData;
import cn.ncut.service.ncutoa.datajur.DatajurManager;

/**
 * 
*
*<p>Title:DatajurService</p>
*<p>Description:组织数据权限表</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-5下午8:44:04
*
 */
@Service("datajurService")
public class DatajurService implements DatajurManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DatajurMapper.save", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DatajurMapper.edit", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DatajurMapper.findById", pd);
	}
	
	/**取出某用户的组织数据权限
	 * @param pd
	 * @throws Exception
	 */
	public PageData getDEPARTMENT_IDS(String USERNAME)throws Exception{
		return (PageData)dao.findForObject("DatajurMapper.getDEPARTMENT_IDS", USERNAME);
	}
	
}

