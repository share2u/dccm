package cn.ncut.service.wechat.memberCallback;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatMemberCallback;

public interface WeChatMemberCallbackManager {
	/**
	 * 获取当前(今天)门店所有未回复的客户咨询记录
	 * @param storeId 门店编号
	 * @return 返回的客户咨询记录
	 * @throws Exception
	 * */
	public List<WeChatMemberCallback> getAllNonresponseMemberCallback(String storeId) throws Exception;
}
  