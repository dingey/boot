<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">设置角色</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<input type="hidden" id="id" name="role.id" value="${(role.id)!}">
					<div class="form-group">
						<label class="col-sm-3 control-label">角色名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="role.name" value="${(role.name)!}" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">描述</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="role.desc" value="${(role.desc)!}" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">权限</label>
						<div class="col-sm-8" style="overflow: auto;">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
						<p class="hide">
						<#if (list??)>[<#list list as t>{"id" : ${t.id!},"pId" : ${t.parentId!"0"},"name" : "${t.name!}","open" : false,"chkDisabled":<#if (t.parentId==0&&t_index==0)>true<#else>false</#if><#if (t.id==1)>,"checked":true<#else><#list perms as per><#if (per.id==t.id)>,"checked":true</#if></#list></#if>}<#if (t_index!=(list?size-1))>,</#if></#list>]</#if>
						</p>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" onclick="save()">保存</a>
				<a class="btn btn-default" data-dismiss="modal">取消</a>
			</div>
		</div>
	</div>
</div>
<script>
	function save() {
		if (valid()) {
			var param=$("form:eq(1)").serialize();
			var nodes =$.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes(true);
			for(var i=0;i<nodes.length;i++){
				param+="&permissions["+i+"].permissionId="+nodes[i].id;
			}
			console.log(param);
			$.post("save", param, function(data) {
				console.log(nodes);
				if (data == "success") {
					$(".modal").modal('hide');
					pageTo($("input[name=pageNum]").val());
				} else {
					layer.msg(data);
				}
			}, "text");
		}
	}
</script>
