package cn.ncut.recommend.job;

import javax.annotation.Resource;

import cn.ncut.recommend.collaborative.CollaborativeFiltering;
import cn.ncut.recommend.kmeans.Operation;

public class RecommedJob {
	@Resource(name = "operation")
	private Operation operation;
	@Resource(name = "collaborativeFiltering")
	private CollaborativeFiltering collaborativeFiltering;

	public void recommendOperation() throws Exception{
	/*	System.out.println("聚类开始");
		operation.KmeansOperation();
		System.out.println("聚类完成");*/
		try{
		System.out.println("个性化推荐开始");
		collaborativeFiltering.Application();
		System.out.println("个性化推荐完成");
		}catch(Exception e){
			 throw new RuntimeException(); 
		}
	}


}