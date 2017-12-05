function getAccount(pageIndex,type,path,isAdmin){
	$.ajax({
		type: "POST",
		url: path+"controller/manager/getBetMountSum?searchType="+type,
	    data: "",
	    success:function(data){
	    	var returns =  eval('(' + data + ')');
	    	 var totalcount=returns.betMountSum;
			 $('#total b').html(totalcount/10);
	    }
	})
	$.ajax({	
		   type: "POST",
		   url: path+"controller/manager/getManagersStats?pageNum="+pageIndex+"&searchType="+type,
		   data: "",
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
				var arr = returns.managers;
				//console.dir(returns);
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
			         <td>${arr[i].inviteCode}</td>
			         <td>${arr[i].userId}</td>
			         <td>${arr[i].totalcards}</td>
			         <td>${arr[i].actualcard/10}</td>
			         <td class="grade" grd="${arr[i].powerId}">${arr[i].powerId==5?"皇冠代理":(arr[i].powerId==4?"钻石代理":(arr[i].powerId==3?"铂金代理":(arr[i].powerId==2?"黄金代理":"超级管理员")))}</td>
			        
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
			         <td>${o.inviteCode}</td>
			         <td>${o.userId}</td>
			         <td>${o.totalcards}</td>
			         <td>${o.actualcard/10}</td>
			         <td class="grade" grd="${o.powerId}">${o.powerId==5?"皇冠代理":(o.powerId==4?"钻石代理":(o.powerId==3?"铂金代理":(o.powerId==2?"黄金代理":"超级管理员")))}</td>
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
			                         <td>${obj.inviteCode}</td>
			                         <td>${obj.userId}</td>
			                         <td>${obj.totalcards}</td>
			                         <td>${obj.actualcard/10}</td>
			                         <td class="grade" grd="${obj.powerId}">${obj.powerId==5?"皇冠代理":(obj.powerId==4?"钻石代理":(obj.powerId==3?"铂金代理":(obj.powerId==2?"黄金代理":"超级管理员")))}</td>
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
			                         <td>${p.inviteCode}</td>
			                         <td>${p.userId}</td>
			                         <td>${p.totalcards}</td>
			                         <td>${p.actualcard/10}</td>
			                         <td class="grade" grd="${p.powerId}">${p.powerId==5?"皇冠代理":(p.powerId==4?"钻石代理":(p.powerId==3?"铂金代理":(p.powerId==2?"黄金代理":"超级管理员")))}</td>
			                        </tr>`;


			                           } }

			                   }
			               }


			           }
			        }

			    }
			    $('#stats .page b').html(totalNum);
			    $('#stats table.mainTbl tbody').html(html);
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