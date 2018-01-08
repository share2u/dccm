package cn.ncut.service.wechat.preStoreMx;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatPreStoreMx;

public interface WeChatPreStoreMxManager {
	/**
	 * 返回用户预存变动明细信息
	 * @param uId
	 * @return 用户预存明细列表
	 * @throws Exception
	 * */
	public abstract List<WeChatPreStoreMx> getUserPreStoreMxByUserId(Integer uId) throws Exception;
}
