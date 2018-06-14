$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'ams/class/list',
        datatype: "json",
        colModel: [
            { label: '班级ID', name: 'deptId', index: "dept_id", width: 45, hidden:true,key: true},
            { label: '班级名', name: 'name', index: "name", width: 45 },
            { label: '所属学院', name: 'parentName', width: 75 },
            { label: '脸集', name: 'groupName', width: 100 }
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
            deptName: null,
            parentName: null
        },
        showList: true,
        title:null,
        roleList:{},
        dept:{
            parentName:null,
            parentId:0,
            orderNum:0
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        detail: function(){
            var row = getSelectedRow();
            if (row == null) return ;
            vm.showList = false;
            vm.getStudentListByDeptId(row);
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{'deptName': vm.q.deptName,'parentName':vm.q.parentName},
                page:page
            }).trigger("reloadGrid");
        },
        updateFace: function () {
            var row = getSelectedUserData();
            if (row == null) return ;
            console.log(row);
            layer.open({
                type: 2,
                offset: '0px',
                skin: 'layui-layer-molv',
                title: "上传人脸",
                area: ['800px', '550px'],
                shade: 0,
                shadeClose: false,
                content:baseURL+'modules/ams/selectPic.html?loginNum='+row.loginNum+'&userName='+row.userName+'&userId='+row.userId+'&deptId='+row.deptId,
                btn: ['确定', '取消'],
                btn1: function (index) {
                    layer.close(index);
                }
            });
        },
        stuReload: function () {
            vm.showList = false;
            vm.showStudent = true;
            var page = $("#showStudentGrid").jqGrid('getGridParam','page');
            $("#showStudentGrid").jqGrid('setGridParam',{
                postData:{'loginNum': vm.q.loginNum,'userName':vm.q.userName},
                page:page
            }).trigger("reloadGrid");
        },
        getStudentListByDeptId: function (deptId) {
            $("#showStudentGrid").jqGrid({
                url: baseURL + 'ams/student/stulist',
                datatype: "json",
                colModel: [
                    { label: '学生ID', name: 'userId', index: "user_id", width: 45,hidden:true, key: true },
                    { label: '学号', name: 'loginNum', index: "loginNum", width: 45, key: false },
                    { label: '姓名', name: 'userName', width: 75 },
                    { label: '班级', name: 'deptName', width: 75,sortable:false },
                    { label: '班级ID', name: 'deptId',hidden:true},
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
                autowidth:false,
                multiselect: true,
                pager: "#showStudentGridPager",
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
                    deptId:deptId
                },
                gridComplete:function(){
                    //隐藏grid底部滚动条
                    $("#bindedGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
                }
            });
            $("#showStudentGrid").jqGrid("clearGridData");
            $("#showStudentGrid").jqGrid('setGridParam',{
                postData:{'deptId':deptId}
            }).trigger("reloadGrid");
        }
    }
});