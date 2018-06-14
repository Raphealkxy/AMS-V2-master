$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ams/attlist/list',
        datatype: "json",
        colModel: [			
			{ label: '考勤单ID', name: 'attId', index: "att_id", width: 45,hidden:true, key: true },
            { label: '考勤编号', name: 'attCode', index: "att_code", width: 85, key: false },
			{ label: '课程名称', name: 'courseName', width: 75 },
            { label: '学期', name: 'termNum', width: 75,sortable:false },
			{ label: '创建人', name: 'createName', width: 45 },
			{ label: '创建时间', name: 'createTime', width: 80 },
            { label: '更新时间', name: 'updateTime', width: 80 },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
			    if(value == 1)
			        return '<span class="label label-info">上传成功</span>';
                else if (value == 2)
                    return '<span class="label label-warning">正在识别</span>';
                else if (value == 3)
                    return '<span class="label label-success">识别完成</span>';
                else if (value == 4)
                    return '<span class="label label-danger">识别失败</span>';
                else
                    return '<span class="label label-info">未识别</span>';
			}}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
            createName: null,
            courseName:null,
            loginNum:null,
            userName:null
		},
		showList: true,
		title:null,
		attlist:{
            attId : null,
            attCode:null,
            courseId:null,
            courseName:null,
            termNum:null,
            createId:null,
            updateId:null,
            createName:null,
            createTime:null,
            updateTime:null,
            status:null
		}
	},
	methods: {
		query:function(){
            vm.reload();
		},
        reload:function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'createName': vm.q.createName,'courseName':vm.q.courseName},
                page:page
            }).trigger("reloadGrid");
        },
        add:function(){
            layer.open({
                type: 2,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择课程",
                area: ['800px', '550px'],
                shade: 0,
                shadeClose: false,
                content:[baseURL+'modules/ams/selectCourse.html', 'no'],
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.close(index);
                    vm.reload();
                }
            });
            vm.reload();
		},
		update:function () {
            alert("update");
        },
		del:function(){
            var attlistIds = getSelectedRows();
            if(attlistIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ams/attlist/delete",
                    contentType: "application/json",
                    data: JSON.stringify(attlistIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
		},
		detall:function () {
            var attlistId = getSelectedRow();
            if(attlistId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "考勤单详情";
            vm.getAttlistInfo(attlistId);
            vm.getDetallInfo(attlistId);
        },
        getAttlistInfo:function (attlistId) {
            $.get(baseURL + "ams/attlist/info/"+attlistId, function(r){
                vm.attlist = r.attlist;
            });
        },
        getDetallInfo:function (attlistId) {
            $("#detallGrid").jqGrid({
                url: baseURL + 'ams/attdetall/list',
                datatype: "json",
                colModel: [
                    { label: '考勤项ID', name: 'attdetallId', index: "attdetall_id", width: 45,hidden:true, key: true },
                    { label: '学号', name: 'loginNum', index: "loginNum", width: 45, key: false },
                    { label: '姓名', name: 'userName', width: 75 },
                    { label: '出勤情况', name: 'status', width: 80, formatter: function(value, options, row){
                        if(value == 1)
                            return '<span class="label label-success">出席</span>';
                        else if (value == 2)
                            return '<span class="label label-danger">缺勤</span>';
                        else if (value == 3)
                            return '<span class="label label-warning">请假</span>';
                        else
                            return '<span class="label label-danger">异常</span>';
                    }}
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList : [10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:false,
                multiselect: true,
                pager: "#detallGridPager",
                jsonReader : {
                    root: "page.list",
                    page: "page.currPage",
                    total: "page.totalPage",
                    records: "page.totalCount"
                },
                prmNames : {
                    page:"page",
                    rows:"limit",
                    order: "order"
                },
                postData:{
                    attlistId:attlistId
                },
                gridComplete:function(){
                    //隐藏grid底部滚动条
                    $("#detallGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
            $("#detallGrid").jqGrid("clearGridData");
            $("#detallGrid").jqGrid('setGridParam',{
                postData:{'attlistId':attlistId}
            }).trigger("reloadGrid");
        },
        detallReload:function () {
            var page = $("#detallGrid").jqGrid('getGridParam','page');
            $("#detallGrid").jqGrid('setGridParam',{
                postData:{'userName': vm.q.userName,'loginNum':vm.q.loginNum},
                page:page
            }).trigger("reloadGrid");
        },
        att:function () {
            var attdetallIds = getSelectedAttdetallRows();
            if(attdetallIds == null){
                return ;
            }

            confirm('确定要修改为出勤？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ams/attdetall/att",
                    contentType: "application/json",
                    data: JSON.stringify(attdetallIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.detallReload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        unAtt:function () {
            var attdetallIds = getSelectedAttdetallRows();
            if(attdetallIds == null){
                return ;
            }

            confirm('确定要修改为未出勤？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ams/attdetall/unatt",
                    contentType: "application/json",
                    data: JSON.stringify(attdetallIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.detallReload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        leave:function () {
            var attdetallIds = getSelectedAttdetallRows();
            if(attdetallIds == null){
                return ;
            }

            confirm('确定要修改为未出勤？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ams/attdetall/leave",
                    contentType: "application/json",
                    data: JSON.stringify(attdetallIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(){
                                vm.detallReload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        upload:function(){
            var f = document.getElementById("picFile").value;
            var flag = vm.checkfile(f);
            var str = "确定上传考勤照片？";
            if(!flag) {alert("图片有误"); return;}
            confirm(str, function(){
                $.ajax({
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: baseURL+'/ams/attdetall/upload?token='+token+'&attId='+vm.attlist.attId,//url
                    cache: false,
                    processData: false,
                    contentType :false,
                    data: new FormData($("#uploadForom")[0]),
                    success: function (r) {
                        if (r.code == 0) {
                            alert("操作成功");
                            vm.reload();
                        }else {
                            alert(r.msg)
                        }
                    },
                    error : function() {
                        alert("操作失败，请检查网络连接！");
                    }
                });
            });
        },
        checkfile: function (f) {
            if (f == "") {
                alert("请上传图片");
                return false;
            } else {
                if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(f)) {
                    alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
                    return false;
                }
                return true;
            }
        }
	}
});

//选择多条记录
function getSelectedAttdetallRows() {
    var grid = $("#detallGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }

    return grid.getGridParam("selarrrow");
}