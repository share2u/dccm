package cn.ncut.service.system.ncutlog.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.Jurisdiction;
import cn.ncut.util.PageData;
import cn.ncut.util.Tools;
import cn.ncut.util.UuidUtil;
import cn.ncut.service.system.ncutlog.NcutlogManager;

/**
 * 
*
*<p>Title:NcutlogService</p>
*<p>Description:操作日志记录</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-6上午10:09:06
*
 */
@Service("ncutlogService")
public class NcutlogService implements NcutlogManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(String USERNAME, String CONTENT,String IP)throws Exception{
		PageData pd = new PageData();
		pd.put("USERNAME", USERNAME);					//用户名
		pd.put("CONTENT", CONTENT);						//事件
		pd.put("NCUTLOG_ID", UuidUtil.get32UUID());		//主键
		pd.put("CZTIME", Tools.date2Str(new Date()));	//操作时间
		pd.put("IP", IP);
		dao.save("NcutlogMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("NcutlogMapper.delete", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("NcutlogMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NcutlogMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("NcutlogMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("NcutlogMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 根据备注和IP插入日志
	 */
	@Override
	public void save(String CONTENT,String IP) throws Exception {
		PageData pd = new PageData();
		pd.put("USERNAME", Jurisdiction.getUsername());					//用户名
		pd.put("CONTENT", CONTENT);						//事件
		pd.put("NCUTLOG_ID", UuidUtil.get32UUID());		//主键
		pd.put("CZTIME", Tools.date2Str(new Date()));	//操作时间
		pd.put("IP", IP);
		dao.save("NcutlogMapper.save", pd);
		
	}
	
}

