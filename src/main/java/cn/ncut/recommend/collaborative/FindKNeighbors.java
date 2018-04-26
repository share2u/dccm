package cn.ncut.recommend.collaborative;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindKNeighbors {
	/**
	 * This method is used to find the nearest K neighbors to the un_scored item 
	 * @param score
	 * @param i
	 * @param similarityMatrix
	 * @return
	 */
	//该方法有三个参数，score表示某一用户对所有项目的评分；i表示某个项目的序号
	public  List<Integer> findKNeighbors(int[] score,int i,double[][] similarityMatrix) {
		int k =10;//10个最相似的邻居
		List<Integer> neighborSerial = new ArrayList<Integer>();
		double[] similarity = new double[similarityMatrix.length];//用户个数
		for (int j = 0; j < similarityMatrix.length; j++) {//对于每一个用户
			
			if(i !=j) {//if(user_movie_base[i][j]!=0) 用户i对j的评分不为0
				similarity[j] = similarityMatrix[i][j];
			} 
			else {
				similarity[j] = 0.0;
			}
		}
		double[] temp = new double[similarity.length];
		for (int j = 0; j < temp.length; j++) {
			temp[j] = similarity[j];
		}
		Arrays.sort(temp);//从低到高排序，相似度

		for(int j = 0; j < similarity.length; j++) {
			for (int m = temp.length - 1; m >= temp.length - k; m--) {
				if (similarity[j] == temp[m] && similarity[j] != 0.0)
					neighborSerial.add(new Integer(j));/*添加一个整型数据对象*/
			}	
		}
		return neighborSerial; 
	}

}
