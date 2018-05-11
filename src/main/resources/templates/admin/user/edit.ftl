<div class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">设置用户</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal">
					<input type="hidden" id="id" name="id" value="${(user.id)!}">
					<div class="form-group">
						<label class="col-sm-3 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="username" value="${(user.username)!}" required>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">昵称</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" name="name" value="${(user.name)!}" required>
						</div>
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
			$.post("save", param, function(data) {
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
