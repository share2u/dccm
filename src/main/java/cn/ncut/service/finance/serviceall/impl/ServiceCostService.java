package cn.ncut.service.finance.serviceall.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.serviceall.ServiceCostManager;
import cn.ncut.util.PageData;


/** 
 * 说明： 定价
 * 创建人：ljj
 * 创建时间：2016-12-19
 * @version
 */
@Service("servicecostService")
public class ServiceCostService implements ServiceCostManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ServiceCostMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ServiceCostMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ServiceCostMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ServiceCostMapper.listservicecostlistPage", page);
	}
	
	/**列表有效的(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listValidAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ServiceCostMapper.listValidAll", pd);
	}
	

	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ServiceCostMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServiceCostMapper.findById", pd);
	}
	
	
	public PageData findById2(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ServiceCostMapper.findById", pd);
	}
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ServiceCostMapper.deleteAll", ArrayDATA_IDS);
	}
	/**
	 * 自己写的 根据员工编号查询项目和收费价格
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findServiceAndCostByStaff_id(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("ServiceCostMapper.findServiceAndCostByStaff_id", pd);
		
	}
	public List<PageData> queryPriceByPID(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("ServiceCostMapper.queryPriceByPID", pd);
	}
	
	public List<PageData> queryPriceByPIDAndStaff(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("ServiceCostMapper.queryPriceByPIDAndStaff", pd);
	}
	
	/**
	 * 根据该记录的STORE_ID、STAFF_ID、PID查出之前生效的相同的项目
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryProjectByThreeParameters(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ServiceCostMapper.queryProjectByThreeParameters", pd);
	}
	
	/**
	 * 批量置为无效
	 * @param list
	 * @throws Exception
	 */
	public void batchUpdateStatusToInvalid(List<PageData> list) throws Exception{
		dao.batchUpdate("ServiceCostMapper.batchUpdateStatusToInvalid", list);
	}
}

