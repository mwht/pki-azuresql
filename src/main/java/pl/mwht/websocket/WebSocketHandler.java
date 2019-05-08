package pl.mwht.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import pl.mwht.entity.ChatRecord;
import pl.mwht.repository.ChatRecordRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatRecordRepository chatRecordRepository;

    private Map<WebSocketSession, String> usersInRoomsBinding = new HashMap<WebSocketSession, String>();
    private ObjectMapper objectMapper;

    public WebSocketHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String messagePayload = message.getPayload();
        Logger.getAnonymousLogger().log(Level.INFO, "Received payload: " + messagePayload);

        try {
            ChatRecord chatRecord = objectMapper.readValue(messagePayload, ChatRecord.class);
            chatRecord.setId(null);
            switch (chatRecord.getMessageType()) {
                case CHAT:
                    chatRecordRepository.save(chatRecord);
                    break;
                case JOIN:
                    List<ChatRecord> messagesInRoom;
                    usersInRoomsBinding.put(session, chatRecord.getRoomId());
                    messagesInRoom = chatRecordRepository.findChatRecordsByRoomId(chatRecord.getRoomId());
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(messagesInRoom)));
                    break;
                case LEAVE:
                    break;
            }
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.toString());
            e.printStackTrace();
            session.close();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Logger.getAnonymousLogger().log(Level.INFO, "New WebSocket connection arrived (" + session.getRemoteAddress().toString() + ")");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Logger.getAnonymousLogger().log(Level.INFO, "WebSocket connection closed (" + session.getRemoteAddress().toString() + ")");
        if(usersInRoomsBinding.containsKey(session)) {
            String roomName = usersInRoomsBinding.get(session);
            usersInRoomsBinding.remove(session);
            if(!usersInRoomsBinding.containsValue(roomName)) {

            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        Logger.getAnonymousLogger().log(Level.SEVERE, "WebSocket connection error (" + session.getRemoteAddress().toString() + ")");
        exception.printStackTrace();
    }
}
