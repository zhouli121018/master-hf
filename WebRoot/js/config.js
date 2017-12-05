/**
 * Created by Administrator on 2017-08-18.
 */
 $(function(){
//     var arr=[
//         {
//             ID:1,
//             key:"freetime",
//             value:"起始时间1",
//             properties:"属性",
//             label:"标签",
//             createTime:"创建时间"
//         },
//         {
//             ID:2,
//             key:"freetime",
//             value:"起始时间2",
//             properties:"属性",
//             label:"标签",
//             createTime:"创建时间"
//         },
//         {
//             ID:3,
//             key:"freetime",
//             value:"起始时间3",
//             properties:"属性",
//             label:"标签",
//             createTime:"创建时间"
//         }
//     ];
//
//     for(var i=0,html='';i<arr.length;i++){
//         var obj=arr[i];
//         html+=`<tr>
//         <td>${obj.ID}</td>
//         <td>${obj.key}</td>
//         <td>${obj.value}</td>
//         <td>${obj.properties}</td>
//         <td>${obj.label}</td>
//         <td>${obj.createTime}</td>
//         <td><a href="${obj.ID}" class="btn btn-sm btn-warning modify" >修 改</a>
//             <a href="${obj.ID}" class="btn btn-sm btn-danger delete" >删 除</a></td>
//         </tr>`;
//     }
//     $("#configTbl tbody").html(html);

     $("#config button.addConfig").click(function(){
    	 $.each($("#add-config>form input"),function(i,dom){
    		 $(dom).removeClass('success');
    		 $(dom).removeClass('has-err').next().removeClass('error');
    	 });
         $("#add-config").fadeIn();
     });
     $("#add-config .cancel").click(function(){
         $("#add-config").fadeOut();
     });
     $("#add-config .sure").click(function(){
         var str=$("#add-config>form").serialize();
         //console.log(str);
         
         function checkIsNull(id){
        	 if(id.val()==""){
        		 $(id).removeClass('success');
        		 $(id).addClass('has-err').next().addClass('error');
        		 return false;
        	 }else{
        		 $(id).removeClass('has-err');
        		 $(id).addClass('success').next().removeClass('error');
        		 return true;
        	 }

         }
         checkIsNull($("#add-config>form input[name='key']"));
         checkIsNull($("#add-config>form input[name='value']"));
         checkIsNull($("#add-config>form input[name='properties']"));
         checkIsNull($("#add-config>form input[name='label']"));
         if(!checkIsNull($("#add-config>form input[name='key']"))){
        	 $("#add-config>form input[name='key']").focus();
        	 return;
         }
         if(!checkIsNull($("#add-config>form input[name='value']"))){
        	 $("#add-config>form input[name='value']").focus();
        	 return;
         }
         if(!checkIsNull($("#add-config>form input[name='properties']"))){
        	 $("#add-config>form input[name='properties']").focus();
        	 return;
         }
         if(!checkIsNull($("#add-config>form input[name='label']"))){
        	 $("#add-config>form input[name='label']").focus();        	 
        	 return;
         }
         
         $.ajax({
              url: sessionStorage['basePath']+"controller/manager/addConfig",
              data:str,
              success:function(data){
            	  console.log(data);
            	  var returns =  eval('(' + data + ')');
            	  var insertId = returns.id;
            	  var now=new Date().toLocaleString();
                  var html=`<tr><td>${insertId}</td>
                  <td>${$("#add-config [name='key']").val()}</td>
                  <td>${$("#add-config [name='value']").val()}</td>
                  <td>${$("#add-config [name='properties']").val()}</td>
                  <td>${$("#add-config [name='label']").val()}</td>
                  <td>${now}</td>
                  <td><a href="${insertId}" class="btn btn-sm btn-warning modify" >修 改</a>
                      <a href="${insertId}" class="btn btn-sm btn-danger delete" >删 除</a></td></td>
                  </tr>`;
                  $("#configTbl tbody").append(html);
                  $("#add-config .form-group input").val("");
                  $("#add-config").hide();
              }
         });
        
        

     });
     var index=0;
     $("#configTbl").on('click','tbody a',function(e){
         e.preventDefault();
         index = $(this).parents('tr')[0].rowIndex;
         console.log(index);
         if($(this).hasClass('delete')){
             var key=$(this).parents('tr').children()[1].innerHTML;
             var id0=$(this).parents('tr').children()[0].innerHTML;
             if(confirm("确定要删除 "+key+" 项吗？")){
                 $("#configTbl tr:eq("+index+")").remove();
                 $.ajax({
                	 url: sessionStorage['basePath']+"controller/manager/deleteConfig",
                     data:{id:id0},
                     success:function(data){
                   	  console.log(data);
                     }
                 })
             }
         }
         else if($(this).hasClass('modify')){
        	 $.each($("#configDetail>form input"),function(i,dom){
        		 $(dom).removeClass('success');
        		 $(dom).removeClass('has-err').next().removeClass('error');
        	 });
             var id0=$(this).parents('tr').children()[0].innerHTML;
             $("#configDetail h4 b").html(id0);
            $("#configDetail").fadeIn();
             
             $("#configDetail [name='key']").val($("#configTbl tr:eq("+index+")").children()[1].innerHTML);
             $("#configDetail [name='value']").val($("#configTbl tr:eq("+index+")").children()[2].innerHTML);
             $("#configDetail [name='properties']").val($("#configTbl tr:eq("+index+")").children()[3].innerHTML);
             $("#configDetail [name='label']").val($("#configTbl tr:eq("+index+")").children()[4].innerHTML);
             
         }
     });

     $("#configDetail .cancel").click(function(){
         $("#configDetail").fadeOut();
     });

     $("#configDetail .sure").click(function(){
    	 
    	 
    	 function checkIsNull(id){
        	 if(id.val()==""){
        		 $(id).removeClass('success');
        		 $(id).addClass('has-err').next().addClass('error');
        		 return false;
        	 }else{
        		 $(id).removeClass('has-err');
        		 $(id).addClass('success').next().removeClass('error');
        		 return true;
        	 }

         }
         checkIsNull($("#configDetail>form input[name='key']"));
         checkIsNull($("#configDetail>form input[name='value']"));
         checkIsNull($("#configDetail>form input[name='properties']"));
         checkIsNull($("#configDetail>form input[name='label']"));
         if(!checkIsNull($("#configDetail>form input[name='key']"))){
        	 $("#configDetail>form input[name='key']").focus();
        	 return;
         }
         if(!checkIsNull($("#configDetail>form input[name='value']"))){
        	 $("#configDetail>form input[name='value']").focus();
        	 return;
         }
         if(!checkIsNull($("#configDetail>form input[name='properties']"))){
        	 $("#configDetail>form input[name='properties']").focus();
        	 return;
         }
         if(!checkIsNull($("#configDetail>form input[name='label']"))){
        	 $("#configDetail>form input[name='label']").focus();        	 
        	 return;
         }
         
    	  var id0 = $("#configTbl tr:eq("+index+")").children()[0].innerHTML;
         var str=$("#configDetail>form").serialize();
         str+="&id="+id0;
         console.log(str);
         var nowid=$("#configDetail h4 b").html();
         $.ajax({
        	 url: sessionStorage['basePath']+"controller/manager/updateConfig",
                 data:str,
                 success:function(data){
               	  console.log(data);
                 }
         })
         var now=new Date().toLocaleString();
         $("#configTbl tr:eq("+index+")").children()[1].innerHTML = $("#configDetail [name='key']").val();
         $("#configTbl tr:eq("+index+")").children()[2].innerHTML = $("#configDetail [name='value']").val();
         $("#configTbl tr:eq("+index+")").children()[3].innerHTML = $("#configDetail [name='properties']").val();
         $("#configTbl tr:eq("+index+")").children()[4].innerHTML = $("#configDetail [name='label']").val();
         $("#configDetail .form-group input").val("");
         $("#configDetail").hide();

     });
 });