package pro.gravit.simplecabinet.web.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.gravit.simplecabinet.web.model.shop.ServiceProduct;
import pro.gravit.simplecabinet.web.service.shop.service.ServiceProductService;
import pro.gravit.simplecabinet.web.service.user.UserCustomizationService;
import pro.gravit.simplecabinet.web.service.user.UserService;

@RestController
@RequestMapping("/cabinet/prefix")
public class CabinetPrefixController {
    public static final String ALWAYS_PERMISSION_NAME = "simplecabinet.customization.prefix.update";
    @Autowired
    private ServiceProductService serviceProductService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCustomizationService userCustomizationService;

    @DeleteMapping
    public void deletePrefix() {
        var user = userService.getCurrentUser();
        var alwaysPermission = user.getPermission("simplecabinet.customization.prefix.delete");
        if (alwaysPermission == null) {
            var order = serviceProductService.findByUserAndType(user.getReference(), ServiceProduct.ServiceType.CHANGE_PREFIX);
            if (order.isEmpty()) {
                throw new SecurityException("Access denied");
            }
            userCustomizationService.deletePrefix(user.getReference());
        } else {
            userCustomizationService.deletePrefix(user.getReference());
        }
    }

    @GetMapping
    public GetPrefixResponse getPrefix() {
        var user = userService.getCurrentUser();
        return new GetPrefixResponse(user.getReference().getPrefix(), user.getPermission(ALWAYS_PERMISSION_NAME) != null);
    }

    @PutMapping
    public void setPrefix(@RequestBody SetPrefixRequest request) {
        var user = userService.getCurrentUser();
        var alwaysPermission = user.getPermission(ALWAYS_PERMISSION_NAME);
        if (alwaysPermission == null) {
            var order = serviceProductService.findByUserAndType(user.getReference(), ServiceProduct.ServiceType.CHANGE_PREFIX);
            if (order.isEmpty()) {
                throw new SecurityException("Access denied");
            }
            userCustomizationService.updatePrefixByOrder(user.getReference(), request.prefix, order.get());
        } else {
            userCustomizationService.updatePrefixByGroup(user.getReference(), request.prefix, alwaysPermission.getGroupName());
        }
    }

    public record SetPrefixRequest(String prefix) {

    }

    public record GetPrefixResponse(String prefix, boolean freePrefixChangeAvailable) {

    }
}
