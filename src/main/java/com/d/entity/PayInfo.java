package com.d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.d.base.BaseEntity;

/**
 * 支付信息
 * 
 * @author MvcGenerator by d
 * @date 2018-06-07 15:14
 */
@ApiModel("支付信息")
public class PayInfo extends BaseEntity<PayInfo> {
	private static final long serialVersionUID = 1004622533539921920L;
	@ApiModelProperty("用户id")
	private Integer userId;
	@ApiModelProperty("支付的标题")
	private String title;
	@ApiModelProperty("支付单号：支付机构返回的交易号")
	private String payNo;
	@ApiModelProperty("支付的内容")
	private String content;
	@ApiModelProperty("支付金额：分")
	private Integer amount;
	@ApiModelProperty("支付渠道：0支付宝；1微信；2银联")
	private int channel;
	@ApiModelProperty("发送请求的内容")
	private String request;
	@ApiModelProperty("响应内容")
	private String response;
	@ApiModelProperty("支付状态：0待支付;1支付成功;2支付失败;")
	private int status;
	@ApiModelProperty("前台回跳地址")
	private String jumpUrl;
	@ApiModelProperty("异步通知地址")
	private String notifyUrl;
	@ApiModelProperty("更新时间")
	private java.util.Date updateTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
}