<#import "base/admin_layout.ftl" as layout /> <#import "base/spring.ftl" as spring/> <@layout.standard title="首页" bodyclass="hold-transition skin-blue sidebar-mini" cssMap={"":"https://dingey.github.io/demo/adminlte/dist/css/AdminLTE.min.css,https://dingey.github.io/demo/adminlte/dist/css/skins/all-skins.min.css"} jsMap={"":"https://dingey.github.io/demo/adminlte/dist/js/app.min.js,https://dingey.github.io/demo/adminlte/dist/js/demo.js,https://dingey.github.io/demo/adminlte/dist/js/index.js"}>
<style>
iframe {
	border: 0px;
	margin: 0px;
	padding: 0px;
	width: 100%;
	height: 100%;
	min-height: 780px;
}

.tab-pane {
	height: 100%;min-height: 780px;
}

#tabs_body {
	height: 100%;min-height:800px;
}
</style>
<!-- Site wrapper -->
<div class="wrapper">

	<header class="main-header">
		<!-- Logo -->
		<a href="#" class="logo">
			<!-- mini logo for sidebar mini 50x50 pixels -->
			<span class="logo-mini">boot</span>
			<!-- logo for regular state and mobile devices -->
			<span class="logo-lg"> <b>boot</b>
			</span>
		</a>
		<!-- Header Navbar: style can be found in header.less -->
		<nav class="navbar navbar-static-top">
			<!-- Sidebar toggle button-->
			<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</a>
			<div class="collapse navbar-collapse pull-left" id="navbar-collapse">
				<ul class="nav navbar-nav">
					<#list list as n>
					<li data-type="primary">
						<a class="btn">${n.icon!}${n.name!}</a>
					</li>
					</#list>
				</ul>
			</div>
			<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">
					<!-- User Account: style can be found in dropdown.less -->
					<li class="dropdown user user-menu">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<img src="https://dingey.github.io/demo/adminlte/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
							<span class="hidden-xs">管理员</span>
						</a>
						<ul class="dropdown-menu">
							<!-- User image -->
							<li class="user-header">
								<img src="https://dingey.github.io/demo/adminlte/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
								<p>超级管理员</p>
							</li>
							<!-- Menu Body -->
							<li class="user-body">
								<div class="row">
									<div class="col-xs-4 text-center">
										<a href="#">粉丝</a>
									</div>
									<div class="col-xs-4 text-center">
										<a href="#">任务</a>
									</div>
									<div class="col-xs-4 text-center">
										<a href="#">好友</a>
									</div>
								</div>
								<!-- /.row -->
							</li>
							<!-- Menu Footer-->
							<li class="user-footer">
								<div class="pull-left">
									<a href="#" class="btn btn-default btn-flat">简介</a>
								</div>
								<div class="pull-right">
									<a href="/admin/logout" class="btn btn-default btn-flat"><@spring.message "sign-out"/></a>
								</div>
							</li>
						</ul>
					</li>
					<li>
            			<a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          			</li>
				</ul>
			</div>
		</nav>
	</header>

	<!-- =============================================== -->

	<!-- Left side column. contains the sidebar -->
	<aside class="main-sidebar">
		<!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar">
			<!-- sidebar menu: : style can be found in sidebar.less -->
			<ul class="sidebar-menu">
<!-- 				<li class="header"><@spring.message "main-navigation"/></li> -->
				<#list list as nav>
				<#list nav.children as menu>
				<li data-value="${nav.name!}">
					<a href="">
						${menu.icon!}<span>${menu.name!}</span>
					</a>
					<ul class="treeview-menu">
						<#list menu.children as item>
						<li>
							<a href="${item.url!}">
								${item.icon!}${item.name!}
							</a>
						</li>
						</#list>
					</ul>
				</li>
				</#list>
				</#list>
<!-- 				<li class="header">标签</li> -->
<!-- 				<li> -->
<!-- 					<a href="#"> -->
<!-- 						<i class="fa fa-circle-o text-red"></i> <span>重要</span> -->
<!-- 					</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a href="#"> -->
<!-- 						<i class="fa fa-circle-o text-yellow"></i> <span>警告</span> -->
<!-- 					</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a href="#"> -->
<!-- 						<i class="fa fa-circle-o text-aqua"></i> <span>信息</span> -->
<!-- 					</a> -->
<!-- 				</li> -->
			</ul>
		</section>
		<!-- /.sidebar -->
	</aside>
	<!-- =============================================== -->
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<!--<section class="content-header"></section>-->

		<!-- Main content -->
		<section class="content" style="padding-bottom: 0px;height: 855px;">

			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs" id="tabs_head">
					<li class="pull-right dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<i class="fa fa-gear"></i>
						</a>
						<ul class="dropdown-menu" style="min-width: 60px;">
							<li role="presentation">
								<a role="menuitem" tabindex="-1" href="#" onclick="closeTabs()"><@spring.message "close-all"/></a>
							</li>
						</ul>
					</li>
					<li class="active" id="li_tab_1">
						<a href="#tab_1" data-toggle="tab">
							Tab 1 <i class="fa fa-fw fa-close" onclick="closeTab('1')"></i>
						</a>
					</li>
				</ul>
				<div class="tab-content" id="tabs_body">
					<div class="tab-pane active" id="tab_1">The European languages are members of the same family.</div>
					<!-- /.tab-pane -->
				</div>
				<!-- /.tab-content -->
			</div>

		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

	<footer class="main-footer">
		<div class="pull-right hidden-xs">
			<b>Version</b> 1.0
		</div>
		<strong>Copyright &copy.</strong> All rights reserved.
	</footer>
  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>

      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">Recent Activity</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                <p>Will be 23 on April 24th</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-user bg-yellow"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

                <p>New phone +1(800)555-1234</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

                <p>nora@example.com</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-file-code-o bg-green"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>

                <p>Execution time 5 seconds</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <h3 class="control-sidebar-heading">Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Custom Template Design
                <span class="label label-danger pull-right">70%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Update Resume
                <span class="label label-success pull-right">95%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-success" style="width: 95%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Laravel Integration
                <span class="label label-warning pull-right">50%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Back End Framework
                <span class="label label-primary pull-right">68%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->
      <!-- Stats tab content -->
      <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
      <!-- /.tab-pane -->
      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">General Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Allow mail redirect
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Other sets of options are available
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Expose author name in posts
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Allow the user to show his name in blog posts
            </p>
          </div>
          <!-- /.form-group -->

          <h3 class="control-sidebar-heading">Chat Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Show me as online
              <input type="checkbox" class="pull-right" checked>
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Turn off notifications
              <input type="checkbox" class="pull-right">
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Delete chat history
              <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
            </label>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
	<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
	<div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<script>
	$(function() {
		$("#navbar-collapse>ul>li>a").click(function() {
			var n = $(this).text();
			$("ul.sidebar-menu>li").each(function() {
				if (n != ""&& n != undefined&& !$(this).hasClass("header")&& $(this).attr("data-value") != ""&& $(this).attr("data-value") != undefined) {
					if ($(this).attr("data-value") == n) {
						$(this).removeClass("hide");
					} else {
						$(this).addClass("hide");
					}
				}
			});
		});
		$("#navbar-collapse>ul>li>a:eq(0)").click();
	});
</script>
</@layout.standard>
