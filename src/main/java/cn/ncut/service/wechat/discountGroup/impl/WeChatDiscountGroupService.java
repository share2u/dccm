package cn.ncut.service.wechat.discountGroup.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatDiscountGroup;
import cn.ncut.service.wechat.discountGroup.WeChatDiscountGroupManager;

@Service("weChatDiscountGroupService")
public class WeChatDiscountGroupService implements WeChatDiscountGroupManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,WeChatDiscountGroup> getUserDiscountGroupInfo(Integer uId)
			throws Exception {
		List<WeChatDiscountGroup> weChatDiscountGroups = (List<WeChatDiscountGroup>)dao.findForList(
				"WeChatDiscountGroupMapper.getUserDiscountGroupInfo", uId);
		Map<String,WeChatDiscountGroup> interceptor = this.processDiscountGroupName(weChatDiscountGroups);
		return interceptor;
	}
	
	// 简单处理优惠券组名重复问题
	private Map<String,WeChatDiscountGroup> processDiscountGroupName(List<WeChatDiscountGroup> weChatDiscountGroups) throws Exception{
		Map<String,WeChatDiscountGroup> intercepter = new HashMap<String,WeChatDiscountGroup>();
		// 在页面上显示不同的优惠券组名
		// 记录同一个优惠券组的id信息
		Map<String,List<Integer>> map = new HashMap<String, List<Integer>>();
		for(WeChatDiscountGroup weChatDiscountGroup : weChatDiscountGroups){
			// 将groupId作为判断是否应该加入map的依据;同一个优惠券组的groupId是一样的
			if(intercepter.containsKey(weChatDiscountGroup.getGroupId()) == false){
				map.clear();
				// 获取当前优惠券组的主键,并加入到map中
				ArrayList<Integer> discountGroupId = new ArrayList<Integer>();
				discountGroupId.add(weChatDiscountGroup.getDiscountGroupId());
				map.put(weChatDiscountGroup.getGroupId(), discountGroupId);
				weChatDiscountGroup.setMap(map);
				
				// 在intercepter中加入当前优惠券组
				intercepter.put(weChatDiscountGroup.getGroupId(),weChatDiscountGroup);
				
				continue;
			}
			// 若当前优惠券组在intercepter中已存在,在intercepter中获取优惠券组;重新设置其主键信息
			ArrayList<Integer> discountGroupId = (ArrayList<Integer>)map.get(weChatDiscountGroup.getGroupId());
			discountGroupId.add(weChatDiscountGroup.getDiscountGroupId());
			map.put(weChatDiscountGroup.getGroupId(), discountGroupId);
			weChatDiscountGroup = intercepter.get(weChatDiscountGroup.getGroupId());
			weChatDiscountGroup.setMap(new HashMap<String,List<Integer>>(map));
		}
		
		System.out.println("...");
		return intercepter;
	}

	@Override
	public WeChatDiscountGroup getDiscountGroupInfoByDiscountGroupId(
			WeChatDiscountGroup weChatDiscountGroup) throws Exception {
		return (WeChatDiscountGroup)dao.findForObject(
			"WeChatDiscountGroupMapper.getDiscountGroupInfoByDiscountGroupId", weChatDiscountGroup);
	}
}