function remove(dom) {
    $(dom).find("input,select,textarea,a,button").each(function () {
        renameOther(this);
    });
    $(dom).remove();
}

function renameOther(dom) {
    var i = $(dom).attr("id");
    var n = $(dom).attr("name");
    if (n != undefined && n != "") {
        var re = /[A-Za-z]\[\d\]/;
        var len = 0;var count=0;
        if (n.match(re)) {
            var prefix = n.substr(0, n.indexOf("[") + 1);
            var surfix = n.substr(n.indexOf("]"));
            len = parseInt(n.substr(n.indexOf("[") + 1, n.indexOf("]")));
            $("[name^='" + prefix + "'][name$='"+surfix+"']").each(function () {
                var n0 = $(this).attr("name");
                var surfix0 = n0.substr(n0.indexOf("]"));
                var len0 = parseInt(n0.substr(n0.indexOf("[") + 1, n0.indexOf("]")));
                if (len0 > len) {
                    count++;
                    $(this).attr("name", prefix + (len0 - 1) + surfix0);
                    console.log(count+" : "+prefix + len0 + surfix0 + " -> " + prefix + (len0 - 1) + surfix0);
                }
            })
        }
    }
}

/**
 * 克隆元素，重命名id,name
 * @param id 元素id
 */
function renameClone(id) {
    var o = $(id).clone();
    $(o).find("input,select,textarea,a,button").each(function () {
        rename(this);
    })
    rename(o);
    return o;
}

/**
 * 重命名元素的id,name
 * @param o 元素对象
 */
function rename(o) {
    var i = $(o).attr("id");
    var n = $(o).attr("name");
    $(o).prop("value","");
    var i_ = i;
    if (i != undefined && i != "") {
        var len = 0;
        console.log(i.substr(i.length - 1));
        if (isNum(i.substr(i.length - 1))) {
            while (true) {
                if ($("#" + i.substr(0, i.length - 1) + len).length < 1) {
                    i = i.substr(0, i.length - 1) + len;
                    $(o).attr("id", i);
                    break;
                }
                len++;
            }
        } else {
            while (true) {
                if ($("#" + i + len).length < 1) {
                    i = i + len;
                    $(o).attr("id", i);
                    break;
                }
                len++;
            }
        }
    }
    if (i != i_) {
        $(o).find("a,button").each(function () {
            if ($(this).attr("onclick").indexOf(i_) != -1) {
                $(this).attr("onclick", $(this).attr("onclick").replace(i_, i));
            }
        });
    }
    if (n != undefined && n != "") {
        var re = /[A-Za-z]\[\d\]/;
        var len = 0;
        if (n.match(re)) {
            var prefix = n.substr(0, n.indexOf("[") + 1);
            var surfix = n.substr(n.indexOf("]"));
            while (true) {
                if ($("[name='" + prefix + len + surfix + "']").length < 1) {
                    n = prefix + len + surfix;
                    $(o).attr("name", n);
                    break;
                }
                len++;
            }
        }
    }
    return o;
}

function isNum(s) {
    var reg = /^[0-9]+.?[0-9]*$/;
    if (s.match(reg)) {
        return true;
    }
    return false;
}

$(function () {
    readonly();
    numValid();
})

function readonly() {
    //只读元素限制
    $("select[readonly]").each(function () {
        $(this).attr('onfocus', "this.defaultIndex=this.selectedIndex;");
        $(this).attr('onchange', "this.selectedIndex=this.defaultIndex;");
    });
    $("input[readonly],textarea[readonly]").click(function (event) {
        event.preventDefault();
    });
}

function numValid() {
    $("input[float]").keyup(function () {
        var v = $(this).val();
        if (v == "" || v == "-") {
            return false;
        } else if (v.startsWith("-")) {
            v = "-" + v.replace(/[^\d.]/g, '');
        } else {
            v = v.replace(/[^\d.]/g, '');
        }
        if (v.split(".").length > 2) {
            v = v.split(".")[0] + "." + v.split(".")[1];
        }
        if (!v.endsWith(".")) {
            v = Math.round(v * 100) / 100;
        }
        $(this).val(v);
    });
    $("input[ufloat]").keyup(function () {
        var v = $(this).val();
        if (v == "") {
            return false;
        } else {
            v = v.replace(/[^\d.]/g, '');
        }
        if (v.split(".").length > 2) {
            v = v.split(".")[0] + "." + v.split(".")[1];
        }
        if (!v.endsWith(".")) {
            v = Math.round(v * 100) / 100;
        }
        $(this).val(v);
    });
    $("input[int]").keyup(function () {
        var v = $(this).val();
        if (v.startsWith("-")) {
            v = "-" + v.replace(/[^\d]/g, '');
        } else {
            v = v.replace(/[^\d]/g, '');
        }
        $(this).val(v);
    });
    $("input[uint]").keyup(function () {
        var v = $(this).val();
        v = v.replace(/[^\d]/g, '');
        $(this).val(v);
    });
}

