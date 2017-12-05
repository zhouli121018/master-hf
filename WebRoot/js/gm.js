/**
 * Created by Administrator on 2017-07-25.
 */
$(function(){
    var now=new Date();
    var begin=new Date();
    begin.setDate(begin.getDate()-30);
    now.setDate(now.getDate()+1);

    var overArr=now.toLocaleDateString().split('/');
    for(var i=0;i<overArr.length;i++){
        if(overArr[i]<10){
            overArr[i]=0+overArr[i];
        }
    }
    var overTime=overArr.join('-');
    var beginArr=begin.toLocaleDateString().split('/');
    for(var i=0;i<beginArr.length;i++){
        if(beginArr[i]<10){
            beginArr[i]=0+beginArr[i];
        }
    }
    var beginTime=beginArr.join('-');
    //console.log(overTime);
    //console.log(now.toLocaleDateString().replace(/\//g,"-"));
    //console.log(begin.toLocaleDateString().replace(/\//g,"-"));

    $(' .overTime').val(overTime);
    $(' .beginTime').val(beginTime);
    //$.ajax({
    //    url:'1',
    //    data:{},
    //    success:function(data){
    //        console.log(data);
    //    }
    //})
   
});



$('#navlist').on('click','li a',function(e){
    if ( e && e.preventDefault ){
    	 e.preventDefault(); 
    } else {
    	 window.event.returnValue = false;
    }
    $(this).parent().addClass('active').siblings().removeClass('active');

    var id=$(this).attr('href').slice(1);
    //console.log(id);
    location.href=id+".jsp";

});
$('#count').on('click','table tbody tr',function(){
    $('#agent-detail .modal-header h3 span').html($(this).children().first().html());
    //$('#agent-detail .modal-body').html($(this).html());
});
$('nav.navbar').on('click','#navBtn',function(){
   if($('#bs-example-navbar-collapse-1').css('display')=='none'){
       $('body').css('paddingTop','260px');

   }else{
       $('body').css('paddingTop','60px');
   }
});

$('.navbar-brand').click(function(){
    location.href="info.jsp";
});

$('#login').on('click','.item',function(){
    //console.log(1243);
    $(this).addClass('focus').siblings().removeClass('focus');
});


$('.close').click(function(){
    $(this).parent().parent().fadeOut();
});

$.each($(".nav-pills li"),function(i,item){
	item.addEventListener('click',function(){
		$(this).addClass('active').siblings().removeClass('active');
	})
})

