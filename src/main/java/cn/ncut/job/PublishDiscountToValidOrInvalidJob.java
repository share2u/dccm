package cn.ncut.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import cn.ncut.entity.wechat.pojo.WeChatDiscount;
import cn.ncut.service.wechat.discount.impl.WeChatDiscountService;

/**
 * 每天晚上23:30:00刷新可使用的优惠券,即修改优惠券为有效状态
 * */
public class PublishDiscountToValidOrInvalidJob {
	@Resource(name = "weChatDiscountService")
	private WeChatDiscountService weChatDiscountService;
	
	public void modifyDiscountStatus() throws Exception{
		this.modifyDiscountStatusToValid();
		this.modifyDiscountStatusToInvalid();
	}
	
	/**
	 * 获取状态为"0",即未生效的优惠券;修改优惠券的状态为有效
	 * 默认的未生效的优惠券开始时间是00:00:00;
	 * 本方法执行的时间是晚间00:10:00
	 * */
	private void modifyDiscountStatusToValid() throws Exception{
		List<WeChatDiscount> weChatDiscounts = this.weChatDiscountService.getDiscountsByStatus(0);
		List<WeChatDiscount> updatedDiscounts = new ArrayList<WeChatDiscount>();
		Iterator<WeChatDiscount> iterator = weChatDiscounts.iterator();
		Date startTime = null;
		Date now = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while(iterator.hasNext()){
			WeChatDiscount weChatDiscount = iterator.next();
			startTime = sdf.parse(weChatDiscount.getStartTime());
			if(startTime != null){
				if(now.compareTo(startTime) >= 0){
					updatedDiscounts.add(weChatDiscount);
				}
			}
		}
		if(updatedDiscounts.size() > 0){
			this.weChatDiscountService.batchUpdateDiscountStatus(updatedDiscounts, 1);
		}
	}
	
	/**
	 * 获取状态为"1",即已生效的优惠券;修改优惠券的状态为无效
	 * 默认的已生效的优惠券结束时间是23:59:59;
	 * 本方法执行的时间是第二天晚间00:10:00;延长的十分钟应该怎么处理
	 * */
	private void modifyDiscountStatusToInvalid() throws Exception{
		List<WeChatDiscount> weChatDiscounts = this.weChatDiscountService.getDiscountsByStatus(1);
		List<WeChatDiscount> updatedDiscounts = new ArrayList<WeChatDiscount>();
		Iterator<WeChatDiscount> iterator = weChatDiscounts.iterator();
		Date expireTime = null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		while(iterator.hasNext()){
			WeChatDiscount weChatDiscount = iterator.next();
			expireTime = sdf.parse(weChatDiscount.getEndTime());
			if(expireTime != null){
				if(now.compareTo(expireTime) >= 0){
					updatedDiscounts.add(weChatDiscount);
				}
			}
		}
		if(updatedDiscounts.size() > 0){
			this.weChatDiscountService.batchUpdateDiscountStatus(updatedDiscounts, 2);
		}
	}
}
