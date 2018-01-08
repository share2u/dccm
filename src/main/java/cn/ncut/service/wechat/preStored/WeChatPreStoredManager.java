package cn.ncut.service.wechat.preStored;

import cn.ncut.entity.wechat.pojo.WeChatPreStore;

public interface WeChatPreStoredManager {
	public Double getTotalPreStored(Integer uId) throws Exception;
	
	/**
	 * modified by scott
	 * */
	public WeChatPreStore getUserPreStoreByUid(Integer uId) throws Exception;
	
	public void updateUserPreStoreByUid(WeChatPreStore weChatPreStore) throws Exception;
}
 