<#if (pageInfo?exists)>
<div class="col-xs-8 pull-right" style="text-align: right">
	<#if (pageInfo.pageNum>1)>
	<a href="#" onclick="pageTo('${pageInfo.prePage}')">上一页</a>
	<#else> 上一页 </#if> 
	<#if (pageInfo.pageNum<pageInfo.pages)> <a href="#" onclick="pageTo('${pageInfo.nextPage}')">下一页</a> 
	<#else> 下一页 </#if> ,当前第${pageInfo.pageNum}页 共 ${pageInfo.total} 项,${pageInfo.pages} 页,每页 <select style="height: 32px;" onchange="$('input[name=pageSize]').val($(this).val());if(typeof(pageTo)=='function'){pageTo(1)};">
		<option value="10"<#if pageInfo.pageSize==10>selected</#if>>10</option>
		<option value="20"<#if pageInfo.pageSize==20>selected</#if>>20</option>
		<option value="50"<#if pageInfo.pageSize==50>selected</#if>>50</option>
		<option value="100"<#if pageInfo.pageSize==100>selected</#if>>100</option>
	</select> 项 到第 <input type="text" style="width: 48px; height: 32px;" id="pgNum"> 页
	<button class="btn btn-default" onclick="if(typeof(pageTo)=='function'){pageTo($('#pgNum').val())};">GO</button>
</div>
</#if>
