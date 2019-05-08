package pl.mwht.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mwht.entity.ChatRecord;

import java.util.List;

@Repository
public interface ChatRecordRepository extends CrudRepository<ChatRecord, Integer> {
    public List<ChatRecord> findChatRecordsByRoomId(String roomId);

    @Modifying
    @Transactional
    @Query("delete from ChatRecord cr where cr.roomId = ?1")
    public void deleteChatRecordsByRoomId(String roomId);
}
