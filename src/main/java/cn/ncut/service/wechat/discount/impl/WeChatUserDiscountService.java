package cn.ncut.service.wechat.discount.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatDiscount;
import cn.ncut.entity.wechat.pojo.WeChatUserDiscount;
import cn.ncut.service.wechat.discount.WeChatUserDiscountManager;

@Service("weChatUserDiscountService")
public class WeChatUserDiscountService implements WeChatUserDiscountManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatUserDiscount> getUserUnusedDiscountsByUserId(Integer uId)
			throws Exception {
		List<WeChatUserDiscount> weChatUserDiscounts = (List<WeChatUserDiscount>)
				dao.findForList("WeChatUserDiscount.getUserUnusedDiscountsByUserId", uId);
		return process(weChatUserDiscounts,2);
	}

	@Override
	public void updateUserDiscountToScanned(Integer uId) throws Exception {
		dao.update("WeChatUserDiscount.updateUserDiscountToScanned",uId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatUserDiscount> getUserUnusedAndUnscannedDiscountsByUserId(
			Integer uId) throws Exception {
		List<WeChatUserDiscount> weChatUserDiscounts = (List<WeChatUserDiscount>)
				dao.findForList("WeChatUserDiscount.getUserUnusedAndUnscannedDiscountsByUserId", uId);
		return process(weChatUserDiscounts,2);
	}
	
	/**
	 * 根据优惠券的状态判断当前优惠券是否可用
	 * @param status 优惠券的状态
	 * @throws ParseException 
	 * */
	public List<WeChatUserDiscount> process(List<WeChatUserDiscount> weChatUserDiscounts,int status) throws ParseException{
		List<WeChatUserDiscount> intercepter = new ArrayList<WeChatUserDiscount>();
		for(WeChatUserDiscount discount : weChatUserDiscounts){
			WeChatDiscount weChatDiscount = discount.getDiscount();
			if(weChatDiscount.getStatus() != status){
				weChatDiscount.setStartTime(weChatDiscount.getStartTime().substring(0, weChatDiscount.getStartTime().indexOf(" ")));
				weChatDiscount.setEndTime(weChatDiscount.getEndTime().substring(0, weChatDiscount.getEndTime().indexOf(" ")));
				intercepter.add(discount);
			}
		}
		return intercepter;
	}

	@Override
	public WeChatUserDiscount getUserDiscountId(String id) throws Exception {
		WeChatUserDiscount weChatUserDiscount = 
				(WeChatUserDiscount)dao.findForObject("WeChatUserDiscount.getUserDiscountByDiscountId", id);
		if(weChatUserDiscount != null){
			WeChatDiscount weChatDiscount = weChatUserDiscount.getDiscount();
			weChatDiscount.setStartTime(weChatDiscount.getStartTime().substring(0, weChatDiscount.getStartTime().indexOf(" ")));
			weChatDiscount.setEndTime(weChatDiscount.getEndTime().substring(0, weChatDiscount.getEndTime().indexOf(" ")));
			weChatUserDiscount.setDiscount(weChatDiscount);
		}
		return weChatUserDiscount;
	}
}
