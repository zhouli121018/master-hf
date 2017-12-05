/**
 * Created by Administrator on 2017-07-28.
 */

	
	
	
	

	
	function getProxyMoney(path,type){
		var inviteCode = $("#inviteCode").val();
		if(typeof(inviteCode)=="undefined")
			inviteCode="";
		$.ajax({	
			   type: "POST",
			   url: path+"controller/manager/getPaylogsSum?type="+type+"&inviteCode="+inviteCode,
			   data: "",
			   success: function data(data){
				   var returns =  eval('(' + data + ')');
					var total = returns.total;
					var mineone = returns.mineone;
					var minetwo = returns.minetwo;
					var minelotone = returns.minelotone;
					var minelottwo = returns.minelottwo;
					$("#sumtotal").html(total);
					$("#sumone").html(mineone);
					$("#sumtwo").html(minetwo);
					$("#sumlotone").html(minelotone);
					$("#sumlottwo").html(minelottwo);
				}
			});
	}
	
    
    $('.divide-count ul.nav li a').click(function(e){
        e.preventDefault();
        $(this).parent().addClass('active').siblings().removeClass('active');
    });

    //$.ajax({
    //    url:'2',
    //    data:{},
    //    success:function(data){
    //        console.log(data);
    //    }
    //})
