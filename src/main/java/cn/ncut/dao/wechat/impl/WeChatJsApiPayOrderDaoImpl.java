package cn.ncut.dao.wechat.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.gson.Gson;

import cn.ncut.dao.wechat.WeChatJsApiPayOrderDao;
import cn.ncut.entity.wechat.pojo.PayResultNotify;
import cn.ncut.entity.wechat.pojo.WeChatAppoint;
import cn.ncut.entity.wechat.pojo.WeChatOrder;
import cn.ncut.entity.wechat.pojo.WeChatPayDetail;
import cn.ncut.entity.wechat.pojo.WeChatPayHistory;
import cn.ncut.entity.wechat.pojo.WeChatStoredDetailCustom;
import cn.ncut.entity.wechat.pojo.WeChatUser;
import cn.ncut.entity.wechat.pojo.WeChatUserDiscount;
import cn.ncut.entity.wechat.pojo.WeChatUserStoredCard;
import cn.ncut.util.DateUtil;
import cn.ncut.util.UuidUtil;
import cn.ncut.util.wechat.PrimaryKeyGenerator;
import cn.ncut.util.wechat.TimeAdjust;

@Repository("weChatJsApiPayOrderDaoImpl")
public class WeChatJsApiPayOrderDaoImpl implements WeChatJsApiPayOrderDao {
	private static Logger logger = LoggerFactory.getLogger("weChatService");
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;
	
	// spring提供的模板类简化事务管理
	@Resource(name = "txTemplate")
	private TransactionTemplate txTemplate;
	
	private WeChatOrder process(WeChatOrder weChatOrder){
		DecimalFormat df = new DecimalFormat("######0.00");
		if(weChatOrder != null && weChatOrder.getOrderMoney() != null && weChatOrder.getProportion() != null){
			BigDecimal orderMoney = new BigDecimal(weChatOrder.getOrderMoney());
			BigDecimal proportion = new BigDecimal(weChatOrder.getProportion());
			// discountId 暂时替换为优惠券金额
			BigDecimal discountAmount = new BigDecimal("0.00");
			if(weChatOrder.getDiscountId() != null){
				discountAmount = new BigDecimal(weChatOrder.getDiscountId());
			}
 			Double payMoney = (orderMoney.multiply(proportion)).subtract(discountAmount).doubleValue();	
 			// 判断下支付金额是否小于零
 			if(payMoney < 0.00000001){
 				weChatOrder.setPayMoney("0.00");
 			}else{
 				weChatOrder.setPayMoney(df.format(payMoney));
 			}
 		}
		return weChatOrder;
	}

