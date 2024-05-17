package com.softwarecooperative.softwareciooperative.ws;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/teacher/notice/{cid}")
@Slf4j
public class TeacherNotificationWs {

    //存放会话对象
    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("cid") String cid) {
        log.info("{}客户端登入", cid);
        sessionMap.put(cid, session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("cid") String cid) {
        log.info("收到来自客户端：{}的信息: {}", cid, message);
        sendToAllClient("我测你的码");
    }

    @OnClose
    public void onClose(@PathParam("cid") String cid) throws IOException {
        Session session = sessionMap.remove(cid);
        session.close();
        log.info("{}客户端连接断开", cid);
    }
    
    @OnError
    public void onError(@PathParam("cid") String cid, Throwable error) {
        try {
            Session session = sessionMap.remove(cid);
            session.close();
        } catch (Exception ignored) {}

        log.info("傻逼{}客户端又出错", cid);
    }

    public static void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                //服务器向客户端发送消息
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendToOneClient(Integer cid, String message) throws IOException {
        Session session = sessionMap.get(cid.toString());
        if (session == null)
            return;
        session.getBasicRemote().sendText(message);
    }

}
