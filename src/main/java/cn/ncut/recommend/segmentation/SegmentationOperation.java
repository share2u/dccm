package cn.ncut.recommend.segmentation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;



import org.springframework.stereotype.Component;

import cn.ncut.recommend.segmentation.Player;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.util.PageData;
import cn.ncut.util.UuidUtil;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Component("operationSegmentation")
@Configuration
@EnableAsync
@EnableScheduling
public class SegmentationOperation {
	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	@Resource(name = "segmentationPreparation")
	private SegmentationPreparation segmentationPreparation;
	@Resource(name = "memberService")
	private MemberManager memberService;
	@Scheduled(cron = " 00 15 16 * * ?  ")
	public void KmeansOperation() throws Exception{
		//对用户购买的商品类别进行聚类
        int k = 5;
		List<Player> oriList = segmentationPreparation.prepareFile1();
		//List<Player> oriList2 = segmentationPreparation.prepareFile2();
		Object[] ob = segmentationPreparation.prepareFile2();
		List<Player> oriList2 = (List<Player>) ob[0];
	       
	        Kmeans<Player> kmeans = new Kmeans<Player>(oriList2,k);//pointCenter,pointCenter.size()
	        //2为聚类个数
	        List<Player>[] results = kmeans.comput();
	       
	   
	        File fileName = new File("E:\\用户细分结果.txt");
	        String segmentation_result = UuidUtil.get32UUID();
  		  PrintWriter outFile = null;
  		  outFile = new PrintWriter(fileName);
  		for (int i = 0; i < results.length; i++) {
  			List<Player> list = results[i];//每一组用户
  			//计算每一组用户的每个维度的平均数
  			double avg_proportion =0.0;
				double avg_attentiontime = 0.0;
				double avg_avgordercount = 0.0;
				double avg_avgordersum = 0.0;
				double avg_ordercount = 0.0;
				double avg_ordersum = 0.0;
				double avg_storedcount = 0.0;
				double avg_remain = 0.0;
				double avg_refundcount = 0.0;
				double avg_refundmoney  = 0.0;
				int group_id=0;
  			for(Player p : list){
  				outFile.println("第"+(i)+"组"+p.toString());
  				outFile.flush();
  				//写入数据库
  				String uid = p.getUid();
  				//个人折扣
  				
  				PageData userpd = new PageData();
  				userpd.put("uid", uid);
  				avg_proportion = avg_proportion + p.getProportion();
  				avg_attentiontime =  avg_attentiontime + p.getAttentiontime();
  				avg_avgordercount = avg_avgordercount + p.getAvgordercount();
  				avg_avgordersum = avg_avgordersum + p.getAvgordersum();
  				avg_ordercount = avg_ordercount + p.getOrdercount();
  				avg_ordersum = avg_ordersum + p.getOrdersum();
  				avg_storedcount = avg_storedcount + p.getStoredcount();
  				avg_remain = avg_remain + p.getRemain();
  				avg_refundcount = avg_refundcount + p.getRefundcount();
  				avg_refundmoney = avg_refundmoney + p.getRefundmoney();
  				group_id = i+1;
  				userpd.put("groupid", group_id);
  				recommendService.updateSegmentationGroupid(userpd);
  			}
  			avg_proportion = (double)avg_proportion/list.size();
  			avg_attentiontime = (double)avg_attentiontime/list.size();
  			avg_avgordercount = (double)avg_avgordercount/list.size();
  			avg_avgordersum = (double)avg_avgordersum/list.size();
  			avg_ordercount = (double)avg_ordercount/list.size();
  			avg_ordersum = (double)avg_ordersum/list.size();
  			avg_storedcount = (double)avg_storedcount/list.size();
  			avg_remain = (double)avg_remain/list.size();
  			avg_refundcount = (double)avg_refundcount/list.size();
  			avg_refundmoney = (double)avg_refundmoney/list.size();
  			StringBuffer sb = new StringBuffer("");
  			//查询
  			PageData pd = new PageData();
  			pd.put("segmentation_info_id", ob[1]);
  			PageData segmentationinfopd = recommendService.selectSegmentationInfoById(pd);
  			if(avg_proportion/(double)segmentationinfopd.get("avg_proportion")>2){
  				sb.append("超高平均折扣,");
  			}else if((double)segmentationinfopd.get("avg_proportion")<avg_proportion){
  				sb.append("高平均折扣,");
  			}else if((double)segmentationinfopd.get("avg_proportion")/avg_proportion>2){
  				sb.append("超低平均折扣,");
  			}else if(avg_proportion<(double)segmentationinfopd.get("avg_proportion")){
  				sb.append("低平均折扣,");
  				}
  			
  			if(avg_attentiontime/(double)segmentationinfopd.get("avg_attentiontime")>2){
  				sb.append("超高平均关注时间,");
  			}else if((double)segmentationinfopd.get("avg_attentiontime")<avg_attentiontime){
  				sb.append("高平均关注时间,");
  			}else if((double)segmentationinfopd.get("avg_attentiontime")/avg_attentiontime>2){
  				sb.append("超低平均关注时间,");
  			}else if(avg_attentiontime<(double)segmentationinfopd.get("avg_attentiontime")){
  				sb.append("低平均关注时间,");
  				}
  			
  			if(avg_avgordercount/(double)segmentationinfopd.get("avg_avgordercount")>2){
  				sb.append("超高月均订单次数,");
  			}else if((double)segmentationinfopd.get("avg_avgordercount")<avg_avgordercount){
  				sb.append("高月均订单次数,");
  			}else if((double)segmentationinfopd.get("avg_avgordercount")/avg_avgordercount>2){
  				sb.append("超低月均订单次数,");
  			}else if(avg_avgordercount<(double)segmentationinfopd.get("avg_avgordercount")){
  				sb.append("低月均订单次数,");
  				}
  			if(avg_avgordersum/(double)segmentationinfopd.get("avg_avgordersum")>2){
  				sb.append("超高月均订单金额,");
  			}else if((double)segmentationinfopd.get("avg_avgordersum")<avg_avgordersum){
  				sb.append("高月均订单金额,");
  			}else if((double)segmentationinfopd.get("avg_avgordersum")/avg_avgordersum>2){
  				sb.append("超低月均订单金额,");
  			}else if(avg_avgordersum<(double)segmentationinfopd.get("avg_avgordersum")){
  				sb.append("低月均订单金额,");
  				}
  			if(avg_ordercount/(double)segmentationinfopd.get("avg_ordercount")>2){
  				sb.append("超高订单数量,");
  			}else if((double)segmentationinfopd.get("avg_ordercount")<avg_ordercount){
  				sb.append("高订单数量,");
  			}else if((double)segmentationinfopd.get("avg_ordercount")/avg_ordercount>2){
  				sb.append("超低订单数量,");
  			}else if(avg_ordercount<(double)segmentationinfopd.get("avg_ordercount")){
  				sb.append("低订单数量,");
  				}
  			if(avg_ordersum/(double)segmentationinfopd.get("avg_ordersum")>2){
  				sb.append("超高订单金额,");
  			}else if((double)segmentationinfopd.get("avg_ordersum")<avg_ordersum){
  				sb.append("高订单金额,");
  			}else if((double)segmentationinfopd.get("avg_ordersum")/avg_ordersum>2){
  				sb.append("超低订单金额,");
  			}else if(avg_ordersum<(double)segmentationinfopd.get("avg_ordersum")){
  				sb.append("低订单金额,");
  				}
  			if(avg_storedcount/(double)segmentationinfopd.get("avg_storedcount")>2){
  				sb.append("超高储值次数,");
  			}else if((double)segmentationinfopd.get("avg_storedcount")<avg_storedcount){
  				sb.append("高储值次数,");
  			}else if((double)segmentationinfopd.get("avg_storedcount")/avg_storedcount>2){
  				sb.append("超低储值次数,");
  			}else if(avg_storedcount<(double)segmentationinfopd.get("avg_storedcount")){
  				sb.append("低储值次数,");
  				}
  			if(avg_remain/(double)segmentationinfopd.get("avg_remain")>2){
  				sb.append("超高储值卡余额,");
  			}else if((double)segmentationinfopd.get("avg_remain")<avg_remain){
  				sb.append("高储值卡余额,");
  			}else if((double)segmentationinfopd.get("avg_remain")/avg_remain>2){
  				sb.append("超低储值卡余额,");
  			}else if(avg_remain<(double)segmentationinfopd.get("avg_remain")){
  				sb.append("低储值卡余额,");
  				}
  			if(avg_refundcount/(double)segmentationinfopd.get("avg_refundcount")>2){
  				sb.append("超高退款次数,");
  			}else if((double)segmentationinfopd.get("avg_refundcount")<avg_refundcount){
  				sb.append("高退款次数,");
  			}else if((double)segmentationinfopd.get("avg_refundcount")/avg_refundcount>2){
  				sb.append("超低退款次数,");
  			}else if(avg_refundcount<(double)segmentationinfopd.get("avg_refundcount")){
  				sb.append("低退款次数,");
  				}
  			if(avg_refundmoney/(double)segmentationinfopd.get("avg_refundmoney")>2){
  				sb.append("超高退卡金额,");
  			}else if((double)segmentationinfopd.get("avg_refundmoney")<avg_refundmoney){
  				sb.append("高退卡金额,");
  			}else if((double)segmentationinfopd.get("avg_refundmoney")/avg_refundmoney>2){
  				sb.append("超低退卡金额,");
  			}else if(avg_refundmoney<(double)segmentationinfopd.get("avg_refundmoney")){
  				sb.append("低退卡金额,");
  				}
  			if (sb.length() > 0)  
  	  			sb.deleteCharAt(sb.length() - 1); //调用 字符串的deleteCharAt() 方法,删除最后一个多余的逗号 
  			String result = new String(sb);
  		  
  			//更新segmentation_info(k,segmentation_result,create_time)
  			pd.put("k", k);
  			pd.put("segmentation_result", segmentation_result);
  			Date d = new Date();
  			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  			pd.put("create_time", sdf.format(d));
  			recommendService.updateSegmentationInfoById(pd);
  			//插入segmentation_result()
  			PageData segmentationresultpd = new PageData();
  			segmentationresultpd.put("avg_proportion", avg_proportion);
  			segmentationresultpd.put("avg_attentiontime", avg_attentiontime);
  			segmentationresultpd.put("avg_avgordercount", avg_avgordercount);
  			segmentationresultpd.put("avg_avgordersum", avg_avgordersum);
  			segmentationresultpd.put("avg_ordercount", avg_ordercount);
  			segmentationresultpd.put("avg_ordersum", avg_ordersum);
  			segmentationresultpd.put("avg_storedcount", avg_storedcount);
  			segmentationresultpd.put("avg_remain", avg_remain);
  			segmentationresultpd.put("avg_refundcount", avg_refundcount);
  			segmentationresultpd.put("avg_refundmoney", avg_refundmoney);
  			segmentationresultpd.put("segmentation_result", segmentation_result);
  			segmentationresultpd.put("group_id", group_id);
  			segmentationresultpd.put("result", result);
  			segmentationresultpd.put("number", list.size());
  			segmentationresultpd.put("flag", 1);
  			recommendService.insertSegmentationResult(segmentationresultpd);
  			//segmentationresultpd.get("segmentation_result_id");
  			//把其他的flag都设成0
  			recommendService.updateSegmentationResultByresult(segmentationresultpd);
  			
  		}
  		outFile.close();
	      
	}
}

