package cn.ncut.recommend.segmentation;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;



import cn.ncut.recommend.segmentation.Player;

import cn.ncut.service.finance.serviceall.ServiceCategoryManager;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.recommend.impl.RecommendService;
import cn.ncut.service.user.member.MemberManager;

import cn.ncut.util.PageData;


@Component("segmentationPreparation")
public class SegmentationPreparation {
	
	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	@Resource(name = "memberService")
	private MemberManager memberService;
	@Resource(name = "servicecategoryService")
	private ServiceCategoryManager servicecategoryService;
	@Resource(name = "standardization")
	private Standardization standardization;
	
	/**
	 * 1、数据准备
	 * 2、
	 * @return
	 * @throws Exception 
	 */
	
	public  List<Player> prepareFile1() throws Exception{
		PageData pd = new PageData();
		List<Player> playerlist = new ArrayList<Player>();
		Player player = null;
		 SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
	     System.out.println("时间："+sd.format(new Date())+"： ---开始进行数据采集---");
		//1、查询用户类别、用户个人折扣
		List<PageData> userlistpd = recommendService.selectUser(pd);//用户信息
		for(PageData userpd:userlistpd){
			player = new Player();
			//判断用户的关注时间是否为空
			PageData orderpd = recommendService.selectFirstOrderByUid(userpd);
			if(userpd.get("attentiontime")==null||userpd.get("attentiontime")==""){
				//如果为空，查询用户首次下订单的时间
				
				if(orderpd.getString("mincreatetime")==null||orderpd.getString("mincreatetime")==""){
					Date day=new Date();    
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                    orderpd.put("mincreatetime", df.format(day));
                    userpd.put("attentiontime", df.format(day));
				}
				userpd.put("attentiontime", orderpd.getString("mincreatetime"));
			}
			
				//判断与系统时间间隔的天数
			long betweendays =0;
				Date day=new Date();    
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                String etime = df.format(day);
                String stime = userpd.getString("attentiontime");
                if(!stime.equals("0")){
                betweendays = getBetweenDays(stime,etime);
                }else{
                	betweendays = 0;
                }
                	if(betweendays<0){
                		betweendays = 0;
                	}	
				userpd.put("attentiontime", betweendays);
			
			//2、查询用户订单数量、订单总金额
			 orderpd = recommendService.selectOrderCountAndSumByUid(userpd);
			userpd.put("ordercount", orderpd.get("ordercount"));
			userpd.put("ordersum", orderpd.get("ordersum"));
			//3、查询用户月均订单数量、订单总金额
			//查询用户首单时间
			 double avgordercount = 0.00;
			 double avgordersum = 0.00;
			 orderpd = recommendService.selectFirstOrderByUid(userpd);
			 if(!orderpd.getString("mincreatetime").equals("0")){
			 stime = orderpd.getString("mincreatetime");
					 day=new Date();    
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            etime = df.format(day);
             betweendays = getBetweenDays(stime,etime);
            DecimalFormat df2 = new DecimalFormat("######0.00");     
            avgordercount = Double.parseDouble(df2.format(((double)Integer.parseInt(String.valueOf(userpd.get("ordercount")))/betweendays)*31));
            		
            avgordersum = Double.parseDouble(df2.format(((Double.parseDouble(userpd.getString("ordersum")))/betweendays)*31));
		}
            userpd.put("avgordercount", avgordercount);
            userpd.put("avgordersum", avgordersum);
			//3、查询用户储值次数、用户储值卡余额
			PageData storedpd = recommendService.selectStoredCountAndRemainByUid(userpd);
			userpd.put("storedcount", storedpd.get("storedcount"));
			userpd.put("remain", storedpd.get("remain"));
			//4、查询用户退费次数、用户退卡金额
			PageData refundpd = recommendService.selectRefundCountAndRefundMoneyByUid(userpd);
			userpd.put("refundcount", refundpd.get("refundcount"));
			userpd.put("refundmoney", refundpd.get("refundmoney"));
			player.setUid(String.valueOf(userpd.get("uid")));
			player.setOrdercount((double)Integer.parseInt(String.valueOf(userpd.get("ordercount"))));
			player.setProportion((double)userpd.get("proportion"));
			player.setOrdersum(Double.parseDouble((String)userpd.get("ordersum")));
			player.setAvgordersum(avgordersum);
			player.setAvgordercount(avgordercount);
			player.setAttentiontime((double)((long)userpd.get("attentiontime")));
			player.setUsercategoryid((double)Integer.parseInt(String.valueOf(userpd.get("usercategory_id"))));
			player.setRefundcount((double)Integer.parseInt(String.valueOf(userpd.get("refundcount"))));
			player.setRefundmoney((double)userpd.get("refundmoney"));
			player.setRemain((double)userpd.get("remain"));
			player.setStoredcount((double)Integer.parseInt(String.valueOf(userpd.get("storedcount"))));
			//插入数据库
			userpd.put("usercategoryid", userpd.get("usercategory_id"));
			recommendService.insertSegmentation(userpd);
			playerlist.add(player);
		}
		 System.out.println("时间："+sd.format(new Date())+"： ---开始进行数据log转换---");
		List<Player> newplayerlist = standardization.log(playerlist);
		System.out.println("时间："+sd.format(new Date())+"： ---开始进行数据标准化---");
		newplayerlist = standardization.standardization(newplayerlist);//标准化
		System.out.println("时间："+sd.format(new Date())+"： ---开始进行计算各细分变量权重---");
		standardization.weight(newplayerlist);//设置权重
		return playerlist;
		
	}
	public Object[] prepareFile2() throws Exception{//设置权重之后的数据
		Object[] ob = new Object[2];
		PageData pd = new PageData();
		List<Player> playerlist = new ArrayList<Player>();
		Player player = null;
		List<PageData> userlistpd = recommendService.selectUser(pd);//用户信息
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
		for(PageData userpd:userlistpd){
			player = new Player();
			PageData recommendpd= recommendService.selectByUid(userpd);
			player.setAttentiontime((double)recommendpd.get("attentiontime"));
			player.setAvgordercount((double)recommendpd.get("avgordercount"));
			player.setAvgordersum((double)recommendpd.get("avgordersum"));
			player.setOrdercount((double)recommendpd.get("ordercount"));
			player.setOrdersum((double)recommendpd.get("ordersum"));
			player.setProportion((double)recommendpd.get("proportion"));
			player.setRefundcount((double)recommendpd.get("refundcount"));
			player.setRefundcount((double)recommendpd.get("refundmoney"));
			player.setRemain((double)recommendpd.get("remain"));
			player.setStoredcount((double)recommendpd.get("storedcount"));
			player.setUid(String.valueOf((int)userpd.get("uid")));
			player.setUsercategoryid((double)recommendpd.get("usercategoryid"));
			    avg_proportion = avg_proportion + (double)recommendpd.get("proportion");
				avg_attentiontime =  avg_attentiontime + (double)recommendpd.get("attentiontime");
				avg_avgordercount = avg_avgordercount + (double)recommendpd.get("avgordercount");
				avg_avgordersum = avg_avgordersum + (double)recommendpd.get("avgordersum");
				avg_ordercount = avg_ordercount + (double)recommendpd.get("ordercount");
				avg_ordersum = avg_ordersum + (double)recommendpd.get("ordersum");
				avg_storedcount = avg_storedcount + (double)recommendpd.get("storedcount");
				avg_remain = avg_remain + (double)recommendpd.get("remain");
				avg_refundcount = avg_refundcount + (double)recommendpd.get("refundcount");
				avg_refundmoney = avg_refundmoney + (double)recommendpd.get("refundmoney");
			playerlist.add(player);
			
		}
		    avg_proportion = (double)avg_proportion/userlistpd.size();
			avg_attentiontime = (double)avg_attentiontime/userlistpd.size();
			avg_avgordercount = (double)avg_avgordercount/userlistpd.size();
			avg_avgordersum = (double)avg_avgordersum/userlistpd.size();
			avg_ordercount = (double)avg_ordercount/userlistpd.size();
			avg_ordersum = (double)avg_ordersum/userlistpd.size();
			avg_storedcount = (double)avg_storedcount/userlistpd.size();
			avg_remain = (double)avg_remain/userlistpd.size();
			avg_refundcount = (double)avg_refundcount/userlistpd.size();
			avg_refundmoney = (double)avg_refundmoney/userlistpd.size();
			PageData segmentationinfopd = new PageData();
			segmentationinfopd.put("avg_proportion", avg_proportion);
			segmentationinfopd.put("avg_attentiontime", avg_attentiontime);
			segmentationinfopd.put("avg_avgordercount", avg_avgordercount);
			segmentationinfopd.put("avg_avgordersum", avg_avgordersum);
			segmentationinfopd.put("avg_ordercount", avg_ordercount);
			segmentationinfopd.put("avg_ordersum", avg_ordersum);
			segmentationinfopd.put("avg_storedcount", avg_storedcount);
			segmentationinfopd.put("avg_remain", avg_remain);
			segmentationinfopd.put("avg_refundcount", avg_refundcount);
			segmentationinfopd.put("avg_refundmoney", avg_refundmoney);
			recommendService.insertSegmentationInfo(segmentationinfopd);
			System.out.println(segmentationinfopd.get("segmentation_info_id"));
			ob[0] = playerlist;
			ob[1] = (long)segmentationinfopd.get("segmentation_info_id");
		return ob;
		
	}
	public static  long getBetweenDays(String stime,String etime){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date sdate=null;
        Date eDate=null;
        try {
             sdate=df.parse(stime);
             eDate=df.parse(etime);
        } catch (ParseException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
        }

   long betweendays=(long) ((eDate.getTime()-sdate.getTime())/(1000 * 60 * 60 *24)+0.5);//天数间隔
       
        return betweendays;
  }
	}

