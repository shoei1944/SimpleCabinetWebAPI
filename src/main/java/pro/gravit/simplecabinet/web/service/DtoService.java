package pro.gravit.simplecabinet.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.dto.ItemDeliveryDto;
import pro.gravit.simplecabinet.web.dto.UserDto;
import pro.gravit.simplecabinet.web.dto.UserGroupDto;
import pro.gravit.simplecabinet.web.model.ItemDelivery;
import pro.gravit.simplecabinet.web.model.User;
import pro.gravit.simplecabinet.web.model.UserGroup;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoService {
    @Autowired
    private SkinService skinService;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public UserDto toPublicUserDto(User user) {
        var groups = getUserGroups(user).stream().map(UserGroupDto::new).collect(Collectors.toList());
        var skin = skinService.getSkinTexture(user);
        var cloak = skinService.getCloakTexture(user);
        return new UserDto(user.getId(), user.getUsername(), user.getUuid(), user.getGender(), user.getStatus(), user.getRegistrationDate(),
                groups, skin, cloak);
    }

    public ItemDeliveryDto itemDeliveryDto(ItemDelivery delivery) {
        List<ItemDeliveryDto.ItemEnchantDto> list;
        if (delivery.getItemEnchants() != null) {
            try {
                var type = new TypeReference<List<ItemDeliveryDto.ItemEnchantDto>>() {
                };
                list = new ObjectMapper().readValue(delivery.getItemEnchants(), type);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            list = null;
        }
        return new ItemDeliveryDto(delivery.getId(), delivery.getItemName(), delivery.getItemExtra(), list, delivery.getItemNbt(), delivery.getPart(), delivery.isCompleted());
    }

    public List<UserGroup> getUserGroups(User user) {
        return user.getGroups();
    }
}
