package cn.ncut.service.wechat.order;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatCustomComment;
import cn.ncut.entity.wechat.pojo.WeChatOrder;
import cn.ncut.util.PageData;

/**
 * 订单业务操作接口
 * */
public interface WeChatOrderManager {
	/**
	 * @param id 用户编号
	 * @return 返回用户的订单详情信息
	 * @throws Exception
	 * */
	public abstract List<PageData> getUserOrdersByUserId(Integer id) throws Exception;
	
	/**
	 * 2017-02-19 目前不包括已关闭的订单
	 * @param id 用户编号
	 * @return 返回用户的未支付与已关闭订单详情信息
	 * @throws Exception
	 * */
	public abstract List<WeChatOrder> getUserPrePayOrCloseOrderByUserId(Integer id) throws Exception;
	
	/**
	 * 查询已完成,待评价,已取消的订单
	 * @param id 用户编号
	 * @return 返回查询结果
	 * @throws Exception
	 * */
	public abstract List<WeChatOrder> getUserPreCommentOrDoneOrderByUserId(Integer id) throws Exception;
	
	/**
	 * 查询已完成与待评价的订单
	 * @param id 用户编号
	 * @return 返回查询结果
	 * @throws Exception
	 * */
	public abstract List<WeChatOrder> getUserAppointOrOvertimeOrderByUserId(Integer id) throws Exception;
	
	/**
	 * 根据订单号修改订单状态,将超时"未支付"订单状态设置为"已关闭"
	 * @param orderId 订单号
	 * @return 
	 * @throws Exception
	 * */
	public abstract void updateUserPrePayOrderToClose(String orderId) throws Exception;
	
	/**
	 * 根据订单号修改订单状态,将超时"已预约"订单状态设置为"已过期"
	 * @param orderId 订单号
	 * @return 
	 * @throws Exception
	 * */
	public abstract void updateUserAppointOrderToOvertime(String orderId) throws Exception;
	
	/**
	 * 根据订单编号查询订单明细信息
	 * @param orderId 订单号
	 * @return 
	 * @throws Exception
	 * */
	public abstract WeChatOrder getUserOrderDetails(String orderId) throws Exception;
	
	/**
	 * 订单评价
	 * @param weChatCustomComment
	 * @return 
	 * @throws Exception
	 * */
	public void insertOrderCommentInfoToCustomComment(WeChatCustomComment weChatCustomComment) throws Exception;
	
	/**
	 * 根据订单号修改订单状态,将"待评价"订单状态设置为"已完成"
	 * @param orderId 订单号
	 * @return 
	 * @throws Exception
	 * */
	public abstract void updateUserPreCommentOrderToDone(String orderId) throws Exception;
	
	/**
	 * 根据订单编号查询订单信息
	 * @param orderId 订单编号
	 * @return 订单
	 * @throws Exception
	 * */
	public abstract WeChatOrder getOrderByOrderId(String orderId) throws Exception;
	
	/**
	 * 获取指定订单状态为status的订单
	 * @param status 订单状态
	 * @return 订单列表
	 * @throws Exception
	 * */
	public abstract List<WeChatOrder> getOrdersByOrderStatus(Integer status) throws Exception;
	
	/**
	 * 批量修改订单的状态的状态为status
	 * @param status 修改后的状态
	 * @param weChatOrders 待修改的订单
	 * @return
	 * @throws Exception
	 * */
	public abstract void batchUpdateOrderStatus(List<WeChatOrder> weChatOrders,Integer status) throws Exception;
	
	/**
	 * 获取指定订单状态为status的订单
	 * @param status 修改后的状态
	 * @return weChatOrders 订单
	 * @throws Exception
	 * */
	public abstract List<WeChatOrder> getAppointedOrdersByOrderStatus(Integer status) throws Exception;
	
	public abstract List<WeChatOrder> getAppointedOrdersByOrderStatusRegardlessTime(Integer status) throws Exception;
	
	public abstract List<WeChatOrder> getOrdersByOrderStatusRegardlessTime(Integer status) throws Exception;
	
	/**
	 * 查询划价以及消耗品订单
	 * @param 用户编号
	 * @return 订单
	 * @throws Exception
	 * */
	public abstract List<WeChatOrder> getOrdersWhereServiceCostIdIn2Or3ByUid(Integer id) throws Exception;
	
	/**
	 * 查询划价以及消耗品订单详情
	 * @param orderId
	 * @return
	 * @throws Exception
	 * */
	public abstract WeChatOrder showOrderDetailsPatch(String orderId) throws Exception;
}