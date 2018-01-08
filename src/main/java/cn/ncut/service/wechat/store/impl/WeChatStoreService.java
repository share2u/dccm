package cn.ncut.service.wechat.store.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatStore;
import cn.ncut.service.wechat.store.WeChatStoreManager;
import cn.ncut.util.PageData;

@Service("weChatStoreService")
public class WeChatStoreService implements WeChatStoreManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	@Override
	public List<WeChatStore> getStoresBymId(String mId) throws Exception {
		List<WeChatStore> stores = (List<WeChatStore>) dao.findForList("WeChatStoreMapper.getStoresBymId", mId);
		return stores;
	}

	@Override
	public WeChatStore getStoreDetailByStoreId(String storeId) throws Exception {
		WeChatStore store = (WeChatStore) dao.findForObject("WeChatStoreMapper.getStoreDetailByStoreId", storeId);
		return store;
	}
	@Override
	public List<WeChatStore> getStoresBySome(String queryParam) throws Exception {
		
		List<WeChatStore> stores = (List<WeChatStore>) dao.findForList("WeChatStoreMapper.getStoresBySome", queryParam);
		return stores;
	}
	@Override
	public List<PageData> getStoreDoctersByStoreId(String storeId) throws Exception {
		PageData pd = new PageData();
		pd.put("storeId", storeId);
		pd.put("sub2storeId", storeId.substring(0, 2));
		return (List<PageData>) dao.findForList("WeChatStoreMapper.getStoreDoctersByStoreId", pd);
		
	}
	@Override
	public List<PageData> getCommentsByStoreId(String storeId) throws Exception {
		return (List<PageData>) dao.findForList("WeChatStoreMapper.getCommentsByStoreId", storeId);
	}
	@Override
	public void sendTelToStore(PageData pageData) throws Exception {
		dao.save("WeChatStoreMapper.sendTelToStoreCallback", pageData);
	}
	@Override
	public PageData getDoctorDetailById(String staffId) throws Exception {
		return (PageData) dao.findForObject("WeChatStoreMapper.getDoctorDetailById", staffId);
	}

	@Override
	public List<PageData> getServiceProjectsById(PageData pd) throws Exception {
		return  (List<PageData>) dao.findForList("WeChatStoreMapper.getServiceProjectsById", pd);
	}


}
