package cn.ncut.recommend.collaborative;


import java.util.ArrayList;
import java.util.List;

public class GetScore {
	
	public static double [][]getScore(int[][] user_movie_base,double[][] combineMatrix ){//评分矩阵，相似度矩阵
		int usercount = user_movie_base.length;
		int servicecostcount = user_movie_base[0].length;
	//评分矩阵
	double[][] matrix = new double[usercount][servicecostcount];//943*1682
	// 进行评分预测
	List<Integer> neighborSerial = new ArrayList<Integer>();//邻居序列
	
	double[] avg = new double[usercount];//用户评分的平均值数组
	for(int m=0;m<usercount;m++){
		double sum1 = 0;
		double count1 = 0;
		for(int n=0;n<servicecostcount;n++){
			if(user_movie_base[m][n] != 0){//计算评分的平均值
				sum1 +=user_movie_base[m][n];
				count1++;
			}
		}
		if(count1!=0){
		avg[m] = sum1/count1;
		}else{
			avg[m] = 0;
		}
	}
	for (int i = 0; i < usercount; i++) {//用户个数
		neighborSerial.clear();
		double max = 0;
		int j = 0;
		int itemSerial = 0;
		int itemId = 0;
		for (; j < servicecostcount; j++) {//对于每一个项目
			if (user_movie_base[i][j] == 0) {//user_movie_base[i][j] == 0如果用户对项目j的评分为0，才进行预测，预测用户没买过的项目
				double similaritySum = 0;
				double sum = 0;
				double score = 0;
				
				// 该方法有三个参数，score表示某一用户对所有项目的评分；i表示某个项目的序号combineMatrix表示项目间的相似性
				neighborSerial = new FindKNeighbors().findKNeighbors(user_movie_base[i], i, combineMatrix);//产生邻居序列，10个最相似的邻居
				for (int m = 0; m < neighborSerial.size(); m++) {
					
					if(user_movie_base[neighborSerial.get(m)][j]!=0){
					//sum += combineMatrix[i][neighborSerial.get(m)]* user_movie_base[neighborSerial.get(m)][j];
					
						
					similaritySum += combineMatrix[i][neighborSerial.get(m)];
					//score = avg[i] + (combineMatrix[i][neighborSerial.get(m)]*(user_movie_base[neighborSerial.get(m)][j]-avg[neighborSerial.get(m)]))/similaritySum;
					sum += combineMatrix[i][neighborSerial.get(m)]*(user_movie_base[neighborSerial.get(m)][j]-avg[neighborSerial.get(m)]);
					}
					else{
						sum += 0;
						similaritySum += 0;
					}
				}
				if (similaritySum == 0)
					score = 0;
				else
					//score = sum / similaritySum;
					score = avg[i] + (sum / similaritySum);
				itemId = j;
				matrix[i][itemId] = score;//自己的平均评分+相似度计算评分
			}
			
		}
		

	}
	return matrix;
	}
}
