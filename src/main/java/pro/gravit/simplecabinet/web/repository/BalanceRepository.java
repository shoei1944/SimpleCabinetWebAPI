package pro.gravit.simplecabinet.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserBalance;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<UserBalance, Long> {
    Page<UserBalance> findUserBalanceByUser(User user, Pageable pageable);

    Optional<UserBalance> findUserBalanceByUserAndCurrency(User user, String currency);
}
