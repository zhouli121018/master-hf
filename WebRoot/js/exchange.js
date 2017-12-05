function getPaylog(pageIndex){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var uuid = $("#inviteCode").val();
	$.ajax({	
		   type: "POST",
		   url: sessionStorage['basePath']+"controller/manager/getExchange?pageNum="+pageIndex+ "&startTime="+startTime+"&endTime="+endTime+"&uuid="+uuid,
		   data: "",
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
			   console.log(returns);
				var arr = returns.exchanges;
				for(var i=0,html='';i<arr.length;i++){
					html+=`<tr>
					<td>${arr[i].uuid}</td>
					<td>${arr[i].nickName}</td>
					<td>${arr[i].amount}</td>
					<td>${arr[i].datestring}</td>
					<td>${arr[i].randomnum}</td>
					<td>${arr[i].status==1?"成功":(arr[i].status==2?"失败":"未兑换")}</td>
					</tr>`;
				}
				$("#tixianlogTbody").html(html);
				
				
				var totalPages = returns.totalNum/10+1;
		        if(totalPages>1){
		        	var options = {
		                    currentPage: pageIndex,
		                    totalPages: totalPages,
		                    bootstrapMajorVersion: 3,
		                    onPageChanged: function(e,oldPage,newPage){
		                    	getPaylog(newPage);
		                    }
		                }

		                $('#note-pages').bootstrapPaginator(options);
		        }else{
		        	 $('#note-pages').html("");
		        }
		        
		        $("td").each(function(){
		        	if($(this).html()=='undefined'){
		        		$(this).html("----");
		        	}
		          });
			}
		});
}
$(function(){
	getPaylog(1);
});