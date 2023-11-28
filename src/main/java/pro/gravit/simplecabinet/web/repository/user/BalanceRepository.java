package pro.gravit.simplecabinet.web.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.model.user.UserBalance;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<UserBalance, Long> {
    Page<UserBalance> findUserBalanceByUser(User user, Pageable pageable);

    Optional<UserBalance> findUserBalanceByUserAndCurrency(User user, String currency);
}
