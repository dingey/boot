package com.d.base;

import java.io.Serializable;
import java.util.Date;

import com.d.util.SqlProvider.DeleteMark;
import com.d.util.SqlProvider.Id;
import com.d.util.SqlProvider.Transient;

/**
 * @author di
 */
public class BaseEntity<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int DEL_FLAG_NORMAL = 0;
	public static final int DEL_FLAG_DELETE = 1;
	public static final int DEL_FLAG_AUDIT = 2;
	@Id
	private Integer id;
	private Date createTime;
	@DeleteMark
	private Integer delFlag;
	@Transient
	private Boolean newRecord;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isNewRecord() {
		return this.getId() == null || this.getId() <= 0 || (this.newRecord != null && this.newRecord);
	}

	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}

}
