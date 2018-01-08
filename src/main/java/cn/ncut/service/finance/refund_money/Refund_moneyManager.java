package cn.ncut.service.finance.refund_money;

import java.util.List;
import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

/** 
 * 说明： 退款接口
 * 创建人：FH Q313596790
 * 创建时间：2017-02-23
 * @version
 */
public interface Refund_moneyManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 * 
	 *<p>Tittle:memberRefundlistPage</p>
	 *<p>Description:找出用户可退的余额钱</p>
	 *@param page
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-2-23下午3:35:52
	 */
	public List<PageData> memberRefundlistPage(Page page) throws Exception;

	/**
	 * 
	 *<p>Tittle:memberRefundlistPage</p>
	 *<p>Description:找出用户可退的储值卡钱</p>
	 *@param page
	 *@return
	 *@throws Exception
	 *@author lph
	 *@date 2017-2-23下午3:35:52
	 */
	public List<PageData> memberRefundPrestorelistPage(Page page) throws Exception;

	public PageData findMoneyById(PageData pd) throws Exception;


	/**
	 * 
	 *<p>Tittle:caiwuUpdatePrestoreMoney</p>
	 *<p>Description:财务处退还用户余额</p>
	 *@param refund_money_insert
	 *@param tb_order_insert
	 *@author lph
	 *@date 2017-3-3下午1:30:36
	 */
	public void updateCaiwuPrestoreMoney(PageData refund_money_insert,
			PageData tb_order_insert,PageData tb_ordermx_insert) throws Exception;

	/**
	 * 
	 *<p>Tittle:updateCaiwuCustomStoredMoney</p>
	 *<p>Description:财务退还用户储值卡</p>
	 *@param refund_money_insert
	 *@param tb_order_insert
	 *@throws Exception
	 *@author lph
	 *@date 2017-3-3下午2:56:09
	 */
	public void updateCaiwuCustomStoredMoney(PageData refund_money_insert,
			PageData tb_order_insert,PageData tb_storedDetail_insert) throws Exception;

	
	
}

