package cn.ncut.service.recommend.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ncut.dao.DaoSupport;
import cn.ncut.entity.Page;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.util.PageData;



@Service("recommendService")
public class RecommendService  implements RecommendManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void insertinit(PageData pd)throws Exception{
		dao.save("RecommendMapper.insertinit", pd);
	}
	/**查询用户之前购买了哪些项目类别的项目，groupby项目类别
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectcategorybuy(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("RecommendMapper.selectcategorybuy", pd);
	}
	/**更新recommend表的group2
	 * @param pd
	 * @throws Exception
	 */
	public void updateGroup2ByUid(PageData pd)throws Exception{
		 dao.update("RecommendMapper.updategroup2", pd);
	}
	/**根据group2查询所有的用户
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectUidByGroup2(PageData pd)throws Exception{
		 return (List<PageData>) dao.findForList("RecommendMapper.selectUidByGroup2", pd);
	}
	/**根据group1查询所有的用户
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectUidByGroup1(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectUidByGroup1", pd);
	}
	/**根据group2查询所有的用户的个数
	 * @param pd
	 * @throws Exception
	 */
	public int selectCountUidByGroup2(PageData pd)throws Exception{
		 return (int) dao.findForObject("RecommendMapper.selectCountUidByGroup2", pd);
	}
	/**根据group2查询所有的用户购买的项目
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectServicecostByGroup2(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectServicecostByGroup2", pd);
	}
	/**查询用户都购买了哪些医生的项目
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectStaffByUid(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectStaffByUid", pd);
	}
	/**查询该用户购买过什么项目，根据医生分组
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectServicecostByUidGroupByStaffid(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectServicecostByUidGroupByStaffid", pd);
	}
	/**查询该用户在该医生下购买了什么项目
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectServicecostByUidAndStaffid(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectServicecostByUidAndStaffid", pd);
	}
	/**根据项目名查询项目编号
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectServicecostidByPname(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectServicecostidByPname", pd);
	}
	/**根据pid查询pname
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectPnameByServicecostid(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectPnameByServicecostid", pd);
	}
	/**根据pname查询所有信息
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectservicecostidBypname(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectservicecostidBypname", pd);
	}
	/**查询用户是否买过初诊项目
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectchuzhen(PageData pd)throws Exception{
		 return (List<PageData>)dao.findForList("RecommendMapper.selectchuzhen", pd);
	}
	/**插入协同过滤结果
	 * @param pd
	 * @throws Exception
	 */
	public void insertCF(PageData pd)throws Exception{
		 dao.save("RecommendMapper.insertCF", pd);
	}
	/**查询有多少各group2
	 * @param pd
	 * @throws Exception
	 */
	public int selectGroup2Count(PageData pd)throws Exception{
		 
		return (int) dao.findForObject("RecommendMapper.selectGroup2Count", pd);
	}
	/**查询uid是否已经插入
	 * @param pd
	 * @throws Exception
	 */
	public int selectUid(PageData pd)throws Exception{
		 
		return (int) dao.findForObject("RecommendMapper.selectUid", pd);
	}
	/**更新group1
	 * @param pd
	 * @throws Exception
	 */
	public void updateGroup1ByUid(PageData pd)throws Exception{
		 dao.save("RecommendMapper.updateGroup1ByUid", pd);
	}
	/**按group1查询top10
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectTop10Bygroup1(PageData pd)throws Exception{
		 
		return (List<PageData>) dao.findForList("RecommendMapper.selectTop10Bygroup1", pd);
	}
	/**更新top10
	 * @param pd
	 * @throws Exception
	 */
	public void updateTop10ByGroup1(PageData pd)throws Exception{
		 dao.save("RecommendMapper.updateTop10ByGroup1", pd);
	}
	
	public int transcount(PageData pd) throws Exception {
		
		return (int) dao.findForObject("RecommendMapper.transcount", pd);
	}
	/**查询所有事物
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectTrans(PageData pd)throws Exception{
		 
		return (List<PageData>) dao.findForList("RecommendMapper.selectTrans", pd);
	}
	
	/**查询哪些用户购买了商品
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> selectBuyUid(PageData pd)throws Exception{
		 
		return (List<PageData>) dao.findForList("RecommendMapper.selectBuyUid", pd);
	}
	/**插入关联规则结果
	 * @param pd
	 * @throws Exception
	 */
	public void insertApriori(PageData pd)throws Exception{
		 dao.save("AprioriMapper.insertapriori", pd);
		 
		 
	}
	/**查询老用户人数
	 * 
	 */
    public int countOldMember(PageData pd) throws Exception {
		
		return (int) dao.findForObject("RecommendMapper.countOldMember", pd);
	}
    
