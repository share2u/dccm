package cn.ncut.service.system.servicetime.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;
import cn.ncut.service.system.servicetime.ServiceTimeManager;

/**
 * 
*
*<p>Title:ServiceTimeService</p>
*<p>Description:医生排班</p>
*<p>Company:ncut</p> 
* @author lph 
* @date 2016-12-18下午3:55:39
*
 */
@Service("servicetimeService")
public class ServiceTimeService implements ServiceTimeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ServiceTimeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ServiceTimeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ServiceTimeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ServiceTimeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ServiceTimeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServiceTimeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ServiceTimeMapper.deleteAll", ArrayDATA_IDS);
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listStaffNameAndIdById(String sTORE_ID)
			throws Exception {
		
		return (List<PageData>) dao.findForList("ServiceTimeMapper.listStaffNameAndIdById", sTORE_ID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByStaffAndWeek(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ServiceTimeMapper.findByStaffAndWeek", pd);
	}

	@Override
	public void deleteByStaffAndWeek(PageData pd) throws Exception {
		dao.delete("ServiceTimeMapper.deleteByStaffAndWeek", pd);
	}

	@Override
	public void addEveryDay(PageData pd) throws Exception {
		dao.save("ServiceTimeMapper.addEveryDay",pd);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findByYearAndWeekOfYearStaff(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ServiceTimeMapper.findByYearAndWeekOfYearStaff", pd);
	}
}

