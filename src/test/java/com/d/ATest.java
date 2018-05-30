package com.d;

import org.junit.Test;

import com.di.kit.Conversion;
import com.di.kit.IdDateWorker;
import com.di.kit.IdWorker;

public class ATest {
	@Test
	public void test() {
		System.out.println(IdWorker.nextId());
		System.out.println(IdWorker.nextId());

		System.out.println("--------------------------------------");

		long id = IdWorker.nextId();
		System.out.println(id);
		System.out.println(Long.toHexString(id));
		String compressNumber = Conversion.fromDecimal(id, 62);
		System.out.println("64:" + compressNumber);
		System.out.println(Conversion.toDecimal(compressNumber, 62));

		System.out.println("--------------------------------------");

		System.out.println(IdDateWorker.nextSyncId());
		String id2 = IdDateWorker.nextSyncId();
		System.out.println(id2);
		String nDecimal = Conversion.fromDecimal(Long.valueOf(id2), 62);
		System.out.println(nDecimal);
		System.out.println(Conversion.toDecimal(nDecimal, 62));

	}
}
