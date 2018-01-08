package cn.ncut.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import cn.ncut.entity.wechat.pojo.WeChatOrder;
import cn.ncut.service.wechat.order.impl.WeChatOrderService;

/**
 * 定时修改30分钟内未支付订单为关闭状态
 * 定时修改60分钟内未支付订单为过期状态
 * 目前刷新的时间周期为10分钟
 * */
public class ModifyOrderStatusToCloseOrOverTimeJob {
	@Resource(name = "weChatOrderService")
	private WeChatOrderService weChatOrderService;
	
	public void modifyOrderStatus() throws Exception{
		this.modifyOrderStatusToClose();
		this.modifyOrderStatusToOverTime();
	}
	
	/**
	 * 遍历数据库中的所有状态为"0"的订单,判断该订单是否超过30分钟
	 * 未支付;若是,则修改该订单的状态为"1",即已关闭
	 * @throws Exception 
	 * */
	private void modifyOrderStatusToClose() throws Exception{
		List<WeChatOrder> weChatOrders = this.weChatOrderService.getOrdersByOrderStatusRegardlessTime(0);
		List<WeChatOrder> updatedItems = new ArrayList<WeChatOrder>();
		Iterator<WeChatOrder> iterator = weChatOrders.iterator();
		Date expireTime = null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(iterator.hasNext()){
			WeChatOrder weChatOrder = iterator.next();
			if(weChatOrder.getExpireTime() != null && !weChatOrder.getExpireTime().equals("")){
				expireTime = sdf.parse(weChatOrder.getExpireTime());
				if(now.compareTo(expireTime) >= 0){
					updatedItems.add(weChatOrder);
				}
			}
		}
		if(updatedItems.size() > 0){
			this.weChatOrderService.batchUpdateOrderStatus(updatedItems, 1);
		}
	}
	
	/**
	 * 遍历数据库中的所有状态为"2"的订单,判断该订单是否超过60分钟
	 * 未消费;若是,则修改该订单的状态为"3",即已过期
	 * @throws Exception 
	 * */
	private void modifyOrderStatusToOverTime() throws Exception{
		List<WeChatOrder> weChatOrders = this.weChatOrderService.getAppointedOrdersByOrderStatusRegardlessTime(2);
		List<WeChatOrder> updatedItems = new ArrayList<WeChatOrder>();
		Iterator<WeChatOrder> iterator = weChatOrders.iterator();
		Date expireTime = null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(iterator.hasNext()){
			WeChatOrder weChatOrder = iterator.next();
			if(weChatOrder.getAppoint() != null && weChatOrder.getAppoint().getExpireTime() != null
					&& !(weChatOrder.getAppoint().getExpireTime().equals(""))){
				expireTime = sdf.parse(weChatOrder.getAppoint().getExpireTime());
				if(now.compareTo(expireTime) >= 0){
					updatedItems.add(weChatOrder);
				}
			}
		}
		if(updatedItems.size() > 0){
			this.weChatOrderService.batchUpdateOrderStatus(updatedItems, 3);
		}
	}
}
