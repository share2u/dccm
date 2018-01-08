package cn.ncut.service.finance.refund_money.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.util.DateUtil;
import cn.ncut.util.PageData;
import cn.ncut.service.finance.refund_money.Refund_moneyManager;

/**
 * 
 * 
 * <p>
 * Title:Refund_moneyService
 * </p>
 * <p>
 * Description:退款
 * </p>
 * <p>
 * Company:ncut
 * </p>
 * 
 * @author lph
 * @date 2017-2-23下午2:28:52
 * 
 */
@Service("refund_moneyService")
public class Refund_moneyService implements Refund_moneyManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("Refund_moneyMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("Refund_moneyMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("Refund_moneyMapper.edit", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"Refund_moneyMapper.datalistPage", page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("Refund_moneyMapper.listAll",
				pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("Refund_moneyMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 找出用户可退的余额的钱
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> memberRefundlistPage(Page page) throws Exception {

		return (List<PageData>) dao.findForList(
				"Refund_moneyMapper.memberRefundlistPage", page);
	}

	/**
	 * 找出用户可退的储值卡的钱
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> memberRefundPrestorelistPage(Page page)
			throws Exception {
		return (List<PageData>) dao.findForList(
				"Refund_moneyMapper.memberRefundPrestorelistPage", page);
	}

	/**
	 * 退处置卡
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("Refund_moneyMapper.findById", pd);
	}

	/**
	 * 退余额
	 */
	@Override
	public PageData findMoneyById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("Refund_moneyMapper.findMoneyById",
				pd);
	}

	/**
	 * 加入事务管理
	 */
	@Override
	public void updateCaiwuPrestoreMoney(PageData refund_money_insert,
			PageData tb_order_insert, PageData tb_ordermx_insert)
			throws Exception {
		PageData pd = new PageData();
		Long begin_time = System.currentTimeMillis();
		String operate_time = DateUtil.getTime();
		// 1、插入退款表
		dao.save("Refund_moneyMapper.refundMoneySave", refund_money_insert);
		// 2、插入订单表
		dao.save("Refund_moneyMapper.orderSave", tb_order_insert);
		// 4、余额明细表插入一条数据
		dao.save("Refund_moneyMapper.prestoreMxInsert", tb_ordermx_insert);
		// 3、修改余额表
		dao.delete("Refund_moneyMapper.editPrestore", tb_order_insert);
		Long end_time = System.currentTimeMillis();
		pd.put("operate_time", operate_time);
		pd.put("begin_time", begin_time);
		pd.put("end_time", end_time);
		pd.put("comment", "总耗时："+(end_time-begin_time)+"ms");
		pd.put("transaction_name", "退还余额");
		pd.put("transaction_operation", "updateCaiwuPrestoreMoney");
		dao.save("Refund_moneyMapper.saveProcedureTime", pd);
	}

	@Override
	public void updateCaiwuCustomStoredMoney(PageData refund_money_insert,
			PageData tb_order_insert, PageData tb_storedDetail_insert)
			throws Exception {
		PageData pd = new PageData();
		Long begin_time = System.currentTimeMillis();
		String operate_time = DateUtil.getTime();
		// 1、插入退款表
		dao.save("Refund_moneyMapper.refundMoneySave", refund_money_insert);
		// 2、插入订单表
		dao.save("Refund_moneyMapper.orderSave", tb_order_insert);
		// 4、储值明细表插入一条数据
		dao.save("Refund_moneyMapper.storedDetailInsert",
				tb_storedDetail_insert);
		// 3、修改储值卡表
		dao.delete("Refund_moneyMapper.editCustomStored", tb_order_insert);
		Long end_time = System.currentTimeMillis();
		pd.put("operate_time", operate_time);
		pd.put("begin_time", begin_time);
		pd.put("end_time", end_time);
		pd.put("comment", "总耗时："+(end_time-begin_time)+"ms");
		pd.put("transaction_name", "退还储值卡");
		pd.put("transaction_operation", "updateCaiwuCustomStoredMoney");
		dao.save("Refund_moneyMapper.saveProcedureTime", pd);
	}

}
