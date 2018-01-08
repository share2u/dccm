package cn.ncut.service.wechat.memberCallback.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatMemberCallback;
import cn.ncut.service.wechat.memberCallback.WeChatMemberCallbackManager;

@Service("weChatMemberCallbackService")
public class WeChatMemberCallbackService implements WeChatMemberCallbackManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatMemberCallback> getAllNonresponseMemberCallback(
			String storeId) throws Exception {
		return (List<WeChatMemberCallback>)dao.findForList("WeChatMemberCallbackMapper.getAllNonresponseMemberCallback", storeId);
	}
}
   