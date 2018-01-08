package cn.ncut.service.finance.staffpoint.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.finance.staffpoint.StaffpointManager;
import cn.ncut.util.PageData;


@Service("staffpointService")
public class StaffpointService implements StaffpointManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public void save(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.save("StaffpointMapper.save", pd);
	}

	@Override
	public void delete(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.delete("StaffpointMapper", pd);
	}

	@Override
	public void edit(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("StaffpointMapper", pd);
	}

	@Override
	public List<PageData> list(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("StaffpointMapper.datalistPage", page);
		
	}

	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StaffpointMapper.listAll", pd);
	}


	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		
		dao.delete("StaffpointMapper.deleteAll", ArrayDATA_IDS);
		
	}

	@Override
	public String findBystaffid(String staffid) throws Exception {
		// 
		return (String) dao.findForObject("StaffpointMapper.findBystaffid", staffid);
	}

	@Override
	public Integer findBypoints(String itemid) throws Exception {
		// 
		return (Integer) dao.findForObject("StaffpointMapper.findBypoints", itemid);
	}


}
