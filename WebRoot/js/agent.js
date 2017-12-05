/**
 * Created by Administrator on 2017-07-28.
 */
$(function(){
    var arr=[
        {
        uid:1,
        aname:"刘慧1",
        phone:"17607619682",
        wx:"zhouli",
        visit:"123456",
        userId:"456789",
        vipCount:8,
        childagent:[
                {
                    uid:4,
                    aname:"刘慧4",
                    phone:"17607619682",
                    wx:"zhouli",
                    visit:"123456",
                    userId:"456789",
                    vipCount:8,childagent:''
                },
                {
                    uid:5,
                    aname:"刘慧5",
                    phone:"17607619682",
                    wx:"zhouli",
                    visit:"123456",
                    userId:"456789",
                    vipCount:8,
                    childagent:[
                        {
                            uid:6,
                            aname:"刘慧6",
                            phone:"17607619682",
                            wx:"zhouli",
                            visit:"123456",
                            userId:"456789",
                            vipCount:8,
                            childagent:''
                        },
                        {
                            uid:7,
                            aname:"刘慧7",
                            phone:"17607619682",
                            wx:"zhouli",
                            visit:"123456",
                            userId:"456789",
                            vipCount:8,
                            childagent:''
                        }
                    ]
                }
            ]
        },
        {
            uid:2,
            aname:"刘慧2",
            phone:"17607619682",
            wx:"zhouli",
            visit:"123456",
            userId:"456789",
            vipCount:8,
            childagent:''
        },
        {
            uid:3,
            aname:"刘慧3",
            phone:"17607619682",
            wx:"zhouli",
            visit:"123456",
            userId:"456789",
            vipCount:8,
            childagent:[
                {
                    uid:8,
                    aname:"刘慧8",
                    phone:"17607619682",
                    wx:"zhouli",
                    visit:"123456",
                    userId:"456789",
                    vipCount:8,childagent:''
                },
                {
                    uid:9,
                    aname:"刘慧9",
                    phone:"17607619682",
                    wx:"zhouli",
                    visit:"123456",
                    userId:"456789",
                    vipCount:8,
                    childagent:[
                        {
                            uid:10,
                            aname:"刘慧10",
                            phone:"17607619682",
                            wx:"zhouli",
                            visit:"123456",
                            userId:"456789",
                            vipCount:8,
                            childagent:''
                        },
                        {
                            uid:11,
                            aname:"刘慧11",
                            phone:"17607619682",
                            wx:"zhouli",
                            visit:"123456",
                            userId:"456789",
                            vipCount:8,
                            childagent:''
                        }
                    ]
                }
            ]
        }
    ];
    for(var i=0,html='';i<arr.length;i++){
        if(arr[i].childagent.length==0){
        html+=`<tr>
         <td><a href="${arr[i].uid}"><i>+</i>${arr[i].aname}</a></td>
         <td>${arr[i].phone}</td>
         <td>${arr[i].wx}</td>
         <td>${arr[i].visit}</td>
         <td>${arr[i].userId}</td>
         <td>${arr[i].vipCount}</td>
         <td> <button class="btn btn-success">启用</button>
              <button class="btn btn-danger">禁用</button>
         </td>
         <td><a href="#">修改</a></td>
        </tr>`;
        }else{
            html+=`<tr>
         <td><a href="${arr[i].uid}"><span>+</span> ${arr[i].aname}</a></td>
         <td>${arr[i].phone}</td>
         <td>${arr[i].wx}</td>
         <td>${arr[i].visit}</td>
         <td>${arr[i].userId}</td>
         <td>${arr[i].vipCount}</td>
         <td> <button class="btn btn-success">启用</button>
              <button class="btn btn-danger">禁用</button>
         </td>
         <td><a href="#">修改</a></td>
        </tr>`;
        }

    }
    $('#agent table.mainTbl tbody').html(html);


    $('#agent table.mainTbl').on('click','td:first-child a',function(e){
        e.preventDefault();
        var uid=$(this).attr('href');
        for(var j=0;j<arr.length;j++){
            if(arr[j].uid==uid){
                if($(this).find('span').html()=="+"&&arr[j].childagent.length!=0){
                    for(var i=0,html1='';i<arr[j].childagent.length;i++){
                        var o=arr[j].childagent[i];
                        if(o.childagent.length==0){
                            html1+=`<tr class="child1" mark="${uid}" like="${uid}">
                 <td><a href="${o.uid}"><i>+</i>${o.aname}</a></td>
                 <td>${o.phone}</td>
                 <td>${o.wx}</td>
                 <td>${o.visit}</td>
                 <td>${o.userId}</td>
                 <td>${o.vipCount}</td>
                 <td> <button class="btn btn-success">启用</button>
                      <button class="btn btn-danger">禁用</button>
                 </td>
                 <td><a href="#">修改</a></td>
                </tr>`;
                        }else{
                            html1+=`<tr class="child1" mark="${uid}" like="${uid}">
                 <td><a href="${o.uid}"><span>+</span> ${o.aname}</a></td>
                 <td>${o.phone}</td>
                 <td>${o.wx}</td>
                 <td>${o.visit}</td>
                 <td>${o.userId}</td>
                 <td>${o.vipCount}</td>
                 <td> <button class="btn btn-success">启用</button>
                      <button class="btn btn-danger">禁用</button>
                 </td>
                 <td><a href="#">修改</a></td>
                </tr>`;
                        }

                    }
                    $(this).find('span').html("-");
                    $(this).parents('tr').siblings('.child1[mark='+uid+']').remove();
                    $(this).parents('tr').after(html1);
                    $(this).parents('tr').siblings('.child1[mark='+uid+']').css('display','table-row');

                }else if($(this).find('span').html()=="-"){
                    $(this).parents('tr').siblings('[like='+uid+']').css('display','none');
                    $(this).find('span').html("+");
                }
            }
            for(var k=0;k<arr[j].childagent.length;k++){
                var count=arr[j].uid;
                var obj=arr[j].childagent[k];
                if(obj.uid==uid ){
                    if($(this).find('span').html()=="+"&&arr[j].childagent.length!=0){
                        for(var i=0,html1='';i<obj.childagent.length;i++){
                            var o=obj.childagent[i];
                            if(o.childagent.length==0){
                                html1+=`<tr class="child2" mark="${uid}" like="${count}">
                 <td><a href="${o.uid}"><i>+</i>${o.aname}</a></td>
                 <td>${o.phone}</td>
                 <td>${o.wx}</td>
                 <td>${o.visit}</td>
                 <td>${o.userId}</td>
                 <td>${o.vipCount}</td>
                 <td> <button class="btn btn-success">启用</button>
                      <button class="btn btn-danger">禁用</button>
                 </td>
                 <td><a href="#">修改</a></td>
                </tr>`;
                            }else{
                                html1+=`<tr class="child2" mark="${uid}" like="${count}">
                 <td><a href="${o.uid}"><span>+</span> ${o.aname}</a></td>
                 <td>${o.phone}</td>
                 <td>${o.wx}</td>
                 <td>${o.visit}</td>
                 <td>${o.userId}</td>
                 <td>${o.vipCount}</td>
                 <td> <button class="btn btn-success">启用</button>
                      <button class="btn btn-danger">禁用</button>
                 </td>
                 <td><a href="#">修改</a></td>
                </tr>`;
                            }

                        }
                        $(this).find('span').html("-");
                        $(this).parents('tr').siblings('.child2[mark='+uid+']').remove();
                        $(this).parents('tr').after(html1);
                        $(this).parents('tr').siblings('.child2[mark='+uid+']').css('display','table-row');

                    }else if($(this).find('span').html()=="-"){
                        $(this).parents('tr').siblings('.child2[mark='+uid+']').css('display','none');
                        $(this).find('span').html("+");
                    }

                }
            }
        }



    });

    //$.ajax({
    //    url:'2',
    //    data:{},
    //    success:function(data){
    //        console.log(data);
    //    }
    //})
});