public List<PageData> selectOldMember(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("RecommendMapper.listOldUser", pd);
	}
	
	public List<PageData> selectUser(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList("MemberMapper.selectUser", pd);
	}
	
	public PageData selectOrderCountAndSumByUid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (PageData) dao.findForObject("OrderMapper.selectOrderCountAndSumByUid", pd);
	}
	public PageData selectStoredCountAndRemainByUid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return  (PageData) dao.findForObject("CustomStoredMapper.selectStoredCountAndRemainByUid", pd);
	}
	
	public PageData selectRefundCountAndRefundMoneyByUid(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("RefundMapper.selectRefundCountAndRefundMoneyByUid", pd);
	}
	
	public PageData selectFirstOrderByUid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("OrderMapper.selectFirstOrderByUid", pd);
	}
	public void insertSegmentation(PageData pd)throws Exception{
		 dao.save("SegmentationMapper.insertSegmentation", pd);
	}
	@Override
	public void updateSegmentation(PageData pd) throws Exception {
		dao.save("SegmentationMapper.updateSegmentation", pd);
		
	}
	@Override
	public PageData selectMaxMinAvg(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("SegmentationMapper.selectMaxMinAvg", pd);
	}
	@Override
	public PageData selectByUid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("SegmentationMapper.selectByUid", pd);
	}
	@Override
	public void updateSegmentationGroupid(PageData pd) throws Exception {
		dao.save("SegmentationMapper.updateSegmentationGroupid", pd);
		
	}
	/**查询所有
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listsegmentation(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SegmentationMapper.listsegmentationlistPage", page);
	}
	
	public PageData selectRecommendByUid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("RecommendMapper.selectRecommendByUid", pd);
	}
	public PageData selectTop10ByUid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("RecommendMapper.selectTop10ByUid", pd);
	}
	
	public List<String> selectAfterBuyByUid(int uid) throws Exception {
		// TODO Auto-generated method stub
		return (List<String>) dao.findForList("RecommendMapper.selectAfterBuyByUid", uid);
	}
	public int insertSegmentationInfo(PageData pd)throws Exception{
		return (int) dao.save("SegmentationInfoMapper.insertSegmentationInfo", pd);
		
	}
	public PageData selectSegmentationInfoById(PageData pd)throws Exception{
		return (PageData) dao.findForObject("SegmentationInfoMapper.selectSegmentationInfoById", pd);
		
	}
	@Override
	public int updateSegmentationInfoById(PageData pd) throws Exception {
		
		 return (int) dao.save("SegmentationInfoMapper.updateSegmentationInfoById", pd);
	}
	@Override
	public int insertSegmentationResult(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		 return (int) dao.save("SegmentationResultMapper.insertSegmentationResult", pd);
	}
	@Override
	public int updateSegmentationResultByresult(PageData pd) throws Exception {
		
		 return (int) dao.save("SegmentationResultMapper.updateSegmentationResultByresult", pd);
	}
	public List<PageData> listsegmentationresult(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SegmentationResultMapper.listsegmentationresult", page);
	}
	public PageData selectSegmentationResultById(PageData pd)throws Exception{
		return (PageData) dao.findForObject("SegmentationResultMapper.selectSegmentationResultById", pd);
		
	}
	public int updateSegmentationRemarkById(PageData pd) throws Exception {
		
		 return (int) dao.save("SegmentationResultMapper.updateSegmentationRemarkById", pd);
	}
	@Override
	public int insertSegmentationWeight(PageData pd) throws Exception {
		
		return (int) dao.save("SegmentationWeightMapper.insertSegmentationWeight", pd);
	}
	public List<PageData> listSegmentationWeight(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SegmentationWeightMapper.listSegmentationWeight", page);
	}
}
