<#import "base/admin_layout.ftl" as layout />
<#import "base/page.ftl" as page />
<@layout.standard title="首页">
<br/>
<div class="container">
    <div class="row">
        <form class="form-inline" action="/man">
            <label>标签</label>
            <select name="status" class="form-control">
                <option selected="" value="">下拉</option>
                <option value="0">默认</option>
                <option value="1">取消</option>
            </select>
            <input type="text" class="form-control" name="name" placeholder="文本">
            <a class="btn btn-primary" query-table="#example">查询</a>
            <a class="btn btn-default" reflush-table="#example">刷新</a>
            <button type="reset" class="btn btn-default">重置</button>
            <a class="btn btn-primary pull-right">新增</a>
        </form>
    </div>
    <@page.page>
        <table id="example" class="table table-bordered">
            <thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>age</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
           <#list pageInfo.list as d>
           <tr>
               <td>${d.id!}</td>
               <td>${d.name!}</td>
               <td>${d.age!}</td>
               <td>${d.id!}</td>
           </tr>
           </#list>
            </tbody>
        </table>
    </@page.page>
</div>
</@layout.standard>