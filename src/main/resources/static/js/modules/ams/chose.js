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

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            courseName: null,
            teacherName: null,
            userName: null,
            loginNum: null
        },
        showList: true,
        showBinded:false,
        showUnBinded:false,
        title: null,
        listTitle: null,
        roleList: {},
        course: {
            courseId: null,
            courseCode: null,
            courseName: null,
            teacherName: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getCourseInfo:function(courseId){
            $.get(baseURL + "ams/course/info/"+courseId, function(r){
                vm.course = r.course;
            });
        },
        reload: function () {
			vm.showList = true; //打开选课管理，关闭【查看该课程学生】
            vm.showBinded = false;
            vm.showUnBinded= false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'courseName': vm.q.courseName,'teacherName':vm.q.teacherName},
                page:page
            }).trigger("reloadGrid");
		},
		// 查看该课程学生
        showCourseStu :function () {
            var courseId = getSelectedRow();
            if(courseId == null){
                return ;
            }
            vm.showList = false; //关闭选课管理，打开【查看该课程学生】
            vm.showBinded = true;
            vm.title = "课程信息";
            vm.listTitle ="已绑定学生";
            vm.getCourseInfo(courseId);
            vm.getBindedStudent(courseId);//已选学生信息
        },
        //已绑定学生信息
        getBindedStudent:function(courseId) {
            $("#bindedGrid").jqGrid({
                url: baseURL + 'ams/chose/binded',
                datatype: "json",
                colModel: [
                    { label: '学生ID', name: 'userId', index: "user_id", width: 45,hidden:true, key: true },
                    { label: '学号', name: 'loginNum', index: "loginNum", width: 45, key: false },
                    { label: '姓名', name: 'userName', width: 75 },
                    { label: '班级', name: 'deptName', width: 75,sortable:false }
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList : [10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:false,
                multiselect: true,
                pager: "#bindedGridPager",
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
                    courseId:courseId
                },
                gridComplete:function(){
                    //隐藏grid底部滚动条
                    $("#bindedGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
            $("#bindedGrid").jqGrid("clearGridData");
            $("#bindedGrid").jqGrid('setGridParam',{
                postData:{'courseId':courseId}
            }).trigger("reloadGrid");
        },
        //已绑定学生查询按钮
        unbindedReload:function () {
            var page = $("#bindedGrid").jqGrid('getGridParam','page');
            $("#bindedGrid").jqGrid('setGridParam',{
                postData:{'userName': vm.q.userName,'loginNum':vm.q.loginNum},
                page:page
            }).trigger("reloadGrid");
        },
        //解绑
        unbinding:function(){
            var userIds = getSelectedUnbandRows();
            if(userIds == null){
                return ;
            }
            var url ="ams/chose/unbind";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                data: {
                    courseId:vm.course.courseId,
                    userIds:userIds
                },
                traditional: true,
                dataType:"json",
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.unbindedReload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },

        ////////////////绑定///////

        showCourseUnbindStu:function () {
            var courseId = getSelectedRow();
            if(courseId == null){
                return ;
            }
            vm.showList = false; //关闭选课管理，打开【查看该课程学生】
            vm.showBinded = false;
            vm.showUnBinded = true;
            vm.title = "课程信息";
            vm.listTitle ="未绑定学生";
            vm.getCourseInfo(courseId);
            vm.getUnBindedStudent(courseId);//已选学生信息
        },
        getUnBindedStudent:function(courseId){
            $("#unbindedGrid").jqGrid({
                url: baseURL + 'ams/chose/unbinded',
                datatype: "json",
                colModel: [
                    { label: '学生ID', name: 'userId', index: "user_id", width: 45,hidden:true, key: true },
                    { label: '学号', name: 'loginNum', index: "loginNum", width: 45, key: false },
                    { label: '姓名', name: 'userName', width: 75 },
                    { label: '班级', name: 'deptName', width: 75,sortable:false }
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList : [10,30,50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth:false,
                multiselect: true,
                pager: "#unbindedGridPager",
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
                    courseId:courseId
                },
                gridComplete:function(){
                    //隐藏grid底部滚动条
                    $("#unbindedGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
            $("#unbindedGrid").jqGrid("clearGridData");
            $("#unbindedGrid").jqGrid('setGridParam',{
                postData:{'courseId':courseId}
            }).trigger("reloadGrid");
        },
        bindedReload:function(){
            var page = $("#unbindedGrid").jqGrid('getGridParam','page');
            $("#unbindedGrid").jqGrid('setGridParam',{
                postData:{'userName': vm.q.userName,'loginNum':vm.q.loginNum},
                page:page
            }).trigger("reloadGrid");
        },
        binding:function(){
            var userIds = getSelectedBandRows();
            if(userIds == null){
                return ;
            }
            var url ="ams/chose/bind";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                data: {
                    courseId:vm.course.courseId,
                    userIds:userIds
                },
                traditional: true,
                dataType:"json",
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.bindedReload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        }
    }
});
//选择要解绑的学生
function getSelectedUnbandRows() {
    var grid = $("#bindedGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    return grid.getGridParam("selarrrow");
}
//选择需要绑定的学生
function getSelectedBandRows() {
    var grid = $("#unbindedGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    return grid.getGridParam("selarrrow");
}