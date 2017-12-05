/**
 * Created by Administrator on 2017-07-28.
 */
$(function(){
    var arr=[
        {
            vid:1,
            ID:'123456',
            nickname:'鸿运当头',
            regtime:'2017-07-25 08:26:27'
        },
        {
            vid:2,
            ID:'456789',
            nickname:'鸿运',
            regtime:'2017-07-25 08:26:27'
        },
        {
            vid:3,
            ID:'789123',
            nickname:'鸿运当头',
            regtime:'2017-07-25 08:26:27'
        },
        {
            vid:4,
            ID:'123456',
            nickname:'鸿运当头',
            regtime:'2017-07-25 08:26:27'
        }
    ];
    for(var i=0,html='';i<arr.length;i++){
        html+=`<tr>
                    <td>${arr[i].ID}</td>
                    <td>${arr[i].nickname}</td>
                    <td>${arr[i].regtime}</td>
                    <td><a href="${arr[i].vid}">充值</a> &nbsp; <a class="disable" href="${arr[i].vid}">禁用</a> </td>
               </tr>`;
    }

    $('#vipTbl tbody').html(html);

    //$.ajax({
    //    url:'2',
    //    data:{},
    //    success:function(data){
    //        console.log(data);
    //    }
    //})

});

function changeMessage(id,formId,tdId,c){
    var index=0;
    $(id).on('dblclick','table tbody tr',function(e){

        if(id=="#agent"&&(e.target==this.firstElementChild||e.target.nodeName=="A"||e.target.nodeName=="SPAN")){
            return ;
        }
        $(formId).fadeIn();
        var trDbl=this;
        index=this.rowIndex;
        var n=$(id+' table thead tr').children().length-c;
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

                html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text"  class="form-control" value="${contentArr[j]}" disabled>
         </div>
         `;


        }
        html+=`<div class="form-group">
                    <label >充值金额(￥)：</label>
                    <!--<input type="text"  class="form-control" value="100.00">-->
                    <select><option value="50">50.00</option><option value="100" selected>100.00</option><option value="150">150.00</option><option value="200">200.00</option></select>
         </div>`;
        $(tdId).html(html);
    });
    $(id).on('click','table td:last-child a',function(e){
        e.preventDefault();
        var that=this;
        var sel=$(this).parents('tr').children().first().text();
        if($(this).hasClass('disable')){
            var m=$(this).parents('tr').attr('mark');
            var m1=$(this).parents('tr').attr('mark1');
            if($(this).html()=="禁用"&&confirm("确定禁用账号ID "+sel+" 吗？")){
                //$.ajax({
                //    url:'',
                //    data:{},
                //    success:function(data){
                //        console.log(data)
                //    }
                //});
                $(this).html('启用');
                $(this).parents('tr').addClass('disable0')
            }else if($(this).html()=="启用"&&confirm("确定启用账号ID "+sel+" 吗？")){
                $(this).parents('tr').removeClass('disable0');
                $(this).html('禁用');
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
            content+=$(trDbl).children()[i].innerHTML+',';
        }
        //console.log(title,content);
        var titleArr=title.split(',');
        var contentArr=content.split(',');
        //console.log(titleArr,contentArr);
        for(var j= 0,html='';j<titleArr.length-1;j++){

            html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text"  class="form-control" value="${contentArr[j]}" disabled>

         </div>
         `;
        }
        html+=`<div class="form-group">
                    <label >充值金额(￥)：</label>
                    <!--<input type="text"  class="form-control" value="100.00">-->
                    <select><option value="50">50.00</option><option value="100" selected>100.00</option><option value="150">150.00</option><option value="200">200.00</option></select>
         </div>`;
        $(tdId).html(html);
    });

    $(id+' .sure').click(function(){
        $(formId).fadeOut();

        var n=$(id+' table thead tr').children().length-c;
        for(var i= 0,content2='';i<n-1;i++){
            content2+=$(formId+' form div.form-group').children('input')[i].value+',';
        }
        var contentArr2=content2.split(',');
        for(var i=0,html='';i<contentArr2.length-1;i++) {
            var nowTr = $(id + ' table tbody tr:eq(' + (index - 1) + ')');
            var td = nowTr.children()[i];
            if (td.className == 'uname') {
                $(td).find('b').html(contentArr2[i]);
            } else {
                $(td).html(contentArr2[i]);
            }
        }
    });
    $(formId+' .cancel').on('click',function(){
        $(formId).fadeOut();
    });
}

changeMessage('#vip','#vipDetail','#vip-message',1);