/**
 * Created by Administrator on 2017-07-28.
 */
$(function(){
    var arr=[
        {
            cid:1,
            agentnum:'123456',
            money:'鸿运当头',
            regtime:'2017-07-25 08:26:27'
        },
        {
            cid:2,
            agentnum:'456789',
            money:'鸿运',
            regtime:'2017-07-25 08:26:27'
        },
        {
            cid:3,
            agentnum:'789123',
            money:'鸿运当头',
            regtime:'2017-07-25 08:26:27'
        },
        {
            cid:4,
            agentnum:'123456',
            money:'鸿运当头',
            regtime:'2017-07-25 08:26:27'
        }
    ];
    for(var i=0,html='';i<arr.length;i++){
        html+=`<tr>
                    <td>${arr[i].agentnum}</td>
                    <td>${arr[i].money}</td>
                    <td>${arr[i].regtime}</td>
               </tr>`;
    }

    $('#countTbl tbody').html(html);
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
});