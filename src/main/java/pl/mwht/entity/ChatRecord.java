package pl.mwht.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "chat")
public class ChatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @Column(name = "room_ID", length = 10)
    private String roomId;

    @Column(name = "mesg", length = 100)
    private String mesg;

    protected enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    };

    private MessageType messageType;

    public ChatRecord() {
        this(null, null, null, null);
    }

    public ChatRecord(Integer id, String roomId, String mesg, MessageType messageType) {
        this.id = id;
        this.roomId = roomId;
        this.mesg = mesg;
        this.messageType = messageType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
