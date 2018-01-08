package cn.ncut.service.wechat.userStoredCard;

import cn.ncut.entity.wechat.pojo.WeChatUserStoredCard;

public interface WeChatUserStoredCardManager {
	/**
	 * 根据uId获取用户储值账户信息
	 * @param uId
	 * @return weChatUserStoredCard
	 * @throws Exception
	 * */
	public abstract WeChatUserStoredCard getUserStoredCardByUid(Integer uId) throws Exception;
	/**
	 * 创建一个新的储值账户
	 * @param weChatUserStoredCard
	 * @throws Exception
	 * */
	public abstract void createNewUserStoredCard(WeChatUserStoredCard weChatUserStoredCard) throws Exception;
	/**
	 * 根据uId更新储值账户的密码信息
	 * @param weChatUserStoredCard
	 * @throws Exception
	 * */
	public abstract void updateUserStoredCardPassword(WeChatUserStoredCard weChatUserStoredCard) throws Exception;
	
	/**
	 * 根据uId更新储值账户的手机号,密码信息
	 * @param weChatUserStoredCard
	 * @throws Exception
	 * */
	public abstract void updateUserStoredCardPhoneAndName(WeChatUserStoredCard weChatUserStoredCard) throws Exception;
}
