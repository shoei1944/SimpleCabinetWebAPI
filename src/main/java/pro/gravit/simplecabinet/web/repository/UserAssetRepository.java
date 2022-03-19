package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserAsset;

import java.util.List;
import java.util.Optional;

public interface UserAssetRepository extends JpaRepository<UserAsset, Long> {
    List<UserAsset> findAllByUser(User user);

    Optional<UserAsset> findByUserAndName(User user, String name);
}
