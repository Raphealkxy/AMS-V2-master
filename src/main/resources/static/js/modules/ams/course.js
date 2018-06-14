$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ams/course/list',
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
var ztree;

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
		add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.course = {courseId:null,courseCode:null,courseName:null,teacherName :null};
		},
		update: function () {
            var courseId = getSelectedRow();
            if(courseId == null){
                return ;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getCourseInfo(courseId);
		},
		del: function () {
            var courseIds = getSelectedRows();
            if(courseIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "ams/course/delete",
                    contentType: "application/json",
                    data: JSON.stringify(courseIds),
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
		saveOrUpdate: function () {
            var url = vm.course.courseId == null ? "ams/course/save" : "ams/course/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.course),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        teacher: function(){
            layer.open({
                type: 2,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择任课教师",
                area: ['800px', '550px'],
                shade: 0,
                shadeClose: false,
                content:[baseURL+'modules/ams/selectUser.html', 'no'],
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var val = window["layui-layer-iframe" + index].callbackdata();
                    if(val.userId === ''){
                        layer.msg('请填写标记');
                        return;
                    }
                    vm.course.teacherName=val.userName;
                    vm.course.teacherId = val.userId;
                    layer.close(index);
                }
            });
        },
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'courseName': vm.q.courseName,'teacherName':vm.q.teacherName},
                page:page
            }).trigger("reloadGrid");
		},
        getCourseInfo:function(courseId){
            $.get(baseURL + "ams/course/info/"+courseId, function(r){
                vm.course = r.course;
            });
        }

	}
});