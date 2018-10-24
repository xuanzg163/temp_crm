function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

//退出
function logout() {
    $.messager.confirm("来自Crm","您确认想要退出吗？",function (r) {
        if (r) {
            $.removeCookie("userIdStr");
            $.removeCookie("userName");
            $.removeCookie("realName");

            window.location.href = ctx +"/index";
        }
    });
}

//打开修改用户密码弹窗
function openPasswordModifyDialog() {
    $("#dlg").dialog("open");
}

//修改用户密码
function modifyPassword() {
    $("#fm").form("submit",{
        url:ctx +"/user/updateUserPwd",
        onSubmit:function () {
            return $(this).form("validate");
        },
        success:function (data) {
            data = JSON.parse(data);
            if(data.code==200){
                $.messager.alert('来自Crm',data.msg,'info',function () {
                    $.removeCookie("userIdStr");
                    $.removeCookie("userName");
                    $.removeCookie("realName");

                    window.location.href = ctx + '/index';
                });
            }else{
                $.messager.alert('来自Crm',data.msg,'error');
            }
        }
    });
}