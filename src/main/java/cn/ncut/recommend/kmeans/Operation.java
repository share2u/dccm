package cn.ncut.recommend.kmeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.ncut.recommend.kmeans.Player;

import org.springframework.stereotype.Component;

import cn.ncut.recommend.kmeans.*;
import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.service.user.member.MemberManager;
import cn.ncut.util.PageData;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Component("operation")
@Configuration
@EnableAsync
@EnableScheduling
public class Operation {
	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	@Resource(name = "preparation")
	private Prepararion preparation;
	@Resource(name = "memberService")
	private MemberManager memberService;
	//@Scheduled(cron = " 00 15 14 * * ?  ")//14点15触发
	public void KmeansOperation() throws Exception{
		//对用户购买的商品类别进行聚类
          System.out.println("-----------------开始聚类---------------------");
		List<Player> oriList = preparation.prepareFile();
	
	        Kmeans<Player> kmeans = new Kmeans<Player>(oriList,2);//pointCenter,pointCenter.size()
	        //2为聚类个数
	        List<Player>[] results = kmeans.comput();
	  
	        File fileName = new File("E:\\大诚中医协同过滤聚类结果.txt");
  		  PrintWriter outFile = null;
  		  outFile = new PrintWriter(fileName);
	        //把聚类结果写入数据库recommend表中
  	      List<List<List<Player>>> resultslist  = new ArrayList<List<List<Player>>>();//外面是大于3000人的个数 里面是每个性别
  	      List<List<Player>> resultslist2 = new ArrayList<List<Player>>();//记录没超过3000人的结果
  	int m = 0;//记录大于3000人的组数
	        for (int i =0; i <results.length; i++) {
	        	List<List<Player>> resultslevel = new ArrayList<List<Player>>();
	        	
	        	//判断每组的人数是否过大
	        	if(results[i].size()>=3000){//如果大于3000人 就进行分组
	        		m++;
	        		List<Player> resultsnew1 = new ArrayList<Player>();
	        		List<Player> resultsnew2 = new ArrayList<Player>();
	        		List<Player> resultsnew3 = new ArrayList<Player>();
	        		 //List<Player>[] resultsnew = new ArrayList[3];//把一组分为三组
	        		 for (Player p : results[i]) { 
	        			 //查询这个人的性别
	        			 PageData player = new PageData();
	        			 player.put("uid", p.getAttribute1());
	        			 if((int)player.get("sex")==0){
	        				 resultsnew1.add(p);
	        				 }else if((int)player.get("sex")==1){
	        					 resultsnew2.add(p);
	        				 }else if((int)player.get("sex")==2){
	        					 resultsnew3.add(p);
	        			 }
	        			 
	        	}
	        		 resultslevel.add(resultsnew1);
	        		 resultslevel.add(resultsnew2);
	        		 resultslevel.add(resultsnew3);
	        		
	        	}
	        	resultslist.add(resultslevel);
	        	 if(results[i].size()<3000){//如果没超过3000人 换另一个list记录下来
	        		List<Player> results2 = new ArrayList();
	        		 for (Player p : results[i]) { 
	        			 results2.add(p);
	        		 }
	        		 resultslist2.add(results2);
	        	}
	        	
	        }
	        //把结果写入数据库中
	        //先写大于3000人的resultslist
	        int i = 0;
	        for(List<List<Player>> resultlevel : resultslist){
	        	for(List<Player> resultsnew:resultlevel){
	        		for(Player p: resultsnew){
	        			
	        			outFile.println("第"+(i+1)+"组"+p.toString());
		        		 PageData pd = new PageData();
		        		  pd.put("UID", p.getAttribute1());
		        		  pd.put("GROUP2", i+1);
		        		  recommendService.updateGroup2ByUid(pd);
		        		  i++;
	        		}
	        	}
	        }
	        //再写小于3000人的resultslist
	        int n =m*3;
	        for(List<Player> results2:resultslist2){
        		for(Player p: results2){
        			
        			outFile.println("第"+(n+1)+"组"+p.toString());
	        		 PageData pd = new PageData();
	        		  pd.put("UID", p.getAttribute1());
	        		  pd.put("GROUP2", n+1);
	        		  recommendService.updateGroup2ByUid(pd);
	        		  
        		}
        		n++;
        	}
	        outFile.flush();
	  		  outFile.close();
	  		  
	  		  System.out.println("------------------聚类结束------------------");
	        }
	      
	       

    
}
