package pro.gravit.simplecabinet.web.service.user;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.model.user.User;
import pro.gravit.simplecabinet.web.model.user.UserGroup;
import pro.gravit.simplecabinet.web.model.user.UserPermission;
import pro.gravit.simplecabinet.web.model.user.UserSession;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDetailsService {
    @Autowired
    private UserService service;
    @Autowired
    private UserPermissionService permissionService;
    @Autowired
    private EntityManager em;

    public CabinetUserDetails create(long userId, String username, List<String> roles, String client, long sessionId) {
        return new CabinetUserDetails(userId, null, username, roles, client, sessionId);
    }

    public CabinetUserDetails create(UserSession session) {
        return new CabinetUserDetails(session.getUser().getId(), null, session.getUser().getUsername(), collectUserRoles(session.getUser()), session.getClient(), session.getId());
    }

    @Transactional
    public List<String> collectUserRoles(User user) {
        var groups = service.getUserGroups(user);
        return groups.stream().sorted(Comparator.comparingLong(UserGroup::getPriority)).map(UserGroup::getGroupName).collect(Collectors.toList());
    }

    @Transactional
    public Map<String, UserPermission> collectUserPermissionsEx(Collection<UserGroup> groups) {
        List<String> groupNames = new ArrayList<>(groups.stream().map(UserGroup::getGroupName).toList());
        groupNames.add("USER"); // Default group
        var permissions = permissionService.findByGroupNames(groupNames);
        Function<String, UserGroup> findGroup = name -> {
            for (var e : groups) {
                if (e.getGroupName().equals(name)) {
                    return e;
                }
            }
            return null;
        };
        permissions.sort(Comparator.comparingLong(x -> -findGroup.apply(x.getGroupName()).getPriority()));
        Map<String, UserPermission> map = new HashMap<>();
        for (var p : permissions) {
            map.putIfAbsent(p.getName(), p);
        }
        return map;
    }

    @Transactional
    public Map<String, String> collectUserPermissions(Collection<UserGroup> groups) {
        Map<String, String> map = new HashMap<>();
        for (var p : collectUserPermissionsEx(groups).values()) {
            map.putIfAbsent(p.getName(), p.getValue());
        }
        return map;
    }

    public class CabinetUserDetails implements UserDetails {
        private final long userId;
        private final String password;
        private final String username;
        private final List<GrantedAuthority> authorities;
        private final String client;
        private final long sessionId;
        private Map<String, UserPermission> permissions;

        public CabinetUserDetails(long userId, String password, String username, List<String> roles, String client, long sessionId) {
            this.userId = userId;
            this.password = password;
            this.username = username;
            this.authorities = roles.stream().map(e -> new SimpleGrantedAuthority("ROLE_".concat(e.toUpperCase(Locale.ROOT)))).collect(Collectors.toList());
            this.client = client;
            this.sessionId = sessionId;
        }

        public long getUserId() {
            return userId;
        }

        public String getClient() {
            return client;
        }

        public long getSessionId() {
            return sessionId;
        }

        public Map<String, UserPermission> getPermissions() { // Optimize this
            if (permissions == null) {
                var user = service.getReference(userId);
                permissions = collectUserPermissionsEx(service.getUserGroups(user));
            }
            return permissions;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
