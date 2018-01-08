package cn.ncut.service.wechat.credit;

import java.util.List;

import cn.ncut.util.PageData;

public interface WeChatCreditManager {

	public Integer getTotalCredit(Integer uId) throws Exception;

	public List<PageData> getCreditDetails(Integer uId) throws Exception;

	


}
