package cn.ncut.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect    
@Component    
public  class weChatServiceLogAspect {    
 
    //weChat日志记录对象    
     private  static  final Logger weChatInfo = LoggerFactory.getLogger("weChatService");    
      
    
    //service层切点    
    //@Pointcut("@annotation(cn.ncut.annotation.SystemControllerLog)")
     @Pointcut("execution (* cn.ncut.service.wechat.*.*.*(..))")
     public  void serviceAspect() {    
    }    
    
   
    
    /**
	 * 异常(例外)通知
	 * */
	@AfterThrowing(value="serviceAspect()",throwing="ex")
	public void doAfterThrowing(JoinPoint joinPoint,Exception ex){
		Object object = joinPoint.getSignature();
        weChatInfo.error(("执行了【"+object+"方法发生异常......】"+"【异常报告:"+ex+"】")); 
	}
}    