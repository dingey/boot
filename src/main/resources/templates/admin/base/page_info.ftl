<#if (pageInfo?exists)>
<div class="col-xs-8 pull-right" style="text-align: right">
	<#if (pageInfo.pageNum>1)>
	<a href="#" onclick="page('${pageInfo.prePage}')">上一页</a>
	<#else> 上一页 </#if> 
	<#if (pageInfo.pageNum<pageInfo.pages)> <a href="#" onclick="page('${pageInfo.nextPage}')">下一页</a>
	<#else> 下一页 </#if> ,当前第${pageInfo.pageNum}页 共 ${pageInfo.total} 项,${pageInfo.pages} 页,每页 <select style="height: 32px;" onchange="page(1,$(this).val());">
		<option value="10"<#if pageInfo.pageSize==10>selected</#if>>10</option>
		<option value="20"<#if pageInfo.pageSize==20>selected</#if>>20</option>
		<option value="50"<#if pageInfo.pageSize==50>selected</#if>>50</option>
		<option value="100"<#if pageInfo.pageSize==100>selected</#if>>100</option>
	</select> 项 到第 <input type="text" style="width: 48px; height: 32px;" id="pgNum"> 页
	<button class="btn btn-default" onclick="page($('#pgNum').val());">GO</button>
</div>
<script>
    function page(num, size) {
        if (num == undefined) {
            num = 1;
        }
        if (size == undefined) {
            size = 10;
        }
        var url = $("form:eq(0)").attr("action");
        if (url == undefined) {
            url = window.location.pathname;
        }
        var param = $("form:eq(0)").serializeArray();
        if ($("form:eq(0)").serialize().indexOf("pageNum") == -1) {
            param.push({name: "pageNum", value: num});
        }
        if ($("form:eq(0)").serialize().indexOf("pageSize") == -1) {
            param.push({name: "pageSize", value: size});
        }
        $.ajax({
            url: url,
            data: param
        }).done(function (data) {
            data=data.substring(data.indexOf("<body"),data.lastIndexOf("</body>")+7);
			document.body.innerHTML=data;
            for (var i = 0; i < param.length; i++) {
                if (param[i].name != undefined && param[i].name != "") {
                    $("body").find("form:eq(0)").find("input[name='" + param[i].name + "'],select[name='" + param[i].name + "']").val(param[i].value);
                }
            }
            $("body").find("script").each(function () {
                var newScript = document.createElement('script');
                newScript.type = 'text/javascript';
                newScript.innerHTML = $(this).html();
				if($(this).attr("src")!=undefined){
                    newScript.src=$(this).attr("src");
				}
                $(this).remove();
                document.body.appendChild(newScript);
            })
        });
    }
    $(function () {
        $("form:eq(0)").submit(function () {
            page();
            return false;
        });
    });
</script>
</#if>
