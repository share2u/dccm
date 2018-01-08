package cn.ncut.service.wechat.credit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.service.wechat.credit.WeChatCreditManager;
import cn.ncut.util.PageData;

@Service("weChatCreditService")
public class WeChatCreditService implements WeChatCreditManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public Integer getTotalCredit(Integer uId) throws Exception {
		Integer totalCredit = (Integer) dao.findForObject("WeChatCreditMapper.getTotalCredit", uId);
		if(null == totalCredit){
			totalCredit = 0;
		}
		return totalCredit;
	}
	@Override
	public List<PageData> getCreditDetails(Integer uId) throws Exception {
		List<PageData> credits =  (List<PageData>) dao.findForList("WeChatCreditMapper.getCreditDetails", uId);
		return credits;
	}


}
