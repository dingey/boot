package com.d.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(value = "/websocket")
public class OnlineWebSocket {
	Logger logger = LoggerFactory.getLogger(OnlineWebSocket.class);
	private static AtomicInteger onlineCount = new AtomicInteger(0);
	private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();
	private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	@OnOpen
	public void onOpen(Session session) {
		sessions.set(session);
		sessionMap.put(session.getId(), session);
		logger.info("【" + session.getId() + "】连接上服务器======当前在线人数【" + onlineCount.incrementAndGet() + "】");
		sendMsg(session, "连接服务器成功！");
	}

	@OnClose
	public void onClose(Session session) {
		sessionMap.remove(session.getId());
		logger.info("【" + session.getId() + "】退出了连接======当前在线人数【" + onlineCount.decrementAndGet() + "】");
	}

	// 接收消息 客户端发送过来的消息
	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("【" + session.getId() + "】客户端的发送消息======内容【" + message + "】");
		String[] split = message.split(",");
		String sessionId = split[0];
		Session ss = sessionMap.get(sessionId);
		if (ss != null) {
			String msgTo = "【" + session.getId() + "】发送给【您】的消息:\n【" + split[1] + "】";
			String msgMe = "【我】发送消息给【" + ss.getId() + "】:\n" + split[1];
			sendMsg(ss, msgTo);
			sendMsg(session, msgMe);
		} else {
			for (Session s : sessionMap.values()) {
				if (!s.getId().equals(session.getId())) {
					sendMsg(s, "【" + session.getId() + "】发送给【您】的广播消息:\n【" + message + "】");
				} else {
					sendMsg(session, "【我】发送广播消息给大家\n" + message);
				}
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable e) {
		logger.error("发生异常!", e);
	}

	// 统一的发送消息方法
	public synchronized void sendMsg(Session session, String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
