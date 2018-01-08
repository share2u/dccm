package cn.ncut.service.wechat.discount.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatDiscount;
import cn.ncut.service.wechat.discount.WeChatDiscountManager;

@Service("weChatDiscountService")
public class WeChatDiscountService implements WeChatDiscountManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatDiscount> getDiscountsByStatus(Integer status)
			throws Exception {
		return (List<WeChatDiscount>)dao.findForList("WeChatDiscountMapper.getDiscountsByStatus", status);
	}

	@Override
	public void batchUpdateDiscountStatus(List<WeChatDiscount> weChatDiscounts,
			Integer status) throws Exception {
		for(WeChatDiscount weChatDiscount : weChatDiscounts){
			weChatDiscount.setStatus(status);
		}
		dao.batchUpdate("WeChatDiscountMapper.batchUpdateDiscountStatus", weChatDiscounts);
	}
}
