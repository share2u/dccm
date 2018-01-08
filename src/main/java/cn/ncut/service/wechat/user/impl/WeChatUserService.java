package cn.ncut.service.wechat.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.wechat.pojo.WeChatUser;
import cn.ncut.service.wechat.user.WeChatUserManager;
@Service("weChatUserService")
public class WeChatUserService implements WeChatUserManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Override
	public WeChatUser insertWeChatUser(WeChatUser weChatUser) throws Exception {
		dao.save("WeChatUserMapper.insertWeChatUser", weChatUser);
		/*WeChatUser weChatUser1 = (WeChatUser) dao.save("WeChatUserMapper.getWeChatUserByuId", weChatUser.get);*/
		return weChatUser;
	}

	@Override
	public WeChatUser getUserByOpenId(String openId) throws Exception {
		WeChatUser weChatUser = (WeChatUser) dao.findForObject("WeChatUserMapper.getUserByOpenId", openId);
		return weChatUser;
	}

	@Override
	public WeChatUser getWeChatUserByuId(Integer uId) throws Exception {
		return (WeChatUser)dao.findForObject("WeChatUserMapper.getWeChatUserByuId", uId);
	}
	
	@Override
	public void updateWeChatUser(WeChatUser weChatUser) throws Exception {
		dao.update("WeChatUserMapper.updateWeChatUser", weChatUser);
	}

	// modified by scott
	@SuppressWarnings("unchecked")
	@Override
	public List<WeChatUser> checkMobileIsDuplicate(WeChatUser weChatUser)
			throws Exception {
		return (List<WeChatUser>)dao.findForList("WeChatUserMapper.checkMobileIsDuplicate", weChatUser);
	}
}
