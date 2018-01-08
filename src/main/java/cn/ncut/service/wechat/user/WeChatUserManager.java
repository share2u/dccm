package cn.ncut.service.wechat.user;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatUser;

public interface WeChatUserManager {
	/**
	 * @param weChatUser 服务器用户
	 * @return  返回插入的用户
	 * @throws Exception
	 */
	public WeChatUser insertWeChatUser(WeChatUser weChatUser)throws Exception;
	
	
	/**
	 * @param openId
	 * @return 如果存在返回用户，不存在返回null
	 * @throws Exception 
	 */
	public WeChatUser getUserByOpenId(String openId) throws Exception;
	
	/**
	 * 根据uId查询用户
	 * @param uId 用户编号
	 * @return WeChatUser
	 * @throws Exception
	 * */
	public WeChatUser getWeChatUserByuId(Integer uId) throws Exception;
	
	/**
	 * 更新用户的基本信息
	 * @param weChatUser 需要修改的用户
	 * @return 
	 * */
	public abstract void updateWeChatUser(WeChatUser weChatUser) throws Exception;

	/**
	 * modified by scott
	 * 根据手机号获取用户信息
	 * */
	public abstract List<WeChatUser> checkMobileIsDuplicate(WeChatUser weChatUser) throws Exception;
}
