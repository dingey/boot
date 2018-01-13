<#import "../base/admin_layout.ftl" as layout /> <#import "../base/spring.ftl" as spring/> <@layout.standard title="角色"
cssMap={"":"https://dingey.github.io/demo/datetimepicker/bootstrap-datetimepicker.min.css"}
jsMap={"":"https://dingey.github.io/demo/common/common.js,https://dingey.github.io/demo/datetimepicker/bootstrap-datetimepicker.min.js"}>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-12">
			<form class="form-inline" id="form">
				<input type="text" class="form-control" name="name">
                <input type="text" class="date form-control" name="date">
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
<#include "/admin/role/edit.ftl"/>
<script>
	function pageTo(num){
		$("input[name=pageNum]").val(num);
		$("#c").load("list #c_",$("form:eq(0)").serialize());
	}
	function edit(id){
		$("div.modal-dialog").load("edit div.modal-content","id="+id,function(){
			initValid();
			var zNodes=JSON.parse($("p.hide").html().trim());
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$(".modal").modal({backdrop: 'static', keyboard: false});
		});
	}
	function initValid(){
		$("input,textarea").not(':disabled').each(function () {
	        validHelp(this);
	    }).change(function () {
	        validHelp(this);
	    });
	}
	$(function () {
		initValid();$('.date').datetimepicker({clearBtn: 1, todayBtn: 1, autoclose: 1});
	});
</script>
<script>
var setting = {
		check : {
			enable : true
		},
		data : {
			simpleData : {
				enable : true
			}
		}
};
</script>
</@layout.standard>
