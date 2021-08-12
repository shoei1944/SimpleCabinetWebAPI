package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.model.PaymentId;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public List<PaymentId> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<PaymentId> findByUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    public Optional<PaymentId> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public Iterable<PaymentId> findAll() {
        return repository.findAll();
    }
}
