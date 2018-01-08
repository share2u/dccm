package cn.ncut.service.wechat.payHistory.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatPayHistory;
import cn.ncut.service.wechat.payHistory.WeChatPayHistoryManager;

@Service("weChatPayHistoryService")
public class WeChatPayHistoryService implements WeChatPayHistoryManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public WeChatPayHistory getPayHistoryByPayHistoryId(Integer payHistoryId)
			throws Exception {
		return (WeChatPayHistory)dao.findForObject("WeChatOrderMapper.getPayHistoryByPayHistoryId", payHistoryId);
	}

	@Override
	public WeChatPayHistory getPayHistoryByOrderId(String orderId)
			throws Exception {
		return (WeChatPayHistory)dao.findForObject("WeChatOrderMapper.getPayHistoryByOrderId", orderId);
	}

	@Override
	public Integer savePayHistory(Map<String, Object> map)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		/**
		 * 订单号,支付方式,优惠券,提交给微信的商户订单号,实际支付金额
		 * */
		String parentOrderId = (String)map.get("parentOrderId");
		String childOrderId = (String)map.get("orderId");
		String payMethod = (String)map.get("payMethod");
		String discountId = (String)map.get("discountId");
		String prePayMoney = (String)map.get("prePayMoney");
		String openId = (String)map.get("openId");
		
		
		/**
		 * 记录用户的支付信息,包含以下
		 * 用户编号,商户订单号,所有订单号,实付金额,支付状态,支付时间
		 * */
		WeChatPayHistory weChatPayHistory = new WeChatPayHistory();
		weChatPayHistory.setOpenId(openId);
		weChatPayHistory.setParentOrderId(parentOrderId);
		weChatPayHistory.setPayMoney(prePayMoney);
		weChatPayHistory.setStatus(2);
		weChatPayHistory.setPayTime(sdf.format(date));
		weChatPayHistory.setPayMethod(Integer.valueOf(payMethod));
		if(discountId != null){
			weChatPayHistory.setDiscountId(discountId);
		}
		
		weChatPayHistory.setChildOrderId(childOrderId);
		
		dao.save("WeChatPayHistoryMapper.savePayHistory", weChatPayHistory);
		Integer payHistoryId = weChatPayHistory.getPayHistoryId();
		return payHistoryId;
	}

	@Override
	public void updatePayHistoryStatus(WeChatPayHistory weChatPayHistory)
			throws Exception {
		dao.update("WeChatPayHistoryMapper.updatePayHistoryStatus", weChatPayHistory);
	}
}
