package cn.ncut.dao.wechat.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import cn.ncut.dao.wechat.WeChatOrderCheckQueryDao;
import cn.ncut.entity.wechat.pojo.WeChatAppoint;
import cn.ncut.entity.wechat.pojo.WeChatOrder;
import cn.ncut.entity.wechat.pojo.WeChatPayDetail;
import cn.ncut.entity.wechat.pojo.WeChatPayHistory;
import cn.ncut.entity.wechat.pojo.WeChatUserDiscount;
import cn.ncut.util.DateUtil;
import cn.ncut.util.UuidUtil;
import cn.ncut.util.wechat.TimeAdjust;

@Repository("weChatOrderCheckQueryDaoImpl")
public class WeChatOrderCheckQueryDaoImpl implements WeChatOrderCheckQueryDao {
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
	public void checkOrderStatus(List<WeChatPayHistory> payHistories) {
		logger.debug("--- 根据异常订单支付历史修改订单状态 start ---");
		/**
		 *  一系列修改数据库相关状态
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(WeChatPayHistory weChatPayHistory : payHistories){
			/**
			 * 订单号,支付方式,优惠券,提交给微信的商户订单号,实际支付金额
			 * */
			final String childOrderId = weChatPayHistory.getChildOrderId();
			final String parentOrderId = weChatPayHistory.getParentOrderId();
			final String payMethod = String.valueOf(weChatPayHistory.getPayMethod());
			final String discountId = weChatPayHistory.getDiscountId();
			final String payTime = sdf.format(new Date());
			
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
			
			// 更新用户支付历史
			updatePayHistoryStatus(weChatPayHistory);
			logger.debug("--- 更新用户支付历史表成功 ---");
			
			logger.debug("--- 根据异常订单支付历史修改订单状态		end ---");
		}
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
				if(weChatOrder != null && weChatOrder.getOrderStatus() != 0 && weChatOrder.getOrderStatus() != 1){
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
}
