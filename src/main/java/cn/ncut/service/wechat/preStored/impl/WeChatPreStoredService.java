package cn.ncut.service.wechat.preStored.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatPreStore;
import cn.ncut.service.wechat.preStored.WeChatPreStoredManager;
@Service("weChatPreStoredService")
public class WeChatPreStoredService implements WeChatPreStoredManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public Double getTotalPreStored(Integer uId) throws Exception {
		Double totalPreStored = (Double) dao.findForObject("WeChatPreStoredMapper.getTotalPreStored", uId);
		if(null == totalPreStored){
			totalPreStored = 0.00;
		}
		return totalPreStored;
	}
	
	/**
	 * modified by scott
	 * */
	@Override
	public WeChatPreStore getUserPreStoreByUid(Integer uId) throws Exception {
		return (WeChatPreStore)dao.findForObject("WeChatPreStoredMapper.getUserPreStoreByUid",uId);
	}

	@Override
	public void updateUserPreStoreByUid(WeChatPreStore weChatPreStore)
			throws Exception {
		dao.update("WeChatPreStoredMapper.updateUserPreStoreByUid", weChatPreStore);
	}

}
