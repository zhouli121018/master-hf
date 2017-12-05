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
        money:88,
        status:true,
        grade:0,
        childagent:[
                {
                    uid:4,
                    aname:"刘慧4",
                    phone:"17607619682",
                    wx:"zhouli",
                    visit:"123456",
                    userId:"456789",
                    money:88,
                    status:true,
                    grade:1,
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
                    money:99,
                    status:false,
                    grade:1,
                    childagent:[
                        {
                            uid:6,
                            aname:"刘慧6",
                            phone:"17607619682",
                            wx:"zhouli",
                            visit:"123456",
                            userId:"456789",
                            vipCount:8,
                            money:88,
                            status:true,
                            grade:2,
                            childagent:[
                                {
                                    uid:12,
                                    aname:"刘慧12",
                                    phone:"17607619682",
                                    wx:"zhouli",
                                    visit:"123456",
                                    userId:"456789",
                                    vipCount:8,
                                    money:88,
                                    status:true,
                                    grade:3,
                                    childagent:''
                                },
                                {
                                    uid:13,
                                    aname:"刘慧13",
                                    phone:"17607619682",
                                    wx:"zhouli",
                                    visit:"123456",
                                    userId:"456789",
                                    vipCount:8,
                                    money:66,
                                    status:true,
                                    grade:3,
                                    childagent:''
                                }
                            ]
                        },
                        {
                            uid:7,
                            aname:"刘慧7",
                            phone:"17607619682",
                            wx:"zhouli",
                            visit:"123456",
                            userId:"456789",
                            vipCount:8,
                            money:66,
                            status:true,
                            grade:2,
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
            money:100,
            status:false,
            grade:0,
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
            money:99,
            status:true,
            grade:0,
            childagent:[
                {
                    uid:8,
                    aname:"刘慧8",
                    phone:"17607619682",
                    wx:"zhouli",
                    visit:"123456",
                    userId:"456789",
                    money:88,
                    status:true,
                    grade:1,
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
                    money:88,
                    status:true,
                    grade:1,
                    childagent:[
                        {
                            uid:10,
                            aname:"刘慧10",
                            phone:"17607619682",
                            wx:"zhouli",
                            visit:"123456",
                            userId:"456789",
                            vipCount:8,
                            money:66,
                            status:false,
                            grade:2,
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
                            money:168,
                            status:true,
                            grade:2,
                            childagent:''
                        }
                    ]
                }
            ]
        }
    ];
    //var json=JSON.stringify(arr);
    //console.log(typeof json,json);
    var n=0;
    for(var i=0,html='';i<arr.length;i++){
          n++;
        var m=n;
            html+=`<tr class="treegrid-${n}" mark="${arr[i].uid}">
         <td class="uname"><b> ${arr[i].aname}</b></td>
         <td>${arr[i].phone}</td>
         <td>${arr[i].wx}</td>
         <td>${arr[i].visit}</td>
         <td>${arr[i].userId}</td>
         <td>${arr[i].vipCount}</td>
         <td>${arr[i].money.toFixed(2)}</td>
         <td>${arr[i].status?'正常':'禁用'}
         </td>
         <td class="grade" grd="${arr[i].grade}">皇冠代理</td>
         <td><a href="${arr[i]}.uid">修改</a> &nbsp; <a class="delete" href="${arr[i]}.uid">删除</a> &nbsp; <a href="${arr[i].uid}" class="resetPwd">密码重置</a></td>
        </tr>`;
        if(arr[i].childagent.length!=0){
           for(var j=0;j<arr[i].childagent.length;j++){
               n++;
               var l=n;
               var o=arr[i].childagent[j];
               html+=`<tr class="treegrid-${n} treegrid-parent-${m}" mark="${arr[i].uid}" mark1="${o.uid}">
         <td class="uname"> <b>${o.aname}</b></td>
         <td>${o.phone}</td>
         <td>${o.wx}</td>
         <td>${o.visit}</td>
         <td>${o.userId}</td>
         <td>${o.vipCount}</td>
         <td>${o.money.toFixed(2)}</td>
         <td>${o.status?'正常':'禁用'}</td>
         <td class="grade" grd="${o.grade}">钻石代理</td>
         <td><a href="${o.uid}">修改</a> &nbsp; <a class="delete" href="${o.uid}">删除</a> &nbsp; <a href="${o.uid}" class="resetPwd">密码重置</a></td>
        </tr>`;
               if(o.childagent.length!=0){
                   for(var k=0;k<o.childagent.length;k++){
                       n++;
                       var q=n;
                       var obj=o.childagent[k];
                       html+=`<tr class="treegrid-${n} treegrid-parent-${l}" mark="${arr[i].uid}" mark1="${o.uid}" mark2="${obj.uid}" >
                         <td class="uname"><b> ${obj.aname}</b></td>
                         <td>${obj.phone}</td>
                         <td>${obj.wx}</td>
                         <td>${obj.visit}</td>
                         <td>${obj.userId}</td>
                         <td>${obj.vipCount}</td>
                         <td>${obj.money.toFixed(2)}</td>
                         <td>${obj.status?'正常':'禁用'}
                         </td>
                         <td class="grade" grd="${obj.grade}">铂金代理</td>
                         <td><a href="${obj.uid}">修改</a> &nbsp; <a class="delete" href="${obj.uid}">删除</a> &nbsp; <a class="resetPwd" href="${obj.uid}">密码重置</a></td>
                        </tr>`;

                       if(obj.childagent.length!=0){
                           for(var p=0;k<obj.childagent.length;k++) {
                               n++;
                               var p = obj.childagent[k];
                               html += `<tr class="treegrid-${n} treegrid-parent-${q}" mark="${arr[i].uid}" mark1="${o.uid}" mark2="${obj.uid}" mark3="${p.uid}">
                         <td class="uname"><b> ${p.aname}</b></td>
                         <td>${p.phone}</td>
                         <td>${p.wx}</td>
                         <td>${p.visit}</td>
                         <td>${p.userId}</td>
                         <td>${p.vipCount}</td>
                         <td>${p.money.toFixed(2)}</td>
                         <td>${p.status ? '正常' : '禁用'}
                         </td>
                         <td class="grade" grd="${p.grade}">黄金代理</td>
                         <td><a href="${p.uid}">修改</a> &nbsp; <a class="delete" href="${p.uid}">删除</a> &nbsp; <a href="${p.uid}" class="resetPwd">密码重置</a></td>
                        </tr>`;


                           } }

                   }
               }


           }
        }

    }

    $('#agent .page b').html(arr.length);
    $('#agent table.mainTbl tbody').html(html);

    $('.tree').treegrid();
    $('.tree').treegrid('collapseAll');
    //$.ajax({
    //    url:'2',
    //    data:{},
    //    success:function(data){
    //        console.log(data);
    //    }
    //})
});
