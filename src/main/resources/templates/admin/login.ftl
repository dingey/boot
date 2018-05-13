<#import "base/admin_layout.ftl" as layout /> <#import "base/spring.ftl"
as spring/> <@layout.standard title="首页" bodyclass="hold-transition login-page"
cssMap={"":"https://dingey.github.io/demo/adminlte/dist/css/AdminLTE.min.css,https://dingey.github.io/demo/adminlte/dist/css/skins/all-skins.min.css,https://dingey.github.io/demo/adminlte/dist/css/icheck-blue.css"}
jsMap={"":"https://dingey.github.io/demo/adminlte/dist/js/app.min.js,https://dingey.github.io/demo/adminlte/dist/js/demo.js,https://dingey.github.io/demo/adminlte/dist/js/icheck.min.js"}>
<div class="login-box">
  <div class="login-logo">
    <a href="/admin/index"><b>boot</b></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">登录信息</p>

    <form action="/admin/login" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="username" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="password" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox" name="remeber" value="true">记住我
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="button" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <div class="social-auth-links text-center">
      <p>第三方登录</p>
      <a href="#" class="btn btn-block btn-social btn-primary"><i class="fa fa-fw fa-qq"></i>QQ登录</a>
      <a href="#" class="btn btn-block btn-social btn-success"><i class="fa fa-wechat"></i>微信登录</a>
    </div>
    <!-- /.social-auth-links -->

    <a href="#">忘记密码</a><br>
    <a href="register.html" class="text-center">注册</a>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    $(".btn-flat").click(function(){
    	$.post("/admin/login",$("form").serialize(),function(data){
    		if(data.code==0){
    			window.location.href="/admin/index";
    		}else{
    			alert(data.message);
    		}
    	},"json");
    });
  });
</script>
</@layout.standard>
