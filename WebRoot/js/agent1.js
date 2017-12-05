$('#agent>button').click(function(){
    $('#add-message').fadeIn();
    $('#agent-message').html("");
    $('#addAgentMessage').html("");
    var n=$('#agent table thead tr').children().length-2;
    //console.log(n);
    for(var i= 0,title='';i<n;i++){
        title+=$('#agent table thead th:eq('+i+')').html()+',';
    }
    // console.log(title);
    var titleArr=title.split(',');
//    console.log(titleArr);
    var html=`
        <div class="form-group">
	    <label>姓名</label>
	    <input id="name" type="text" class="form-control" autofocus  value="" placeholder="请输入代理的真实姓名">
		
	    </div>
		<div class="form-group">
		        <label>手机号</label>
		        <input id="telephone" type="number" min="1" class="form-control" value="">
		        
		</div>
		<div class="form-group">
		        <label>微信</label>
		        <input id="weixin" type="text" required class="form-control" value="" placeholder="请输入你的微信号">
		        
	    </div>
		<div class="form-group">
		        <label>QQ号码</label>
		        <input id="QQ" type="number" min="1" class="form-control" value="">
		</div>
		
		<div class="form-group">
        <label style="font-size:0.4em;">分成比例(不填则默认)</label>
        <input id="rebate" type="text" class="form-control" value="">
        
        </div>

		<div class="form-group">
		        <label>邀请码</label>

		        <input id="inviteCode" type="number" min="1" class="form-control valiCode" required  value="">
		        
		        <input type="hidden" id="checkCanSubmit"  value="true">
		</div>
		<div class="form-group">
		        <label>游戏用户ID</label>
		        <input id="uuid" type="number" min="1" class="form-control" required  value="">
		        
		        </div>
		<div class="form-group">
	       <label>上级代理邀请码</label>
	       <input id="parentInviteCode" type="number" min="1" class="form-control"  value="" placeholder="请输入上级代理邀请码，没有就不填">
	    </div>
		<div class="form-group">
		        <label>代理级别</label>
		        <select id="powerId"><option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option></select>
		</div>

		<div class="form-group">
		       <label>是否能开代理</label>
		       <label><input type="radio" name="rootManager" value="1" checked>是</label>&nbsp;&nbsp;
		       <label><input type="radio" name="rootManager"  value="0">否</label>
		</div>
         `;
    $('#addAgentMessage').html(html);
    $('#addAgentMessage #parentInviteCode').val(sessionStorage['inviteCode']);
    if(sessionStorage['powerId']==1){
		$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
	}else if(sessionStorage['powerId']==5){
		$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
	}else if(sessionStorage['powerId']==4){
		$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
	}else if(sessionStorage['powerId']==3){
		$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="2">黄金代理</option>`);
	}
    /*
    if(sessionStorage['powerId']==1){
    	$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
    }else if(sessionStorage['powerId']==5){
    	$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);  
    }else if(sessionStorage['powerId']==4){
    	$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);  
    }else if(sessionStorage['powerId']==3){
    	$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="2">黄金代理</option>`);  
    }
    */
    $('#addAgentMessage').on('blur',' .form-group input',function(){
        if($(this).prop('required')==true&&$(this).val()==""){
            $(this).removeClass('success');
            $(this).addClass('has-err').next().addClass('error');
            return;
        }else if(($(this).prop('required')==true&&$(this).val().length!==0)){
            $(this).removeClass('has-err').next().removeClass('error');
            $(this).addClass('success');
        }
//        if($(this).attr('id')=="inviteCode"){
//        	validCode();
//    	}
        if($(this).attr('id')=="uuid"){
        	validUuid();
    	}
        if($(this).attr('id')=="parentInviteCode"){
        	if($(this).val()!=""){
        		var inviteCode = $(this).val();
        		$.ajax({
        			url:sessionStorage['basePath']+"controller/manager/inviteCodeValid?inviteCode="+inviteCode,
        			success:function(data){
        				var res = JSON.parse(data);
        				if(!res.valid){
        					$.ajax({
        						url:sessionStorage['basePath']+"controller/manager/getPowerIdByInviteCode?inviteCode="+inviteCode,
        						success:function(data){
        							console.log(data);
        							var obj = JSON.parse(data);
        							if(obj.powerId==0){
        								alert("请重新输入上级邀请码！");
        							}else if(obj.powerId==1){
        								$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
        							}else if(obj.powerId==5){
        								$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
        							}else if(obj.powerId==4){
        								$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
        							}else if(obj.powerId==3){
        								$('#addAgentMessage #powerId').html(`<option value="0">--请选择--</option><option value="2">黄金代理</option>`);
        							}
        						}
        					})
        				}else{
        					alert('该邀请码不存在！');
        				}
        			}
        		})
        	}
        }
    });
    $('#inviteCode').change(function(){
    	validCode();
    })
    
    valid('#name',/^([\u4e00-\u9fa5]){2,4}$/);
    valid('#telephone',/^1[34578]\d{9}$/);
});
$('#add-message .cancel').on('click',function(){
    $('#add-message').hide();
});
$('#pw-message .cancel').on('click',function(){
    $('#pw-message').hide();
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

// reg=/^([\u4e00-\u9fa5]){2,4}$/;
function valid(id,reg){
    $(id).blur(function(){
        var val=$(this).val();
       // console.log(val=='');
        if(!reg.test(val)){
        	$(this).removeClass('success');
            $(this).addClass('has-err').next().addClass('error');
            return false;
        }else{
            $(this).removeClass('has-err').next().removeClass('error');
            $(this).addClass('success');
        }
    });
}
var reg=/^([\u4e00-\u9fa5]){2,7}$/;
valid('#uName',reg);
//valid('#phoneNum',/^1[34578]\d{9}$/);

var options = {
    currentPage: 3,
    totalPages: 10
};

//$('#agent-pages').bootstrapPaginator(options);
//$('#vip-pages').bootstrapPaginator(options);
//$('#count-pages').bootstrapPaginator(options);


function changeMessage(id,formId,tdId,c){
	$('#agent-message').html("");
    $('#addAgentMessage').html("");
    var index=0;
    var str;
    var beforeCode;
    $(id).on('click','table td:last-child a',function(e){
        e.preventDefault();
        var that=this;

        var sel=$(this).parents('tr').children().first().text();
        var num=$(this).parents('tr').children()[1].innerHTML;
        if($(this).hasClass('delete')){
            var m=$(this).parents('tr').attr('mark');
            var m1=$(this).parents('tr').attr('mark1');
            var m2=$(this).parents('tr').attr('mark2');
            var m3=$(this).parents('tr').attr('mark3');
            if(confirm("确定删除"+sel+" 项吗？")){
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
                deleteAgent(this);

            }
            
            
            return;
        }else if($(this).hasClass('resetPwd')){
        	var mid = $(this).parents('tr').children()[1].innerHTML;
        	console.log(mid);
            var str1=prompt('请输入新密码：');
            if(str1==null){
                return;
            }
            var str2=prompt('请再次输入新密码：');
            if(str1!=str2){
                alert('两次输入密码不一致！请重新输入！');
                return;
            }else if((str1!=null)&&str1==str2&&confirm("确定修改密码？")){
            		//var mid = $(this).parents('tr').attr('mark');
            		$.ajax({	
            			   type: "POST",
            			   url: sessionStorage['basePath']+"controller/manager/updateManagerPassword?newPwd="+str1+"&mid="+mid,
            			   data: "",
            			   success: function data(data){
            				  var returns =  eval('(' + data + ')');
            				  if(returns.status=='0')
            					 alert('修改成功！');
            				  else
            					 alert(returns.error);
            			   }
            		});
            	
            }
            return;

        }
        if($(this).hasClass('charge')){
            $("#charge").fadeIn();
        	$("#charge form .uname").val(sel);
        	$("#charge form [name='num']").val(num);
        	return;
        }
        $(formId).fadeIn();
        var trDbl=this.parentNode.parentNode;
        index=this.parentNode.parentNode.rowIndex;
        //console.log(index);
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
        
       // console.log(titleArr,contentArr);
        //console.log(contentArr[4]);
        for(var k=0;k<contentArr.length;k++){
        	//console.log(contentArr[k]=="----");
        	if(contentArr[k]=="----"){
        		contentArr[k]="";
        	}
        }
       // console.log(contentArr);
        var childInviteCode=0;
        for(var j= 0,html='';j<titleArr.length-1;j++){
//        	if(contentArr[j]=="----"){
//        		contentArr[j]==" ";
//        	}
        	if(titleArr[j]=='邀请码'){
        		childInviteCode=contentArr[j];
        	}
        	if(sessionStorage['powerId']==3||sessionStorage['powerId']==2){
        		if(titleArr[j]=='会员数'||titleArr[j]=='代理状态'){
                    html+=`
             <div class="form-group" style="display:none">
                        <label >${titleArr[j]}</label>
                        <input type="text" disabled  class="form-control" value="${contentArr[j]}">
             </div>
             `;
                }else if(titleArr[j]=='邀请码'){
                	beforeCode=contentArr[j];
                	sessionStorage['beforeCode']=beforeCode;
                    html+=`
             
             <div class="form-group" style="display:none">
                        <label >${titleArr[j]}</label>
                        
                        <input id="inviteCode" type="number" min="1" class="form-control"  value="${contentArr[j]}">
                        <p id="validInfo"></p>
        		        <input type="hidden" id="checkCanSubmit" value="true">
             </div>
             `;
                }else if(titleArr[j]=='姓名'){
                    html+=`
                    <div class="form-group">
                               <label >${titleArr[j]}</label>
                               <input id="name" type="text" disabled class="form-control" value="${contentArr[j]}">
                               <p>请输入2-4位中文姓名</p>
                    </div>
                    `;
                       }
                else if(titleArr[j]=='代理编码'){
                    html+=`
                    <div class="form-group">
                               <input id="mid" style="display:none"  value="${contentArr[j]}">
                    </div>
                    `;
                    
                       } else if(titleArr[j]=='金额'){
                           html+=`
                               <div class="form-group">
                                          <input id="money" style="display:none"  value="${contentArr[j]}">
                               </div>
                               `;
                               
                                  }
                else if(titleArr[j]=='手机号'){
                    html+=`
             <div class="form-group" style="display:none">
                        <label >${titleArr[j]}</label>
                        <input id="telephone" type="text" class="form-control" value="${contentArr[j]}">
                        
             </div>
             `;
                }
                else if(titleArr[j]=='微信'){
                    html+=`
             <div class="form-group" style="display:none">
                        <label >${titleArr[j]}</label>
                        <input id="weixin" type="text" class="form-control" value="${contentArr[j]}">
             </div>
             `;
                }
                else if(titleArr[j]=='QQ号码'){
                    html+=`
             <div class="form-group" style="display:none">
                        <label >${titleArr[j]}</label>
                        <input id="QQ" type="number" min="1" class="form-control" value="${contentArr[j]}">
             </div>
             `;
                }else if(titleArr[j]=='分成比例'){
                	var tmprebate;
                	if(contentArr[j]=='----')
                		tmprebate = '';
                	else
                		tmprebate = contentArr[j];
                    html+=`
                    <div class="form-group" style="display:none">
                               <label >${titleArr[j]}</label>
                               <input id="rebate" type="text" class="form-control" value="${tmprebate}">
                               
                    </div>
                    `;
                       }
                else if(titleArr[j]=='游戏用户ID'){
                    html+=`
             <div class="form-group" style="display:none">
                        <label >${titleArr[j]}</label>
                        <input id="uuid" type="number" min="1" class="form-control" value="${contentArr[j]}">
             </div>
             `;
                }
                else if(titleArr[j]=='代理级别'){
                    html+=`
             <div class="form-group">
                        <label>代理级别</label>
                        <select id="powerId" truevalue="${contentArr[j]}"><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option></select>
             </div>
             `;
                }else if(titleArr[j]=='是否开代理'){
                	if(contentArr[j]=='是'){
                	html+=`
                    <div class="form-group" style="display:none">
                               <label >上级代理邀请码</label>
                               <input id="parentInviteCode" type="number" min="1"  class="form-control" value="" placeholder="请输入上级代理邀请码">
                               
                    </div><div class="form-group" style="display:none">
            		       <label>是否能开代理</label> <br/>
            		       <label> <input type="radio" name="rootManager" value="1" checked="true">是</label>&nbsp;&nbsp;
            	           <label> <input type="radio" name="rootManager"  value="0">否</label>
            		</div>
                    `;
                	}else{
                		html+=`
                        <div class="form-group" style="display:none">
                                   <label >上级代理邀请码</label>
                                   <input id="parentInviteCode" type="number" min="1"  class="form-control" value="" placeholder="请输入上级代理邀请码">
                                   
                        </div><div class="form-group" style="display:none">
                		       <label>是否能开代理</label> <br/>
                		       <label> <input type="radio" name="rootManager" value="1" >是</label>&nbsp;&nbsp;
                	           <label> <input type="radio" name="rootManager"  value="0" checked="true">否</label>
                		</div>
                        `;
                	}

               }
            
        	
         }
        else{
        	
            	if(titleArr[j]=='会员数'||titleArr[j]=='代理状态'){
                    html+=`
             <div class="form-group">
                        <label >${titleArr[j]}</label>
                        <input type="text" disabled  class="form-control" value="${contentArr[j]}">
             </div>
             `;
                }else if(titleArr[j]=='邀请码'){
                	beforeCode=contentArr[j];
                	sessionStorage['beforeCode']=beforeCode;
                    html+=`
             
             <div class="form-group">
                        <label >${titleArr[j]}</label>
                        
                        <input id="inviteCode" type="number" min="1" class="form-control"  value="${contentArr[j]}">
                        <p id="validInfo"></p>
        		        <input type="hidden" id="checkCanSubmit" value="true">
             </div>
             `;
                }else if(titleArr[j]=='姓名'){
                    html+=`
                    <div class="form-group">
                               <label >${titleArr[j]}</label>
                               <input id="name" type="text" class="form-control" value="${contentArr[j]}">
                               
                    </div>
                    `;
                       }
                else if(titleArr[j]=='代理编码'){
                    html+=`
                    <div class="form-group">
                               <input id="mid" style="display:none"  value="${contentArr[j]}">
                    </div>
                    `;
                    
                       } else if(titleArr[j]=='金额'){
                           html+=`
                               <div class="form-group">
                                          <input id="money" style="display:none"  value="${contentArr[j]}">
                               </div>
                               `;
                               
                                  }
                else if(titleArr[j]=='手机号'){
                    html+=`
             <div class="form-group">
                        <label >${titleArr[j]}</label>
                        <input id="telephone" type="text" class="form-control" value="${contentArr[j]}">
                        
             </div>
             `;
                }
                else if(titleArr[j]=='微信'){
                    html+=`
             <div class="form-group">
                        <label >${titleArr[j]}</label>
                        <input id="weixin" type="text" class="form-control" value="${contentArr[j]}">
             </div>
             `;
                }
                else if(titleArr[j]=='QQ号码'){
                    html+=`
             <div class="form-group">
                        <label >${titleArr[j]}</label>
                        <input id="QQ" type="number" min="1" class="form-control" value="${contentArr[j]}">
             </div>
             `;
                }else if(titleArr[j]=='分成比例'){
                	var tmprebate;
                	if(contentArr[j]=='----')
                		tmprebate = '';
                	else
                		tmprebate = contentArr[j];
                    html+=`
                    <div class="form-group">
                               <label >${titleArr[j]}</label>
                               <input id="rebate" type="text" class="form-control" value="${tmprebate}">
                               
                    </div>
                    `;
                       }
                else if(titleArr[j]=='游戏ID'){
                	if(sessionStorage['powerId']==1){
                		 html+=`
                         <div class="form-group">
                                    <label >${titleArr[j]}</label>
                                    <input id="uuid" type="number" min="1" class="form-control" value="${contentArr[j]}">
                         </div>
                         `;
                	}else{
                		 html+=`
                         <div class="form-group" style="display:none;">
                                    <label >${titleArr[j]}</label>
                                    <input id="uuid" type="number" min="1" class="form-control" value="${contentArr[j]}">
                         </div>
                         `;
                	}
                   
                }
                else if(titleArr[j]=='代理级别'){
                    html+=`
             <div class="form-group">
                        <label>代理级别</label>
                        <select id="powerId" truevalue="${contentArr[j]}"><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option></select>
             </div>
             `;
                }else if(titleArr[j]=='是否开代理'){
                	if(contentArr[j]=='是'){
                		if(sessionStorage['powerId']==1){
                			html+=`
                            <div class="form-group">
                                       <label >上级代理邀请码</label>
                                       <input id="parentInviteCode" type="number"  min="1" class="form-control" value="" placeholder="请输入上级代理邀请码">
                                       
                            </div><div class="form-group">
                    		       <label>是否能开代理</label> <br/>
                    		       <label> <input type="radio" name="rootManager" value="1" checked="true">是</label>&nbsp;&nbsp;
                    	           <label> <input type="radio" name="rootManager"  value="0">否</label>
                    		</div>
                            `;
                		}else{
                			html+=`
                            <div class="form-group" style="display:none;">
                                       <label >上级代理邀请码</label>
                                       <input id="parentInviteCode" type="number"  min="1" class="form-control" value="" placeholder="请输入上级代理邀请码">
                                       
                            </div><div class="form-group" style="display:none;">
                    		       <label>是否能开代理</label> <br/>
                    		       <label> <input type="radio" name="rootManager" value="1" checked="true">是</label>&nbsp;&nbsp;
                    	           <label> <input type="radio" name="rootManager"  value="0">否</label>
                    		</div>
                            `;
                		}
                	
                	}else{
                		if(sessionStorage['powerId']==1){
                			html+=`
                            <div class="form-group">
                                       <label >上级代理邀请码</label>
                                       <input id="parentInviteCode" type="number" min="1"  class="form-control" value="" placeholder="请输入上级代理邀请码">
                                       
                            </div><div class="form-group">
                    		       <label>是否能开代理</label> <br/>
                    		       <label> <input type="radio" name="rootManager" value="1" >是</label>&nbsp;&nbsp;
                    	           <label> <input type="radio" name="rootManager"  value="0" checked="true">否</label>
                    		</div>
                            `;
                		}else{
                			html+=`
                            <div class="form-group" style="display:none;">
                                       <label >上级代理邀请码</label>
                                       <input id="parentInviteCode" type="number" min="1"  class="form-control" value="" placeholder="请输入上级代理邀请码">
                                       
                            </div><div class="form-group" style="display:none;">
                    		       <label>是否能开代理</label> <br/>
                    		       <label> <input type="radio" name="rootManager" value="1" >是</label>&nbsp;&nbsp;
                    	           <label> <input type="radio" name="rootManager"  value="0" checked="true">否</label>
                    		</div>
                            `;
                		}
                		
                	}

               }
            
        }
        }
        
        $(tdId).html(html);
        setTimeout(function(){
        	getParentInviteByChild(childInviteCode);
        },1000);
        $('#inviteCode').change(function(){
        	validCode();
        })
        $("#parentInviteCode").change(function(){
        	if($(this).val()!=""){
        		var inviteCode = $(this).val();
        		$.ajax({
        			url:sessionStorage['basePath']+"controller/manager/inviteCodeValid?inviteCode="+inviteCode,
        			success:function(data){
        				var res = JSON.parse(data);
        				if(!res.valid){
        					$.ajax({
        						url:sessionStorage['basePath']+"controller/manager/getPowerIdByInviteCode?inviteCode="+inviteCode,
        						success:function(data){
        							console.log(data);
        							var obj = JSON.parse(data);
        							if(obj.powerId==0){
        								alert("请重新输入上级邀请码！");
        							}else if(obj.powerId==1){
        								$('#powerId').html(`<option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
        							}else if(obj.powerId==5){
        								$('#powerId').html(`<option value="0">--请选择--</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
        							}else if(obj.powerId==4){
        								$('#powerId').html(`<option value="0">--请选择--</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
        							}else if(obj.powerId==3){
        								$('#powerId').html(`<option value="0">--请选择--</option><option value="2">黄金代理</option>`);
        							}
        						}
        					})
        				}else{
        					alert('该邀请码不存在！');
        				}
        			}
        		})
        	}
        })
        
        var level = $("#powerId").attr("truevalue");
        if(sessionStorage['powerId']==1){
        	$(tdId+' #powerId').html(`<option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);
        }else if(sessionStorage['powerId']==5){
        	$(tdId+' #powerId').html(`<option value="0">--请选择--</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);  
        }else if(sessionStorage['powerId']==4){
        	$(tdId+' #powerId').html(`<option value="0">--请选择--</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`);  
        }else if(sessionStorage['powerId']==3){
        	$(tdId+' #powerId').html(`<option value="0">--请选择--</option><option value="2">黄金代理</option>`);  
        }
        
        if(level=='皇冠代理')
        	level = '5';
        else if(level=='钻石代理'){
        	level = '4';
        }
        else if(level=='铂金代理'){
        	level = '3';
        }
        else if(level=='黄金代理'){
        	level = '2';
        }

        $("#powerId option[value='"+level+"']").prop("selected",true);
        $(formId+' select').change(function(){
            $(this).prev().prev().val($(this).val()).css('display','none')
        })

    });
    valid('#telephone',/^1[34578]\d{9}$/);

    
    $(formId+' .sure').click(function(){
    	//console.log(1);
    	var mid = $(formId+" #mid").val();
		var weixin = $(formId+" #weixin").val();
		var qq = $(formId+" #QQ").val();
		var name = $(formId+" #name").val();
		var telephone = $(formId+" #telephone").val();
		var uuid = $(formId+" #uuid").val();
		var rebate = $(formId+" #rebate").val();
		var powerId = $(formId+" #powerId").val();
		var inviteCode = $(formId+" #inviteCode").val();
		var parentInviteCode = $(formId+" #parentInviteCode").val();
		if(typeof(mid)=="undefined")
			mid="";
		var rootManager = $(formId+" input[name='rootManager']:checked").val();
		
		var isCheckOk=true;
		function checkRequired(id){
			if($(id).val().length==0){
				$(id).focus();
				$(id).removeClass('success');
	            $(id).addClass('has-err').next().addClass('error');
				isCheckOk=false;
				return false;
			}else{
				return true;
			}
		}
		checkRequired(formId+" #name");
		if(!checkRequired(formId+" #name")){
			return;
		}
//		var nreg=/^([\u4e00-\u9fa5]){2,4}$/;
//		if(!nreg.test($(formId+" #name").val())){
//				$(formId+" #name").focus();
//	        	$(formId+" #name").removeClass("success");
//	        	$(formId+" #name").addClass('has-err').next().addClass('error');
//	        	isCheckOk=false;
//	            return;
//		}
		
		var rebetreg=/^0\.\d{1,2}$/;
		if($("#rebate").val()!=""&& (!rebetreg.test($("#rebate").val()))){
				$("#rebate").focus();
	        	$("#rebate").removeClass("success");
	        	$("#rebate").addClass('has-err').next().addClass('error');
	        	isCheckOk=false;
	            return;
		}else{
			$("#rebate").removeClass("has-err").next().removeClass('error');
        	$("#rebate").addClass('success');
		}
		
		var reg=/^1[34578]\d{9}$/;
        if(!reg.test($(formId+" #telephone").val())){
        	$(formId+" #telephone").focus();
        	$(formId+" #telephone").removeClass("success");
        	$(formId+" #telephone").addClass('has-err').next().addClass('error');
        	isCheckOk=false;
            return;
        }
        checkRequired(formId+" #weixin");
        if(!checkRequired(formId+" #weixin")){
			return;
		};
        checkRequired(formId+" #inviteCode");
        if(!checkRequired(formId+" #inviteCode")){
			return;
		};
        checkRequired(formId+" #uuid");
        if(!checkRequired(formId+" #uuid")){
			return;
		};
		if($(formId+" #powerId").val()=="0"){
			isCheckOk=false;
			alert("请选择代理级别！");
			return;
		}
		var checkCanSubmit = $(formId+" #checkCanSubmit").val()&& isCheckOk;
		var afterCode=$(this).parents("form").find(".valiCode").val();
//    	if($("#agent .checkCanSubmit").html()=="true"||afterCode==beforeCode){
//            $(formId).hide();
//        }
		if(checkCanSubmit||afterCode==beforeCode){
			$(formId).hide();
			console.log("mid="+mid+"&weixin="+weixin+"&qq="+qq+"&name="+name+"&telephone="+telephone+"&uuid="+uuid+"&powerId="+powerId+"&inviteCode="+inviteCode+"&parentInviteCode="+parentInviteCode+"&rootManager="+rootManager);
			$.ajax({	
				   type: "POST",
				   url: sessionStorage['basePath']+"controller/manager/updateManagerInfo",
				   data: "mid="+mid+"&weixin="+weixin+"&qq="+qq+"&name="+name+"&telephone="+telephone+"&uuid="+uuid+"&powerId="+powerId+"&inviteCode="+inviteCode+"&parentInviteCode="+parentInviteCode+"&rootManager="+rootManager+"&rebate="+rebate,
				   success: function data(data){
					   var nowTr = $(id+' table tbody tr:eq(' + (index - 1) + ')');
				 			var param = eval('('+data+')');
							if(param.status == "0"){
								  getAccount(1,5,sessionStorage['basePath'],sessionStorage['powerId']==1);
								  alert("资料提交成功");
							}
							else if(param.status == "1"&& !param.changePowerId){
							 	alert(param.error);
							}
//							if(param.changePowerId>0){
//								$(nowTr.children()[12]).html(param.powerId==5?'皇冠代理':(param.powerId==4?'钻石代理':(param.powerId==3?'铂金代理':'黄金代理')));
//						    }
					       
				   	}
				});
		
		}	else{
			alert("邀请码不可用！");
		}
    	
    	//console.log(beforeCode,afterCode);
    	
			
    });
    
    $(formId+' .cancel').on('click',function(){
        $(formId).fadeOut();
    });
}
//changeMessage('#vip','#vipDetail','#vip-message',1);
changeMessage('#agent','#agentDetail','#agent-message',1);

$("#charge .cancel").click(function(){
	$("#charge").hide();
})
$("#charge .sure").click(function(){
	var isAgent = 0;
	if(sessionStorage['roomCard']){
		isAgent = 1;
		var roomCard = parseInt(sessionStorage['roomCard']);
		if( parseInt($("#charge form [name='payCardNum']").val()) > roomCard ){
			alert("房卡不足，请先充值！(剩余房卡： "+roomCard+" )");
			$("#charge form [name='payCardNum']").focus();
			return;
		}
	}
	if(sessionStorage['powerId']==1){
		if($("#charge form [name='payCardNum']").val()==""){
			$("#charge form [name='payCardNum']").removeClass('success');
			$("#charge form [name='payCardNum']").addClass('has-err').next().addClass('error');
			alert("请输入正确的充钻数量！");
			$("#charge form [name='payCardNum']").focus();
			return;
		}else{
			$("#charge form [name='payCardNum']").removeClass('has-err').next().removeClass('error');
			$("#charge form [name='payCardNum']").addClass('success');
		}
	}else{
		if($("#charge form [name='payCardNum']").val()==""||$("#charge form [name='payCardNum']").val()<=0){
			$("#charge form [name='payCardNum']").removeClass('success');
			$("#charge form [name='payCardNum']").addClass('has-err').next().addClass('error');
			alert("请输入正确的充钻数量！");
			$("#charge form [name='payCardNum']").focus();
			return;
		}else{
			$("#charge form [name='payCardNum']").removeClass('has-err').next().removeClass('error');
			$("#charge form [name='payCardNum']").addClass('success');
		}
	}
	var str = $("#charge form").serialize();
	console.log(str);
	$("#charge").hide();
	$("#charge form [name='payCardNum']").val("");
	$.ajax({	
		   type: "POST",
		   url: sessionStorage['basePath']+"controller/manager/updateAgentAccount",
		   data: str+"&isAgent="+isAgent,
		   success: function data(data){
			   //成功返回之后重置userId,managerId
			  var param  = eval('('+data+')');
			  if(param.roomcard){
				  sessionStorage['roomCard']=param.roomcard;
			  };
			  alert(param.msg);
		 }
	});
})



function getAccount(pageIndex,type,path,isAdmin){
	var uuid=$('#inputUuid').val();
	var inviteCode=$('#inputInviteCode').val();
	var name=$('#inputName').val();
	var parentInviteCode = $('#inputParentInviteCode').val();
	var powerId = $('#inputPowerId').val();
	$.ajax({	
		   type: "POST",
		   url: path+"controller/manager/getManagers2?pageNum="+pageIndex+"&searchType="+type,
		   data: {uuid:uuid,inviteCode:inviteCode,name:name,parentInviteCode:parentInviteCode,powerId:powerId},
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
				var arr = returns.managers;
				var totalNum = returns.totalNum;
			   var n=0;
			   var totalPages = totalNum/10+1;
			    for(var i=0,html='';i<arr.length;i++){
			          n++;
			        var m=n;
			            html+=`<tr class="treegrid-${n}" mark="${arr[i].id}">
			         <td class="uname"><b>${arr[i].name}</b></td>
			         <td>${arr[i].id}</td>
			         <td>${arr[i].telephone}</td>
			         <td>${arr[i].weixin}</td>
			         <td>${arr[i].qq}</td>
			         <td>${arr[i].inviteCode}</td>
			         <td>${arr[i].uuid}</td>
			         <td>${arr[i].createTimeStr}</td>
			         <td>${arr[i].lastLoginTimeStr}</td>
			         <td>${arr[i].userCounts}</td>
			         <td>${arr[i].totalMoney}</td>
			         <td>${arr[i].actualcard}</td>
			         <td>${arr[i].rebate}</td>`;
			         if(sessionStorage['powerId']>1){
			         html+=`<td>${arr[i].name2}</td>
			         <td>${arr[i].inviteCode2}</td>`;
			         }
			         html+=`<td>${arr[i].rootManager==1?'是':'否'}</td>
			         <td class="grade" grd="${arr[i].powerId}">${arr[i].powerId==5?"皇冠代理":(arr[i].powerId==4?"钻石代理":(arr[i].powerId==3?"铂金代理":(arr[i].powerId==2?"黄金代理":"超级管理员")))}</td>
			         <td class="adminEdit"><a href="${arr[i]}.id" class='btn btn-sm btn-warning'>修改</a> <a href="${arr[i]}.id" class='btn btn-sm btn-success charge'>充房卡</a> <a class='btn btn-sm btn-danger delete' href="${arr[i]}.id">删除</a> <a href="${arr[i].id}" class='btn btn-sm btn-primary resetPwd'>密码重置</a></td>
			         
			        </tr>`;
			        if(arr[i].childagent.length!=0){
			           for(var j=0;j<arr[i].childagent.length;j++){
			               n++;
			               var l=n;
			               var o=arr[i].childagent[j];
			               html+=`<tr class="treegrid-${n} treegrid-parent-${m}" mark="${arr[i].id}" mark1="${o.id}">
			         <td class="uname"><b>${o.name}</b></td>
			         <td>${o.id}</td>
			         <td>${o.telephone}</td>
			         <td>${o.weixin}</td>
			         <td>${o.qq}</td>
			         <td>${o.inviteCode}</td>
			         <td>${o.uuid}</td>
			         <td>${o.createTimeStr}</td>
			         <td>${o.lastLoginTimeStr}</td>
			         <td>${o.userCounts}</td>
			         <td>${o.totalMoney}</td>
			         <td>${arr[i].actualcard}</td>
			         <td>${o.rebate}</td>
			         <td>${o.rootManager==1?'是':'否'}</td>
			         <td class="grade" grd="${o.powerId}">${o.powerId==5?"皇冠代理":(o.powerId==4?"钻石代理":(o.powerId==3?"铂金代理":(o.powerId==2?"黄金代理":"超级管理员")))}</td>
			         <td class="adminEdit"><a href="${o.id}" class='btn btn-sm btn-warning'>修改</a> <a href="${arr[i]}.id" class='btn btn-sm btn-success charge'>充房卡</a>  <a class='btn btn-sm btn-danger delete' href="${o.id}">删除</a> <a href="${o.id}" class='btn btn-sm btn-primary resetPwd'>密码重置</a></td>
			         
			        </tr>`;
			               if(o.childagent.length!=0){
			                   for(var k=0;k<o.childagent.length;k++){
			                       n++;
			                       var q=n;
			                       var obj=o.childagent[k];
			                       html+=`<tr class="treegrid-${n} treegrid-parent-${l}" mark="${arr[i].id}" mark1="${o.id}" mark2="${obj.id}" >
			                         <td class="uname"><b>${obj.name}</b></td>
			                         <td>${obj.id}</td>
			                         <td>${obj.telephone}</td>
			                         <td>${obj.weixin}</td>
			                         <td>${obj.qq}</td>
			                         <td>${obj.inviteCode}</td>
			                         <td>${obj.uuid}</td>
			                         <td>${obj.createTimeStr}</td>
			                         <td>${obj.lastLoginTimeStr}</td>
			                         <td>${obj.userCounts}</td>
			                         <td>${obj.totalMoney}</td>
			                         <td>${arr[i].actualcard}</td>
			                         </td>
			                         <td>${obj.rebate}</td>
			                         <td>${obj.rootManager==1?'是':'否'}</td>
			                         <td class="grade" grd="${obj.powerId}">${obj.powerId==5?"皇冠代理":(obj.powerId==4?"钻石代理":(obj.powerId==3?"铂金代理":(obj.powerId==2?"黄金代理":"超级管理员")))}</td>
			                         <td class="adminEdit"><a href="${obj.id}" class='btn btn-sm btn-warning'>修改</a> <a href="${arr[i]}.id" class='btn btn-sm btn-success charge'>充房卡</a>  <a class='btn btn-sm btn-danger delete'  href="${obj.id}">删除</a> <a class='btn btn-sm btn-primary resetPwd' href="${obj.id}">密码重置</a></td>
			                         
			                        </tr>`;

			                       if(obj.childagent.length!=0){
			                           for(var x=0;x<obj.childagent.length;x++) {
			                               n++;
			                               var p = obj.childagent[x];
			                               html += `<tr class="treegrid-${n} treegrid-parent-${q}" mark="${arr[i].id}" mark1="${o.id}" mark2="${obj.id}" mark3="${p.id}">
			                         <td class="uname"><b>${p.name}</b></td>
			                         <td>${p.id}</td>
			                         <td>${p.telephone}</td>
			                         <td>${p.weixin}</td>
			                         <td>${p.qq}</td>
			                         <td>${p.inviteCode}</td>
			                         <td>${p.uuid}</td>
			                         <td>${p.createTimeStr}</td>
			                         <td>${p.lastLoginTimeStr}</td>
			                         <td>${p.userCounts}</td>
			                         <td>${p.totalMoney}</td>
			                         <td>${arr[i].actualcard}</td>
			                         </td>
			                         <td>${p.rebate}</td>
			                         <td>${p.rootManager==1?'是':'否'}</td>
			                         <td class="grade" grd="${p.powerId}">${p.powerId==5?"皇冠代理":(p.powerId==4?"钻石代理":(p.powerId==3?"铂金代理":(p.powerId==2?"黄金代理":"超级管理员")))}</td>
			                         <td class="adminEdit"><a href="${p.id}" class='btn btn-sm btn-warning'>修改</a> <a href="${arr[i]}.id" class='btn btn-sm btn-success charge'>充房卡</a> <a class='btn btn-sm btn-danger delete'  href="${p.id}">删除</a>  <a href="${p.id}" class='btn btn-sm btn-primary resetPwd'>密码重置</a></td>
			                         
			                        </tr>`;


			                           } }

			                   }
			               }


			           }
			        }

			    }
			    
			    
			    $('#agent .page b').html(totalNum);
			    $('#agent table.mainTbl tbody').html(html);
			    if(!isAdmin)
			    $('.adminEdit').html("<a class='btn btn-sm btn-warning'>修改</a> <a class='btn btn-sm btn-success charge'>充房卡</a>");
			    $('.tree').treegrid();
			    $('.tree').treegrid('collapseAll');
		        
		        if(totalPages>1){
		        	var options = {
		                    currentPage: pageIndex,
		                    totalPages: totalPages,
		                    bootstrapMajorVersion: 3,
		                    onPageChanged: function(e,oldPage,newPage){
		                    	getAccount(newPage,type,path,isAdmin);
		                    }
		                }

		                $('#agent-pages').bootstrapPaginator(options);
		        }else{
		        	 $('#agent-pages').html("");
		        }
		        
		        $("td").each(function(){
		        	if($(this).html()=='undefined'){
		        		$(this).html("----");
		        	}
		          });
		        
			}
		});
}
