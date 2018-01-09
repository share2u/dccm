//meishi开始
$(document).ready(function(){
    $(".meishi").click(function(){
        if ($('.meishi22').hasClass('grade-w-roll')) {
            $('.meishi22').removeClass('grade-w-roll');
			$(this).removeClass('current');
			/*$('.screening').attr('style',' ');*/
        } else {
            $('.meishi22').addClass('grade-w-roll');
			$(this).addClass('current');
			$(".Regional").removeClass('current');
			$(".Brand").removeClass('current');
			$(".Sort").removeClass('current');
			/*$('.screening').attr('style','position: fixed;');*/
			/*$('.screening').attr('style',' ');*/
        }
    });
});

$(document).ready(function(){
    $(".meishia-w>li").click(function(){
        $(".meishia-t")
            .css("left","50%")
    });
});

$(document).ready(function(){
    $(".meishia-t>li").click(function(){
        $(".meishia-s")
            .css("left","50%")
    });
});





//Regional开始
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')) {
            $('.grade-eject').removeClass('grade-w-roll');
			$(this).removeClass('current');
			/*$('.screening').attr('style',' ');*/
        } else {
            $('.grade-eject').addClass('grade-w-roll');
			$(this).addClass('current');
			$(".meishi").removeClass('current');
			$(".Brand").removeClass('current');
			$(".Sort").removeClass('current');
			/*$('.screening').attr('style',' ');*/
			/*$('.screening').attr('style','position: fixed;');*/
        }
    });
});

$(document).ready(function(){
    $(".grade-w>li").click(function(){
        $(".grade-t")
            .css("left","30%")
    });
});

$(document).ready(function(){
    $(".grade-t>li").click(function(){
        $(".grade-s")
            .css("left","60%")
    });
});



//Brand开始

$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')) {
            $('.Category-eject').removeClass('grade-w-roll');
			$(this).removeClass('current');
			/*$('.screening').attr('style',' ');*/
        } else {
            $('.Category-eject').addClass('grade-w-roll');
			$(this).addClass('current');
			$(".meishi").removeClass('current');
			$(".Regional").removeClass('current');
			$(".Sort").removeClass('current');
		/*	$('.screening').attr('style',' ');*/
			/*$('.screening').attr('style','position: fixed;');*/
        }
    });
});



//Sort开始

$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')) {
            $('.Sort-eject').removeClass('grade-w-roll');
			$(this).removeClass('current');
			/*$('.screening').attr('style',' ');*/
        } else {
            $('.Sort-eject').addClass('grade-w-roll');
			$(this).addClass('current');
			$(".meishi").removeClass('current');
			$(".Regional").removeClass('current');
			$(".Brand").removeClass('current');
			/*$('.screening').attr('style',' ');*/
			/*$('.screening').attr('style','position: fixed;');*/
        }
    });
});


//判断页面是否有弹出
$(document).ready(function(){
    $(".meishi").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".meishi").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };
    });
});$(document).ready(function(){
    $(".meishi").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };
    });
});


$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Regional").click(function(){
        if ($('.meishi22').hasClass('grade-w-roll')){
            $('.meishi22').removeClass('grade-w-roll');
        };

    });
});





$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.Sort-eject').hasClass('grade-w-roll')){
            $('.Sort-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Brand").click(function(){
        if ($('.meishi22').hasClass('grade-w-roll')){
            $('.meishi22').removeClass('grade-w-roll');
        };
    });
});

$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.Category-eject').hasClass('grade-w-roll')){
            $('.Category-eject').removeClass('grade-w-roll');
        };
    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.grade-eject').hasClass('grade-w-roll')){
            $('.grade-eject').removeClass('grade-w-roll');
        };

    });
});
$(document).ready(function(){
    $(".Sort").click(function(){
        if ($('.meishi22').hasClass('grade-w-roll')){
            $('.meishi22').removeClass('grade-w-roll');
        };

    });
});



//js点击事件监听开始

//把兄弟的li都设为白色，自己设为灰色
function grade1(wbj){
    var arr = document.getElementById("gradew").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
    wbj.style.background = "#eee"
}


function gradet(tbj){
    var arr = document.getElementById("gradet").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
}

//没有使用，四级菜单使用
function grades(sbj){
    var arr = document.getElementById("grades").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
	$(".current>span").html(sbj.innerHTML);
	$('.grade-eject').removeClass('grade-w-roll');
	$('.Regional').removeClass('current');
	shaixuan();
}


// 第二项二级菜单使用
function Sorts(sbj){
    var arr = document.getElementById("Sort-Sort").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
    sbj.style.background = "#eee"
    $(".current>span").html(sbj.innerHTML);
    $('.Sort-eject').removeClass('grade-w-roll');
	$('.Sort').removeClass('current');
	shaixuan();
}


//第三项二级菜单使用
function Categorytw(wbj){
    var arr = document.getElementById("Categorytw").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
	wbj.style.background = "#eee"
	$(".current>span").html(wbj.innerHTML);
	$('.Category-eject').removeClass('grade-w-roll');
	$('.Brand').removeClass('current');
	shaixuan();
}

//没有使用。三级菜单使用
function Categoryt(tbj){
    var arr = document.getElementById("Categoryt").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
}

//没有使用，四级菜单使用
function Categorys(sbj){
    var arr = document.getElementById("Categorys").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
}

function meishia(wbj){
    var arr = document.getElementById("meishia").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
    wbj.style.background = "#eee"
    $(".current>span").html(wbj.innerHTML);
    $('.meishi22').removeClass('grade-w-roll');
    $('.meishi').removeClass('current');
    shaixuan();
}
//第四项三级菜单
function meishib(tbj){
    var arr = document.getElementById("meishib").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.background = "";
    };
}

function meishis(sbj){
    var arr = document.getElementById("meishis").getElementsByTagName("li");
    for (var i = 0; i < arr.length; i++){
        var a = arr[i];
        a.style.borderBottom = "";
    };
    sbj.style.borderBottom = "solid 1px #ff7c08"
}


function shaixuan(){
    var a = $('.Regional>span').html();
    var b = $('.Sort>span').html();
    var c = $('.meishi>span').html();
    console.log('a:'+a+"\t"+'b:'+b+"\t"+'c:'+c+"\t");
}