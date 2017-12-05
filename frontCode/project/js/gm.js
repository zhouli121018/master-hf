$(function(){
    var now=new Date();
    var begin=new Date();
    begin.setDate(begin.getDate()-30);
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


    var options = {
        currentPage: 3,
        totalPages: 10
    };

    $('#agent-pages').bootstrapPaginator(options);
    $('#vip-pages').bootstrapPaginator(options);
    $('#count-pages').bootstrapPaginator(options);

});



$('#navlist').on('click','li a',function(e){
    e.preventDefault();
    $(this).parent().addClass('active').siblings().removeClass('active');

    var id=$(this).attr('href').slice(1);
    //console.log(id);
    location.href=id+".html";

});
$('nav.navbar').on('click','button.logout',function(){
   if(confirm('确认退出？')){
       location.href='login.html';
   }
});
$('#count').on('click','table tbody tr',function(){
    $('#agent-detail .modal-header h3 span').html($(this).children().first().html());
    //$('#agent-detail .modal-body').html($(this).html());
});
$('#count').on('click','div.box a',function(e){
    e.preventDefault();
    $(this).addClass('active').siblings().removeClass('active');
    var id=$(this).attr('href');
    $(id).css('display','block').siblings().css('display','none');
});
$('nav.navbar').on('click','#navBtn',function(){
   if($('#bs-example-navbar-collapse-1').css('display')=='none'){
       $('body').css('paddingTop','260px');

   }else{
       $('body').css('paddingTop','60px');
   }
});

$('.navbar-brand').click(function(){
    location.href="info.html";
});

$('#login').on('click','.item',function(){
    //console.log(1243);
    $(this).addClass('focus').siblings().removeClass('focus');
});

