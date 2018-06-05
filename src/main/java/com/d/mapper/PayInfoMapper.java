package com.d.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.d.entity.PayInfo;
import com.di.kit.SqlProvider;

/**
 * 支付信息Mapper接口
 * 
 * @author MvcGenerator by d
 * @date 2018-06-05 23:05
 */
public interface PayInfoMapper {
	@SelectProvider(type = SqlProvider.class, method = "listByIds")
	List<PayInfo> listByIds(Class<PayInfo> entity, List<Integer> ids);

	@SelectProvider(type = SqlProvider.class, method = "getById")
	PayInfo get(Class<PayInfo> entity, Serializable id);
}