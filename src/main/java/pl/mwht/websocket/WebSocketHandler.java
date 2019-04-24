package pl.mwht.websocket;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pl.mwht.entity.ChatRecord;
import pl.mwht.repository.ChatRecordRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatRecordRepository chatRecordRepository;

    private Map<WebSocketSession, String> usersInRoomsBinding = new HashMap<WebSocketSession, String>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();
        Logger.getAnonymousLogger().log(Level.INFO, "Received payload: " + messagePayload);

        JSONObject inboundObject = new JSONObject(messagePayload);
        try {
            String roomId = inboundObject.getString("roomId");
            String mesg = inboundObject.getString("mesg");

            ChatRecord chatRecord = new ChatRecord(null, roomId, mesg);
            chatRecordRepository.save(chatRecord);
        } catch (JSONException jsone) {
            Logger.getAnonymousLogger().log(Level.SEVERE, jsone.toString());
            jsone.printStackTrace();
        }
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
