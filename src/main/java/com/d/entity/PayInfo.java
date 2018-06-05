package com.d.entity;

import java.io.Serializable;
import java.util.Date;

import com.di.kit.SqlProvider.DeleteMark;
import com.di.kit.SqlProvider.Id;

/**
 * 支付信息
 * 
 * @author MvcGenerator by d
 * @date 2018-06-05 23:05
 */
public class PayInfo implements Serializable {
	private static final long serialVersionUID = 1004016448609189888L;
	@Id
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 支付的标题
	 */
	private String title;
	/**
	 * 支付单号：支付机构返回的交易号
	 */
	private String payNo;
	/**
	 * 支付的内容
	 */
	private String content;
	/**
	 * 支付金额：分
	 */
	private Integer amount;
	/**
	 * 支付渠道：0支付宝；1微信；2银联
	 */
	private Byte channel;
	/**
	 * 发送请求的内容
	 */
	private String request;
	/**
	 * 响应内容
	 */
	private String response;
	/**
	 * 支付状态：0待支付;1支付成功;2支付失败;
	 */
	private Byte status;
	/**
	 * 前台回跳地址
	 */
	private String jumpUrl;
	/**
	 * 异步通知地址
	 */
	private String notifyUrl;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	private Date createTime;
	@DeleteMark
	private Integer delFlag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Byte getChannel() {
		return channel;
	}

	public void setChannel(Byte channel) {
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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}