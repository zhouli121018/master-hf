/**
 * Created by Administrator on 2017-07-28.
 */
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
    $(' .overTime').val(overTime);
    $(' .beginTime').val(beginTime);
function getbonus(){
	var str=$('#searchForm').serialize();
	var endTime=$("#searchForm [name='endTime']").val();
	$.ajax({
		   url: sessionStorage['basePath']+"controller/manager/getBonusLogs",
		   data: str,
		   success: function data(data){
		       console.log(data);
		       var obj=JSON.parse(data);
		       var totalN=obj.totalNum;
		       var totalM=obj.totalMoney;
		       var totalPages=1;
		       if(totalN%10==0){
		    	   totalPages=parseInt(totalN/10);
		       }else{
		    	   totalPages=parseInt(totalN/10)+1;
		       }
		       $('#totalMoney>b').html(totalM);
		       $('#totalRecords').html(totalN);
		       if(obj.bonusLogs.length>0){
			        var options = {
			                    currentPage: 1,
			                    totalPages: totalPages,
			                    bootstrapMajorVersion: 3,
			                    onPageChanged: function(e,oldPage,newPage){
			                    	var start = (newPage-1)*10;
			                    	var end=start+10;
			                    	if(end>obj.bonusLogs.length){end=obj.bonusLogs.length};
			                    	rend(start,end);
			                    }
			       }

			       $('#detail-pages').bootstrapPaginator(options);
			        var end=10;
			        if(end>obj.bonusLogs.length){end=obj.bonusLogs.length};
			        rend(0,end); 
		    	   function rend(start,end){
		    		   for(var i=start,html='';i<end;i++){
			    		   var o=obj.bonusLogs[i];
			    		   html+=`
			    		   <tr>
			    		   	<td>${o.uuid}</td>
			    		   	<td>${o.nickName}</td>
			    		   	<td>${o.money}</td>
			    		   	<td>${o.dateString}</td>
			    		   	<td>${o.bonus}</td>
			    		   	<td>${'分成收益'}</td>
			    		   </tr>
			    		   `;
			    	   }
			    	   $('#detailTbl tbody').html(html);
		    	   }
		    	   
		       }else{
		    	   $('#detailTbl tbody').html('');
		    	   $('#detail-pages').html("");
		       }
		   }
		
	})
}
//getbonus();

function getDetail(pageIndex,type,path,startTime,endTime,inviteCode,uuid,gameId){
	
		$.ajax({	
			   type: "POST",
			   url: path+"controller/manager/getPaylogs?pageNum="+pageIndex+"&type="+type+"&startTime="+startTime+"&endTime="+endTime+"&inviteCode="+inviteCode+"&uuid="+uuid+"&gameId="+gameId,
			   data: "",
			   success: function data(data){
				   var returns =  eval('(' + data + ')');
					var arr = returns.paylogs;
					var totalMoney = returns.totalMoney;
					$('#totalMoney b').html(totalMoney);
					$('#totalBonus b').html(returns.totalBonus);
					if(returns.totalNum==0){
						alert("没有查到该代理数据！");
						return;
					}
					var totalPages = returns.totalNum/10+1;
					if(arr){
						for(var i=0,html='';i<arr.length;i++){
					        html+=`<tr>
								         <td>${arr[i].uuid}</td>
								         <td>${arr[i].nickName}</td>
								         <td>${arr[i].money}</td>
								         <td>${arr[i].dateString}</td>
								         <td>${arr[i].bonus}</td>
					               </tr>`;
					    }

					    $('#detailTbl tbody').html(html);
					}else{
						$('#detailTbl tbody').html('');
					}
				   
			        $('#totalRecords').html(returns.totalNum);
			        if(totalPages>1){
			        	var options = {
			                    currentPage: pageIndex,
			                    totalPages: totalPages,
			                    bootstrapMajorVersion: 3,
			                    onPageChanged: function(e,oldPage,newPage){
			                    	getDetail(newPage,type,path,startTime,endTime,inviteCode,uuid,gameId);
			                    }
			                }

			                $('#detail-pages').bootstrapPaginator(options);
			        }else{
			        	 $('#detail-pages').html("");
			        }
			        
			        $("td").each(function(){
			        	if($(this).html()=='undefined'){
			        		$(this).html("----");
			        	}
			        });
			        function power(obj){
			        	$(obj).each(function(i,dom){
				        	if($(dom).html()=='5'){
				        		$(dom).html("皇冠代理");
				        	}else if($(dom).html()=='4'){
				        		$(dom).html("钻石代理");
				        	}else if($(dom).html()=='3'){
				        		$(dom).html("铂金代理");
				        	}else if($(dom).html()=='2'){
				        		$(dom).html("黄金代理");
				        	}
				        });
			        }
			        power("td:nth-child(8)");
        
				}
			});
	   
	}