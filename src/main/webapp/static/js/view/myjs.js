function newView(Id){
    top.jzts();
    var diag = new top.Dialog();
    diag.Drag=true;
    diag.Title="新增图表"
    diag.URL="http://localhost:8080/dccm/queryDiscount/list.do?groupId="+Id;
    diag.Width = 1200;
    diag.Height = 800;
    diag.Modal = true;				//有无遮罩窗口
    diag. ShowMaxButton = true;	//最大化按钮
    diag.ShowMinButton = true;		//最小化按钮
    diag.CancelEvent = function(){ //关闭事件
        /*  if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
         nextPage(1);
         } */
        diag.close();
    };
    diag.show();
}
