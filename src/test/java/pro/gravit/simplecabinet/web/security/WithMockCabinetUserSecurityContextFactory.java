package pro.gravit.simplecabinet.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import pro.gravit.simplecabinet.web.model.UserSession;
import pro.gravit.simplecabinet.web.service.SessionService;
import pro.gravit.simplecabinet.web.service.UserDetailsService;
import pro.gravit.simplecabinet.web.service.UserService;

public class WithMockCabinetUserSecurityContextFactory implements WithSecurityContextFactory<WithCabinetUser> {
    @Autowired
    private UserDetailsService detailsService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @Override
    public SecurityContext createSecurityContext(WithCabinetUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserSession session = sessionService.create(userService.findById(annotation.userId()).orElseThrow(), "Test");
        var details = detailsService.create(session);
        context.setAuthentication(new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities()));
        return context;
    }
}
