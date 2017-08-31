<#import "base/admin_layout.ftl" as layout /> <#import "base/spring.ftl"
as spring/> <@layout.standard title="首页"
cssMap={"":"//dingey.github.io/demo/adminlte/dist/css/AdminLTE.min.css,//dingey.github.io/demo/adminlte/dist/css/skins/all-skins.min.css"}
jsMap={"":"//dingey.github.io/demo/adminlte/dist/js/app.min.js,//dingey.github.io/demo/adminlte/dist/js/demo.js"}>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<p>n1:${n1}</p>
			<p>n2:${n2}</p>
			<p>user:${user.userName},${user.id},${user.userSex}</p>
		</div>
	</div>
</div>
</@layout.standard>
