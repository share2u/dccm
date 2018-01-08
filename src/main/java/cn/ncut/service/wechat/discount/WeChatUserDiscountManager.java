package cn.ncut.service.wechat.discount;

import java.util.List;

import cn.ncut.entity.wechat.pojo.WeChatUserDiscount;

/**
 * 优惠券相关业务类
 * */
public interface WeChatUserDiscountManager {
	/**
	 * 查询用户未使用的优惠券
	 * @param uId 用户编号
	 * @return 查询到的用户优惠券
	 * @throws Exception
	 * */
	public abstract List<WeChatUserDiscount> getUserUnusedDiscountsByUserId(Integer uId) throws Exception;
	
	/**
	 * 查询用户未使用并且未看过的优惠券
	 * @param uId 用户编号
	 * @return 查询到的用户优惠券
	 * @throws Exception
	 * */
	public abstract List<WeChatUserDiscount> getUserUnusedAndUnscannedDiscountsByUserId(Integer uId) throws Exception; 
	
	/**
	 * 更新用户的优惠券为看过
	 * @param uId 用户编号
	 * @return void
	 * @throws Exception
	 * */
	public abstract void updateUserDiscountToScanned(Integer uId) throws Exception;
	
	/**
	 * 通过userDiscount表主键id查询userDiscount记录
	 * @param id 
	 * @return
	 * @throws Exception
	 * */
	public abstract WeChatUserDiscount getUserDiscountId(String id) throws Exception;
}
