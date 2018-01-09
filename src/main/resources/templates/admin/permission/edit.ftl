<#import "../base/admin_layout.ftl" as layout /> <#import "../base/spring.ftl" as spring/> <@layout.standard title="首页" cssMap={"":"https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css,https://dingey.github.io/demo/ztree/zTreeStyle.css"} jsMap={"":"https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js,https://dingey.github.io/demo/ztree/jquery.ztree.all.js"}>
<style>
.ztree li span.btn {
	background-position: -145px -1px;
}

.select2-container .select2-selection--single,
	.select2-container--default .select2-selection--single .select2-selection__arrow,
	.select2-container--default .select2-selection--single .select2-selection__rendered
	{
	height: 34px;
}
.select2-container--default .select2-selection--single .select2-selection__rendered {
    line-height: 34px;
}
</style>
<div class="modal fade modal-md">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="exampleModalLabel">编辑权限</h4>
			</div>
			<div class="modal-body" id="modal-body">
				<form id="form" class="form-horizontal">
					<input type="hidden" id="id" name="id" value="${(permission.id)!}">
					<input type="hidden" id="pid" name="parentId" value="${(permission.parentId)!}">
					<div class="form-group">
						<label for="title" class="col-sm-3 control-label">权限中文名:</label>
						<div class="col-sm-8">
							<input class="form-control" id="name" name="name" value="${(permission.name)!}">
						</div>
					</div>
					<div class="form-group">
						<label for="id" class="col-sm-3 control-label">权限英文名:</label>
						<div class="col-sm-8">
							<select class="form-control" name="permission" style="width: 100%;">
								<option value=""<#if (permission?exists&&permission.permission?exists&&permission.permission=="")>selected</#if>>无</option>
								<#if mappings?exists> <#list mappings as mapping>
								<option value="${mapping!}"<#if (permission?exists&&permission.permission?exists&&mapping==permission.permission)>selected</#if>>${mapping!}</option>
								</#list> </#if>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="title" class="col-sm-3 control-label">url:</label>
						<div class="col-sm-8">
							<select class="form-control select2" name="url" style="width: 100%;">
								<option value="">无</option>
								<#if urls?exists> <#list urls as url>
								<option value="${url!}"<#if (permission?exists&&permission.url?exists&&url==permission.url)>selected</#if>>${url!}</option>
								</#list> </#if>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">访问方式：</label>
						<div class="col-sm-9">
							<label class="radio-inline">
								<input type="radio" name="authc"
								<#if (permission??&&permission.authc??&&permission.authc==0)>checked<#elseif (!permission??||!permission.authc??)>checked</#if> value="0">匿名访问
							</label>
							<label class="radio-inline">
								<input type="radio" name="authc"
								<#if (permission??&&permission.authc??&&permission.authc==1)>checked</#if> value="1">权限验证
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="title" class="col-sm-3 control-label">类型:</label>
						<div class="col-sm-8">
							<select class="form-control" name="type" style="width: 100%;">
								<#if types?exists> <#list types as type>
								<option value="${type.getValue()}"<#if (permission?exists&&permission.type?exists&&type.getValue()==permission.type)>selected</#if>>${type.getName()}</option>
								</#list> </#if>
							</select>
						</div>
					</div>
					<div class="form-group icon <#if (permission??&&permission.type??&&permission.type==0)>hide</#if>">
						<label for="title" class="col-sm-3 control-label">图标:</label>
						<div class="col-sm-1">
							<p style="cursor: pointer;" id="iconbtn" onclick="$('.bs-example-modal-lg').modal('show');" class="form-control-static"><#if (permission??&&permission.icon?has_content)>${permission.icon}<#else>无</#if></p>
						</div>		
						<div class="col-sm-2">
							<textarea name="icon" style="display: none;">${(permission.icon)!}</textarea>
							<a class="btn btn-default" onclick="$('textarea[name=icon]').val('');$('#iconbtn').html('无');">清除图标</a>
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
<#include "/admin/permission/icon.ftl"/>
<script>
	function initIcon() {
		$("select[name=type]").change(function() {
			console.log($(this).val())
			if ($(this).val() != 0) {
				$("div.icon").removeClass("hide");
			} else {
				$("div.icon").addClass("hide");
			}
		});
	}
	function save() {
		$.post("save", $("#form").serialize(), function(data) {
			$(".modal").modal('hide');
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var node = zTree.getNodeByParam("id", $("#id").val(), null);
			if (node != null) {
				node.name = $("#name").val();
				zTree.updateNode(node);
			}
		}, "text");
	}
</script>
</@layout.standard>
