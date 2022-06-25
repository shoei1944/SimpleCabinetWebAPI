package pro.gravit.simplecabinet.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.dto.PageDto;
import pro.gravit.simplecabinet.web.dto.UserDto;
import pro.gravit.simplecabinet.web.dto.UserGroupDto;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.model.UserGroup;
import pro.gravit.simplecabinet.web.service.DtoService;
import pro.gravit.simplecabinet.web.service.UserAssetService;
import pro.gravit.simplecabinet.web.service.UserGroupService;
import pro.gravit.simplecabinet.web.service.UserService;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class
UserController {
    @Autowired
    private UserService service;
    @Autowired
    private UserGroupService groupService;
    @Autowired
    private UserAssetService userAssetService;
    @Autowired
    private DtoService dtoService;

    @GetMapping("/id/{userId}")
    public UserDto getById(@PathVariable long userId, @RequestParam boolean assets) {
        var optional = assets ? service.findByIdFetchAssets(userId) : service.findById(userId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return dtoService.toPublicUserDto(optional.get());
    }

    @DeleteMapping("/id/{userId}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteStatus(@PathVariable long userId) {
        var optional = service.findById(userId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var user = optional.get();
        user.setStatus(null);
        service.save(user);
    }

    @DeleteMapping("/id/{userId}/asset/{assetName}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteStatus(@PathVariable long userId, @PathVariable String assetName) {
        var optional = userAssetService.findByUserAndName(service.getReference(userId), assetName.toLowerCase(Locale.ROOT));
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User/Asset not found");
        }
        var asset = optional.get();
        userAssetService.delete(asset);
    }

    @DeleteMapping("/id/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUserById(@PathVariable long userId) {
        var optional = service.findById(userId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        service.delete(optional.get());
    }


    @PutMapping("/id/{userId}/group/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserGroupDto addGroup(@PathVariable long userId, @PathVariable String name, @RequestBody AddGroupRequest request) {
        var optional = service.findById(userId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        UserGroup group = new UserGroup();
        group.setGroupName(name);
        group.setStartDate(LocalDateTime.now());
        group.setEndDate(request.days <= 0 ? null : LocalDateTime.now().plusDays(request.days));
        group.setUser(optional.get());
        group.setPriority(request.priority);
        groupService.save(group);
        return new UserGroupDto(group);
    }

    @DeleteMapping("/id/{userId}/group/{name}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteGroup(@PathVariable long userId, @PathVariable String name) {
        var optional = service.findById(userId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var group = groupService.findByGroupNameAndUser(name, optional.get());
        if (group.isEmpty()) {
            throw new EntityNotFoundException("Group not found");
        }
        groupService.delete(group.get());
    }

    @GetMapping("/id/{userId}/group/{name}")
    public UserGroupDto getGroup(@PathVariable long userId, @PathVariable String name) {
        var optional = service.findById(userId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        var group = groupService.findByGroupNameAndUser(name, optional.get());
        if (group.isEmpty()) {
            throw new EntityNotFoundException("Group not found");
        }
        return new UserGroupDto(group.get());
    }

    public record AddGroupRequest(long days, int priority) {

    }

    @GetMapping("/name/{name}")
    public UserDto getByUsername(@PathVariable String name, @RequestParam boolean assets) {
        var optional = assets ? service.findByUsernameFetchAssets(name) : service.findByUsername(name);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return dtoService.toPublicUserDto(optional.get());
    }

    @GetMapping("/uuid/{uuid}")
    public UserDto getByUUID(@PathVariable UUID uuid, @RequestParam boolean assets) {
        var optional = assets ? service.findByUuidFetchAssets(uuid) : service.findByUUID(uuid);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return dtoService.toPublicUserDto(optional.get());
    }

    @GetMapping("/page/{pageId}")
    public PageDto<UserDto> getPage(@PathVariable int pageId) {
        var list = service.findAll(PageRequest.of(pageId, 10));
        return new PageDto<>(list.map(dtoService::toMiniUserDto));
    }
}