$('#agent>button').click(function(){
    $('#add-message').fadeIn();
    var n=$('#agent table thead tr').children().length-2;
    //console.log(n);
    for(var i= 0,title='';i<n;i++){
        title+=$('#agent table thead th:eq('+i+')').html()+',';
    }
   // console.log(title);
    var titleArr=title.split(',');
//    console.log(titleArr);
    for(var j= 0,html='';j<titleArr.length-1;j++){

        html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text"  class="form-control">
                    <p>请输入正确的格式</p>
         </div>
         `;
    }
    $('#addAgentMessage').html(html);
});

$('#add-message .sureAdd').on('click',function(){
    $('#add-message').fadeOut();
    var n=$('#agent table thead tr').children().length-2;
    for(var i= 0,content2='';i<n;i++){
        content2+=$('#add-message form div.form-group').children('input')[i].value+',';
    }
    var contentArr2=content2.split(',');
    var html=$('#agent table tbody').html();
    html+=`<tr>`;
    for(var i=0;i<contentArr2.length-1;i++){
        html+=`
        <td>${contentArr2[i]}</td>
        `;
    }
        html+=`<td>
                   <button class="btn btn-success">
                                    启用
                   </button>
                   <button class="btn btn-danger">
                                    禁用
                   </button>
                </td>
                <td><a href="#">修改</a> &nbsp; <a class="delete" >删除</a></td>
                </tr>`;

    $('#agent table tbody').html(html);
});
$('#add-message .cancel').on('click',function(){
    $('#add-message').fadeOut();
});


//$(function(){
//    function showDate()
//    {
//        var today = new Date();
//        var day = today.getDate();
//        var month = today.getMonth() + 1;
//        var year = today.getFullYear();
//        //var mytime=today.toLocaleTimeString();
//        var date = year + "-" + month + "-" + day ;
//        document.getElementById("txt").value = date;
//        console.log(date);
//    }
//    showDate();
//});

// reg=/^([\u4e00-\u9fa5]){2,7}$/;
function valid(id,reg){
    $(id).blur(function(){
        var val=$(this).val();
       // console.log(val=='');
        if(!reg.test(val)){
            $(this).addClass('has-err').next().addClass('error');
        }else{
            $(this).removeClass('has-err').next().removeClass('error');
            $(this).addClass('success');
        }
    });
}
var reg=/^([\u4e00-\u9fa5]){2,7}$/;
valid('#uName',reg);
valid('#phoneNum',/^1[34578]\d{9}$/);


function changeMessage(id,formId,tdId,c){
    var index=0;
    var str;
    $(id).on('dblclick','table tbody tr',function(e){

        if(id=="#agent"&&(e.target==this.firstElementChild||e.target.nodeName=="A"||e.target.nodeName=="SPAN")){
            return ;
        }

        $(formId).fadeIn();
        var trDbl=this;
        index=this.rowIndex;
        var n=$(id+' table thead tr').children().length-c;
        //console.log(n);
        for(var i= 0,title='',content='';i<n;i++){
            title+=$(id+' table thead th:eq('+i+')').text()+',';
            if($($(trDbl).children()[i]).hasClass('uname')){
                str=$($(trDbl).children()[i]).html();
                content+=$(trDbl).children()[i].textContent+',';

            }else{
                content+=$(trDbl).children()[i].innerHTML+',';
            }
        }
       // console.log(title,content);
        var titleArr=title.split(',');
        var contentArr=content.split(',');
       // console.log(titleArr,contentArr);
        for(var j= 0,html='';j<titleArr.length-1;j++){
            if(titleArr[j]=='会员数'){
                html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text" disabled  class="form-control" value="${contentArr[j]}">
                    <p>请输入正确的格式</p>
         </div>
         `;
            }else if(j==4){
                html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text" disabled  class="form-control" value="${contentArr[j]}">
         </div>
         `;
            }else if(j==6||j==7){
                html+=`
         <div class="form-group" style="display:none;">
                    <label >${titleArr[j]}</label>
                    <input type="text" disabled  class="form-control" value="${contentArr[j]}">
         </div>
         `;
            }else if(j==8){
                console.log(j);
                html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input class="form-control" disabled type="text" value="${contentArr[j]}"/>
                    <h5>修改代理级别</h5>
                    <select><option value="0">皇冠代理</option><option value="1">钻石代理</option><option value="2">铂金代理</option><option value="3">黄金代理</option></select>
         </div>
         `;
            }else{
                html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text"  class="form-control" value="${contentArr[j]}">
                    <p>请输入正确的格式</p>
         </div>
         `;
            }

        }
        $(tdId).html(html);
        $(formId+' select').change(function(){
            $(this).prev().css('display','none');
            $(this).prev().prev().val($(this).val()).css('display','none')

        })
    });
    $(id).on('click','table td:last-child a',function(e){
        e.preventDefault();
        var that=this;

        var sel=$(this).parents('tr').children().first().text();
        if(this.className=='delete'){
            var m=$(this).parents('tr').attr('mark');
            var m1=$(this).parents('tr').attr('mark1');
            var m2=$(this).parents('tr').attr('mark2');
            var m3=$(this).parents('tr').attr('mark3');
            if(confirm("确定删除"+sel+" 项吗？")){
                //$.ajax({
                //    url:'',
                //    data:{},
                //    success:function(data){
                //        console.log(data)
                //    }
                //});
                if(m3){
                    $(that).parents('tr').siblings('[mark3='+m3+']').remove();
                }else if(m2){
                    $(that).parents('tr').siblings('[mark2='+m2+']').remove();
                }else if(m1){
                    $(that).parents('tr').siblings('[mark1='+m1+']').remove();
                }else if(m){
                    $(that).parents('tr').siblings('[mark='+m+']').remove();
                }

                $(that).parents('tr').remove();
            }
            return;
        }else if(this.className=='resetPwd'){
            var str1=prompt('请输入新密码：');
            if(str1==null){
                return;
            }
            var str2=prompt('请再次输入新密码：');
            if(str1!=str2){
                alert('两次输入密码不一致！请重新输入！');
            }else if((str1!=null)&&str1==str2&&confirm("确定修改密码？")){
                console.log('修改成功！');
                //$.ajax({
                //
                //})
            }
            return;

        }
        $(formId).fadeIn();
        var trDbl=this.parentNode.parentNode;
        index=this.parentNode.parentNode.rowIndex;
        console.log(index);
        var n=$(id+' table thead tr').children().length-c;
        //console.log(n);
        for(var i= 0,title='',content='';i<n;i++){
            title+=$(id+' table thead th:eq('+i+')').html()+',';
            if($($(trDbl).children()[i]).hasClass('uname')){
                str=$($(trDbl).children()[i]).html();
                // console.log(typeof str,str);
                content+=$(trDbl).children()[i].textContent+',';

            }else{
            content+=$(trDbl).children()[i].innerHTML+',';}
        }
        //console.log(title,content);
        var titleArr=title.split(',');
        var contentArr=content.split(',');
        //console.log(titleArr,contentArr);
        for(var j= 0,html='';j<titleArr.length-1;j++){
            if(titleArr[j]=='会员数'){
                html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text" disabled  class="form-control" value="${contentArr[j]}">
         </div>
         `;
            }else if(j==4){
                html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text" disabled  class="form-control" value="${contentArr[j]}">
         </div>
         `;
            }else if(j==6||j==7){
                html+=`
         <div class="form-group" style="display:none;">
                    <label >${titleArr[j]}</label>
                    <input type="text" disabled  class="form-control" value="${contentArr[j]}">
         </div>
         `;
            }else if(j==8){
                html+=`
         <div class="form-group">
                    <label>${titleArr[j]}</label>
                    <input class="form-control" disabled type="text" value="${contentArr[j]}"/>
                    <h5>修改代理级别</h5>
                    <select><option value="0">皇冠代理</option><option value="1">钻石代理</option><option value="2">铂金代理</option><option value="3">黄金代理</option></select>
         </div>
         `;
            }else{
            html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text"  class="form-control" value="${contentArr[j]}">
                    <p>请输入正确的格式</p>
         </div>
         `;}
        }
        $(tdId).html(html);
        $(formId+' select').change(function(){
            $(this).prev().css('display','none');
            $(this).prev().prev().val($(this).val()).css('display','none')

        })

    });



    $(id+' .sure').click(function(){
        $(formId).fadeOut();

        var n=$(id+' table thead tr').children().length-c;
        for(var i= 0,content2='';i<n;i++){
            content2+=$(formId+' form div.form-group').children('input')[i].value+',';
        }
        var contentArr2=content2.split(',');
        for(var i=0,html='';i<contentArr2.length-1;i++) {
            var nowTr = $(id + ' table tbody tr:eq(' + (index - 1) + ')');
            var td = nowTr.children()[i];
            if (td.className == 'uname') {
                $(td).find('b').html(contentArr2[i]);
            }else if(td.className == 'grade'){
                var before=$(td).html();
                $(td).html(contentArr2[i]==0?"皇冠代理":(contentArr2[i]==1?"钻石代理":(contentArr2[i]==2?"铂金代理":(contentArr2[i]==3?"黄金代理":before) ) ) );
            } else {
                $(td).html(contentArr2[i]);
            }
        }
    });
    $(formId+' .cancel').on('click',function(){
        $(formId).fadeOut();
    });
}
//changeMessage('#vip','#vipDetail','#vip-message',1);
changeMessage('#agent','#agentDetail','#agent-message',1);

$('.close').click(function(){
    $(this).parent().parent().fadeOut();
});



