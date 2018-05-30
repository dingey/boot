package com.d.service;

import java.util.HashMap;
import java.util.Map;

public interface IPay {
	void createPay(String title, String content, Integer amount)
			throws CreatePayException;

	void notifyHandle();

	public static class CreatePayException extends Exception {
		private static final long serialVersionUID = -4401985987741361697L;
	}

	public static class PayNotifyException extends Exception {
		private static final long serialVersionUID = -5650598369803899651L;
	}

	public static class PayFactory {
		private static Map<Integer, IPay> register = new HashMap<>();

		private PayFactory() {
		}

		public static IPay getPay(Integer payType) {
			return register.get(payType);
		}

		@Deprecated
		public static <T extends IPay> void register(Integer payType,
				T payService) {
			register.put(payType, payService);
		}

		public void register(PayType payType, IPay payService) {
			register.put(payType.ordinal(), payService);
		}
	}

	static enum PayType {
		ALIPAY, WEIXIN;
	}
}
