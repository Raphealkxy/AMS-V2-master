$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ams/attlist/mylist',
        datatype: "json",
        colModel: [			
			{ label: '考勤单ID', name: 'attId', index: "att_id", width: 45,hidden:true, key: true },
            { label: '考勤编号', name: 'attCode', index: "att_code", width: 85, key: false },
			{ label: '课程名称', name: 'courseName', width: 75 },
            { label: '学期', name: 'termNum', width: 75,sortable:false },
			{ label: '创建人', name: 'createName', width: 45 },
			{ label: '创建时间', name: 'createTime', width: 80 },
            { label: '更新时间', name: 'updateTime', width: 80 },
			{ label: '出勤状态', name: 'attStatus', width: 80, formatter: function(value, options, row){
			    if(value == 1)
			        return '<span class="label label-info">出席</span>';
                else if (value == 2)
                    return '<span class="label label-warning">未出席</span>';
                else if (value == 3)
                    return '<span class="label label-success">请假</span>';
                else
                    return '<span class="label label-info">未上传</span>';
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
        }
	}
});