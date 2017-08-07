package com.d.base;

import java.io.Serializable;

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
	private long id;
	private int delFlag;
	@Transient
	private boolean newRecord;

	public BaseEntity() {
		super();
		this.id = 0;
		this.delFlag = 0;
		this.newRecord = false;
	}

	public BaseEntity(long id) {
		super();
		this.id = id;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public long getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	public T setId(long id) {
		this.id = id;
		return (T) this;
	}

	public boolean isNewRecord() {
		return this.newRecord || this.getId() <= 0;
	}

	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}

}
