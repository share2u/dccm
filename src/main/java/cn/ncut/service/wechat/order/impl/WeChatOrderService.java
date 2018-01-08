package cn.ncut.service.wechat.order.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatCustomComment;
import cn.ncut.entity.wechat.pojo.WeChatOrder;
import cn.ncut.service.wechat.order.WeChatOrderManager;
import cn.ncut.util.PageData;

@Service("weChatOrderService")
public class WeChatOrderService implements WeChatOrderManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getUserOrdersByUserId(Integer id) throws Exception {
		return (List<PageData>)dao.findForList("WeChatOrderMapper.getUserOrdersByUserId", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getUserPrePayOrCloseOrderByUserId(Integer id)
			throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getUserPrePayOrCloseOrderByUserId", id);
	}

	@Override
	public void updateUserPrePayOrderToClose(String orderId) throws Exception {
		dao.update("WeChatOrderMapper.updateUserPrePayOrderToClose", orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getUserPreCommentOrDoneOrderByUserId(Integer id)
			throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getUserPreCommentOrDoneOrderByUserId", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getUserAppointOrOvertimeOrderByUserId(Integer id)
			throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getUserAppointOrOvertimeOrderByUserId", id);
	}

	@Override
	public void updateUserAppointOrderToOvertime(String orderId)
			throws Exception {
		dao.update("WeChatOrderMapper.updateUserAppointOrderToOvertime", orderId);
	}

	@Override
	public WeChatOrder getUserOrderDetails(String orderId) throws Exception {
		return (WeChatOrder)dao.findForObject("WeChatOrderMapper.getUserOrderDetails", orderId);
	}

	@Override
	public void insertOrderCommentInfoToCustomComment(
			WeChatCustomComment weChatCustomComment) throws Exception {
		dao.save("WeChatOrderMapper.insertOrderCommentInfoToCustomComment", weChatCustomComment);
		this.updateUserPreCommentOrderToDone(weChatCustomComment.getOrderId());
	}

	@Override
	public void updateUserPreCommentOrderToDone(String orderId)
			throws Exception {
		dao.update("WeChatOrderMapper.updateUserPreCommentOrderToDone", orderId);
	}

	@Override
	public WeChatOrder getOrderByOrderId(String orderId) throws Exception {
		return (WeChatOrder)dao.findForObject("WeChatOrderMapper.getOrderByOrderId", orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getOrdersByOrderStatus(Integer status)
			throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getOrdersByOrderStatus", status);
	}

	@Override
	public void batchUpdateOrderStatus(List<WeChatOrder> weChatOrders,
			Integer status) throws Exception {
		for(WeChatOrder weChatOrder : weChatOrders){
			weChatOrder.setOrderStatus(status);
		}
		dao.batchUpdate("WeChatOrderMapper.batchUpdateOrderStatus",weChatOrders);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getAppointedOrdersByOrderStatus(Integer status)
			throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getAppointedOrdersByOrderStatus", status);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getAppointedOrdersByOrderStatusRegardlessTime(Integer status)
			throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getAppointedOrdersByOrderStatusRegardlessTime", status);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getOrdersByOrderStatusRegardlessTime(Integer status)
			throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getOrdersByOrderStatusRegardlessTime", status);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatOrder> getOrdersWhereServiceCostIdIn2Or3ByUid(Integer id) throws Exception {
		return (List<WeChatOrder>)dao.findForList("WeChatOrderMapper.getOrdersWhereServiceCostIdIn2Or3ByUid",id);
	}

	@Override
	public WeChatOrder showOrderDetailsPatch(String orderId) throws Exception {
		return (WeChatOrder)dao.findForObject("WeChatOrderMapper.showOrderDetailsPatch", orderId);
	}
}
