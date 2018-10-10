package com.d;

import org.junit.Test;

import com.d.entity.PayInfo;
import com.di.kit.SqlProvider;

@SuppressWarnings("unused")
public class SQLTest {
	// @Test
	public void test() {
		SqlProvider sp = new SqlProvider();
		PayInfo p = new PayInfo();
		String delete = sp.delete(p);
		System.out.println(delete);
		String deleteMark = sp.deleteMark(p);
		System.out.println(deleteMark);
		String update = sp.update(p);
		System.out.println(update);
	}
}
