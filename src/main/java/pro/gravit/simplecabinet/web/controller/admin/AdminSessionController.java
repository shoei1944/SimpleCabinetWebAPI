package pro.gravit.simplecabinet.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.dto.PageDto;
import pro.gravit.simplecabinet.web.dto.UserSessionDto;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.service.SessionService;
import pro.gravit.simplecabinet.web.service.UserService;

@RestController
@RequestMapping("/admin/session")
public class AdminSessionController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @GetMapping("/id/{id}")
    public UserSessionDto getById(@PathVariable long id) {
        var optional = sessionService.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("UserSession not found");
        }
        return new UserSessionDto(optional.get());
    }

    @PostMapping("/id/{id}/deactivate")
    public void deactivateById(@PathVariable long id) {
        var optional = sessionService.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("UserSession not found");
        }
        var session = optional.get();
        session.setDeleted(true);
        sessionService.save(session);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable long id) {
        var optional = sessionService.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("UserSession not found");
        }
        var session = optional.get();
        sessionService.delete(session);
    }

    @GetMapping("/usersession/{userId}/page/{pageId}")
    public PageDto<UserSessionDto> getPageByUser(@PathVariable long userId, @PathVariable int pageId) {
        var user = userService.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var list = sessionService.findByUser(user.get(), PageRequest.of(pageId, 10));
        return new PageDto<>(list.map(UserSessionDto::new));
    }

    @PostMapping("/usersession/{userId}/deactivateall")
    public void exitAllByUser(@PathVariable long userId) {
        var user = userService.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        sessionService.deactivateAllByUser(user.get());
    }
}
