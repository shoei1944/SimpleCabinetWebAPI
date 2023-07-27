package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.exception.BalanceException;
import pro.gravit.simplecabinet.web.model.BalanceTransaction;
import pro.gravit.simplecabinet.web.model.ExchangeRate;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserBalance;
import pro.gravit.simplecabinet.web.repository.BalanceRepository;
import pro.gravit.simplecabinet.web.repository.BalanceTransactionsRepository;
import pro.gravit.simplecabinet.web.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository repository;
    @Autowired
    private BalanceTransactionsRepository transactionsRepository;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<ExchangeRate> findExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRateService.findByCurrency(fromCurrency, toCurrency);
    }

    public Page<UserBalance> findUserBalanceByUser(User user, Pageable pageable) {
        return repository.findUserBalanceByUser(user, pageable);
    }

    public Page<BalanceTransaction> findAllByBalance(UserBalance balance, Pageable pageable) {
        return transactionsRepository.findAllByBalance(balance, pageable);
    }

    public Optional<UserBalance> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public UserBalance getReference(Long aLong) {
        return repository.getById(aLong);
    }

    public UserBalance create(User user, String currency) {
        UserBalance balance = new UserBalance();
        balance.setUser(user);
        balance.setBalance(0.0);
        balance.setCurrency(currency);
        repository.save(balance);
        return balance;
    }

    public Optional<UserBalance> findUserBalanceByUserAndCurrency(User user, String currency) {
        return repository.findUserBalanceByUserAndCurrency(user, currency);
    }

    public UserBalance findOrCreateUserBalanceByUserAndCurrency(User user, String currency) {
        return findUserBalanceByUserAndCurrency(user, currency).orElseGet(() -> create(user, currency));
    }

    @Transactional
    public BalanceTransaction transfer(Long userId, Long fromId, Long toId, double fromCount, double toCount, boolean multiCurrency, String comment) {
        if (fromId != null && (Double.isNaN(fromCount) || Double.isInfinite(fromCount) || fromCount < 0.0)) {
            throw new BalanceException("Illegal fromCount (NaN, Inf, Neg)");
        }
        if (toId != null && (Double.isNaN(toCount) || Double.isInfinite(toCount) || toCount < 0.0)) {
            throw new BalanceException("Illegal toCount (NaN, Inf, Neg)");
        }
        if (fromId == null && toId == null) {
            throw new BalanceException("Illegal arguments: fromId and toId is null");
        }
        if (fromId != null) {
            Query fromUpdate = entityManager.createQuery("update UserBalance f set f.balance = f.balance - :fromCount where f.id = :id and f.balance >= :fromCount");
            fromUpdate.setParameter("fromCount", fromCount);
            fromUpdate.setParameter("id", fromId);
            int updated = fromUpdate.executeUpdate();
            if (updated != 1) {
                throw new BalanceException("Insufficient funds");
            }
        }
        if (toId != null) {
            Query toUpdate = entityManager.createQuery("update UserBalance f set f.balance = f.balance + :toCount where f.id = :id");
            toUpdate.setParameter("toCount", toCount);
            toUpdate.setParameter("id", toId);
            int updated = toUpdate.executeUpdate();
            if (updated != 1) {
                throw new BalanceException("Illegal state: target id not found");
            }
        }
        BalanceTransaction transaction = new BalanceTransaction();
        transaction.setUser(userId == null ? null : userRepository.getById(userId));
        transaction.setFrom(fromId == null ? null : repository.getById(fromId));
        transaction.setTo(toId == null ? null : repository.getById(toId));
        transaction.setFromCount(fromId == null ? 0.0 : -fromCount);
        transaction.setToCount(toId == null ? 0.0 : toCount);
        transaction.setMulticurrency(multiCurrency);
        transaction.setComment(comment);
        transactionsRepository.save(transaction);
        return transaction;
    }

    @Transactional
    public BalanceTransaction transfer(Long userId, long fromId, long toId, ExchangeRate rate, double count, String comment) {
        return transfer(userId, fromId, toId, count, count * rate.getValue(), true, comment);
    }

    @Transactional
    public BalanceTransaction transfer(Long userId, long fromId, long toId, String fromCurrency, String toCurrency, double count, String comment, boolean strictRate) {
        if (fromCurrency == null || toCurrency == null) {
            throw new BalanceException("fromCurrency or toCurrency is null");
        }
        Optional<ExchangeRate> rate = findExchangeRate(fromCurrency, toCurrency);
        if (rate.isEmpty()) {
            if (!fromCurrency.equals(toCurrency) || strictRate) {
                throw new BalanceException(String.format("Can't convert money from %s to %s", fromCurrency, toCurrency));
            }
            return transfer(userId, fromId, toId, count, count, false, comment);
        }
        return transfer(userId, fromId, toId, count, count * rate.get().getValue(), true, comment);
    }

    @Transactional
    public BalanceTransaction addMoney(long toId, double count, String comment) throws BalanceException {
        return transfer(null, null, toId, 0.0, count, false, comment);
    }

    @Transactional
    public BalanceTransaction removeMoney(long fromId, double count, String comment) throws BalanceException {
        return transfer(null, fromId, null, count, 0.0, false, comment);
    }

    @Transactional
    public BalanceTransaction addMoney(UserBalance to, double count, String comment) throws BalanceException {
        return addMoney(to.getId(), count, comment);
    }

    @Transactional
    public BalanceTransaction removeMoney(UserBalance from, double count, String comment) throws BalanceException {
        return removeMoney(from.getId(), count, comment);
    }
}
