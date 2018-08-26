<#macro page form="" table="">
<div id="page-container">
    <div id="page-body">
    <#if (pageInfo?exists)>
<div class="row" style="margin-bottom: 10px;">
    <form class="form-inline">
    <span>显示
    <select class="form-control input-sm" style="width: 75px;display: inline-block;" onchange="page(1)">
        <option value="2" <#if (pageInfo.pageSize==2)>selected</#if>>2</option>
        <option value="10" <#if (pageInfo.pageSize==10)>selected</#if>>10</option>
        <option value="15" <#if (pageInfo.pageSize==15)>selected</#if>>15</option>
        <option value="20" <#if (pageInfo.pageSize==20)>selected</#if>>20</option>
        <option value="50" <#if (pageInfo.pageSize==50)>selected</#if>>50</option>
        <option value="100" <#if (pageInfo.pageSize==100)>selected</#if>>100</option>
    </select>项结果
    </span>
    </form>
</div>
    </#if>
        <div class="row">
    <#nested/>
        </div>
    <#if (pageInfo?exists)>
<div class="row">
    <div class="col-sm-12 col-md-5" style="padding-left: 5px;">
        <p style="margin-top: 10px;">显示第 ${(pageInfo.startRow)!} 至 ${(pageInfo.endRow)!} 项结果，共 ${(pageInfo.pages)!}页 ${(pageInfo.total)!}项</p>
    </div>
    <div class="col-sm-12 col-md-7" style="padding-right: 0px;">
        <ul class="pagination pull-right" style="margin:0  0;">
            <li class="paginate_button page-item <#if (pageInfo.prePage?number<1)>disabled</#if>">
                <a href="#" class="page-link" <#if (pageInfo.prePage?number>=1)>onclick="page(${pageInfo.prePage})"</#if>>上页</a>
            </li>
                <#if (pageInfo.prePage?number>=1&&pageInfo.prePage?number<pageInfo.pageNum?number)>
            <li><a href="#" onclick="page('1','${form!}','${table!}')">1</a></li>
                    <#if (pageInfo.prePage?number<pageInfo.pageNum?number&&pageInfo.prePage?number>1)>
                        <#if (pageInfo.prePage?number>2)>
            <li><a href="#">...</a></li>
                        </#if>
            <li><a href="#" onclick="page('${pageInfo.prePage}','${form!}','${table!}')">${pageInfo.prePage}</a></li>
                    </#if>
                </#if>
            <li class="active"><a href="#" pagenum>${pageInfo.pageNum}</a></li>
                <#if (pageInfo.nextPage?number<=pageInfo.pages?number&&pageInfo.nextPage?number>pageInfo.pageNum?number)>
            <li><a href="#" onclick="page('${pageInfo.nextPage}','${form!}','${table!}')">${pageInfo.nextPage}</a></li>
                    <#if (pageInfo.nextPage?number>pageInfo.pageNum?number&&pageInfo.nextPage?number<pageInfo.pages?number)>
                        <#if (pageInfo.nextPage?number<(pageInfo.pages?number-1))>
            <li><a href="#">...</a></li>
                        </#if>
            <li><a href="#" onclick="page('${pageInfo.pages}','${form!}','${table!}')">${pageInfo.pages}</a></li>
                    </#if>
                </#if>
            <li class="paginate_button page-item <#if (pageInfo.nextPage?number>pageInfo.pages?number||pageInfo.nextPage==0)>disabled</#if>">
                <a href="#" class="page-link" <#if (pageInfo.nextPage?number<=pageInfo.pages?number&&pageInfo.nextPage!=0)>onclick="page(${pageInfo.nextPage})"</#if>>下页</a>
            </li>
        </ul>
    </div>
</div>
    <style>
        .table {
            margin-bottom: 10px;
        }
    </style>
<script>
    $("[query-table]").click(function () {
        page(1);
    });
    $("[reflush-table]").click(function () {
        page();
    });

    function page(num, form, table, size) {
        if (form == undefined || form == "") {
            form = "form:eq(0)";
        }
        if (table == undefined || table == "") {
            table = "table:eq(0)";
        }
        if (size == undefined) {
            size = $(table).parent().prev().find("select").val();
        }
        if (num == undefined) {
            num= $(table).parent().next().find("li.active>a").html();
        }
        var url = $(form).attr("action");
        console.log(size);
        $("#page-container").load(url + " #page-body", "pageNum=" + num + "&pageSize=" + size + "&" + $(form).serialize(), function () {
            try {
                if (init && typeof(init) == "function") {
                    init();
                }
            } catch (e) {
            }
        });
    }
</script>
    </#if>
    </div>
</div>
</#macro>