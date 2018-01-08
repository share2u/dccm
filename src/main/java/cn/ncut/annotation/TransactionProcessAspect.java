package cn.ncut.annotation;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ncut.entity.wechat.pojo.WeChatPayPerformance;
import cn.ncut.service.wechat.payPerformance.impl.WeChatPayPerformanceService;

public class TransactionProcessAspect {
	private static Logger logger = LoggerFactory.getLogger("weChatService");
	@Resource(name = "weChatPayPerformanceService")
	private WeChatPayPerformanceService weChatPayPerformanceService;
	public Object watchTransactionProcessTime(ProceedingJoinPoint joinPoint) {
		String processResult = null;
		try {
			long start = System.currentTimeMillis();
			processResult = (String) joinPoint.proceed();
			long end = System.currentTimeMillis();
			String methodName = joinPoint.getSignature().getName();
			String transactionName = "weChatOrder";
			String transactionOperation = "";
			String comment = "";
			switch (methodName) {
				case "payOrder":
					transactionOperation = "payOrder";
					comment = "微信订单支付";
					break;
				case "storedCardPayOrder":
					transactionOperation = "storedCardPayOrder";
					comment = "储值卡订单支付";
					break;
				case "payOrderResponse":
					transactionOperation = "payOrderResponse";
					comment = "储值通知";
					break;
				default:
					transactionOperation = "unknownMethod";
					comment = "未知方法--微信支付模块";
					break;
			}
			this.weChatPayPerformanceService.savePayPerformance(WeChatPayPerformance.
					savePayPerformance(transactionName, transactionOperation, start, end, comment));
		} catch (Throwable e) {			
			logger.warn("--- 配置的切面出现异常，异常信息如下 ---");
			logger.warn(e.getMessage());
		}		
		return processResult;
	}
}