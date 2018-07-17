package com.d.base;

import java.io.Serializable;
import java.util.Date;

import com.di.kit.SqlProvider.DeleteMark;
import com.di.kit.SqlProvider.Id;
import com.di.kit.SqlProvider.Transient;

/**
 * @author di
 */
@SuppressWarnings("unused")
public class BaseEntity<T> implements Serializable {
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
    @Transient
    private String orderBy;

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

    @java.beans.Transient
    public boolean isNewRecord() {
        return this.getId() == null || (this.newRecord != null && this.newRecord) || this.getId() <= 0;
    }

    public void setNewRecord(boolean newRecord) {
        this.newRecord = newRecord;
    }

    @java.beans.Transient
    public String getOrderBy() {
        return orderBy;
    }

    public T orderBy(String... fields) {
        if (fields == null || fields.length == 0) {
            throw new RuntimeException("orderBy parameter fields can't be empty.");
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            for (int i = 0; i < fields.length; i++) {
                orderBy += "," + fields[i];
            }
        } else {
            orderBy = "";
            for (int i = 0; i < fields.length; i++) {
                orderBy += fields[i];
                if (i < fields.length - 1) {
                    orderBy += ",";
                }
            }
        }
        return (T) this;
    }
}
