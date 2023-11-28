package pro.gravit.simplecabinet.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.dto.PageDto;
import pro.gravit.simplecabinet.web.dto.user.UserPermissionDto;
import pro.gravit.simplecabinet.web.exception.EntityNotFoundException;
import pro.gravit.simplecabinet.web.model.user.UserPermission;
import pro.gravit.simplecabinet.web.service.user.UserGroupService;
import pro.gravit.simplecabinet.web.service.user.UserPermissionService;
import pro.gravit.simplecabinet.web.service.user.UserService;

@RestController
@RequestMapping("/admin/permissions")
public class AdminPermissionsController {
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserPermissionService userPermissionService;

    @GetMapping("/by/group/{name}/page/{pageId}")
    public PageDto<UserPermissionDto> getByGroup(@PathVariable String name, @PathVariable int pageId) {
        var list = userPermissionService.findByGroupName(name, PageRequest.of(pageId, 10));
        return new PageDto<>(list.map(UserPermissionDto::new));
    }

    @GetMapping("/by/id/{id}")
    public UserPermissionDto getById(@PathVariable long id) {
        var optional = userPermissionService.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("UserPermission not found");
        }
        return new UserPermissionDto(optional.get());
    }

    @PostMapping("/add")
    public UserPermissionDto add(@RequestBody AddPermissionRequest request) {
        UserPermission permission = new UserPermission();
        permission.setGroupName(request.groupName);
        permission.setName(request.name);
        permission.setValue(request.value);
        userPermissionService.save(permission);
        return new UserPermissionDto(permission);
    }

    public record AddPermissionRequest(String groupName, String name, String value) {

    }
}
