$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [			
			{ label: '用户ID', name: 'userId', index: "user_id", width: 45,hidden:true, key: true },
            { label: '工号', name: 'loginNum', index: "loginNum", width: 45, key: false },
			{ label: '用户名', name: 'userName', width: 75 },
            { label: '所属部门', name: 'deptName', width: 75,sortable:false }
        ],
		viewrecords: true,
        height: 300,
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			userName: null,
            loginNum:null
		},
		showList: true,
		title:null,
		roleList:{},
		user:{
			status:1,
			deptId:null,
            loginNum:null,
            deptName:null,
			roleIdList:[]
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'userName': vm.q.userName,'loginNum':vm.q.loginNum},
                page:page
            }).trigger("reloadGrid");
		}
	}
});

var callbackdata = function () {
    var row = getSelectedRowData();
	var json = {
	    "userName":row.userName,
        "userId":row.userId
    }
	return json;
};
