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
					<input type="hidden" id="id" name="id" value="${(role.id)!}">
					<div class="form-group">
						<label class="col-sm-3 control-label">角色名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="name" value="${(role.name)!}" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">描述</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="desc" value="${(role.desc)!}" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">权限</label>
						<div class="col-sm-8" style="overflow: auto;">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
						<p class="hide">
						[{"id" : 1,"pId" : 0,"name" : "主页","open" : false}, 
						{"id" : 2,"pId" : 1,"name" : "设置","open" : false,"chkDisabled":true}, 
						{"id" : 3,"pId" : 1,"name" : "用户展示测试超长名称超级长","open" : false}]</p>
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
			$.post("save", $("form:eq(1)").serialize(), function(data) {
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
