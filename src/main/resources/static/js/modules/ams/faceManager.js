$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ams/student/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45, hidden:true,key: true},
            { label: '学号', name: 'loginNum', index: "login_num", width: 45 },
			{ label: '用户名', name: 'userName', width: 75 },
            { label: '所属班级', name: 'deptName', width: 75,sortable:false },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">禁用</span>' : 
					'<span class="label label-success">正常</span>';
			}},
            { label: '是否已经注册', name: 'register', width: 80, formatter: function(value, options, row){
                return value === false ?
                    '<span class="label label-danger">未注册</span>' :
                    '<span class="label label-success">已注册</span>';
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
			userName: null,
			loginNum: null
		},
		showList: true,
		title:null,
		roleList:{},
        addUpadte : 0,
		user:{
			status:1,
			deptId:null,
            deptName:null,
			roleIdList:[],
            loginNum:null,
            userName:null,
            register:null
		}
	},
	methods: {
        query: function () {
            vm.reload();
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'userName': vm.q.userName, 'loginNum': vm.q.loginNum},
                page: page
            }).trigger("reloadGrid");
        },
        addUser: function () {
            var row = getSelectedRowData();
            if (row == null) return null;
            vm.showList = false;
            vm.addUpadte = 1;
            vm.user.userName = row.userName;
            vm.user.deptName = row.deptName;
            vm.user.loginNum = row.loginNum;
            vm.user.userId = row.userId;
        },
        updateUser: function () {
            var row = getSelectedRowData();
            if (row == null) return null;
            vm.showList = false;
            vm.addUpadte = 2;
            vm.user.userName = row.userName;
            vm.user.deptName = row.deptName;
            vm.user.loginNum = row.loginNum;
            vm.user.userId = row.userId;
        },
        deleteUser: function () {
            var row = getSelectedRowData();
            if (row == null) return null;
            vm.user.userId = row.userId;
            confirm("确定删除该学生在云端的所有人脸记录？", function(){
                $.ajax({
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: baseURL+'/ams/facetoken/delete?token='+token+'&userId='+vm.user.userId ,//url
                    success: function (r) {
                        if (r.code == 0) {
                            alert("删除成功");
                            vm.reload();
                        }else {
                            alert(r.msg)
                        }
                    },
                    error : function() {
                        alert("上传失败，请检查网络连接！");
                    }
                });
            });
        },
        upload: function () {
            var f = document.getElementById("picFile").value;
            var flag = vm.checkfile(f);
            var str = "";
            if(!flag) alert("图片有误");
            if(vm.addUpadte == 0) return;
            else if (vm.addUpadte ===1 ){
                var newUrl = baseURL+'/ams/facetoken/upload?token='+token+'&userId='+vm.user.userId;
                str = "确定进行注册人脸？重复操作效果叠加"
            }else if(vm.addUpadte ===2){
                var newUrl = baseURL+'/ams/facetoken/update?token='+token+'&userId='+vm.user.userId;
                str = "确定进行更新人脸？将覆盖历史人脸"
            }
            confirm(str, function(){
                $.ajax({
                    type: "POST",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    url: newUrl ,//url
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

