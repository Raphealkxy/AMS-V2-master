$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ams/course/select',
        datatype: "json",
        colModel: [
            { label: '课程ID', name: 'courseId', index: "course_id", width: 45,hidden:true, key: true },
            { label: '课程名称', name: 'courseName', index: "course_name", width: 45, key: false },
            { label: '课程编号', name: 'courseCode', width: 75 },
            { label: '任课教师', name: 'teacherName', width: 75,sortable:false }
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

var vm = new Vue({
	el:'#rrapp',
    data:{
        q:{
            courseName: null,
            teacherName:null
        },
        showList: true,
        title:null,
        roleList:{},
        course:{
            courseId:null,
            courseCode:null,
            courseName:null,
            teacherName:null
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
                postData:{'courseName': vm.q.courseName,'teacherName':vm.q.teacherName},
                page:page
            }).trigger("reloadGrid");
		},
        add:function(){
            var courseId = getSelectedRow();
            if(courseId == null){
                return ;
            }
            confirm('确定创建考勤单？', function(){
                $.get(baseURL + "ams/attlist/add/"+courseId, function(r){
                    if(r.code == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                });
            });
        }
	}
});

var callbackdata = function () {
    var row = getSelectedRowData();
	var json = {
	    "courseId":row.courseId,
        "courseName":row.courseName
    }
	return json;
};
