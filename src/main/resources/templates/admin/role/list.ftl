<#import "../base/admin_layout.ftl" as layout /> <#import "../base/spring.ftl" as spring/> <@layout.standard title="角色" cssMap={"":"https://dingey.github.io/demo/ztree/zTreeStyle.css"} jsMap={"":"https://dingey.github.io/demo/ztree/jquery.ztree.all.js"}>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-12">
			<form class="form-inline" id="form">
				<input type="hidden" name="pageNum">
				<input type="hidden" name="pageSize">
			</form>
		</div>
	</div>
	<div class="row" id="c">
		<div class="col-sm-12" id="c_">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="index" style="width: 20%">主键</th>
						<th style="width: 20%">名称</th>
						<th style="width: 40%">描述</th>
						<th style="width: 20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<#list pageInfo.list as p>
					<tr>
						<td class="id">${p.id}</td>
						<td class="index">${p.name}</td>
						<td>${p.desc}</td>
						<td>
							<a onclick="edit(${p.id})" class="btn btn-primary btn-xs">编辑</a>
						</td>
					</tr>
					</#list>
				</tbody>
			</table>
			<#include "/admin/base/page_info.ftl"/>
		</div>
	</div>
</div>
<script>
	function pageTo(num){
		$("input[name=pageNum]").val(num);
		$("#c").load("list #c_",$("form").serialize());
	}
</script>
</@layout.standard>
