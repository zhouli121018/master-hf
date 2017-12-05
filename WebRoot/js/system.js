/**
 * Created by Administrator on 2017-08-04.
 */
$(function(){
//获取参数信息
    //$.ajax({
    //
    //});

//    for(var i=0,html='';i<arr.length;i++){
//        html+=`<tr>
//                    <td>${arr[i].noticeName}</td>
//                    <td>${arr[i].content}</td>
//                    <td>${arr[i].type}</td>
//                    <td>${arr[i].pubTime}</td>
//                    <td><a href="${arr[i].nid}">修改</a></td>
//               </tr>`;
//    }
//   $('#noticeTbl tbody').html(html);
});

function changeMessage(id,formId,tdId,c){
    var index=0;
    $(id).on('click','table td:last-child input',function(e){
        e.preventDefault();
        var that=this;
        var sel=$(this).parents('tr').children().first().text();
        $(formId).fadeIn();
        var trDbl=this.parentNode.parentNode;
        index=this.parentNode.parentNode.rowIndex;
        var n=$(id+' table thead tr').children().length-c;
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
                    <input type="text"  class="form-control" value="${contentArr[j]}">

         </div>
         `;
        }
        $(tdId).html(html);
    });

    $(id+' #system-params .sure').click(function(){
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
            } else {
                $(td).html(contentArr2[i]);
            }
        }

 //向后台发送数据，修改数据
        //$.ajax({
        //
        //})



    });
    $(formId+' .cancel').on('click',function(){
        $(formId).fadeOut();
    });
}

changeMessage('#system','#system-params','#params-message',2);




