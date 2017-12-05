/**
 * Created by Administrator on 2017-07-28.
 */
function validCode(){
    		var mid = sessionStorage['managerId'];
    		var inviteCode = $("#inviteCode").val();
    		if(typeof(mid)=="undefined")
    			mid="";
    		var able=true;
    		$.ajax({
    			   url: sessionStorage['basePath']+"controller/manager/inviteCodeValid?inviteCode="+inviteCode+"&id="+mid,
    			   data: "",
    			   success: function data(data){
    				   var returns =  eval('(' + data + ')');
    					var total = returns.valid;
    					if(total){
    						able=true;
    					}else{
    						alert('该邀请码不可用!');
    						able=false;
    						return;
    					}
    				}
    		});
    		if(!able){
    			return;
    		}
}
function changeMessage(id,formId,tdId,c){
    var index=0;
    $(id).on('click','table td:last-child a',function(e){
        e.preventDefault();
        var that=this;
        var uuid = $(this).parents('tr').find('td:eq(0)').html();
        var sel=$(this).parents('tr').children().first().text();
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
        	if(j==0){
        		html+=`
                <div class="form-group">
                           <label >${titleArr[j]}</label>
                           <input type="text"  class="form-control" value="${contentArr[j]}" id="userId" disabled>
                           <p>请输入正确的格式</p>
                </div>
                `;
        	}else{
            html+=`
         <div class="form-group">
                    <label >${titleArr[j]}</label>
                    <input type="text"  class="form-control" value="${contentArr[j]}" disabled>
                    <p>请输入正确的格式</p>
         </div>
         `;
        }
        }
        html+=`<div class="form-group">
                    <label >充钻石</label>
                    <input type="number"  class="form-control" id="payCardNum" >
                    <p>请输入正确的充钻数量</p>
         </div>`;
         if(sessionStorage['powerId']==1){
         html+=`<div class="form-group">
         <label >充红钻</label>
         <input type="number"  class="form-control" id="payRedCardNum" >
         <p>请输入正确的红钻数量（注意！是红钻）</p>
         </div>`;
         }
         html+=`<div class="form-group" style="display:none;">
         <label >重新绑定邀请码</label>
         <input type="number" min="1" class="form-control" id="inviteCode"  value="">
         </div>`;
        $(tdId).html(html);
    });

    $(id+' .sure').click(function(){
    	if($("#payRedCardNum").length>0){
    		if($("#payCardNum").val()==''&&$("#payRedCardNum").val()==''&&$("#inviteCode").val()==''){
        		alert('请输入信息！');
        		return;
        	}
    	}else{
    		if($("#payCardNum").val()==''&&$("#inviteCode").val()==''){
        		alert('请输入信息！');
        		return;
        	}
    	}
    	if(sessionStorage['powerId']==1){
        	if($("#payRedCardNum").val()%1!=0){
        		$("#payRedCardNum").removeClass('success');
        		$("#payRedCardNum").addClass('has-err').next().addClass('error');
        		alert("请输入正确的充钻数量！");
        		$("#payRedCardNum").focus();
        		return;
        	}else{
        		$("#payRedCardNum").removeClass('has-err').next().removeClass('error');
        		$("#payRedCardNum").addClass('success');
        	}
        }else{
        	if($("#payCardNum").val()%1!=0||$("#payCardNum").val()<0){
        		$("#payCardNum").removeClass('success');
        		$("#payCardNum").addClass('has-err').next().addClass('error');
        		alert("请输入正确的充钻数量！");
        		$("#payCardNum").focus();
        		return;
        	}else{
        		$("#payCardNum").removeClass('has-err').next().removeClass('error');
        		$("#payCardNum").addClass('success');
        	}
        }
//    	$("#payCardNum").val()==''&&$("#payCardNum").val(0);
//    	$("#payRedCardNum").val()==''&&$("#payRedCardNum").val(0);
    	if(sessionStorage['roomCard']){
    		var roomCard = parseInt(sessionStorage['roomCard']);
        	console.log(roomCard);
        	console.log($("#payCardNum").val());
        	console.log(($("#payCardNum").val() )> roomCard);
        	if( ($("#payCardNum").val() )> roomCard ){
        		alert("房卡不足，请先充值！(剩余房卡： "+roomCard+" )");
        		$("#payCardNum").focus();
        		return false;
        	}
    	}
    	$.ajax({	
    		   type: "POST",
    		   url: sessionStorage['basePath']+"controller/manager/updateAccount",
    		   data: "payRedCardNum="+$("#payRedCardNum").val()+"&payCardNum="+$("#payCardNum").val()+"&userid="+$("#userId").val()+"&inviteCode="+$("#inviteCode").val(),
    		   success: function data(data){
    			   //成功返回之后重置userId,managerId
    			   var param  = eval('('+data+')');
     			   if(param.msg){alert(param.msg);}
    			   if($("#payCardNum").val()!=0){
    				   if(param.roomcard){
    	    				  sessionStorage['roomCard']=param.roomcard;
    	    			  }
    	    			  var n= parseInt($("#vipTbl tr:eq("+index+")").children()[2].innerHTML);
    	    			  var m= parseInt($("#payCardNum").val());
    	    			  console.log(index,n,m,n+m);
    	    			  $("#vipTbl tr:eq("+index+")").children()[2].innerHTML=n+m;
    			   }
    		 }
    	});
        $(formId).hide();

       
    });
    $(formId+' .cancel').on('click',function(){
        $(formId).fadeOut();
    });
}
$('#vip').on('click','.editVip .updateStatus',function(){
	var val=$(this).prev().val();
	var uuid=$(this).parents('tr').find('td:eq(0)').html();
	if(confirm("确定修改账号ID "+uuid+" 状态吗？")){
        $.ajax({
            url:sessionStorage['basePath']+"controller/manager/changeAccountStatusByUuid",
            data:{uuid:uuid,status:val},
            success:function(json){
                console.log(json);
                var data =JSON.parse(json);
                if(data.status>0){
                	alert(data.msg);
                }
            }
        });
    }
})

changeMessage('#vip','#vipDetail','#vip-message',1);