// 通用JS校验
function valid() {
    var a = true;
    var msg = "";
    $("input,select,textarea").not(':disabled').each(function () {
        if ($(this).attr("minlength") != undefined && $(this).attr("minlength") != "") {
            var len = parseInt($(this).attr("minlength"));
            if ($(this).val().length < len) {
                a = false;
                var m = $(this).attr("minlength-msg");
                if (m == undefined && $(this).parent().prev().prop("tagName") == "LABEL") {
                    m = $(this).parent().prev().html().replace("：", "").replace(":", "") + "不能小于" + len + "个字符";
                }
                if (msg.indexOf(m) == -1) {
                    msg += m + "、";
                }
            }
        } else if ($(this).attr("required") == true || $(this).attr("required") != undefined) {
            //单选,多选
            if ($(this).attr("type") == "radio" && $(this).attr("required") != undefined && $(this).attr("name") != undefined) {
                if ($("input[type=radio][name='" + $(this).attr("name") + "']:checked").val() == undefined) {
                    a = false;
                    var m = $(this).attr("required-msg");
                    if (m == undefined && $(this).parent().parent().prev().prop("tagName") == "LABEL") {
                        m = $(this).parent().parent().prev().html().replace("：", "").replace(":", "") + "不能为空";
                    }
                    if (msg.indexOf(m) == -1) {
                        msg += m + "、";
                    }
                }
            } else if ($(this).attr("type") == "checkbox" && $(this).attr("required") != undefined && $(this).attr("name") != undefined) {
                if ($("input[type=checkbox][name='" + $(this).attr("name") + "']:checked").val() == undefined) {
                    a = false;
                    var m = $(this).attr("required-msg");
                    if (m == undefined && $(this).parent().parent().prev().prop("tagName") == "LABEL") {
                        m = $(this).parent().parent().prev().html().replace("：", "").replace(":", "") + "不能为空";
                    }
                    if (msg.indexOf(m) == -1) {
                        msg += m + "、";
                    }
                }
            } else if ($(this).val() == "") {
                a = false;
                var m = $(this).attr("required-msg");
                if (m == undefined && $(this).parent().prev().prop("tagName") == "LABEL") {
                    m = $(this).parent().prev().html().replace("：", "").replace(":", "") + "不能为空";
                }
                if (msg.indexOf(m) == -1) {
                    msg += m + "、";
                }
            }
        }
        //large-id:元素选择器;large-msg:验证消息
        if ($(this).attr("large-id") != undefined && $(this).attr("large-id") != "") {
            var lid = $(this).attr("large-id");
            if (lid.indexOf(",") > 0) {
                var strs = new Array();
                strs = lid.split(",");
                var msgs = new Array();
                msgs = $(this).attr("large-msg").split(",");
                for (var i = 0; i < strs.length; i++) {
                    if ($(this).val() < $(strs[i]).val()) {
                        a = false;
                        msg += msgs[i] + ",";
                    }
                }
            } else {
                if ($(this).val() < $(lid).val()) {
                    a = false;
                    msg += $(this).attr("large-msg") + "、";
                }
            }
        }
        //less-id:元素选择器;less-msg:验证消息
        if ($(this).attr("less-id") != undefined && $(this).attr("less-id") != "") {
            var lid = $(this).attr("less-id");
            if (lid.indexOf(",") > 0) {
                var strs = new Array();
                strs = lid.split(",");
                var msgs = new Array();
                msgs = $(this).attr("less-msg").split(",");
                for (var i = 0; i < strs.length; i++) {
                    if ($(this).val() < $(strs[i]).val()) {
                        a = false;
                        msg += msgs[i] + "、";
                    }
                }
            } else {
                if ($(this).val() < $(lid).val()) {
                    a = false;
                    msg += $(this).attr("less-msg") + "、";
                }
            }
        }
        //大于指定值
        if ($(this).attr("large-val") != undefined && $(this).attr("large-val") != "") {
            if ($(this).val() < $(this).attr("large-val")) {
                a = false;
                msg += $(this).attr("large-val-msg") + "、";
            }
        }
        //小于指定值
        if ($(this).attr("less-val") != undefined && $(this).attr("less-val") != "") {
            if ($(this).val() < $(this).attr("less-val")) {
                a = false;
                msg += $(this).attr("less-val-msg") + "、";
            }
        }
    });
    if (!a) {
        layer.msg(msg);
    }
    return a;
}

