package com.d;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.d.entity.PayInfo;
import com.d.mapper.PayInfoMapper;
import com.di.kit.JsonUtil;

public class PayInfoTest extends ApplicationTests {
	@Autowired
	PayInfoMapper payInfoMapper;

	@Override
	public void test() {
	}

	void listByIds() {
		List<PayInfo> ps = payInfoMapper.listByIds(PayInfo.class,
				Arrays.asList(1, 2));
		System.out.println(JsonUtil.toJson(ps));
	}

	void get() {
		PayInfo payInfo = payInfoMapper.get(PayInfo.class, 1);
		System.out.println(JsonUtil.toJson(payInfo));
	}
}
