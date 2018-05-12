<#import "../base/admin_layout.ftl" as layout /> <#import "../base/spring.ftl" as spring/> 
<@layout.standard title="用户"
cssMap={"":"https://dingey.github.io/demo/datetimepicker/bootstrap-datetimepicker.min.css,https://dingey.github.io/demo/ztree/zTreeStyle.css"}
jsMap={"":"https://dingey.github.io/demo/common/common.js,https://dingey.github.io/demo/datetimepicker/bootstrap-datetimepicker.min.js,https://dingey.github.io/demo/ztree/jquery.ztree.all.js"}>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-12">
			<form class="form-inline" id="form">
				<input type="text" class="form-control" name="id">
                <input type="text" class="form-control" name="name">
				<button type="submit" class="btn btn-primary">查询</button>
				<button type="reset" class="btn btn-default">重置</button>
				<a class="btn btn-primary pull-right" onclick="edit(0)">新增</a>
			</form>
		</div>
	</div>
	<div class="row" id="c">
		<div class="col-sm-12" id="c_">
			<table class="table table-bordered table-condensed">
				<thead>
					<tr>
						<th class="index" style="width: 20%">主键</th>
						<th style="width: 20%">username</th>
						<th style="width: 40%">name</th>
						<th style="width: 20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<#list pageInfo.list as p>
					<tr>
						<td class="id">${p.id}</td>
						<td class="index">${p.username}</td>
						<td>${p.name}</td>
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
<#include "/admin/user/edit.ftl"/>
<script>
	function pageTo(num){
		$("input[name=pageNum]").val(num);
		$("#c").load("list #c_",$("form:eq(0)").serialize());
	}
	function edit(id){
		$("div.modal-dialog").load("edit div.modal-content","id="+id,function(){
			$(".modal").modal({backdrop: 'static', keyboard: false});
			$('select.select2').select2();
		});
	}
</script>
</@layout.standard>
