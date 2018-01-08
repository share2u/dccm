package cn.ncut.service.wechat.pay.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.ncut.entity.wechat.pojo.PayH5;
import cn.ncut.entity.wechat.pojo.PayUnifiedOrder;
import cn.ncut.entity.wechat.pojo.PayUnifiedOrderBack;
import cn.ncut.service.wechat.pay.WeChatPayManager;
import cn.ncut.util.wechat.RandomUtil;
import cn.ncut.util.wechat.SignGenerateAndCheckUtil;
import cn.ncut.util.wechat.SpringPropertyResourceReader;
import cn.ncut.util.wechat.TypeConvertUtil;

@Service("weChatPayService")
public class WeChatPayService implements WeChatPayManager {
	private static Logger logger = LoggerFactory.getLogger(WeChatPayService.class);

	/**
	 * 常用的jsAPi的统一下单方法，使用下面的几个参数 商品描述，商户订单号，标价金额（分），终端ip,通知地址，用户标示,attach
	 * 
	 * @return
	 */
	@Override
	public PayH5 packageUnifiedOrderByJsAPI(String body, String outTradeNo,
			int total_fee, String spbillCreateIp, String notifyUrl,
			String openId, String attach) {
		PayUnifiedOrder payUnifiedOrder = new PayUnifiedOrder();
		// 传过来的7个参数
		payUnifiedOrder.setBody(body);
		payUnifiedOrder.setOut_trade_no(outTradeNo);
		payUnifiedOrder.setTotal_fee(total_fee);
		payUnifiedOrder.setSpbill_create_ip(spbillCreateIp);
		payUnifiedOrder.setNotify_url(notifyUrl);
		payUnifiedOrder.setOpenid(openId);
		
		//
		payUnifiedOrder.setTrade_type("JSAPI");
		payUnifiedOrder.setDevice_info("WEB");// 设备号
		payUnifiedOrder.setAttach(attach);// 附加参数
		PayUnifiedOrderBack payUnifiedOrderBack = packageUnifiedOrder(payUnifiedOrder);
		PayH5 payH5 = new PayH5();
		if (null != payUnifiedOrderBack) {
			payH5.setAppId(payUnifiedOrderBack.getAppid());
			payH5.setNonceStr(RandomUtil.generateMixedCharAndDigit(32));
			payH5.setPayPackage(payUnifiedOrderBack.getPrepay_id());
			payH5.setSignType("MD5");
			payH5.setTimeStamp(Long.toString((new Date().getTime()) / 1000));
			String sign = SignGenerateAndCheckUtil.createSign("UTF-8",
					SignGenerateAndCheckUtil.sortBeanAttributes(payH5));
			payH5.setPaySign(sign);

		}
		logger.debug("返回jsapi页面的H5" + payH5);
		return payH5;
	}

	/**
	 * 需要传入body,outTradeNo,total_fee,spbillCreateIp,notifyUrl,trade_type
	 * [detail，Device_info,attach] 负责 appid,mchid,noncestr,
	 * time_start,time_expire,limit_pay,fee_type,goods_tag,sign 的初始化
	 * 
	 * @return 1.return_code(通信成功SUCCESS)2.result_code(业务结果SUCCESS)3.取相应的值
	 * 
	 */
	private PayUnifiedOrderBack packageUnifiedOrder(
			PayUnifiedOrder payUnifiedOrder) {
		String requestUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		payUnifiedOrder.setAppid(SpringPropertyResourceReader
				.getProperty("appId"));
		payUnifiedOrder.setMch_id(SpringPropertyResourceReader
				.getProperty("mch_id"));// 商户号
		String nonceStr = RandomUtil.generateMixedCharAndDigit(32);
		payUnifiedOrder.setNonce_str(nonceStr);// 随机字符串
		payUnifiedOrder.setDetail("");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		String time_start = simpleDateFormat.format(date.getTime());
		int order_time_space = Integer.parseInt(SpringPropertyResourceReader
				.getProperty("order_time_space"));
		String time_expire = simpleDateFormat.format(date.getTime()
				+ order_time_space);
		String formatDate = simpleDateFormat.format(date);
		payUnifiedOrder.setTime_start(time_start);
		payUnifiedOrder.setTime_expire(time_expire);

		payUnifiedOrder.setLimit_pay("");// 不可以使用信用卡支付
		payUnifiedOrder.setFee_type("CNY");
		payUnifiedOrder.setGoods_tag("");// 商品标记，代金卷的时候时候的参数

		TreeMap<Object, Object> sortMap = SignGenerateAndCheckUtil
				.sortBeanAttributes(payUnifiedOrder);
		String sign = SignGenerateAndCheckUtil.createSign("UTF-8", sortMap);
		payUnifiedOrder.setSign(sign);// 签名
		logger.debug("调用微信统一下单的参数" + sortMap.toString());
		PayUnifiedOrderBack payUnifiedOrderBack = TypeConvertUtil
				.transformBeanToBeanFactory(requestUrl, payUnifiedOrder,
						PayUnifiedOrderBack.class);
		logger.debug("统一下单返回的参数信息" + payUnifiedOrderBack.toString());
		if (payUnifiedOrderBack.getReturn_code().equals("SUCCESS")) {
			if (payUnifiedOrderBack.getResult_code().equals("SUCCESS")) {

				String backSign = SignGenerateAndCheckUtil.createSign("UTF-8",
						SignGenerateAndCheckUtil
								.sortBeanAttributes(payUnifiedOrderBack));
				// sign签名验证
				if (payUnifiedOrderBack.getSign().equals(backSign)) {
					return payUnifiedOrderBack;
				}
			}

		}
		return null;
	}

}
