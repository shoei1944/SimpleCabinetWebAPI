package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.dto.PageDto;
import pro.gravit.simplecabinet.web.dto.ServerDto;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.service.ServerService;

import java.util.List;

@RestController
@RequestMapping("/servers")
public class ServersController {
    @Autowired
    private ServerService service;

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ServerDto create(@RequestBody CreateServerRequest request) {
        var server = service.create(request.name(), request.displayName());
        return new ServerDto(server);
    }

    @GetMapping("/id/{serverId}")
    public ServerDto getById(@PathVariable long serverId) {
        var optional = service.findById(serverId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Server not found");
        }
        return new ServerDto(optional.get());
    }

    @PostMapping("/id/{serverId}/ping")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void pingById(@PathVariable long serverId, @RequestBody PingRequest request) {
        service.ping(serverId, request.online(), request.maxOnline(), request.tps(), request.users());
    }

    @GetMapping("/name/{name}")
    public ServerDto getByName(@PathVariable String name) {
        var optional = service.findByName(name);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Server not found");
        }
        return new ServerDto(optional.get());
    }

    @PostMapping("/name/{name}/ping")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void pingByName(@PathVariable String name, @RequestBody PingRequest request) {
        service.ping(name, request.online(), request.maxOnline(), request.tps(), request.users());
    }

    @GetMapping("/page/{pageId}")
    public PageDto<ServerDto> getPage(@PathVariable int pageId) {
        var list = service.findAll(PageRequest.of(pageId, 10));
        return new PageDto<>(list.map(ServerDto::new));
    }

    public record PingRequest(int online, int maxOnline, int tps, List<String> users) {

    }

    public record CreateServerRequest(String name, String displayName) {

    }
}
