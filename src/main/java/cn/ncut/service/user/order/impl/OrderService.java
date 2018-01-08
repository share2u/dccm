package cn.ncut.service.user.order.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.QueryOrder;
import cn.ncut.service.user.order.OrderManager;
import cn.ncut.util.PageData;
import cn.ncut.util.wechat.CommonUtil;

/**
 * 说明： 订单 创建人： 创建时间：2016-12-30
 * 
 * @version
 */
@Service("orderService")
public class OrderService implements OrderManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		dao.save("OrderMapper.save", pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("OrderMapper.delete", pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("OrderMapper.edit", pd);
	}

	/**
	 * 更改状态为4
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateStatus4(PageData pd) throws Exception {
		dao.update("OrderMapper.updateStatus4", pd);
	}

	/**
	 * 更改状态为3
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateStatus3(PageData pd) throws Exception {
		dao.update("OrderMapper.updateStatus3", pd);
	}

	public void updateStatus2(PageData pd) throws Exception {
		dao.update("OrderMapper.updateStatus2", pd);
	}

	/**
	 * 更改预约时间
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateTime(PageData pd) throws Exception {
		dao.update("OrderMapper.updateTime", pd);
	}

	/**
	 * 更改订单备注
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void updateRemark(PageData pd) throws Exception {
		dao.update("OrderMapper.updateRemark", pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.datalistPage",
				page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.listAll", pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderMapper.findById", pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("OrderMapper.deleteAll", ArrayDATA_IDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> findAll(Page page) throws Exception {

		return (List<PageData>) dao.findForList("OrderMapper.findAlllistPage",
				page);
	}

	/**
	 * 查询今日订单总额
	 */
	public String queryTodayTotalMoney(String d) throws Exception {
		return (String) dao
				.findForObject("OrderMapper.queryTodayTotalMoney", d);
	}

	/**
	 * 查询历史订单总额
	 */
	public String queryhistoryTotalMoney(String d) throws Exception {
		return (String) dao.findForObject("OrderMapper.queryhistoryTotalMoney",
				d);
	}

	/**
	 * 查找所有可以退款的订单信息
	 */
	@Override
	public List<PageData> findAllCanRefund(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"OrderMapper.findAllRefundlistPage", page);
	}

	/**
	 * 退款时修改订单状态并插入退款金额
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void editStatusAndRefund(PageData pd) throws Exception {
		dao.update("OrderMapper.editStatusAndRefund", pd);
	}

	@Override
	public List<QueryOrder> quaryAllOrder(Page page) throws Exception {
		return (List<QueryOrder>) dao.findForList(
				"QueryOrderMapper.queryOrderlistPage", page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> queryServiceCostByPName(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"QueryOrderMapper.queryServiceCostByPName", pd);
	}

	@Override
	public List<QueryOrder> quaryAllOrder(PageData pd) throws Exception {
		return (List<QueryOrder>) dao.findForList(
				"QueryOrderMapper.queryOrderAll", pd);
	}

	@Override
	public List<PageData> queryOrderMxBypMethod(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"QueryOrderMapper.queryOrderMxBypMethod", pd);
	}

	@Override
	public List<PageData> queryOrderSum(PageData pdd) throws Exception {
		return (List<PageData>) dao.findForList(
				"QueryOrderMapper.queryOrderSum", pdd);
	}

	@Override
	public List<PageData> queryOrderMxSum(PageData pdd) throws Exception {
		return (List<PageData>) dao.findForList(
				"QueryOrderMapper.queryOrderMxSum", pdd);
	}

	@Override
	public List<PageData> staticticsOrdersSum(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StaticticsOrderMapper.orderCountsByStore", pd);
	}

	@Override
	public List<PageData> staticticsOrderByStaff(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StaticticsOrderMapper.orderCountsByStaff", pd);
	}

	@Override
	public List<PageData> staticticsOrdersPayMethod(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList(
				"StaticticsOrderMapper.orderPayMethodByStore", pd);
	}

	@Override
	public List<PageData> staticticsUserSource(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StaticticsOrderMapper.UserSourceProvinceSum", pd);
	}

	@Override
	public List<PageData> staticticsUserSourceProvince(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList(
				"StaticticsOrderMapper.UserSourceCitySumByProvince", pd);
	}

	@Override
	public List<PageData> staticticsOrdersSource(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StaticticsOrderMapper.staticticsOrdersSource", pd);
	}

	@Override
	public List<PageData> staticticsService(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StaticticsOrderMapper.orderCountsByService", pd);
	}

	@Override
	public Integer selectUniqueUserOrder() throws Exception {
		return (Integer) dao.findForObject(
				"StaticticsOrderMapper.selectUniqueUserOrder", null);
	}

	@Override
	public Integer selectRegistUserSum() throws Exception {
		return (Integer) dao.findForObject(
				"StaticticsOrderMapper.selectRegistUserSum", null);
	}

	@Override
	public Integer selectCompleteUserSum() throws Exception {
		return (Integer) dao.findForObject(
				"StaticticsOrderMapper.selectCompleteUserSum", null);
	}

	@Override
	public Integer selectWechatUserSum() throws Exception {
		String requestUrl = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN"
				.replace("ACCESS_TOKEN", CommonUtil.getToken().getAccessToken());
		Date currentTime = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(currentTime);
		calendar.add(calendar.DATE, -1);
		currentTime = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("begin_date", dateString);
		jsonObject.put("end_date", dateString);
		JSONObject httpsRequest = CommonUtil.httpsRequest(requestUrl, "POST",
				jsonObject.toString());
		Map m = httpsRequest;
		List list = (List) m.get("list");
		return (Integer) ((Map) list.get(0)).get("cumulate_user");
	}

	@Override
	public Integer findCountByUser(PageData pd) throws Exception {
		return (Integer) dao.findForObject("OrderMapper.findCountByUser", pd);
	}

}