//通用校验帮助文本
function validHelp(dom, b) {
    if (b == undefined) {
        b = false;
    }
    if ($(dom).attr("minlength") != undefined && $(dom).attr("minlength") != "") {
        if (!$(dom).parent().hasClass("has-feedback")) {
            $(dom).parent().addClass("has-feedback");
        }
        var len = parseInt($(dom).attr("minlength"));
        if ($(dom).val().length < len && $(dom).parent().find("span.help-block").length < 1) {
            var h = "<span class='help-block'>不能小于" + len + "个字符</span>";
            if (b && !$(dom).next().hasClass("form-control-feedback")) {
                $(dom).after("<span class='glyphicon glyphicon-remove form-control-feedback'></span>");
            } else if (b && $(dom).next().hasClass("glyphicon-ok")) {
                $(dom).next().removeClass("glyphicon-ok").addClass("glyphicon-remove");
            }
            $(dom).parent().addClass("has-error").removeClass("has-success").append(h);
        } else if ($(dom).val().length >= len && $(dom).parent().hasClass("has-error")) {
            $(dom).parent().removeClass("has-error").find("span.help-block").remove();
            if (b) {
                $(dom).parent().addClass("has-success").find("span.glyphicon-remove").removeClass("glyphicon-remove").addClass("glyphicon-ok");
            }
        }
    } else if ($(dom).attr("required") != undefined) {
        if (!$(dom).parent().hasClass("has-feedback")) {
            $(dom).parent().addClass("has-feedback");
        }
        if ($(dom).attr("type") == "radio" && $(dom).attr("name") != "") {
            if ($("input[type=radio][name='" + $(dom).attr("name") + "']:checked").val() == undefined) {
                if ($(dom).parent().parent().find("span.help-block").length < 1) {
                    var h = "<span class='help-block'>必须选择一个</span>";
                    if (b && $(dom).parent().parent().find("form-control-feedback").length > 0) {
                        $(dom).parent().parent().find("form-control-feedback").removeClass("glyphicon-ok").addClass("glyphicon-remove");
                    } else if (b) {
                        $(dom).parent().parent().addClass("has-feedback").append("<span class='glyphicon glyphicon-remove form-control-feedback' style='top: 0px;'></span>");
                    }
                    $(dom).parent().parent().addClass("has-error").append(h);
                }
            } else {
                $(dom).parent().parent().removeClass("has-error").find("span.help-block").remove();
                if (b) {
                    $(dom).parent().parent().addClass("has-success").find(".glyphicon-remove").removeClass("glyphicon-remove").addClass("glyphicon-ok");
                }
            }
        } else if ($(dom).attr("type") == "checkbox" && $(dom).attr("name") != "") {
            if ($("input[type=checkbox][name='" + $(dom).attr("name") + "']:checked").val() == undefined) {
                if ($(dom).parent().parent().find("span.help-block").length < 1) {
                    $(dom).parent().parent().find(".glyphicon-ok").remove();
                    var h = "<span class='help-block'>必须选择一个</span>";
                    if (b && $(dom).parent().parent().find("form-control-feedback").length > 0) {
                        $(dom).parent().parent().find("form-control-feedback").removeClass("glyphicon-ok").addClass("glyphicon-remove");
                    } else if (b) {
                        $(dom).parent().parent().addClass("has-feedback").append("<span class='glyphicon glyphicon-remove form-control-feedback' style='top: 0px;'></span>");
                    }
                    $(dom).parent().parent().addClass("has-error").append(h);
                }
            } else {
                $(dom).parent().parent().removeClass("has-error").find("span.help-block").remove();
                if (b) {
                    $(dom).parent().parent().addClass("has-success").find(".glyphicon-remove").removeClass("glyphicon-remove").addClass("glyphicon-ok");
                }
            }
        } else if (!$(dom).parent().hasClass("has-error") && $(dom).val() == "") {
            var h = "<span class='help-block'>不能为空</span>";
            if (b && !$(dom).next().hasClass("form-control-feedback")) {
                $(dom).after("<span class='glyphicon glyphicon-remove form-control-feedback'></span>");
            } else if (b && $(dom).next().hasClass("glyphicon-ok")) {
                $(dom).next().removeClass("glyphicon-ok").addClass("glyphicon-remove");
            }
            $(dom).parent().addClass("has-error").removeClass("has-success").append(h);
        } else if ($(dom).parent().hasClass("has-error")) {
            $(dom).parent().removeClass("has-error").find("span.help-block").remove();
            if (b) {
                $(dom).parent().addClass("has-success").find("span.glyphicon-remove").removeClass("glyphicon-remove").addClass("glyphicon-ok");
            }
        }
    }
}

//$(function () {
//    $("input,textarea").not(':disabled').each(function () {
//        validHelp(this);
//    }).change(function () {
//        validHelp(this);
//    });
//});
