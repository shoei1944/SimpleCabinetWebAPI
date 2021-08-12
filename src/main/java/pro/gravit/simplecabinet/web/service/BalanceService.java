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

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository repository;
    @Autowired
    private BalanceTransactionsRepository transactionsRepository;
    @Autowired
    private ExchangeRateService exchangeRateService;

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
    public BalanceTransaction transferMoney(User user, UserBalance from, UserBalance to, double count, String comment, boolean munticurrency) throws BalanceException {
        if (from.getCurrency() == null || to.getCurrency() == null) {
            throw new BalanceException("Currency is null");
        }
        double k = 1.0;
        if (munticurrency) {
            Optional<ExchangeRate> rate = findExchangeRate(from.getCurrency(), to.getCurrency());
            if (rate.isPresent()) {
                k = rate.get().getValue();
            } else {
                throw new BalanceException(String.format("From %s to %s transfer denied", from.getCurrency(), to.getCurrency()));
            }
            munticurrency = from.getCurrency().equals(to.getCurrency());
        } else {
            if (!from.getCurrency().equals(to.getCurrency())) {
                throw new BalanceException("Source currency and destination currency do not match");
            }
        }
        if (count <= 0) {
            throw new BalanceException("Count is negative");
        }
        if (from.getBalance() < count) {
            throw new BalanceException("Insufficient funds");
        }
        var toValue = count * k;
        var transaction = new BalanceTransaction();
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setComment(comment);
        transaction.setFromCount(count);
        transaction.setToCount(toValue);
        transaction.setUser(user);
        transaction.setMulticurrency(munticurrency);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionsRepository.save(transaction);
        to.setBalance(to.getBalance() + toValue);
        from.setBalance(from.getBalance() - count);
        repository.save(to);
        repository.save(from);
        return transaction;
    }

    @Transactional
    public BalanceTransaction addMoney(UserBalance to, double count, String comment) throws BalanceException {
        if (count <= 0) {
            throw new BalanceException("Count is negative");
        }
        var transaction = new BalanceTransaction();
        transaction.setFrom(null);
        transaction.setTo(to);
        transaction.setUser(null);
        transaction.setFromCount(count);
        transaction.setComment(comment);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionsRepository.save(transaction);
        to.setBalance(to.getBalance() + count);
        repository.save(to);
        return transaction;
    }

    @Transactional
    public BalanceTransaction removeMoney(UserBalance from, double count, String comment) throws BalanceException {
        if (count <= 0) {
            throw new BalanceException("Count is negative");
        }
        if (from.getBalance() - count < 0) {
            throw new BalanceException("Insufficient funds");
        }
        var transaction = new BalanceTransaction();
        transaction.setFrom(from);
        transaction.setTo(null);
        transaction.setUser(null);
        transaction.setFromCount(-count);
        transaction.setComment(comment);
        transaction.setCreatedAt(LocalDateTime.now());
        transactionsRepository.save(transaction);
        from.setBalance(from.getBalance() - count);
        repository.save(from);
        return transaction;
    }
}
