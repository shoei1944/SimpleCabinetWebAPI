package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.gravit.simplecabinet.web.model.Server;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findByName(String name);

    @Query("update Server s set s.online = ?2, s.maxOnline = ?3, s.tps = ?4, s.users = ?5, s.updateDate = ?6 where s.name = ?1")
    void ping(String name, int online, int maxOnline, int tps, List<String> users, LocalDateTime updateDate);

    @Query("update Server s set s.online = ?2, s.maxOnline = ?3, s.tps = ?4, s.users = ?5, s.updateDate = ?6 where s.id = ?1")
    void ping(long id, int online, int maxOnline, int tps, List<String> users, LocalDateTime updateDate);
}
