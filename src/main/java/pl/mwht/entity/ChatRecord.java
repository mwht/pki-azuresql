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
}
