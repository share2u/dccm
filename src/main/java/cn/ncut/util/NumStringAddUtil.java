package cn.ncut.util;

/**
 * 数字字符串累加1工具类;
 * @author xia
 *
 */
public class NumStringAddUtil {
	
	/**
	 * 对一级分类的编号进行加1操作
	 * @param num  传递进来的一级分类的最大编号
	 * @return
	 */
	public static String CategoryAdd(String num){
		//转成整数并执行累加操作，但是位数变少了(如："01")，怎么办？
		int i=Integer.valueOf(num)+1;
		//累加后转换为字符串;
		String Num=String.valueOf(i);
		if(i<10){
			//为了拼接字符串使用；
			StringBuffer sb=new StringBuffer();
			//补缺前面缺失的0；
			sb.append("0");
			Num=sb.toString()+Num;
		}
		return Num;
	}
	
	
	
	/**
	 * 拼接父分类和子分类（子分类为父分类的第一个）
	 * @param id  父分类的编号
	 * @return
	 */
	public static String ProjectAdd1(String id){
		//为了拼接字符串使用；
		StringBuffer sb=new StringBuffer();
		sb.append("01");
		return id+sb.toString();
	}
	
	
	/**
	 * 拼接父分类和子分类（子分类为非父分类的第一个）
	 * @param id  父分类的编号03
	 * @param num  父分类下子分类中最大的编号0301
	 * @return
	 */
	public static String ProjectAdd2(String id,String num){
		String n = num.substring(num.length()-2); 
		int i=Integer.valueOf(n)+1;
		//累加后转换为字符串;
		String Num = "";
		if(i<10){
			//为了拼接字符串使用；
			StringBuffer sb=new StringBuffer();
			//补缺前面缺失的0；
			sb.append("0");
			sb.append(i);
			Num=id + sb.toString();
		}else{
			Num=id + i;
		}
		return Num;
	}
	
	
	/**
	 * 对6位员工进行编号+1
	 * @param id  最大的员工编号
	 */
	public static String StaffAdd(String id){
		int i=Integer.valueOf(id)+1;
		String num =  String.format("%06d", i);
		return num;
	}
	
	
	/**
	 * 对8位客户进行编号+1
	 * @param args
	 */
	public static String UserAdd(String id){
		int i=Integer.valueOf(id)+1;
		return String.format("%08d", i);
	}

}
