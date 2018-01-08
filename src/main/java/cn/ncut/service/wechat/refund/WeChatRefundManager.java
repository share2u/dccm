package cn.ncut.service.wechat.refund;

import cn.ncut.entity.wechat.pojo.WeChatRefund;

public interface WeChatRefundManager {
	/**
	 * 根据订单编号查询该订单的退款详情
	 * @param 订单编号
	 * @return 该订单的退款详情
	 * @throws Exception
	 * */
	public WeChatRefund getWeChatRefundByOrderId(String orderId) throws Exception;
}
