package cn.ncut.service.wechat.orderQuery.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.dao.wechat.WeChatOrderCheckQueryDao;
import cn.ncut.entity.wechat.pojo.OrderCheckQuery;
import cn.ncut.entity.wechat.pojo.OrderCheckQueryResponse;
import cn.ncut.entity.wechat.pojo.WeChatPayHistory;
import cn.ncut.service.wechat.orderQuery.WeChatOrderCheckQueryManager;
import cn.ncut.util.wechat.RandomUtil;
import cn.ncut.util.wechat.SignGenerateAndCheckUtil;
import cn.ncut.util.wechat.SpringPropertyResourceReader;
import cn.ncut.util.wechat.TypeConvertUtil;

@Service("orderCheckQueryService")
public class OrderCheckQueryService implements WeChatOrderCheckQueryManager {
	private static Logger logger = LoggerFactory.getLogger("weChatService");

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name = "weChatOrderCheckQueryDaoImpl")
	private WeChatOrderCheckQueryDao weChatOrderCheckQueryDao;

	@SuppressWarnings("unchecked")
	@Override
	public void checkOrderStatus() {

		logger.debug("--- 查询支付状态为2的记录 ---");
		List<WeChatPayHistory> payHistories = null;
		List<WeChatPayHistory> payHistoryWillChange = new ArrayList<WeChatPayHistory>();
		try {
			/**
			 * 查询支付历史表中所有订单状态为2的订单
			 * */
			payHistories = (List<WeChatPayHistory>) dao
					.findForList(
							"WeChatOrderCheckQueryMapper.getAllPayHistoryOrdersByStatus",
							2);

			// 构造微信请求对象
			OrderCheckQuery orderCheckQuery = null;
			for (WeChatPayHistory payHistory : payHistories) {
				orderCheckQuery = new OrderCheckQuery();
				orderCheckQuery.setAppid(SpringPropertyResourceReader
						.getProperty("appId"));
				orderCheckQuery.setMch_id(SpringPropertyResourceReader
						.getProperty("mch_id"));
				orderCheckQuery.setOut_trade_no(payHistory.getParentOrderId());
				orderCheckQuery.setNonce_str(RandomUtil
						.generateMixedCharAndDigit(32));
				orderCheckQuery.setSign_type("MD5");
				String sign = SignGenerateAndCheckUtil.createSign("UTF-8",
						SignGenerateAndCheckUtil
								.sortBeanAttributes(orderCheckQuery));
				orderCheckQuery.setSign(sign);
				
				// 请求微信订单查询接口
				String requestUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
				OrderCheckQueryResponse orderCheckQueryResponse = TypeConvertUtil
						.transformBeanToBeanFactory(requestUrl,
								orderCheckQuery, OrderCheckQueryResponse.class);
				
				logger.debug("--- orderCheckQueryService 异常订单核查	开始 ---");
				// 判断当前订单是否支付
				if("SUCCESS".equals(orderCheckQueryResponse.getResult_code())){
					logger.debug("--- 通讯成功 ---");
					if("SUCCESS".equals(orderCheckQueryResponse.getResult_code())){
						logger.debug("--- 请求业务成功 ---");
						if("SUCCESS".equals(orderCheckQueryResponse.getTrade_state())){
							logger.debug("--- 当前订单用户已支付,但是由于某些原因未修改数据库订单表状态 ---");
							payHistoryWillChange.add(payHistory);
						}else{
							logger.debug("--- 当前订单用户未支付 ---");
							logger.debug(payHistory.getChildOrderId());
						}
					}
				}
			}
			
			// 打印系统出错订单
			logger.debug("--- 用户已支付,但是系统未修改订单状态的订单信息是 ---");
			logger.debug(payHistoryWillChange.toString());
			
		} catch (Exception e) {
			logger.debug("--- 查询历史表中所有订单状态为2的订单失败,异常信息如下 ---");
			logger.debug(e.getMessage());
		}

		/**
		 * 根据返回的信息,判断是否更新相关表
		 * */
		if(payHistoryWillChange.size() > 0){
			// 当前存在错误的订单
			this.weChatOrderCheckQueryDao.checkOrderStatus(payHistories);
		}
		
		logger.debug("--- orderCheckQueryService 异常订单核查	结束 ---");
	}
}
