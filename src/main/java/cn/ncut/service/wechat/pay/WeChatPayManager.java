package cn.ncut.service.wechat.pay;

import cn.ncut.entity.wechat.pojo.PayH5;

public interface WeChatPayManager {
	public abstract PayH5 packageUnifiedOrderByJsAPI(String body,String outTradeNo,int total_fee,
			String spbillCreateIp,String notifyUrl,String openId,String attach);
}
