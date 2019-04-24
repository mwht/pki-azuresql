package pl.mwht.entity;

import javax.persistence.*;

@Entity
@Table(name = "chat")
public class ChatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "room_ID", length = 10)
    private String roomId;

    @Column(name = "mesg", length = 100)
    private String mesg;

    public ChatRecord() {
        this(null, null, null);
    }

    public ChatRecord(Integer id, String roomId, String mesg) {
        this.id = id;
        this.roomId = roomId;
        this.mesg = mesg;
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
}
