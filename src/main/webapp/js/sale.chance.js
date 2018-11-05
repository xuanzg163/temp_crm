//营销机会查询方法
function querySaleChancesByParams() {
    $('#dg').datagrid('load', {
        customerName: $('#customerName').val(),
        state: $('#state').combobox('getValue'),
        devResult: $('#devResult').combobox('getValue'),
        createDate: $('#time').datebox('getValue')
    });
}

//分配状态-数据格式化显示
function formatterState(value,row,index) {
    if (0 == value){
        return "未分配";
    }
    if (1 == value){
        return "已分配";
    }
}

//客户开发状态-数据格式化显示
function formatterDevResult(value,row,index) {
    if (0 == value){
        return "未开发";
    } else if (1 == value){
        return "开发中";
    } else if (2 == value){
        return "开发成功";
    } else if (3 == value){
        return "开发失败";
    }
}

//修改显示背景颜色,页面加载完毕触发
$(function () {

    $("#dg").datagrid({
        rowStyler:function (index,row) {
            var devResult = row.devResult;
            if (devResult == 0) {
                return "background-color:#5bc0de;"; // 蓝色
            } else if (devResult == 1) {
                return "background-color:#f0ad4e;"; // 黄色
            } else if (devResult == 2) {
                return "background-color:#5cb85c;"; // 绿色
            } else if (devResult == 3) {
                return "background-color:#d9534f;"; // 红色
            }
        }
    });
});

//打开添加营销机会的弹窗
function openAddSaleChacneDialog () {
    $("#dlg").dialog("open")
}

//添加营销机会管理信息
function saveOrUpdateSaleChance() {
    $('#fm').form('submit', {
        url: ctx + '/saleChance/saveOrUpdateSaleChance',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            data = JSON.parse(data);
            if (data.code == 200) {
                $.messager.alert('来自Crm', data.msg, 'info', function () {
                    // 关闭弹窗
                    $('#dlg').dialog('close');
                    // 刷新数据表格
                    $('#dg').datagrid('load');
                });
            } else {
                $.messager.alert('来自Crm', data.msg, 'error');
            }
        }
    });
}
