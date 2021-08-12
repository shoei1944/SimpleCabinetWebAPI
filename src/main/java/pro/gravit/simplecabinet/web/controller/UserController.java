package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.gravit.simplecabinet.web.dto.UserDto;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.service.UserService;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/id/{id}")
    public UserDto getById(@PathVariable long id) {
        var optional = service.findById(id);
        if(optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return new UserDto(optional.get());
    }

    @GetMapping("/name/{name}")
    public UserDto getByUsername(@PathVariable String name) {
        var optional = service.findByUsername(name);
        if(optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return new UserDto(optional.get());
    }

    @GetMapping("/uuid/{uuid}")
    public UserDto getByUUID(@PathVariable UUID uuid) {
        var optional = service.findByUUID(uuid);
        if(optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return new UserDto(optional.get());
    }

    @GetMapping("/page/{pageId}")
    public Iterable<UserDto> getPage(@PathVariable int pageId) {
        var list = service.findAll(PageRequest.of(pageId, 10));
        return list.stream().map(UserDto::new).collect(Collectors.toList());
    }
}
