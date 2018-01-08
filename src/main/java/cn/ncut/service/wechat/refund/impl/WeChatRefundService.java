package cn.ncut.service.wechat.refund.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatRefund;
import cn.ncut.service.wechat.refund.WeChatRefundManager;

@Service("weChatRefundService")
public class WeChatRefundService implements WeChatRefundManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public WeChatRefund getWeChatRefundByOrderId(String orderId)
			throws Exception {
		return (WeChatRefund)dao.findForObject("WeChatRefundMapper.getWeChatRefundByOrderId", orderId);
	}
	
}
