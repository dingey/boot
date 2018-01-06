<#import "../base/admin_layout.ftl" as layout /> <#import
"../base/spring.ftl" as spring/> <@layout.standard title="首页"
cssMap={"":"https://dingey.github.io/demo/ztree/zTreeStyle.css"}
jsMap={"":"https://dingey.github.io/demo/ztree/jquery.ztree.all.js"}>
<style>
.ztree li span.btn {
    background-position: -145px -1px;
}
</style>
<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog"
	aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">模态框</h4>
			</div>
			<div class="modal-body" id="modal-body">
				<form id="form">
					<input type="hidden" id="id" name="id" value="${(permission.id)!}">
					<input type="hidden" id="pid" name="parentId" value="${(permission.parentId)!}">
					<div class="form-group">
						<label for="title" class="control-label">权限中文名:</label> <input
							class="form-control" id="name" name="name" value="${(permission.name)!}">
					</div>
					<div class="form-group">
						<label for="id" class="control-label">权限英文名:</label> <select
							class="form-control" name="permission">
							<option value="">无</option> 
							<#if mappings?exists> 
							<#list mappings as mapping>
								<option value="${mapping!}"<#if
								(permission?exists&&permission.permission?exists&&mapping==permission.permission)>selected</#if>>${mapping!}
								</option>
							</#list> 
							</#if>
						</select>
					</div>
					<div class="form-group">
						<label for="title" class="control-label">url:</label>
							<select class="form-control" name="url">
							<option value="">无</option> 
							<#if urls?exists> 
							<#list urls as url>
								<option value="${url!}"<#if
								(permission?exists&&permission.url?exists&&url==permission.url)>selected</#if>>${url!}
								</option>
							</#list> 
							</#if>
						</select>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" onclick="save()">保存</a> <a
					class="btn btn-default" data-dismiss="modal">取消</a>
			</div>
		</div>
	</div>
</div>
<script>
	function save() {
		$.post("save", $("form").serialize(), function(data) {
			$(".bs-example-modal-sm").modal('hide');
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var node = zTree.getNodeByParam("id", $("#id").val(), null);
			if(node!=null){
				node.name = $("#name").val();
				zTree.updateNode(node);
			}
		}, "text");
	}
</script>
</@layout.standard>