	@Override
	public void processOrderNotifyUsedTransaction(PayResultNotify payResultNotify) {
		logger.debug("--- 微信支付 start ---");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			date = sdf.parse(payResultNotify.getTime_end());
		} catch (ParseException e1) {
			logger.debug("--- 日期解析出现异常 ---");
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String,Object> map = this.parsePayResultNotify(payResultNotify);
		
		// 判断当前是否是第一次接收到通知
		final Integer payHistoryId = Integer.valueOf((String)map.get("payHistoryId"));
		WeChatPayHistory weChatPayHistory = this.getPayHistoryByPayHistoryId(payHistoryId);
		
		if(weChatPayHistory != null && weChatPayHistory.getStatus() != 2){
			logger.debug("--- 当前并非是第一次接受微信通知 ---");
			return;
		}
		
		
		/**
		 * 订单号,支付方式,优惠券,提交给微信的商户订单号,实际支付金额
		 * */
		final String childOrderId = weChatPayHistory.getChildOrderId();
		final String parentOrderId = weChatPayHistory.getParentOrderId();
		final String payMethod = String.valueOf(weChatPayHistory.getPayMethod());
		final String discountId = weChatPayHistory.getDiscountId();
		final String payTime = sdf.format(date);
		
		logger.debug("--- 开始执行数据库更新操作,订单表,预约表,支付明细表 ---");
		int retVal = 1;
		retVal = (int)txTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try{
					commonOperationOn3Table(parentOrderId,childOrderId, discountId, payMethod, payTime);
					logger.debug("--- 执行数据库更新操作,订单表,预约表,支付明细表 成功---");
				}catch(Exception e){
					status.setRollbackOnly();
					logger.debug("--- 执行数据库更新操作,订单表,预约表,支付明细表失败 ---");
					return 0;
				}
				return 1;
			}
		});
		
		
		if(retVal == 0){
			logger.debug("--- 更新用户支付历史表,将其状态置为异常,通知客服处理 ---");
			weChatPayHistory.setStatus(1);
		}else{
			weChatPayHistory.setStatus(0);
		}
		
		updatePayHistoryStatus(weChatPayHistory);
		logger.debug("--- 更新用户支付历史表成功 ---");
		
		logger.debug("--- 微信支付 end ---");
		
	}
	
	/**
	 * 查询用户支付历史记录
	 * */
	public WeChatPayHistory getPayHistoryByOrderId(String outTradeNo){
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		return session.selectOne("WeChatPayHistoryMapper.getPayHistoryByOrderId", outTradeNo);
	}
	
	public WeChatPayHistory getPayHistoryByPayHistoryId(Integer payHistoryId){
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		return session.selectOne("WeChatPayHistoryMapper.getPayHistoryByPayHistoryId", payHistoryId);
	}
	
	/**
	 * 保存用户支付历史
	 * */
	public WeChatPayHistory savePayHistory(Map<String,Object> map) {	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		/**
		 * 订单号,支付方式,优惠券,提交给微信的商户订单号,实际支付金额
		 * */
		String orderId = (String)map.get("orderId");
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
		if(orderId.indexOf(",") != -1){
			weChatPayHistory.setParentOrderId(orderId.substring(0,orderId.indexOf(",")));
		}else{
			weChatPayHistory.setParentOrderId(orderId);
		}
		weChatPayHistory.setPayMoney(prePayMoney);
		weChatPayHistory.setStatus(2);
		weChatPayHistory.setPayTime(sdf.format(date));
		weChatPayHistory.setPayMethod(Integer.valueOf(payMethod));
		if(discountId != null){
			weChatPayHistory.setDiscountId(discountId);
		}
		
		weChatPayHistory.setChildOrderId(orderId);
		
		logger.debug("--- weChatPayHistory信息如下 ---");
		logger.debug(weChatPayHistory.toString());
		
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		try{
			session.insert("WeChatPayHistoryMapper.savePayHistory", weChatPayHistory);
			session.commit();
		}catch(Exception e){
			logger.debug("--- 插入用户支付历史失败 ---");
			logger.debug(e.getMessage());
			e.printStackTrace();
		}finally{
			session.close();
		}
		return weChatPayHistory;
	}
	
	/**
	 * 更新用户支付历史状态
	 * */
	public void updatePayHistoryStatus(WeChatPayHistory weChatPayHistory){
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		try{
			session.update("WeChatPayHistoryMapper.updatePayHistoryStatus", weChatPayHistory);
			session.commit();
		}catch(Exception e){
			logger.debug("--- 更新用户支付历史失败 ---");
			logger.debug(e.getMessage());
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	/**
	 * 解析微信返回的通知，提取信息
	 * @param payResultNotify 
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object> parsePayResultNotify(PayResultNotify payResultNotify){
		logger.debug("--- 开始解析微信通知 payResultNotify ---");
		logger.debug("--- 微信通知 payResultNotify 原始信息如下 ---");
		logger.debug(payResultNotify.toString());
		
		Gson gson = new Gson();
		Map<String,Object> map = null;
		
		logger.debug("--- 微信服务器传回的附件内容是 payResultNotify.getAttach() ---");
		logger.debug(payResultNotify.getAttach());
		
		// 获取attach中的支付历史表主键 payHistoryId
		map = gson.fromJson(payResultNotify.getAttach(),Map.class);
		
		logger.debug("--- 处理之后的map信息是 ---");
		logger.debug(map.toString());
				
		return map;
	}
	
	public void commonOperationOn3Table(String parentOrderId,String childOrderId,String discountId,String payMethod,String payTime){
		WeChatOrder weChatOrder = null;
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		try{
			String[] childOrderIds = childOrderId.split(",");
			for(String orderId : childOrderIds){
				logger.debug("--- 当前的订单编号是 ---");
				logger.debug(orderId);
				weChatOrder = (WeChatOrder)session.selectOne("WeChatOrderMapper.getOrderByOrderId", 
						orderId);
				if(weChatOrder != null){
					logger.debug("--- 订单信息如下 ---");
					logger.debug(weChatOrder.toString());
				}
				
				// 判断当前通知是否是第一次通知,判断的依据是订单的状态信息;未支付的订单状态为"0"
				if(weChatOrder != null && weChatOrder.getOrderStatus() != 0){
					logger.debug("--- 当前订单已支付 ---");
					return;
				}
				
				// 设置每个订单的实际支付金额
				weChatOrder = this.process(weChatOrder);
				logger.debug("--- 本笔订单实际的字符金额是 ---");
				logger.debug(weChatOrder.getPayMoney());
				
				logger.debug("--- 开始计算订单实际金额 ---");
				if(discountId != null){
					// 更新用户优惠券的状态为已使用
					logger.debug("--- 更新用户优惠券的状态为已使用 ---");
					WeChatUserDiscount weChatUserDiscount = new WeChatUserDiscount();
					weChatUserDiscount.setId(Integer.valueOf(discountId));
					weChatUserDiscount.setIsUsed(1);
					session.update("WeChatUserDiscount.updateUserDiscountStatus", weChatUserDiscount);
					
					DecimalFormat df = new DecimalFormat("######0.00");
					// 往第一个订单中插入优惠券使用情况
					if(orderId.equals(parentOrderId)){
						weChatOrder.setDiscountId(discountId);
						weChatUserDiscount = session.selectOne("WeChatUserDiscount.getUserDiscountByDiscountId", discountId);
						logger.debug("--- 当前返回的weChatUserDiscount的信息如下 ---");
						logger.debug(weChatUserDiscount.toString());
						BigDecimal payMoney = new BigDecimal(weChatOrder.getOrderMoney());
						BigDecimal discountAmount = new BigDecimal(weChatUserDiscount.getDiscount().getDiscountAmount().toString());
						logger.debug("--- 优惠券优惠的金额是 ---");
						logger.debug(discountAmount.toString());
						if(payMoney.compareTo(discountAmount) < 1){
							// 当优惠券的金额大于该笔订单的支付额度时,金额设置为0.01;微信H5支付时需要设置金额..
							weChatOrder.setDiscountId(df.format(payMoney.doubleValue()));
							payMoney = new BigDecimal("0.01");
						}else{
							// 重新设置用户优惠券余额
							weChatOrder.setDiscountId(df.format(discountAmount.doubleValue()));
							payMoney = payMoney.subtract(discountAmount);
						}
						logger.debug("--- 最终支付的金额是 ---");
						logger.debug(payMoney.toString());
						
						weChatOrder.setPayMoney(df.format(payMoney.doubleValue()));
					}
				}
				logger.debug("--- 结束计算订单实际金额 ---");
				
				// 更新订单记录
				logger.debug("--- 更新用户订单 ---");
				weChatOrder.setOrderStatus(2);
				logger.debug(weChatOrder.toString());
				session.update("WeChatOrderMapper.updateOrderStatus", weChatOrder);
				
				// 在预约表中插入一条记录
				logger.debug("--- 在预约表中插入一条记录 ---");
				WeChatAppoint weChatAppoint = new WeChatAppoint();
				weChatAppoint.setCustomAppointId(UuidUtil.get32UUID());
				weChatAppoint.setOrderId(weChatOrder.getOrderId());
				weChatAppoint.setuId(weChatOrder.getuId());
				
				weChatAppoint.setServiceStaffId(weChatOrder.getServiceStaffId());
				String code = weChatOrder.getOrderId();
				weChatAppoint.setAppointCode(Integer.valueOf(code.substring(code.length() - 8, code.length())));
				
				// 一次预约多个订单时,仅仅只有第一个订单有预约时间,其他的订单没有
				if(weChatOrder.getRecommendTime() != null && !weChatOrder.getRecommendTime().equals("")){
					weChatAppoint.setAppointTime(weChatOrder.getRecommendTime());
					weChatAppoint.setExpireTime(TimeAdjust.addDateMinut(weChatOrder.getRecommendTime(), 
							60, "yyyy-MM-dd HH:mm:ss"));
					try {
						weChatAppoint.setExpireTime(DateUtil.caculateGuoqiTime(weChatOrder.getRecommendTime()));
					} catch (Exception e) {
						logger.debug("--- 设置过期时间出现异常,异常信息如下 ---");
						logger.debug(e.getMessage());
					}
				}
				
				logger.debug(weChatAppoint.toString());
				session.insert("WeChatAppointMapper.saveAppoint", weChatAppoint);
							
				// 在支付明细表中插入一条记录
				logger.debug("--- 在支付明细表中插入一条记录 ---");
				WeChatPayDetail weChatPayDetail = new WeChatPayDetail();
				weChatPayDetail.setPayDetailId(UuidUtil.get32UUID());
				weChatPayDetail.setuId(weChatOrder.getuId());
				weChatPayDetail.setOrderId(weChatOrder.getOrderId());
				weChatPayDetail.setOrderMoney(weChatOrder.getOrderMoney());
				weChatPayDetail.setPayMoney(weChatOrder.getPayMoney());
				weChatPayDetail.setPayMethod(Integer.valueOf(payMethod));
				
				weChatPayDetail.setPayTime(payTime);
				logger.debug(weChatPayDetail.toString());
				session.insert("WeChatPayDetailMapper.savePayDetail", weChatPayDetail);
				
				session.flushStatements();
				session.commit();
				session.clearCache();
			}
		} finally {
			logger.debug("--- 关闭session ---");
			session.close();
		}
	}
	
	@Override
	public String storedCardPayOrder(Map<String, Object> map) {
		logger.debug("--- 储值卡支付 start ---");
		
		Map<String,String> result = new HashMap<String,String>();
		Gson gson = new Gson();
		
		String orderId = (String)map.get("orderId");
		final String payMethod = (String)map.get("payMethod");
		final String totalMoney = (String)map.get("prePayMoney");
		final String discountId = (String)map.get("discountId");
		Integer uId = (Integer)map.get("uId");
		
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		WeChatUser weChatUser = session.selectOne("WeChatUserMapper.getWeChatUserByuId", uId);
		if(weChatUser != null){
			map.put("openId",weChatUser.getOpenId());
		}
		
		// 更新用户储值账户余额
		WeChatUserStoredCard weChatUserStoredCard = session.selectOne("getUserStoredCardByUid",uId);
		BigDecimal remainMoney = weChatUserStoredCard.getRemainMoney();
		BigDecimal remainPoints = weChatUserStoredCard.getRemainPoints();
		BigDecimal validMoney = remainMoney.add(remainPoints);
		logger.debug("--- 当前可用余额是 ---");
		logger.debug(validMoney.toString());
		BigDecimal prePayMoney = new BigDecimal(totalMoney);
		final BigDecimal whetherRecordPoint = new BigDecimal(remainMoney.toString());
		BigDecimal caculateMoneyOfPerOrder = new BigDecimal(remainMoney.toString());
		if(validMoney.compareTo(prePayMoney) < 0){
			result.put("errorCode", "fail");
			result.put("errorDes", "账户余额不足,请充值");
			// 关闭数据库连接
			session.close();
			return gson.toJson(result);
		}
		
		if(remainMoney.compareTo(prePayMoney) < 0){
			// 若储值卡余额不足,扣除点数
			remainPoints = (remainPoints.add(remainMoney)).subtract(prePayMoney);
			remainMoney = new BigDecimal("0.00"); 
			
		}else{
			// 若储值卡余额足够,扣除余额
			remainMoney = remainMoney.subtract(prePayMoney);
		}
		
		weChatUserStoredCard.setRemainMoney(remainMoney);
		weChatUserStoredCard.setRemainPoints(remainPoints);
		
		// 记录用户支付历史
		WeChatPayHistory weChatPayHistory = this.savePayHistory(map);
		
		final List<String> orderIds = new ArrayList<String>(Arrays.asList(orderId.split(",")));
		
		logger.debug("--- 开始执行数据库更新操作,订单表,预约表,支付明细表 ---");
		int retVal = 1;
		retVal = (int)txTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try{
					commonOperationOn3Table(orderIds,discountId, payMethod, whetherRecordPoint);
					logger.debug("--- 执行数据库更新操作,订单表,预约表,支付明细表 成功---");
				}catch(Exception e){
					status.setRollbackOnly();
					logger.debug("--- 执行数据库更新操作,订单表,预约表,支付明细表失败 ---");
					return 0;
				}
				return 1;
			}
		});
		
		if(retVal == 0){
			logger.debug("--- 更新用户支付历史表,将其状态置为异常,通知客服处理 ---");
			weChatPayHistory.setStatus(1);
			logger.debug("--- 更新用户支付历史表成功 ---");
			result.put("errorCode", "fail");
			result.put("errorDes", "支付出现异常,请联系客服处理当前订单");
		}else{
			weChatPayHistory.setStatus(0);
			result.put("errorCode", "success");
			result.put("errorDes", "支付成功");
			
			try{
				// 更新账户储值卡余额与点数
				session.update("WeChatUserStoredCardMapper.updateUserStoredCardRemainMoneyAndPoints", weChatUserStoredCard);
				session.commit();
				// 写入储值卡记录
				saveStoreDetail(caculateMoneyOfPerOrder,orderIds);
			}catch(Exception e){
				session.rollback();
				logger.debug("--- 更新用户储值卡账户失败,异常信息如下 ---");
				logger.debug(e.getMessage());
			}finally{
				session.close();
			}
		}
		
		updatePayHistoryStatus(weChatPayHistory);
		
		logger.debug("--- 储值卡支付 end ---");
		
		return gson.toJson(result);
	}
	
	private void commonOperationOn3Table(List<String> orderIds,String discountId,String payMethod,BigDecimal whetherRecordPoint){
		int i = 0;
		WeChatOrder weChatOrder = null;
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		try{
			for(String orderId : orderIds){
				logger.debug("--- 当前的订单编号是 ---");
				logger.debug(orderId);
				weChatOrder = (WeChatOrder)session.selectOne("WeChatOrderMapper.getOrderByOrderId", 
						orderId);
				
				// 判断当前通知是否是第一次通知,判断的依据是订单的状态信息;未支付的订单状态为"0"
				if(weChatOrder != null && weChatOrder.getOrderStatus() != 0){
					logger.debug("--- 当前订单已支付 ---");
					break;
				}
				
				// 设置每个订单的实际支付金额
				weChatOrder = this.process(weChatOrder);
				logger.debug("--- 本笔订单实际的字符金额是 ---");
				logger.debug(weChatOrder.getPayMoney());
				
				logger.debug("--- 开始计算订单实际金额 ---");
				if(discountId != null && i == 0){
					// 更新用户优惠券的状态为已使用
					logger.debug("--- 更新用户优惠券的状态为已使用 ---");
					WeChatUserDiscount weChatUserDiscount = new WeChatUserDiscount();
					weChatUserDiscount.setId(Integer.valueOf(discountId));
					weChatUserDiscount.setIsUsed(1);
					session.update("WeChatUserDiscount.updateUserDiscountStatus", weChatUserDiscount);
					// 往第一个订单中插入优惠券使用情况
					// 特别需要注意的是,由于discountId留作优惠券使用金额而不是用户优惠券主键
					DecimalFormat df = new DecimalFormat("######0.00");
					if(i == 0){
						weChatOrder.setDiscountId(discountId);
						weChatUserDiscount = session.selectOne("WeChatUserDiscount.getUserDiscountByDiscountId", discountId);
						logger.debug("--- 当前返回的weChatUserDiscount的信息如下 ---");
						logger.debug(weChatUserDiscount.toString());
						BigDecimal payMoney = new BigDecimal(weChatOrder.getOrderMoney());
						BigDecimal discountAmount = weChatUserDiscount.getDiscount().getDiscountAmount();
						logger.debug("--- 优惠券优惠的金额是 ---");
						logger.debug(discountAmount.toString());
						if(payMoney.compareTo(discountAmount) < 0){
							weChatOrder.setDiscountId(df.format(payMoney.doubleValue()));
							payMoney = new BigDecimal("0.00");
						}else{
							weChatOrder.setDiscountId(df.format(discountAmount.doubleValue()));
							payMoney = payMoney.subtract(discountAmount);
						}
						logger.debug("--- 最终支付的金额是 ---");
						logger.debug(payMoney.toString());
						weChatOrder.setPayMoney(df.format(payMoney.doubleValue()));
					}
				}
				logger.debug("--- 结束计算订单实际金额 ---");
				
				// 更新订单记录
				logger.debug("--- 更新用户订单 ---");
				weChatOrder.setOrderStatus(2);
				logger.debug(weChatOrder.toString());
				session.update("WeChatOrderMapper.updateOrderStatus", weChatOrder);
				
				// 在预约表中插入一条记录
				logger.debug("--- 在预约表中插入一条记录 ---");
				WeChatAppoint weChatAppoint = new WeChatAppoint();
				weChatAppoint.setCustomAppointId(UuidUtil.get32UUID());
				weChatAppoint.setOrderId(weChatOrder.getOrderId());
				weChatAppoint.setuId(weChatOrder.getuId());
				weChatAppoint.setServiceStaffId(weChatOrder.getServiceStaffId());
				String code = weChatOrder.getOrderId();
				weChatAppoint.setAppointCode(Integer.valueOf(code.substring(code.length() - 8, code.length())));
				
				// 一次预约多个订单时,仅仅只有第一个订单有预约时间,其他的订单没有
				if(weChatOrder.getRecommendTime() != null && !weChatOrder.getRecommendTime().equals("")){
					weChatAppoint.setAppointTime(weChatOrder.getRecommendTime());
					try {
						weChatAppoint.setExpireTime(DateUtil.caculateGuoqiTime(weChatOrder.getRecommendTime()));
					} catch (Exception e) {
						logger.debug(e.getMessage());
						e.printStackTrace();
					}
				}
				
				logger.debug(weChatAppoint.toString());
				session.insert("WeChatAppointMapper.saveAppoint", weChatAppoint);
							
				if(whetherRecordPoint.compareTo(new BigDecimal(weChatOrder.getPayMoney())) < 0){
					DecimalFormat df = new DecimalFormat("######0.00");
					// 用户使用点数支付,在支付明细表中插入两条记录
					logger.debug("--- 在支付明细表中插入使用余额支付记录 ---");
					WeChatPayDetail weChatPayDetailByMoney = new WeChatPayDetail();
					weChatPayDetailByMoney.setPayDetailId(UuidUtil.get32UUID());
					weChatPayDetailByMoney.setuId(weChatOrder.getuId());
					weChatPayDetailByMoney.setOrderId(weChatOrder.getOrderId());
					weChatPayDetailByMoney.setOrderMoney(weChatOrder.getOrderMoney());
					weChatPayDetailByMoney.setPayMoney(df.format(whetherRecordPoint));
					weChatPayDetailByMoney.setPayMethod(Integer.valueOf(payMethod));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					weChatPayDetailByMoney.setPayTime(sdf.format(date));
					logger.debug(weChatPayDetailByMoney.toString());
					session.insert("WeChatPayDetailMapper.savePayDetail", weChatPayDetailByMoney);
					
					logger.debug("--- 在支付明细表中插入使用点数支付记录 ---");
					WeChatPayDetail weChatPayDetailByPoint = new WeChatPayDetail();
					weChatPayDetailByPoint.setPayDetailId(UuidUtil.get32UUID());
					weChatPayDetailByPoint.setuId(weChatOrder.getuId());
					weChatPayDetailByPoint.setOrderId(weChatOrder.getOrderId());
					weChatPayDetailByPoint.setOrderMoney(weChatOrder.getOrderMoney());
					weChatPayDetailByPoint.setPayMoney(df.format(new BigDecimal(weChatOrder.getPayMoney()).subtract(whetherRecordPoint)));
					weChatPayDetailByPoint.setPayMethod(new Integer(6));
					weChatPayDetailByPoint.setPayTime(sdf.format(date));
					logger.debug(weChatPayDetailByMoney.toString());
					session.insert("WeChatPayDetailMapper.savePayDetail", weChatPayDetailByPoint);
					
					// 重置为零
					whetherRecordPoint = new BigDecimal("0.00");
				}else{
					// 用户未使用点数支付,在支付明细表中插入一条记录
					whetherRecordPoint = whetherRecordPoint.subtract(new BigDecimal(weChatOrder.getPayMoney()));
					logger.debug("--- 在支付明细表中插入一条记录 ---");
					WeChatPayDetail weChatPayDetail = new WeChatPayDetail();
					weChatPayDetail.setPayDetailId(UuidUtil.get32UUID());
					weChatPayDetail.setuId(weChatOrder.getuId());
					weChatPayDetail.setOrderId(weChatOrder.getOrderId());
					weChatPayDetail.setOrderMoney(weChatOrder.getOrderMoney());
					weChatPayDetail.setPayMoney(weChatOrder.getPayMoney());
					weChatPayDetail.setPayMethod(Integer.valueOf(payMethod));
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = new Date();
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					weChatPayDetail.setPayTime(sdf.format(date));
					logger.debug(weChatPayDetail.toString());
					session.insert("WeChatPayDetailMapper.savePayDetail", weChatPayDetail);
				}
				i ++;
			}
			// 提交数据库修改
			session.flushStatements();
			session.commit();
			session.clearCache();
		}finally{
			session.close();
		}
	}
	
	public void saveStoreDetail(BigDecimal remainMoney,List<String> orderIds){
		SqlSession session = this.sqlSessionTemplate.getSqlSessionFactory().openSession(false);
		WeChatOrder weChatOrder = null;
		WeChatStoredDetailCustom weChatStoredDetailCustom = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(String orderId : orderIds){
			weChatStoredDetailCustom = new WeChatStoredDetailCustom();
			weChatOrder = (WeChatOrder)session.selectOne("WeChatOrderMapper.getOrderByOrderId", orderId);
			weChatOrder = this.process(weChatOrder);
			if(remainMoney.compareTo(new BigDecimal(weChatOrder.getOrderMoney())) < 0){
				// 用户使用点数付款
				weChatStoredDetailCustom.setMoney(remainMoney);
				weChatStoredDetailCustom.setPoints(new BigDecimal(weChatOrder.getPayMoney()).subtract(remainMoney));
				remainMoney = new BigDecimal("0.00");
			}else{
				// 用户未使用点数付款
				remainMoney = remainMoney.subtract(new BigDecimal(weChatOrder.getPayMoney()));
				weChatStoredDetailCustom.setMoney(new BigDecimal(weChatOrder.getPayMoney()));
				weChatStoredDetailCustom.setPoints(new BigDecimal("0.00"));
			}
			weChatStoredDetailCustom.setStoredDetailId("OC" + PrimaryKeyGenerator.generateKey());
			weChatStoredDetailCustom.setType(4);	// 线上消费
			weChatStoredDetailCustom.setStatus(0);	// 已支付
			weChatStoredDetailCustom.setCreateTime(sdf.format(new Date()));
			weChatStoredDetailCustom.setStoreId(weChatOrder.getStore().getStoreId());
			weChatStoredDetailCustom.setuId(weChatOrder.getuId());
			weChatStoredDetailCustom.setStaffId(weChatOrder.getServiceStaffId());
			session.insert("WeChatStoredDetailCustomMapper.saveStoredDetailCustom", weChatStoredDetailCustom);
		}
		session.commit();
		session.close();
	}
}