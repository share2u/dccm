package cn.ncut.recommend.segmentation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.util.PageData;
@Component("standardization")
public class Standardization {
	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	public List<Player> log(List<Player> playerlist) throws Exception {
		List<Player> newplayerlist = new ArrayList<Player>();
		for(Player p : playerlist){
			PageData userpd = new PageData();
			if(p.getRemain()<0){
				p.setRemain(0.00);
			}
		p.setAttentiontime((Math.log((double)p.getAttentiontime()+1)/Math.log((double)10)));
		p.setAvgordercount((Math.log((double)p.getAvgordercount()+1)/Math.log((double)10)));
		p.setAvgordersum((Math.log((double)p.getAvgordersum()+1)/Math.log((double)10)));
		p.setOrdercount((Math.log((double)p.getOrdercount()+1)/Math.log((double)10)));
		p.setOrdersum((Math.log((double)p.getOrdersum()+1)/Math.log((double)10)));
		p.setRefundcount((Math.log((double)p.getRefundcount()+1)/Math.log((double)10)));
		p.setRefundmoney((Math.log((double)p.getRefundmoney()+1)/Math.log((double)10)));
		p.setStoredcount((Math.log((double)p.getStoredcount()+1)/Math.log((double)10)));
		p.setRemain((Math.log((double)p.getRemain()+1)/Math.log((double)10)));
		userpd.put("uid", p.getUid());
		userpd.put("usercategoryid", p.getUsercategoryid());
		userpd.put("proportion", p.getProportion());
		userpd.put("attentiontime", p.getAttentiontime());
		userpd.put("ordercount", p.getOrdercount());
		userpd.put("ordersum", p.getOrdersum());
		userpd.put("avgordercount", p.getAvgordercount());
		userpd.put("avgordersum", p.getAvgordersum());
		userpd.put("storedcount", p.getStoredcount());
		userpd.put("remain", p.getRemain());
		userpd.put("refundcount", p.getRefundcount());
		userpd.put("refundmoney", p.getRefundmoney());
		recommendService.updateSegmentation(userpd);
		newplayerlist.add(p);
		}
		return newplayerlist;
		
	}
	public List<Player> standardization(List<Player> playerlist) throws Exception {
		//找出最大最小的值
		PageData pd = new PageData();
		PageData recommendpd = recommendService.selectMaxMinAvg(pd);
		
		List<Player> newplayerlist = new ArrayList<Player>();
		for(Player p : playerlist){
			PageData userpd = new PageData();
			p.setAttentiontime((p.getAttentiontime()-(double)recommendpd.get("min_attentiontime"))/((double)recommendpd.get("max_attentiontime")-(double)recommendpd.get("min_attentiontime")));	
			p.setAvgordercount((p.getAvgordercount()-(double)recommendpd.get("min_avgordercount"))/((double)recommendpd.get("max_avgordercount")-(double)recommendpd.get("min_avgordercount")));	
			p.setAvgordersum((p.getAvgordersum()-(double)recommendpd.get("min_avgordersum"))/((double)recommendpd.get("max_avgordersum")-(double)recommendpd.get("min_avgordersum")));	
			p.setOrdercount((p.getOrdercount()-(double)recommendpd.get("min_ordercount"))/((double)recommendpd.get("max_ordercount")-(double)recommendpd.get("min_ordercount")));	
			p.setOrdersum((p.getOrdersum()-(double)recommendpd.get("min_ordersum"))/((double)recommendpd.get("max_ordersum")-(double)recommendpd.get("min_ordersum")));	
			p.setRefundcount((p.getRefundcount()-(double)recommendpd.get("min_refundcount"))/((double)recommendpd.get("max_refundcount")-(double)recommendpd.get("min_refundcount")));	
			p.setRefundmoney((p.getRefundmoney()-(double)recommendpd.get("min_refundmoney"))/((double)recommendpd.get("max_refundmoney")-(double)recommendpd.get("min_refundmoney")));	
			p.setRemain((p.getRemain()-(double)recommendpd.get("min_remain"))/((double)recommendpd.get("max_remain")-(double)recommendpd.get("min_remain")));	
			p.setStoredcount((p.getStoredcount()-(double)recommendpd.get("min_storedcount"))/((double)recommendpd.get("max_storedcount")-(double)recommendpd.get("min_storedcount")));	
			p.setUsercategoryid((p.getUsercategoryid()-(double)recommendpd.get("min_usercategoryid"))/((double)recommendpd.get("max_usercategoryid")-(double)recommendpd.get("min_usercategoryid")));
		/*p.setAttentiontime((Double.valueOf(p.getAttentiontime())-Double.valueOf(String.valueOf(recommendpd.get("min_attentiontime"))))/(Double.valueOf(String.valueOf(recommendpd.getString("max_attentiontime")))-Double.valueOf(String.valueOf(recommendpd.getString("min_attentiontime")))));	
		p.setAvgordercount((Double.valueOf(p.getAvgordercount())-Double.valueOf(String.valueOf(recommendpd.get("min_avgordercount"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_avgordercount")))-Double.valueOf(String.valueOf(recommendpd.get("min_avgordercount")))));	
		p.setAvgordersum((Double.valueOf(p.getAvgordersum())-Double.valueOf(String.valueOf(recommendpd.get("min_avgordersum"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_avgordersum")))-Double.valueOf(String.valueOf(recommendpd.get("min_avgordersum")))));	
		p.setOrdercount((Double.valueOf(p.getOrdercount())-Double.valueOf(String.valueOf(recommendpd.get("min_ordercount"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_ordercount")))-Double.valueOf(String.valueOf(recommendpd.get("min_ordercount")))));	
		p.setOrdersum((Double.valueOf(p.getOrdersum())-Double.valueOf(String.valueOf(recommendpd.get("min_ordersum"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_ordersum")))-Double.valueOf(String.valueOf(recommendpd.get("min_ordersum")))));	
		p.setRefundcount((Double.valueOf(p.getRefundcount())-Double.valueOf(String.valueOf(recommendpd.get("min_refundcount"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_refundcount")))-Double.valueOf(String.valueOf(recommendpd.get("min_refundcount")))));	
		p.setRefundmoney((Double.valueOf(p.getRefundmoney())-Double.valueOf(String.valueOf(recommendpd.get("min_refundmoney"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_refundmoney")))-Double.valueOf(String.valueOf(recommendpd.get("min_refundmoney")))));	
		p.setRemain((Double.valueOf(p.getRemain())-Double.valueOf(String.valueOf(recommendpd.get("min_remain"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_remain")))-Double.valueOf(String.valueOf(recommendpd.get("min_remain")))));	
		p.setStoredcount((Double.valueOf(p.getStoredcount())-Double.valueOf(String.valueOf(recommendpd.get("min_storedcount"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_storedcount")))-Double.valueOf(String.valueOf(recommendpd.get("min_storedcount")))));	
		p.setUsercategoryid((Double.valueOf(p.getUsercategoryid())-Double.valueOf(String.valueOf(recommendpd.get("min_usercategoryid"))))/(Double.valueOf(String.valueOf(recommendpd.get("max_usercategoryid")))-Double.valueOf(String.valueOf(recommendpd.get("min_usercategoryid")))));	*/
		userpd.put("uid", p.getUid());
		userpd.put("usercategoryid", p.getUsercategoryid());
		userpd.put("proportion", p.getProportion());
		userpd.put("attentiontime", p.getAttentiontime());
		userpd.put("ordercount", p.getOrdercount());
		userpd.put("ordersum", p.getOrdersum());
		userpd.put("avgordercount", p.getAvgordercount());
		userpd.put("avgordersum", p.getAvgordersum());
		userpd.put("storedcount", p.getStoredcount());
		userpd.put("remain", p.getRemain());
		userpd.put("refundcount", p.getRefundcount());
		userpd.put("refundmoney", p.getRefundmoney());
		recommendService.updateSegmentation(userpd);
		
		newplayerlist.add(p);
		}
		return newplayerlist;
		
	}
	public List<Player> weight(List<Player> playerlist) throws Exception {
		//找出最大最小的值
		PageData pd = new PageData();
		PageData userpd = new PageData();
		PageData recommendpd = recommendService.selectMaxMinAvg(pd);
		double attentiontime = 0.0;
		double avgordercount = 0.0;
		double avgordersum = 0.0;
		double ordercount =0.0;
		double ordersum = 0.0;
		double refundcount = 0.0;
		double refundmoney = 0.0;
		double storedcount = 0.0;
		double remain = 0.0;
		double proportion = 0.0;
		double usercategoryid = 0.0;
		
		List<Player> newplayerlist = new ArrayList<Player>();
		for(Player p : playerlist){
			
			
			attentiontime = attentiontime +Math.abs(p.getAttentiontime()-(double) recommendpd.get("avg_attentiontime"));
			
			avgordercount = avgordercount +Math.abs(p.getAvgordercount()-(double) recommendpd.get("avg_avgordercount"));
			
			avgordersum = avgordersum +Math.abs(p.getAvgordersum()-(double) recommendpd.get("avg_avgordersum"));
			
			ordercount = ordercount + Math.abs(p.getOrdercount()-(double) recommendpd.get("avg_ordercount"));
		
			ordersum = ordersum + Math.abs(p.getOrdersum()-(double) recommendpd.get("avg_ordersum"));
			
			refundcount = refundcount + Math.abs(p.getRefundcount()-(double) recommendpd.get("avg_refundcount"));
			
			refundmoney = refundmoney + Math.abs(p.getRefundmoney()-(double) recommendpd.get("avg_refundmoney"));
			
			storedcount = storedcount + Math.abs(p.getStoredcount()-(double) recommendpd.get("avg_storedcount"));
			
			remain = remain + Math.abs(p.getRemain()-(double) recommendpd.get("avg_remain"));
			
			proportion = proportion + Math.abs(p.getProportion()-(double) recommendpd.get("avg_proportion"));
			
			usercategoryid =usercategoryid + Math.abs(p.getUsercategoryid()-(double) recommendpd.get("avg_usercategoryid"));
		}
		attentiontime = (attentiontime/(double)recommendpd.get("sum_attentiontime"));
		avgordercount = (avgordercount/(double)recommendpd.get("sum_avgordercount"));
		avgordersum = (avgordersum/(double)recommendpd.get("sum_avgordersum"));
	    ordercount = (ordercount/(double)recommendpd.get("sum_ordercount"));
		ordersum = (ordersum/(double)recommendpd.get("sum_ordersum"));
		refundcount =(refundcount/(double)recommendpd.get("sum_refundcount"));
		refundmoney = (refundmoney/(double)recommendpd.get("sum_refundmoney"));
		storedcount =(storedcount/(double)recommendpd.get("sum_storedcount"));
		remain = (remain/(double)recommendpd.get("sum_remain"));
		proportion = (proportion/(double)recommendpd.get("sum_proportion"));
		usercategoryid = (usercategoryid/(double)recommendpd.get("sum_usercategoryid"));
		double sum = attentiontime+avgordercount+avgordersum+ordercount+ordersum+refundcount+refundmoney+storedcount+remain+proportion+usercategoryid;
		attentiontime = attentiontime/sum;
		avgordercount = avgordercount/sum;
		avgordersum= avgordersum/sum;
		ordercount = ordercount/sum;
		ordersum = ordersum/sum;
		refundcount = refundcount/sum;
		refundmoney = refundmoney/sum;
		storedcount =storedcount/sum;
		remain =remain/sum;
		proportion = proportion/sum;
		usercategoryid =usercategoryid/sum;
		PageData weightpd = new PageData();
		weightpd.put("attentiontime", attentiontime);
		weightpd.put("avgordercount", avgordercount);
		weightpd.put("avgordersum", avgordersum);
		weightpd.put("ordercount", ordercount);
		weightpd.put("ordersum", ordersum);
		weightpd.put("refundcount", refundcount);
		weightpd.put("refundmoney", refundmoney);
		weightpd.put("storedcount", storedcount);
		weightpd.put("remain", remain);
		weightpd.put("proportion", proportion);
		weightpd.put("usercategoryid", usercategoryid);
		//把权重插入数据库中
		recommendService.insertSegmentationWeight(weightpd);
		for(Player p : playerlist){
			userpd.put("uid", p.getUid());
			userpd.put("usercategoryid", p.getUsercategoryid()*usercategoryid);
			userpd.put("proportion", p.getProportion()*proportion);
			userpd.put("attentiontime", p.getAttentiontime()*attentiontime);
			userpd.put("ordercount", p.getOrdercount()*ordercount);
			userpd.put("ordersum", p.getOrdersum()*ordersum);
			userpd.put("avgordercount", p.getAvgordercount()*avgordercount);
			userpd.put("avgordersum", p.getAvgordersum()*avgordersum);
			userpd.put("storedcount", p.getStoredcount()*storedcount);
			userpd.put("remain", p.getRemain()*remain);
			userpd.put("refundcount", p.getRefundcount()*refundcount);
			userpd.put("refundmoney", p.getRefundmoney()*refundmoney);
			recommendService.updateSegmentation(userpd);
			
			//System.out.println("权重：attentiontime:"+attentiontime+";avgordercount:"+avgordercount+";avgordersum"+avgordersum+";ordersum:"+ordersum+";ordercount:"+ordercount+";storedcount:"+storedcount+";remain:"+remain+";refundcount:"+refundcount+";refundmoney:"+refundmoney+";proportion:"+proportion+";usercategoryid:"+usercategoryid+";");
		}
		
		
		return newplayerlist;
		
	}

}
