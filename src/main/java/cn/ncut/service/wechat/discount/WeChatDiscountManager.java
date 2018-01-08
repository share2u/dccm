package cn.ncut.service.wechat.discount;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatDiscount;

public interface WeChatDiscountManager {
	/**
	 * 根据状态,优惠券开始时间查询优惠券
	 * @param status
	 * @return 优惠券列表
	 * @throws Exception
	 * */
	public abstract List<WeChatDiscount> getDiscountsByStatus(Integer status) throws Exception;
	
	/**
	 * 批量修改优惠券的状态的状态为status
	 * @param status 修改后的状态
	 * @param weChatDiscounts 待修改的订单
	 * @return
	 * @throws Exception
	 * */
	public abstract void batchUpdateDiscountStatus(List<WeChatDiscount> weChatDiscounts,Integer status) throws Exception;
}
