package cn.ncut.service.wechat.payHistory;

import java.util.Map;

import cn.ncut.entity.wechat.pojo.WeChatPayHistory;

public interface WeChatPayHistoryManager {
	
	/**
	 * 根据主键查询支付历史
	 * @param payHistoryId
	 * @return 根据主键查询的支付历史
	 * @throws Exception
	 * */
	public WeChatPayHistory getPayHistoryByPayHistoryId(Integer payHistoryId) throws Exception;
	
	/**
	 * 根据订单编号查询支付历史
	 * @param orderId
	 * @return 根据订单编号查询的支付历史
	 * @throws Exception
	 * */
	public WeChatPayHistory getPayHistoryByOrderId(String orderId) throws Exception;
	
	/**
	 * 保存
	 * @param map
	 * @return 当前插入记录在表中的主键
	 * @throws Exception
	 * */
	public Integer savePayHistory(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改支付历史状态
	 * @param weChatPayHistory
	 * @return 
	 * @throws Exception
	 * */
	public void updatePayHistoryStatus(WeChatPayHistory weChatPayHistory) throws Exception;
}
