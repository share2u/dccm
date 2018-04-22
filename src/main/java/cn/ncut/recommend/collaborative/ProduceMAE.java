package cn.ncut.recommend.collaborative;


public class ProduceMAE {
	public double[] produceMAE(double[][] m,int[][]test){ 
		int usercount = test.length;
		int servicecostcount = test[0].length;
	    double mae= 0.0;
	    double []mm=new double[usercount];
	    for(int i=0;i<usercount ;i++ )  {
			 double sum_fencha= 0.0;   
			 int num=0;
		  for(int j=0;j<servicecostcount;j++){ 
		   if(test[i][j]!=0&& m[i][j]!=0){
			   sum_fencha+=Math.abs(m[i][j]-(double)test[i][j]);
			   num++;
			  }
		  }if (num==0) mae=0;else mae= sum_fencha/num;
		   mm[i]=mae;	
		 }
		return mm;
	  }
	public double produceRMSE(double[][] m,int[][]test){ 
		int usercount = test.length;
		int servicecostcount = test[0].length;
	    double rmse= 0.0;
	   
	    double sum_fencha= 0.0;   
		 int num=0;
	    for(int i=0;i<usercount ;i++ )  {
			 
		  for(int j=0;j<servicecostcount;j++){ 
		   if(test[i][j]!=0&& m[i][j]!=0){
			   sum_fencha+=Math.pow((m[i][j]-(double)test[i][j]),2);
			   num++;
			  }
		  }
		 
		 }
	    if(num==0){
	    	rmse=0.0;
	    }else{
	    	sum_fencha=sum_fencha/num;
	    	rmse=Math.sqrt(sum_fencha);
	    }
		return rmse;
	  }
}
