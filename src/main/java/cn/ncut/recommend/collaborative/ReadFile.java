package cn.ncut.recommend.collaborative;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.ncut.service.recommend.RecommendManager;
import cn.ncut.util.PageData;
@Component("readFile")
public class ReadFile {
	@Resource(name = "recommendService")
	private RecommendManager recommendService;
	
		public int[][] readFile(int i,int[] uid,int usercount,int servicecostcount) throws Exception {	
		
		int [][] user_movie=new int [usercount][servicecostcount];//用户人数*项目个数
		
		//查询每个用户都购买了什么项目当group2=i的时候
		PageData pd = new PageData();
		pd.put("GROUP2", i);
		List<PageData> userservicecostpdlist = recommendService.selectServicecostByGroup2(pd);//用户，项目编号，次数，查询用户对什么项目进行了预约
		for(PageData userservicecost : userservicecostpdlist){
			//查找uid的位置
			int positon = Arrays.binarySearch(uid, (int)userservicecost.get("uid"));
			//给矩阵赋值
			int servicecostid = (int)userservicecost.get("SERVICECOST_ID");
			//把long强转成int
			int intservicecostid = new Long(servicecostid).intValue();
			long points = (long) userservicecost.get("points");
			int intpoints = new Long(points).intValue();
			user_movie[positon][servicecostid-1]=intpoints;//按uid从小到大的顺序存放
		}
		
		return user_movie;
		
	}
		
		
		public static boolean contains(int[] arr, int targetValue) {
		    for(int s: arr){
		        if(s==targetValue)
		            return true;
		    }
		    return false;
		}
}
