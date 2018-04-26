package cn.ncut.service.recommend;

import java.util.List;

import cn.ncut.entity.Page;
import cn.ncut.util.PageData;

public interface RecommendManager {

	
	public void insertinit(PageData pd)throws Exception;
	public List<PageData> selectcategorybuy(PageData pd)throws Exception;
	public void updateGroup2ByUid(PageData pd)throws Exception;

	public List<PageData> selectUidByGroup2(PageData pd)throws Exception;
	public List<PageData> selectUidByGroup1(PageData pd)throws Exception;
	public int selectCountUidByGroup2(PageData pd)throws Exception;
	public List<PageData> selectServicecostByGroup2(PageData pd)throws Exception;
	public List<PageData> selectStaffByUid(PageData pd)throws Exception;
	public List<PageData> selectServicecostByUidAndStaffid(PageData pd)throws Exception;
	public List<PageData> selectServicecostidByPname(PageData pd)throws Exception;
	public List<PageData> selectPnameByServicecostid(PageData pd)throws Exception;
	public List<PageData> selectservicecostidBypname(PageData pd)throws Exception;
	public List<PageData> selectchuzhen(PageData pd)throws Exception;
	public void insertCF(PageData pd)throws Exception;
	public int selectGroup2Count(PageData pd)throws Exception;
	public int selectUid(PageData pd)throws Exception;
	public void updateGroup1ByUid(PageData pd)throws Exception;
	public List<PageData> selectTop10Bygroup1(PageData pd)throws Exception;
	public void updateTop10ByGroup1(PageData pd)throws Exception;
	public int transcount(PageData pd) throws Exception;
	public List<PageData> selectTrans(PageData pd)throws Exception;
	public List<PageData> selectBuyUid(PageData pd)throws Exception;
	public void insertApriori(PageData pd)throws Exception;
	public int countOldMember(PageData pd) throws Exception;
	//以下是用户细分
	public List<PageData> selectUser(PageData pd)throws Exception;
	public PageData selectOrderCountAndSumByUid(PageData pd)throws Exception;
	public PageData selectStoredCountAndRemainByUid(PageData pd)throws Exception;
	public PageData selectRefundCountAndRefundMoneyByUid(PageData pd)throws Exception;
	public PageData selectFirstOrderByUid(PageData pd)throws Exception;
	public void insertSegmentation(PageData pd)throws Exception;
	public void updateSegmentation(PageData pd)throws Exception;
	public PageData selectMaxMinAvg(PageData pd)throws Exception;
	public PageData selectByUid(PageData pd)throws Exception;
	public void updateSegmentationGroupid(PageData pd)throws Exception;
	public List<PageData> listsegmentation(Page page)throws Exception;
	public PageData selectRecommendByUid(PageData pd) throws Exception;
	public List<String> selectAfterBuyByUid(int uid) throws Exception ;
	public PageData selectTop10ByUid(PageData pd) throws Exception;
	public int insertSegmentationInfo(PageData pd)throws Exception;
	public PageData selectSegmentationInfoById(PageData pd)throws Exception;
	public int updateSegmentationInfoById(PageData pd)throws Exception;
	public int insertSegmentationResult(PageData pd)throws Exception;
	public int updateSegmentationResultByresult(PageData pd) throws Exception;
	public List<PageData> listsegmentationresult(Page page)throws Exception;
	public PageData selectSegmentationResultById(PageData pd)throws Exception;
	public int updateSegmentationRemarkById(PageData pd) throws Exception;
	public List<PageData> selectOldMember(PageData pd) throws Exception;
	public List<PageData> selectServicecostByUidGroupByStaffid(PageData pd)throws Exception;
	public int insertSegmentationWeight(PageData pd)throws Exception;
	public List<PageData> listSegmentationWeight(Page page)throws Exception;
	public List<PageData> selectAllMember(PageData pd) throws Exception;
	public void updateCF(PageData pd)throws Exception;
	public PageData selectRecommendByUid2(PageData pd) throws Exception;
	public PageData selectSegmentationWeight(PageData pd)throws Exception;
}
