package com.msg.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import com.msg.model.ChatMessage;
import com.msg.model.State;

//(L)�p�d�Ѯv�v���ɶ�:2020/10/06 16:18
//(L)���[�Wannotation�AwebSocket�|��Ĳ�o�ɰ���Ӭq�{��
//(L)�i�Ѧ� https://javaee.github.io/javaee-spec/javadocs/javax/websocket/package-summary.html
//(L)@OnOpen   :to decorate a Java method that wishes to be called when a new web socket session is open
//(L)@OnMessage: to make a Java method receive incoming web socket messages.
//(L)@OnError  : to decorate a Java method that wishes to be called in order to handle errors.
//(L)@OnClose  :  to decorate a Java method that wishes to be called when a web socket session is closing.
//(L)@ClientEndpoint:to denote that a POJO is a web socket client and can be deployed as such.

//(L)@ServerEndpoint�O�i�D�e���A�o��class�n�Q���@��webSocket���s�����I�A�ӬA������value�N�O�o�Ӻ��I���s��path(�������}������)
@ServerEndpoint("/FriendWS/{mem_id}")
public class FriendWS {

	// (L)�ϥ�ConcurrentHashMap�i�H�קKhashmap�F�칡�Mresize�Უ�ͦ����A�æP�ɰ���P�B��w(�į�]��ϥ�hashtable��synchronizedMap�n)
	// (L)�Ѧ� https://zhuanlan.zhihu.com/p/31614308
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	// (L)�s�u�}�Ү�
	// (L)@PathParam("mem_id")�i�H���oURI�W��mem_id�ѼƤ��e�A�Ѧ�
	// https://medium.com/1developer/spring-requestparam-vs-queryparam-vs-pathvariable-vs-pathparam-7c5655e541ad
	// (L)URL�O���|�AURI�O�귽��ڦ�m�A�Ѧ�
	// https://codertw.com/%E5%89%8D%E7%AB%AF%E9%96%8B%E7%99%BC/53793/
	@OnOpen
	public void onOpen(@PathParam("mem_id") String mem_id, Session userSession) throws IOException {

		// (L)�N�i�s�W�u���|��id�j�[�J�i�W�u�����|���M��j��
		sessionsMap.put(mem_id, userSession);

		// (L)�N�i�W�u�����|���M��j��key�ȳ����X(�Y�o��ثe�b�u�W���|��IDs)
		Set<String> mem_ids = sessionsMap.keySet();

		State stateMessage = new State("open", mem_id, mem_ids);
		String stateMessageJson = gson.toJson(stateMessage);

		// (L)���X�i�W�u�����|���M��j��value(�Y���osession)
		// (L)�p�Gsession�٦��ġA�h�P�B�e�X�T����r
		// (L)getAsyncRemote(���B)�PgetBasicRemote(�P�B)�A�Ѧ� https://blog.csdn.net/who_is_xiaoming/article/details/53287691
		// (L)�ϥ�getAsyncRemote�i�קK�]���P�B�ӥi��X�{�T������B����IllegalStateException�����p
		Collection<Session> sessions = sessionsMap.values();
		for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}
		
		String text = String.format("Session ID = %s, connected; mem_id = %s%nusers: %s", userSession.getId(), mem_id,
				mem_ids);
		System.out.println(text);
	}

	// ����T����
	@OnMessage
	public void onMessage(Session userSession, String message) {
		
		//�NJson�����ର���w��java����
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		
		//�o�e�T�����|��
		String sender = chatMessage.getSender();
		
		//�����T�����|��
		String receiver = chatMessage.getReceiver();
		
		//�qredis���X���v�T��
		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}
		
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
			JedisHandleMessage.saveChatMessage(sender, receiver, message);
		}
		System.out.println("Message received: " + message);
	}

	// �����~��
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	// �s�u������
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String mem_idClose = null;
		Set<String> mem_ids = sessionsMap.keySet();
		for (String mem_id : mem_ids) {
			if (sessionsMap.get(mem_id).equals(userSession)) {
				mem_idClose = mem_id;
				sessionsMap.remove(mem_id); // �q�Ҧ���e�b�u�W���|���W�椺�簣
				break;
			}
		}

		if (mem_idClose != null) {
			State stateMessage = new State("close", mem_idClose, mem_ids);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), mem_ids);
		System.out.println(text);
	}
}
