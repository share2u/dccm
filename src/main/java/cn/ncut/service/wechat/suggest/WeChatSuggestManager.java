package cn.ncut.service.wechat.suggest;

import cn.ncut.util.PageData;

public interface WeChatSuggestManager {

	/**
	 * 插入投诉和建议
	 * @param pd
	 * @throws Exception
	 */
	public void sendSuggest(PageData pd) throws Exception;
}
