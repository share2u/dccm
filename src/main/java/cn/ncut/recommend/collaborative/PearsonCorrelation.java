package cn.ncut.recommend.collaborative;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PearsonCorrelation  {
	 
	
	public double pearsonCorrelation(List<Integer> a,List<Integer> b) {

		
		int num = a.size();//个数
		double sum_prefOne = 0;//每个的和
		double sum_prefTwo = 0;//每个的和
		
		double avg_prefOne = 0.0;//a的平均
		double avg_prefTwo = 0.0;//b的平均
		double fenzi = 0.0;
		double fenmu1 = 0.0;
		double fenmu2 = 0.0;
		double fenmu = 0.0;
		double result = 0.0;
		
		if(num!=0){
		for (int i = 0; i < num; i++) {
			sum_prefOne += a.get(i);//每个的和
			sum_prefTwo += b.get(i);//每个的和
			
		}
		avg_prefOne = sum_prefOne/num;//平均评分
		avg_prefTwo = sum_prefTwo/num;//平均评分
		for (int i = 0; i < num; i++) {
			fenzi += (a.get(i)-avg_prefOne)*(b.get(i)-avg_prefTwo);
			fenmu1 += Math.pow(a.get(i)-avg_prefOne,2);
			fenmu2 += Math.pow(b.get(i)-avg_prefTwo,2);		
		}
		fenmu = Math.sqrt(fenmu1)* Math.sqrt(fenmu2);
			if(fenmu==0.00){
				result = 0.0;
			}else{
		 
		    	result = fenzi / fenmu;
			}
		    }
		else{result = 0.0;}
		
		return result;
	}

	public static double improvePearsonCorrelation(List<Integer> a,List<Integer> b,double n,double m,double k) {//改进皮尔逊相似度
		int num = a.size();//个数
		double sum_prefOne = 0;//每个的和
		double sum_prefTwo = 0;//每个的和
		//double sum_squareOne = 0;//平方的和
		//double sum_squareTwo = 0;//平方的和
		double avg_prefOne = 0.0;//a的平均
		double avg_prefTwo = 0.0;//b的平均
		double fenzi = 0.0;
		double fenmu1 = 0.0;
		double fenmu2 = 0.0;
		double fenmu = 0.0;
		double result = 0.0;
		
		if(num!=0){
		for (int i = 0; i < num; i++) {
			sum_prefOne += a.get(i);//每个的和
			sum_prefTwo += b.get(i);//每个的和
			
		}
		avg_prefOne = sum_prefOne/num;//平均评分
		avg_prefTwo = sum_prefTwo/num;//平均评分
		for (int i = 0; i < num; i++) {
			fenzi += (a.get(i)-avg_prefOne)*(b.get(i)-avg_prefTwo);
			fenmu1 += Math.pow(a.get(i)-avg_prefOne,2);
			fenmu2 += Math.pow(b.get(i)-avg_prefTwo,2);		
		}
		fenmu = Math.sqrt(fenmu1)* Math.sqrt(fenmu2);
			if(fenmu1==0.0&&fenmu2!=0.0){
				result = n/m;
			}else if(fenmu1!=0.0&&fenmu2==0.0){
				result = n/m;
			}else if(fenmu1==0.0&&fenmu2==0.0){
				result = n/m;
			}
		
		    else {
		    	//System.out.println(fenzi / fenmu);
		    	
		    	
		    	result = (n/m)*(fenzi / fenmu);
		   // System.out.println(result);
		    }
		}
		return result;
	}
	public static double improvePearsonCorrelationNew(int[] item1,int[] item2,int[][] preference,int[] count,double avgc) {//改进皮尔逊相似度最新版
	
		double n = 0;//相交个数
		double m = 0;//u1的评分个数
		double k = 0;//u2的评分个数
		
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		List<Integer> listn = new ArrayList<Integer>();
		//int j = 0;
		for (int i = 0; i < item1.length; i++) {//第i个项目
			if(item1[i] != 0 && item2[i] !=0) {
				
				list1.add(new Integer(item1[i]));
				list2.add(new Integer(item2[i]));
				listn.add(new Integer(count[i]));
				n ++;//相交个数
				m++;//u1的评分个数
				k++;//u2的评分个数
		}
			else if(item1[i] != 0){
				m ++;
			}
			else if(item2[i] != 0){
				k ++;
			}
			//totalcount = list1.size() + list2.size() - count;
			//j++;
		}
		
		System.out.println("n:"+n);
		System.out.println("m:"+m);
		int num = list1.size();//个数
		double sum_prefOne = 0;//每个的和
		double sum_prefTwo = 0;//每个的和
		//double sum_squareOne = 0;//平方的和
		//double sum_squareTwo = 0;//平方的和
		double avg_prefOne = 0.0;//a的平均
		double avg_prefTwo = 0.0;//b的平均
		double fenzi = 0.0;
		double fenmu1 = 0.0;
		double fenmu2 = 0.0;
		double fenmu = 0.0;
		double result = 0.0;
		
		if(num!=0){
		for (int i = 0; i < num; i++) {
			sum_prefOne += list1.get(i);//每个的和
			sum_prefTwo += list2.get(i);//每个的和
			
		}
		avg_prefOne = sum_prefOne/num;//平均评分
		avg_prefTwo = sum_prefTwo/num;//平均评分
		for (int i = 0; i < num; i++) {
			
			fenzi += (avgc/count[i])*(list1.get(i)-avg_prefOne)*(list2.get(i)-avg_prefTwo);
			//fenzi += (list1.get(i)-avg_prefOne)*(list2.get(i)-avg_prefTwo);
			fenmu1 += Math.pow(list1.get(i)-avg_prefOne,2);
			fenmu2 += Math.pow(list2.get(i)-avg_prefTwo,2);		
		}
		fenmu = Math.sqrt(fenmu1)* Math.sqrt(fenmu2);
			if(fenmu1==0.0&&fenmu2!=0.0){
				result = n/(m+k);
			}else if(fenmu1!=0.0&&fenmu2==0.0){
				result = n/(m+k);
			}else if(fenmu1==0.0&&fenmu2==0.0){
				result = n/(m+k);
			}
		
		    else {
		    	//System.out.println(fenzi / fenmu);
		    	
		    	
		    	result = (n/(m+k))*(fenzi / fenmu);
		    	//result = (fenzi / fenmu);
		   // System.out.println(result);
		    }
		}
		list1 = null;
		list2 = null;
		listn = null;
		return result;
	}
	public double cosCorrelation(List<Integer> a,List<Integer> b) {
		int num = a.size();//个数
		int sum_prefOne = 0;//每个的和
		int sum_prefTwo = 0;//每个的和
        int sum_squareOne = 0;//平方的和
		int sum_squareTwo = 0;//平方的和
		double avg_prefOne = 0.0;//a的平均
		double avg_prefTwo = 0.0;//b的平均
		double fenzi = 0.0;
		double fenmu1 = 0.0;
		double fenmu2 = 0.0;
		double fenmu = 0.0;
		double result;
		int sum_product = 0;//乘积的和
		if(num!=0){
		for (int i = 0; i < num; i++) {
			sum_prefOne += a.get(i);
			sum_prefTwo += b.get(i);
			
		}
		avg_prefOne = sum_prefOne/num;
		avg_prefTwo = sum_squareTwo/num;
		for (int i = 0; i < num; i++) {
			fenzi += (a.get(i)-avg_prefOne)*(b.get(i)-avg_prefTwo);
			fenmu1 += Math.pow(a.get(i)-avg_prefOne,2);
			fenmu2 += Math.pow(b.get(i)-avg_prefTwo,2);		
		}
		fenmu = Math.sqrt(fenmu1)* Math.sqrt(fenmu2);
		
		
		if(fenmu==0) result=0;
		else result = fenzi / fenmu;
		}
		else result = 0.0;
		return result;
	}

	public static double MixCorrelation(List<Integer> a,List<Integer> b,double n,double m) { //n是相交的个数  m是并的个数
		int num = a.size();//个数
		double result=0.0;
		//double result1 = 0.0;//未保留保留小数的结果
		//DecimalFormat  df = new DecimalFormat("######0.0000");   
		if(m!=0){//totalcount不为0
		
		if(num>2){//大于2时使用杰卡德-皮尔逊相似度
		double sum_prefOne = 0;//每个的和
		double sum_prefTwo = 0;//每个的和
		//double sum_squareOne = 0;//平方的和
		//double sum_squareTwo = 0;//平方的和
		double avg_prefOne = 0.0;//a的平均
		double avg_prefTwo = 0.0;//b的平均
		double fenzi = 0.0;
		double fenmu1 = 0.0;
		double fenmu2 = 0.0;
		double fenmu = 0.0;
		
		
		if(num!=0){
		for (int i = 0; i < num; i++) {
			sum_prefOne += a.get(i);//每个的和
			sum_prefTwo += b.get(i);//每个的和
			
		}
		avg_prefOne = sum_prefOne/num;//平均评分
		avg_prefTwo = sum_prefTwo/num;//平均评分
		for (int i = 0; i < num; i++) {
			fenzi += (a.get(i)-avg_prefOne)*(b.get(i)-avg_prefTwo);
			fenmu1 += Math.pow(a.get(i)-avg_prefOne,2);
			fenmu2 += Math.pow(b.get(i)-avg_prefTwo,2);		
		}
		fenmu = Math.sqrt(fenmu1)* Math.sqrt(fenmu2);
			if(fenmu1==0&&fenmu2!=0){
				result = n/m;
			}else if(fenmu1!=0&&fenmu2==0){
				result = n/m;
			}
		
		    else {
		    	//System.out.println(fenzi / fenmu);
		    	
		    	
		    	result = (n/m)*(fenzi / fenmu);
		   // System.out.println(result);
		    }
		/*if(result<=0){
			result = - result;
		}*/
		}
		else result = 0.0;
		}
		else if(num==2){//等于2时使用杰卡德-余弦相似度
			double fenzi = 0.0;
			double fenmu1 = 0.0;
			double fenmu2 = 0.0;
			double fenmu = 0.0;
			for (int i = 0; i < num; i++) {
				fenzi +=a.get(i)*b.get(i);
				fenmu1 +=a.get(i)*a.get(i);
				fenmu2 +=b.get(i)*b.get(i);
				
			}
			fenmu1 = Math.sqrt(fenmu1);
			fenmu2 = Math.sqrt(fenmu2);
			fenmu = fenmu1*fenmu2;
			if(fenmu==0) result=0;
			else {
				//System.out.println(fenzi / fenmu);
				//result1 = Double.parseDouble(df.format(fenzi / fenmu));
		    	result = (n/m)*(fenzi / fenmu);
				//result = (n/m)*(fenzi / fenmu);
			//System.out.println(result);
			}
			
			
			
		}else if(num == 1){//有一个共同评分的项目 等于1时 使用杰卡德比分
			System.out.println(a.get(0));
			System.out.println(b.get(0));
			if((double)a.get(0)>=(double)b.get(0)){
				result=(double)b.get(0)/(double)a.get(0);
				result = result*(n/m);
				System.out.println(result);
				
			}
			else{
				result=(double)a.get(0)/(double)b.get(0);
				result= result*(n/m);
				System.out.println(result);
			}
		}
		else{ //一个共同评分都没有
			result = 0;
		}
		}else{
			result = 0;
		}
		return result;
	}
	
	public static double MixCorrelation2(List<Integer> a,List<Integer> b,int n,int m, int k) {//n 交的个数，m u1的个数，k u2的个数
		int num = a.size();//个数
		double result=0.0;
		if(m!=0&&k!=0){//分母不为0
		
		if(num>2){//大于2时使用杰卡德-皮尔逊相似度
		double sum_prefOne = 0;//每个的和
		double sum_prefTwo = 0;//每个的和
		//double sum_squareOne = 0;//平方的和
		//double sum_squareTwo = 0;//平方的和
		double avg_prefOne = 0.0;//a的平均
		double avg_prefTwo = 0.0;//b的平均
		double fenzi = 0.0;
		double fenmu1 = 0.0;
		double fenmu2 = 0.0;
		double fenmu = 0.0;
		
		
		if(num!=0){
		for (int i = 0; i < num; i++) {
			sum_prefOne += a.get(i);//每个的和
			sum_prefTwo += b.get(i);//每个的和
			
		}
		avg_prefOne = sum_prefOne/num;//平均评分
		avg_prefTwo = sum_prefTwo/num;//平均评分
		for (int i = 0; i < num; i++) {
			fenzi += (a.get(i)-avg_prefOne)*(b.get(i)-avg_prefTwo);
			fenmu1 += Math.pow(a.get(i)-avg_prefOne,2);
			fenmu2 += Math.pow(b.get(i)-avg_prefTwo,2);		
		}
		fenmu = Math.sqrt(fenmu1)* Math.sqrt(fenmu2);
	if(fenmu1==0&&fenmu2!=0){
		//result = n/m;
		result = Math.sqrt(n/m)*Math.sqrt(n/k);
	}else if(fenmu1!=0&&fenmu2==0){
		result = Math.sqrt(n/m)*Math.sqrt(n/k);
		//result = n/m;
	}
		else result = Math.sqrt(n/m)*Math.sqrt(n/k)*(fenzi / fenmu);
		/*if(result<=0){
			result = - result;
		}*/
		}
		else result = 0.0;
		}
		else if(num==2){//等于2时使用杰卡德-余弦相似度
			double fenzi = 0.0;
			double fenmu1 = 0.0;
			double fenmu2 = 0.0;
			double fenmu = 0.0;
			for (int i = 0; i < num; i++) {
				fenzi +=a.get(i)*b.get(i);
				fenmu1 +=a.get(i)*a.get(i);
				fenmu2 +=b.get(i)*b.get(i);
				
			}
			fenmu1 = Math.sqrt(fenmu1);
			fenmu2 = Math.sqrt(fenmu2);
			fenmu = fenmu1*fenmu2;
			if(fenmu==0) result=0;
			else {result = Math.sqrt(n/k)*Math.sqrt(n/m)*(fenzi / fenmu);}
			
			
			
		}else if(num ==1){//有一个共同评分的项目 等于1时 使用杰卡德比分
			if(a.get(0)>=b.get(0)){
				result=Math.sqrt(n/k)*Math.sqrt(n/m)*(b.get(0)/a.get(0));
				
			}
			else{
				result=Math.sqrt(n/k)*Math.sqrt(n/m)*(a.get(0)/b.get(0));
			}
		}
		}else{
			result = 0;
		}
		return result;
	}
}
