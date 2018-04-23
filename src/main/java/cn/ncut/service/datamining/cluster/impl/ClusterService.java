package cn.ncut.service.datamining.cluster.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.datamining.ClusterManager;
import cn.ncut.util.PageData;

@Service("ClusterService")
public class ClusterService implements ClusterManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void save(PageData pd) throws Exception {
		dao.save("ClusterMapper.save", pd);

	}


	public void delete(PageData pd) throws Exception {
		dao.delete("ClusterMapper.delete", pd);

	}

//标明用户等级
	public void edit(PageData pd) throws Exception {
		//dao.update("ClusterMapper.edit", pd);
		dao.update("ClusterMapper.updatelevel", pd);
	}

	@Override
	public void updatenameandphone(PageData pd) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PageData> list(Page page) throws Exception {
		// 显示平均表格
		return (List<PageData>) dao.findForList("ClusterMapper.listAvg", page);
	}

	@Override
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ClusterMapper.listAll", pd);
	}

	@Override
	public PageData findById(Page page) throws Exception {
		// 查询某个label的level
		return (PageData) dao.findForObject("ClusterMapper.findById", page);
	}

	@Override
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PageData> listmenberandgroup(Page page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PageData> DiscountlistmemberlistPage(Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PageData> listCompleteMemberlistPage(Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageData findUserStorededAndPrestoreByUid(int uid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PageData> userPayQueryMemberlistPage(Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public List<PageData> findByLabel(Page pd) throws Exception {
		return (List<PageData>)dao.findForList("ClusterMapper.findByLabellistPage", pd);
	}


	@Override
	public List<PageData> listcluster(Page page) throws Exception {
		// 
		return (List<PageData>)dao.findForList("ClusterMapper.findresult", page);
	}


	@Override
	public List<PageData> findVIP(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ClusterMapper.findVIP", page);
	}


	@Override
	public List<PageData> listLiushi(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ClusterMapper.LiushilistPage", page);
	}


	@Override
	public List<PageData> listClassifier(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ClusterMapper.findclassifierresult", page);
	}


	@Override
	public List<PageData> findLiushi(Page page) throws Exception {
		return (List<PageData>)dao.findForList("ClusterMapper.findLiushi", page);
	}

}
