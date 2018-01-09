<#macro standard title="" keywords="" description="" bodyclass="" jsMap={} cssMap={}>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link rel="icon" href="/favicon.ico" />
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<#list cssMap?keys as key>
  <#if key==""&&cssMap[key]!="">
  <#list cssMap[key]?split(",") as val>
    <link href="${val}" rel="stylesheet">
  </#list>
  </#if>
</#list>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://dingey.github.io/demo/layer/layer.js"></script>
<#list jsMap?keys as key>
  <#if key==""&&jsMap[key]!="">
  <#list jsMap[key]?split(",") as val>
    <script src="${val}"></script>
  </#list>
  </#if>
</#list>
<style>
form.form-inline>.input-group, form.form-inline>label, form.form-inline>select,
	form.form-inline>input, form.form-inline>a, form.form-inline>button {
	margin-bottom: 10px;
}
span.help-block{margin-bottom: 0px;}
.form-group {
    margin-bottom: 10px;
}
</style>
</head>
<body class="${bodyclass!}">
    <#nested/>
</body>
</html>
</#macro>