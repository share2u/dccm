package cn.ncut.service.finance.customappoint.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.customappoint.CustomappointManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 预约表
 * 创建人：FH Q313596790
 * 创建时间：2017-01-03
 * @version
 */
@Service("customappointService")
public class CustomappointService implements CustomappointManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CustomappointMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("CustomappointMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CustomappointMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CustomappointMapper.datalistPage", page);
	}
	
	
	/**列表查看今天预约人员
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listToday(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CustomappointMapper.todaylistPage", page);
	}
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustomappointMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CustomappointMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CustomappointMapper.deleteAll", ArrayDATA_IDS);
	}
	
	
	/**查询这个时段预约人数
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> querySum(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CustomappointMapper.querySum", pd);
	}

	@Override
	public PageData findCount(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CustomappointMapper.findCount", pd);
	}

	@Override
	public PageData findByOrderId(PageData pd) throws Exception {
		return (PageData)dao.findForObject("CustomappointMapper.findByOrderId", pd);
	}
}

