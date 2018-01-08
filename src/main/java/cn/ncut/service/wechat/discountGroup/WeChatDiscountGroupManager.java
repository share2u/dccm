package cn.ncut.service.wechat.discountGroup;

import java.util.Map;

import cn.ncut.entity.wechat.pojo.WeChatDiscountGroup;

public interface WeChatDiscountGroupManager {
	public abstract Map<String,WeChatDiscountGroup> getUserDiscountGroupInfo(Integer uId) throws Exception;
	
	public abstract WeChatDiscountGroup getDiscountGroupInfoByDiscountGroupId(
			WeChatDiscountGroup weChatDiscountGroup) throws Exception;
}
