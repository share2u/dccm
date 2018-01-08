package cn.ncut.service.wechat.card;

import java.util.List;

import cn.ncut.entity.wechat.pojo.PayResultNotify;
import cn.ncut.entity.wechat.pojo.WeChatStored;
import cn.ncut.entity.wechat.pojo.WeChatStoredDetail;
import cn.ncut.util.PageData;

public interface WeChatCardManager {

	/**
	 * 根据用户得到账户的总钱数
	 * @param uId
	 * @return
	 * @throws Exception
	 */
	public PageData getTotalCardMoney(Integer uId)throws Exception;

	/** 得到储值卡类型s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCardTypes() throws Exception;

	/**
	 * 根据uId得到储值明细
	 * @param uId
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getRechargeDetailsByuId(Integer uId) throws Exception;

	/**
	 * 通过储值卡id得到储值卡信息
	 * @param storedCategoryId
	 * @return
	 * @throws Exception 
	 */
	public PageData getCardTypeById(Integer storedCategoryId) throws Exception;

	/**新建一条储值卡记录，
	 * @param pd
	 * @throws Exception 
	 */
	public void insertStoredDetail(PageData pd) throws Exception;

	/**
	 * 根据储值订单号修改储值成功
	 * @param out_trade_no
	 * @return
	 * @throws Exception
	 */
	public Integer updateStoredDetailByOutTradeNo(String out_trade_no) throws Exception;

	/**
	 * 通过储值明细的编号改变账户的储值信息
	 * @param out_trade_no
	 * @throws Exception 
	 */
	public void updateStoredByDetailOutTradeNo(PageData pd) throws Exception;

	/**根据储值单号查询储值明细
	 * @param out_trade_no
	 * @return
	 * @throws Exception
	 */
	public WeChatStoredDetail selectStoredDetailByOutTradeNo(String out_trade_no) throws Exception;

	/** 创建账户
	 * @param storedDetailuId
	 * @return
	 * @throws Exception
	 */
	public WeChatStored createCard(WeChatStored weChatStored) throws Exception;

	/**根据uid查询账户
	 * @param storedDetailuId
	 * @return
	 * @throws Exception 
	 */
	public WeChatStored selectStoredByuId(Integer storedDetailuId) throws Exception;

	
	/**
	 * 根据微信异步充值处理业务
	 * @param payResultNotify
	 * @return
	 */
	public void rechargeNotify(PayResultNotify payResultNotify );
}
