// JavaScript Document
	
 $(function() {
	
	var $key=0; 
	
    $(".rt").click(function(event) {
         autoplay();  
    });
    

      $(".lt").click(function(event) {
    	$(".major1 ul li").eq($key).fadeOut(600);  
    	$key--; 
    	
		$key=$key%$(".major1 ul li").length; 
		
    	$(".major1 ul li").eq($key).fadeIn(600);   
    	
        console.log($key);
        $(".major1 ol li").eq($key).addClass('current').siblings().removeClass('current');

    });

      /*定时器开始*/

      
       var timer=setInterval(autoplay, 3000);  /*开启定时器*/
       function autoplay(){
	       	$(".major1 ul li").eq($key).fadeOut(600); 
	    	$key++;  
	    	
			$key=$key%$(".major1 ul li").length;  
			
	    	$(".major1 ul li").eq($key).fadeIn(600);   
	    	
	        /*console.log($key);*/
	        $(".major1 ol li").eq($key).addClass('current').siblings().removeClass('current');
       }
      

       $(".major1").hover(function() {
          $(".lt,.rt").show();
          clearInterval(timer);
          timer=null;   /*节省内存*/
       }, function() {
         $(".lt,.rt").hide();
       
         clearInterval(timer);  
         timer=setInterval(autoplay, 2000);  
       });

     

     

      $(".major1 ol li").click(function(event) {
      	$(".major1 ul li").eq($key).fadeOut(600);  /*因为 我们不知道当前播放了第几张 当我们点击了这个ol li 的时候，把当前的那个隐藏起来*/
      	/*console.log($key);*/
      	$key=$(this).index();/* 把当前的索引号给$key 更该图片xuhao*/
      	$(this).addClass('current').siblings().removeClass("current");
      	/*console.log($key);*/
      	$(".major1 ul li").eq($key).fadeIn(600); /*显示出你点击的那个*/

        });
});

