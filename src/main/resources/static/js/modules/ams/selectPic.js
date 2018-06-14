$(function () {
    var userId = getQueryString("userId");
    var deptId = getQueryString("deptId");
    var ajaxupload = new AjaxUpload('#upload', {
        action: baseURL + 'ams/oss/upload?token=' + token+'&userId='+userId+'&deptId='+deptId,
        name: 'file',
        autoSubmit:false,
        responseType:"json",
        data : {
            userId : 1,
            deptId : 12
        },
        onSubmit:function(file, extension){
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete : function(file, r){
            if(r.code == 0){
                alert("上传成功");
            }else{
                alert(r.msg);
            }
        }
    });
    $("#uploadbtn").click(function(){
        ajaxupload.submit();
    });
});

$(function() {
    var userName = getQueryString("userName");
    var loginNum = getQueryString("loginNum");
    $("#userName").val(decodeURI(userName));
    $("#loginNum").val(loginNum);

});

function getQueryString(name) {
    var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
    if (result == null || result.length < 1) {
        return "";
    }
    return result[1];
}

function uploadExtraData() {
    var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        loginNum: $("#loginNum").val(),
    };
    return temp;
}

