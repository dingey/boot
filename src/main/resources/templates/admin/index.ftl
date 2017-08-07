<#import "base/admin_layout.ftl" as layout />
<#import "base/spring.ftl" as spring/>
<@layout.standard title="首页" 
cssMap={"":"//dingey.github.io/demo/adminlte/dist/css/AdminLTE.min.css,//dingey.github.io/demo/adminlte/dist/css/skins/all-skins.min.css"} 
jsMap={"":"//dingey.github.io/demo/adminlte/dist/js/app.min.js,//dingey.github.io/demo/adminlte/dist/js/demo.js"}>
<style>
    iframe{
      border: 0px;
      margin: 0px;
      padding:0px;
      width: 100%;
      height: 100%;
    }
    .tab-pane{
      height: 100%;
    }
    #tabs_body{
      height: 760px;
    }
</style>
<!-- Site wrapper -->
<div class="wrapper">

  <header class="main-header">
    <!-- Logo -->
    <a href="index2.html" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini">boot</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>boot</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">    
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs">Alexander</span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="img/user2-160x160.jpg" class="img-circle" alt="User Image">

                <p>
                  Alexander Pierce - Web Developer
                  <small>Member since Nov. 2012</small>
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
                <!-- /.row -->
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Profile</a>
                </div>
                <div class="pull-right">
                  <a href="#" class="btn btn-default btn-flat"><@spring.message "sign-out"/></a>
                </div>
              </li>
            </ul>
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
        <li class="header"><@spring.message "main-navigation"/></li>
        <li class="treeview">
          <a href="#">
            <i class="fa fa-share"></i> <span>Multilevel</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>
            <li>
              <a href="#"><i class="fa fa-circle-o"></i> Level One
                <span class="pull-right-container">
                  <i class="fa fa-angle-left pull-right"></i>
                </span>
              </a>
              <ul class="treeview-menu">
                <li><a href="#"><i class="fa fa-circle-o"></i> Level Two</a></li>
                <li>
                  <a href="#"><i class="fa fa-circle-o"></i> Level Two
                    <span class="pull-right-container">
                      <i class="fa fa-angle-left pull-right"></i>
                    </span>
                  </a>
                  <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i> Level Three</a></li>
                  </ul>
                </li>
              </ul>
            </li>
            <li><a href="#"><i class="fa fa-circle-o"></i> Level One</a></li>
          </ul>
        </li>
        <li><a href=""><i class="fa fa-fw fa-gears"></i> <span>设置</span></a>
         <ul class="treeview-menu">
            <li><a href="permission"><i class="fa fa-fw fa-key"></i>权限设置</a></li>
         </ul>
        </li>
        <li class="header">LABELS</li>
        <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- =============================================== -->

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <!--<h1>
        Blank page
        <small>it all starts here</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Examples</a></li>
        <li class="active">Blank page</li>
      </ol>-->
    </section>

    <!-- Main content -->
    <section class="content">

      <div class="nav-tabs-custom">
        <ul class="nav nav-tabs" id="tabs_head">
          <li class="pull-right dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
              <i class="fa fa-gear"></i>
            </a>
            <ul class="dropdown-menu" style="min-width: 60px;">
              <li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="closeTabs()"><@spring.message "close-all"/></a></li>
            </ul>
          </li>
          <li class="active" id="li_tab_1"><a href="#tab_1" data-toggle="tab">Tab 1<i class="fa fa-fw fa-close" onclick="closeTab('1')"></i></a></li>
        </ul>
        <div class="tab-content" id="tabs_body">
          <div class="tab-pane active" id="tab_1">
            The European languages are members of the same family. Their separate existence is a myth.
            For science, music, sport, etc, Europe uses the same vocabulary.
          </div>
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
    <strong>Copyright &copy.</strong> All rights
    reserved.
  </footer>

  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<script>
  function println(s) {
    console.log(s);
  }
  var count=$("#tabs_head>li").length;
  println(count);
  function getCount() {
    count++;
    return count;
  }
  function stopJump() {
    $(".treeview a,.sidebar-menu a").each(function () {
      var url=$(this).attr("href");
      if(url!="#"&&url!=""){
        $(this).attr("href","#");
        $(this).attr("url",url);
      }else{
        $(this).attr("url","#");
      }
    })
  }
  function bindClick() {
    $(".treeview a,.sidebar-menu a").click(function () {
      //println($(this).attr("url"));
      if($(this).attr("url")!=""&&$(this).attr("url")!="#"&&!checkExists($(this).attr("url"))){
        var b=false;
        var obj=this;
        $("#tabs_head>li").removeClass("active");
        $("#tabs_body>div").removeClass("active");
        var i = getCount();
        var li = "<li class=\"active\" id=\"li_tab_" + i + "\"><a href=\"#tab_" + i + "\" data-toggle=\"tab\">" + $(this).text() + "<i class=\"fa fa-fw fa-close\" onclick=\"closeTab('" + i + "')\"></i></a></li>";
        var con = "<div class=\"tab-pane active\" id=\"tab_" + i + "\"><iframe src=\"" + $(this).attr("url") + "\"></iframe></div>";
        $("#tabs_head").append(li);
        $("#tabs_body").append(con);
      }
    });
  }
  function checkExists(url) {
    var exists=false;
    $("#tabs_head>li").each(function () {
      var tb=$(this).attr("id")+"";
      tb=tb.substring(3);
      var url2=$("#"+tb).children().attr("src");
      if(url2!=undefined&&url2==url){
        $("#tabs_head>li").removeClass("active");
        $("#tabs_body>div").removeClass("active");
        $(this).addClass("active");
        $("#"+tb).addClass("active");
        exists=true;
      }
    });
    return exists;
  }
  function closeTab(id) {
    if($("#li_tab_"+id).hasClass("active")){
      var obj=$("#li_tab_"+id).next();
      if(obj.length==0){
        obj=$("#li_tab_"+id).prev();
        if(obj.length>0&&!$(obj).hasClass("pull-right")){
          $(obj).find("a").click();
        }
      }else{
        $(obj).find("a").click();
      }
    }
    $("#li_tab_"+id).remove();
    $("#tab_"+id).remove();
  }
  function closeTabs() {
    $("#tabs_body").empty();
    $("#tabs_head>li").each(function () {
      if(!$(this).hasClass("pull-right")){
        $(this).remove();
      }
    })
  }
  $(function () {
    stopJump();
    bindClick();
  })
</script>
</@layout.standard>