package cn.ncut.service.wechat.mobileInfo;

import cn.ncut.entity.wechat.pojo.WeChatMobileInfo;

public interface WeChatMobileInfoManager {
	/**
	 * 通过手机号前7位查询所在地理位置
	 * @param mobileNum 手机号前7位
	 * @return 查询得到的手机号对应信息
	 * */
	public abstract WeChatMobileInfo getMobileInfoByMobileNum(String mobileNum) throws Exception;
}
  