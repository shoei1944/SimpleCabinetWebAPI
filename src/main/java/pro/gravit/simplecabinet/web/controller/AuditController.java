package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.dto.AuditDto;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.service.AuditService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/audit")
public class AuditController {
    @Autowired
    private AuditService service;

    @GetMapping("/id/{id}")
    public AuditDto getById(@PathVariable long id) {
        var optional = service.findById(id);
        if(optional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return new AuditDto(optional.get());
    }

    @GetMapping("/page/{pageId}")
    public Iterable<AuditDto> getPage(@PathVariable int pageId) {
        var list = service.findAll(PageRequest.of(pageId, 10));
        return list.stream().map(AuditDto::new).collect(Collectors.toList());
    }
}
