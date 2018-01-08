package cn.ncut.service.user.member.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.entity.system.QueryUser;
import cn.ncut.util.PageData;
import cn.ncut.service.user.member.MemberManager;

/** 
 * 说明： 会员管理
 * 创建人：FH Q313596790
 * 创建时间：2016-12-18
 * @version
 */
@Service("memberService")
public class MemberService implements MemberManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MemberMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MemberMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MemberMapper.edit", pd);
	}
	
	/**更新手机号和姓名
	 * @param pd
	 * @throws Exception
	 */
	public void updatenameandphone(PageData pd)throws Exception{
		dao.update("MemberMapper.updatenameandphone", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MemberMapper.deleteAll", ArrayDATA_IDS);
	}
	/**查询所有
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listmenberandgroup(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.listmenberandgrouplistPage", page);
	}
	/**自己写的 编辑时用
	 * 
	 */
	@SuppressWarnings("unchecked")
	public PageData findMemberAndGroupById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MemberMapper.findMemberAndGroupById", pd);
	}

	
	/**
	 * 储值卡售卖：查询客户的基本信息
	 * @param pd
	 * @return 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listCompleteMemberlistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.ListCompleteMemberlistPage", page);
	}
	

	/**
	 * 用户缴费：查询客户的基本信息
	 * @param pd
	 * @return 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> userPayQueryMemberlistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("MemberMapper.userPayQueryMemberlistPage", page);
	}

	/**
	 * 根据uid查询用户的储值卡和余额信息
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public PageData findUserStorededAndPrestoreByUid(int uid) throws Exception{
		return (PageData)dao.findForObject("MemberMapper.findUserStorededAndPrestoreByUid", uid);
	}
	
	
	@Override
	public PageData findByuid(Object object) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("MemberMapper.findByuid", object);
	}


	@Override
	public PageData findName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("MemberMapper.findName", pd);
	}

	/**
	 * 根据用户电话查询用户记录，如果是多条则返回List集合
	 */
	public List<PageData> queryUsernameByPhone(String phone)throws Exception{

		return (List<PageData>)dao.findForList("MemberMapper.queryUsernameByPhone", phone);
	}


	@Override
	public String findCityByPhone(String pHONE) throws Exception {
		return (String) dao.findForObject("MemberMapper.findCityByPhone", pHONE);
	}

	@Override
	public String findCityByAreaCode(String pHONE) throws Exception {
		
		return (String) dao.findForObject("MemberMapper.findCityByAreaCode", pHONE);
	}

	@Override
	public boolean isPhoneNumExist(String pHONE) throws Exception {
		return ((int)dao.findForObject("MemberMapper.isPhoneNumExist",pHONE))!=0;
	}

	@Override
	public List<PageData> DiscountlistmemberlistPage(Page page)
			throws Exception {
		return (List<PageData>)dao.findForList("MemberMapper.DiscountlistmemberlistPage", page);
	}

	@Override
	public List<QueryUser> menberandgroup(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<QueryUser>)dao.findForList("QueryMemberMapper.queryMemberlistPage", page);
	}

	@Override
	public List<PageData> selectUsersByName(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("MemberMapper.selectUsersByName", pd);
	}

	@Override
	public List<PageData> querystoredDetail(Page page) throws Exception {
		return (List<PageData>)dao.findForList("QueryMemberMapper.storedDetaillistPage", page);
	}

	@Override
	public List<PageData> queryprestoredDetail(Page page) throws Exception {
		return (List<PageData>)dao.findForList("QueryMemberMapper.prestoredDetaillistPage", page);
	}

	@Override
	public List<PageData> querydiscountDetail(Page page) throws Exception {
		return (List<PageData>)dao.findForList("QueryMemberMapper.discountDetaillistPage", page);
	}

	@Override
	public List<PageData> querycreditDetail(Page page) throws Exception {
	
		return (List<PageData>)dao.findForList("QueryMemberMapper.creditDetaillistPage", page);
	}

	@Override
	public List<QueryUser> selectUserAll(PageData pd) throws Exception {
		
		return (List<QueryUser>)dao.findForList("QueryMemberMapper.selectUser",pd);
	}

	@Override
	public List<PageData> selectUsersBy(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("QueryMemberMapper.selectUsersBy", pd);
	}

	@Override
	public List<PageData> selectstoredAll(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>)dao.findForList("QueryMemberMapper.selectstoredAll", pd);
	}


	

	
}

