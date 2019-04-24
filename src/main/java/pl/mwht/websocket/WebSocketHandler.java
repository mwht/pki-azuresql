package pl.mwht.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Logger.getAnonymousLogger().log(Level.INFO, "New WebSocket connection arrived (" + session.getRemoteAddress().toString() + ")");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Logger.getAnonymousLogger().log(Level.INFO, "WebSocket connection closed (" + session.getRemoteAddress().toString() + ")");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Logger.getAnonymousLogger().log(Level.SEVERE, "WebSocket connection error (" + session.getRemoteAddress().toString() + ")");
        exception.printStackTrace();
    }
}
