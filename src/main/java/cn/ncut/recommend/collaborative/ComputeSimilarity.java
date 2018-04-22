package cn.ncut.recommend.collaborative;


import java.util.ArrayList;
import java.util.List;

public class ComputeSimilarity {
	public double computeSimilarity(int[] item1,int[] item2) {//计算相似度
	
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		int j = 0;
		for (int i = 1; i < item1.length; i++) {
			if(item1[i] != 0 && item2[i] !=0) {
				list1.add(new Integer(item1[i]));
				list2.add(new Integer(item2[i]));
				
			}
			
			j++;
		}
		
		return new PearsonCorrelation().pearsonCorrelation(list1, list2);//皮尔逊相似度
		
	}

	public double computeSimilarity2(int[] item1,int[] item2) {//改进的皮尔逊-cos替换
		int count = 0;//相交个数
		int count1 = 0;//u1的评分个数
		int count2 = 0;//u2的评分个数
		//int totalcount =0;//总个数
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		//int j = 0;
		for (int i = 0; i < item1.length; i++) {
			if(item1[i] != 0 && item2[i] !=0) {
				list1.add(new Integer(item1[i]));
				list2.add(new Integer(item2[i]));
				count ++;//count加1
				count1++;
				count2++;
		}
			else if(item1[i] != 0){
				count1 ++;
			}
			else if(item2[i] != 0){
				count2 ++;
			}
			//totalcount = list1.size() + list2.size() - count;
			//j++;
		}
		
		return new PearsonCorrelation().MixCorrelation2(list1, list2, count, count1,count2);//混合相似度
		//return new PearsonCorrelation().pearsonCorrelation(list1, list2);//皮尔逊相似度
	}
	public double computeImproveSimilarity(int[] item1,int[] item2,int[][] preference,int[] count,double avgc) {//最新的改进相似度
	
		
		return new PearsonCorrelation().improvePearsonCorrelationNew(item1,item2,preference,count,avgc);//混合相似度
		//return new PearsonCorrelation().pearsonCorrelation(list1, list2);//皮尔逊相似度
	}
	

}
