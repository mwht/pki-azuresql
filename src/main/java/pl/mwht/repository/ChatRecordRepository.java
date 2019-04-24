package pl.mwht.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.mwht.entity.ChatRecord;

import java.util.List;

@Repository
public interface ChatRecordRepository extends CrudRepository<ChatRecord, Integer> {
    public List<ChatRecord> findChatRecordsByRoomId(String roomId);
}
