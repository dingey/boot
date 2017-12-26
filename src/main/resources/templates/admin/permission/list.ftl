<#import "../base/admin_layout.ftl" as layout /> <#import
"../base/spring.ftl" as spring/> <@layout.standard title="首页"
cssMap={"":"https://dingey.github.io/demo/ztree/zTreeStyle.css"}
jsMap={"":"https://dingey.github.io/demo/ztree/jquery.ztree.all.js"}>
<style>
.ztree li span.btn {
    line-height: 0;
    margin: 0;
    width: 16px;
    height: 16px;
    display: inline-block;
    vertical-align: middle;
    border: 0 none;
    cursor: pointer;
    outline: none;
    background-color: transparent;
    background-repeat: no-repeat;
    background-attachment: scroll;
    background-image: url(https://dingey.github.io/demo/ztree/img/zTreeStandard.png);
}
</style>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
</div>
<#include "/admin/permission/edit.ftl"/>
<script>
	var setting = {
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		check : {
			enable : true
		},
		edit : {
			enable : true,
			editNameSelectAll : true,
			//showRemoveBtn: showRemoveBtn,
			//showRenameBtn: showRenameBtn
			showRemoveBtn : true,
			showRenameBtn : true
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeDrag : beforeDrag,
			beforeDrop : beforeDrop,
			//onDragMove: zTreeOnDragMove,
			beforeEditName : beforeEditName,
			beforeRemove : beforeRemove,
			beforeRename : beforeRename,
			onRemove : onRemove,
			onRename : onRename,
			onDrop : zTreeOnDrop
		}
	};

	var zNodes = [<#list list as t>{id : ${t.id!},pId : ${t.parentId!"0"},name : "${t.name!}",open : true}<#if (t_index!=(list?size-1))>,</#if></#list>];
	//拖拽
	function beforeDrag(treeId, treeNodes) {
		for (var i = 0, l = treeNodes.length; i < l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		console.log(treeNodes[0].id + ":" + targetNode.id)
	}
	function zTreeOnDragMove(event, treeId, treeNodes) {
		//console.log(JSON.stringify(treeNodes));
	}
	function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
		if (moveType == "inner") {
			console.log("拖拽[" + treeNodes[0].name + ";pId:" + treeNodes[0].pId
					+ "]" + targetNode.id);
		} else if (moveType == "prev") {
			console.log("拖拽[" + treeNodes[0].name + ";pId:" + treeNodes[0].pId
					+ "]" + targetNode.pId);
		} else if (moveType == "next") {
			console.log("拖拽[" + treeNodes[0].name + ";pId:" + treeNodes[0].pId
					+ "]" + targetNode.pId);
		}
	}
	function printAllNode() {
		var data = [];
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		for (var i = 0; i < zTree.getNodes().length; i++) {
			data[i] = getSimpleNode(zTree.getNodes()[i]);
		}
		//data= getSimpleNode(zTree.transformToArray(zTree.getNodes())[0]);
		console.log(JSON.stringify(data));
	}
	function getSimpleNode(node) {
		var data = {
			"id" : "",
			"pid" : "",
			children : []
		};
		data.id = node.id;
		data.pid = node.pId;
		var child = node.children;
		if (child != undefined && child.length > 0) {
			for (var i = 0; i < child.length; i++) {
				data.children[i] = getSimpleNode(node.children[i]);
			}
		}
		return data;
	}
	function printIndex() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 0) {
			var index = treeObj.getNodeIndex(nodes[0]);
			console.log(index);
		}
	}
	function printAllNodes() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		console.log(JSON.stringify(treeObj.transformTozTreeNodes(treeObj
				.getNodes())));
	}
	function showCode(id, str) {
		var code = $("#code" + id);
		code.empty();
		for (var i = 0, l = str.length; i < l; i++) {
			code.append("<li>" + str[i] + "</li>");
		}
	}
	var log, className = "dark";
	//编辑
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		setTimeout(function() {
			$("#modal-body").load("edit?id="+treeNode.id+" #form",function(){
				$(".bs-example-modal-sm").modal({backdrop: 'static', keyboard: false});
			});
		}, 0);
		return false;
	}
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
	}
	function onRemove(e, treeId, treeNode) {
		showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
	}
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "" : "dark");
		showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
				+ " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
				+ (isCancel ? "</span>" : ""));
		if (newName.length == 0) {
			setTimeout(function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.cancelEditName();
				alert("节点名称不能为空.");
			}, 0);
			return false;
		}
		return true;
	}
	function onRename(e, treeId, treeNode, isCancel) {
		showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
				+ " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
				+ (isCancel ? "</span>" : ""));
	}
	function showRemoveBtn(treeId, treeNode) {
		return !treeNode.isFirstNode;
	}
	function showRenameBtn(treeId, treeNode) {
		return !treeNode.isLastNode;
	}
	function showLog(str) {
		if (!log)
			log = $("#log");
		log.append("<li class='"+className+"'>" + str + "</li>");
		if (log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
				.getSeconds(), ms = now.getMilliseconds();
		return (h + ":" + m + ":" + s + " " + ms);
	}
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='btn add' id='addBtn_" + treeNode.tId
				+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn)
			btn.bind("click", function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				$.get("add","pid="+treeNode.id,function(data){
					zTree.addNodes(treeNode, {
						id : data,
						pId : treeNode.id,
						name : "new node" + (newCount++)
					});
				},"text");			
				return false;
			});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}
	$(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		//setCheck();
	});
</script>
</@layout.standard>
