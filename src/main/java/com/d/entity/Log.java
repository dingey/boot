package com.d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.d.base.BaseEntity;

/**
 * 日志
 *
 * @author MvcGenerator by d
 * @date 2018-06-30 21:49
 */
@ApiModel("日志")
public class Log extends BaseEntity<Log> {
    private static final long serialVersionUID = 1013056856462983168L;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("参数值")
    private String params;
    @ApiModelProperty("类全名")
    private String className;
    @ApiModelProperty("方法")
    private String method;
    @ApiModelProperty("行数")
    private Integer lineNum;
    @ApiModelProperty("原因")
    private String cause;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}