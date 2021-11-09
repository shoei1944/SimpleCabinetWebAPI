package pro.gravit.simplecabinet.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pro.gravit.simplecabinet.web.controller.admin.AdminHardwareController;
import pro.gravit.simplecabinet.web.model.HardwareId;
import pro.gravit.simplecabinet.web.repository.HardwareIdRepository;

import java.util.Optional;

@Service
public class HardwareIdService {
    @Autowired
    private HardwareIdRepository repository;

    public <S extends HardwareId> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<HardwareId> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public HardwareId getById(Long aLong) {
        return repository.getById(aLong);
    }

    public Optional<HardwareId> findByHardware(AdminHardwareController.HardwareSearchRequest id) {
        return repository.findByHwDiskId(id.hwDiskId());
    }

    public Optional<HardwareId> findByPublicKey(byte[] publicKey) {
        return repository.findByPublicKey(publicKey);
    }

    public Page<HardwareId> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
