/**
 * Created by Administrator on 2017-07-28.
 */
function getDetail(pageIndex,type,path,serialNum){
	
		$.ajax({	
			   type: "POST",
			   url: path+"controller/manager/handleLotResult?pageNum="+pageIndex+"&type="+type+"&serialNum="+serialNum,
			   data: "",
			   success: function data(data){
				   var returns =  eval('(' + data + ')');
//				   console.log(returns.totalNum);
//				   console.log(data);
				    var totalcount=returns.mountSum;
					var arr = returns.paylogLots;
					var totalPages = returns.totalNum/10+1;
					if(arr){
						for(var i=0,html='';i<arr.length;i++){
					        html+=`<tr>
								         <td>${arr[i].uuid}</td>
								         <td>${arr[i].nickName}</td>
								         <td>${arr[i].serialnum}</td>
								         <td>${arr[i].betmount}</td>
								         <td>${arr[i].dateString}</td>
								         <td>${arr[i].bettype}</td>
								         <td>${arr[i].betresult}</td>
								         <td>${arr[i].lotResult}</td>
								         <td>${arr[i].facevalue}</td>
								         <td>${arr[i].cardcount}</td>
								         
								         <td>${arr[i].invitecode}</td>
								         <td>${arr[i].powerid}</td>
								         <td>${arr[i].invitecode1}</td>
								         <td>${arr[i].powerid1}</td>
					               </tr>`;
					    }

					    $('#cathecticTbl tbody').html(html);
					}
			        $('#totalRecords').html(returns.totalNum);
			       
				    $('#total b').html(totalcount);
			        if(totalPages>1){
			        	var options = {
			                    currentPage: pageIndex,
			                    totalPages: totalPages,
			                    bootstrapMajorVersion: 3,
			                    onPageChanged: function(e,oldPage,newPage){
			                    	getDetail(newPage,type,path,serialNum);
			                    }
			                }

			                $('#cathectic-pages').bootstrapPaginator(options);
			        }else{
			        	 $('#cathectic-pages').html("");
			        }

			        $($("#cathecticTbl td")).each(function(i,dom){
			        	if($(dom).html()=='undefined'){
			        		$(dom).html("----");
			        	}
			        	
			        })
			        
			        	
			        
			        
			       
			        $.each($("#cathecticTbl td:nth-child(6)"),function(i,dom){
			        	if($(this).html()=='undefined'){
			        		$(this).html("----");
			        	}else if($(this).html()=='4'){
			        		$(this).html("双");
			        	}else if($(this).html()=='3'){
			        		$(this).html("单");
			        	}else if($(this).html()=='2'){
			        		$(this).html("小");
			        	}else if($(this).html()=="1"){
			        		$(this).html("大");
			        	}
			        });
			        
			        $.each($("#cathecticTbl td:nth-child(7)"),function(i,dom){
			        	if($(this).html()=='undefined'){
			        		$(this).html("----");
			        	}else if($(this).html()=='0'){
			        		$(this).html("未中奖");
			        	}else if($(this).html()=='1'){
			        		$(this).html("中奖");
			        	}else if($(this).html()=='-1'){
			        		$(this).html("未开奖");
			        	}
			        });

			        $.each($("#cathecticTbl td:nth-child(12)"),function(i,dom){
			        	if($(this).html()=='undefined'){
			        		$(this).html("----");
			        	}else if($(this).html()=='5'){
			        		$(this).html("皇冠代理");
			        	}else if($(this).html()=='4'){
			        		$(this).html("钻石代理");
			        	}else if($(this).html()=='3'){
			        		$(this).html("铂金代理");
			        	}else if($(this).html()=='2'){
			        		$(this).html("黄金代理");
			        	}else if($(this).html()=="1"){
			        		$(this).html("超级管理员");
			        	}
			        });

			        $.each($("#cathecticTbl td:nth-child(14)"),function(i,dom){
			        	if($(this).html()=='undefined'){
			        		$(this).html("----");
			        	}else if($(this).html()=='5'){
			        		$(this).html("皇冠代理");
			        	}else if($(this).html()=='4'){
			        		$(this).html("钻石代理");
			        	}else if($(this).html()=='3'){
			        		$(this).html("铂金代理");
			        	}else if($(this).html()=='2'){
			        		$(this).html("黄金代理");
			        	}else if($(this).html()=="1"){
			        		$(this).html("超级管理员");
			        	}
			        });
//
				}
			});
	   
	}