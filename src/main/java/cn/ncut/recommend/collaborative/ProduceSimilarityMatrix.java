package cn.ncut.recommend.collaborative;
import java.util.Arrays;



public class ProduceSimilarityMatrix {
		public double[][] produceSimilarityMatrix(int[][] preference,int[] uid) {
			int usercount = preference.length;
			int servicecostcount = preference[0].length;
			int [][] t = new int [servicecostcount][usercount];//转换的矩阵
			double avgc = 0.0;
			int sum =0;
			for(int i=0 ; i<preference.length;i++){  
	            for(int j=0; j<preference[i].length;j++){  
	                t[j][i]=preference[i][j]; //转置核心  
	            }     
	        }  
			int[] count = new int [servicecostcount];//记录项目被评分的个数的矩阵
			int c = 0;
			for(int i1=0 ; i1<t.length;i1++){
				for(int j1=0;j1<t[i1].length;j1++){
					if(t[i1][j1]!=0){
						c++;
					}
				}
				count[i1]=c;//项目被评价的个数
				sum = sum +c;
				c=0;
			}
			avgc = (double)sum/servicecostcount;
			//System.out.println("商品的平均评价次数："+avgc);
		double[][] similarityMatrix = new double[usercount][usercount];//servicecostcount
		ComputeSimilarity cs = new ComputeSimilarity();
		for (int i = 0; i < uid.length; i++) {//对于每一个用户，uid从小到大排列
			for (int j = 0; j < uid.length ; j++) {//对于每一个用户，uid从小到大排列
				//int positon_i = Arrays.binarySearch(uid, ddd[0]);
				if (i == j) {
					similarityMatrix[i][j] = 1;//自己和自己的相似度是1
				}
				else {
					
					similarityMatrix[i][j] = cs.computeImproveSimilarity(preference[i], preference[j], preference,count,avgc);
					//similarityMatrix[i][j] = cs.computeSimilarity(preference[i], preference[j]);
					//System.out.println("用户"+i+"用户"+j+"的相似度为："+similarityMatrix[i][j]);
				}			
			}
		}
		return similarityMatrix;
	}

}
