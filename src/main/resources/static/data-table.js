$.extend($.fn.dataTable.defaults, {
    "searching": false,
    "ordering": false,
    "processing": true,
    "serverSide": true,
    "bStateSave": true,
    "language": {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项结果",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索:",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    },
});
$(function () {
    $("table[query-form]").each(function () {
        var query_form = $(this).attr("query-form");
        var url = $(query_form).attr("action");
        $(this).DataTable({
            "ajax": {
                "url": url,
                //"type": "POST",
                "dataFilter": function (data) {
                    var json = jQuery.parseJSON(data);
                    json.recordsTotal = json.total;
                    json.recordsFiltered = json.total;
                    json.draw = json.pageNum;
                    json.data = json.list;
                    return JSON.stringify(json); // return JSON string
                }, "data": function (d) {
                    $(query_form).find("input,select").each(function () {
                        d[$(this).attr("name")] = $(this).val();
                    })
                }
            },
            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "age"},
                {
                    "render": function (data, type, row, meta) {
                        return "<a href='#'>" + row.name + "</a>";
                    }
                }
            ]
        });
    })
})