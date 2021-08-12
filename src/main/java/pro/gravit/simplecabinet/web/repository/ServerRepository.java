package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.Server;

public interface ServerRepository extends JpaRepository<Server, Long> {
}
