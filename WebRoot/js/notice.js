/**
 * Created by Administrator on 2017-08-04.
 */

$("#notice button.add-notice").click(function(){
    $("#add-notice").fadeIn();
        var html = `
         <div class="form-group">
                    <label >公告类型</label>
                    <select id="noticetype">
                    <option value="0" select="true">代理公告</option>
                    <option value="1">消息公告</option>
                    <option value="2">全服图片公告</option>
                    <option value="3">给所有代理公告</option>
                    </select>
         </div>`;
         html += `
         <div class="form-group" id="normalContent">
                    <label >公告内容</label>
                    <input type="text" id="content" class="form-control">

         </div>`;
         html += `
         <div class="form-group" id="normalInviteCode">
                    <label >代理邀请码</label>
                    <input type="text" id="inviteCode" class="form-control">

         </div>`;
         html += `
         <div class="form-group" id="normalFile" style="display:none;">
                    <label >公告内容</label>
                    <input type="file" id="filecontent" name="filecontent" onchange="ajaxFileUpload()" >
                    

         </div>`;

    $("#add-notice form>div").html(html);
    $('#noticetype').change(function () {
        if($(this).val()=='2'){
        	$("#normalFile").show();
        	$("#normalContent").hide();
        	$("#normalInviteCode").hide();
        }else{
        	if($(this).val()=='0'){
        		$("#normalInviteCode").show();
        	}else{
        		$("#normalInviteCode").hide();
        	}
        	$("#normalFile").hide();
        	$("#normalContent").show();
        }
    })
});
$("#add-notice").on('click','form>input',function(){
    if($(this).hasClass('sure')){
        $("#add-notice").fadeOut();


    }else if($(this).hasClass('cancel')){
        $("#add-notice").fadeOut();
    }
});



