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
import pro.gravit.simplecabinet.web.model.UserAsset;
import pro.gravit.simplecabinet.web.model.UserGroup;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DtoService {
    @Autowired
    private UserAssetService userAssetService;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public UserDto toPublicUserDto(User user) {
        var groups = getUserGroups(user).stream().map(UserGroupDto::new).collect(Collectors.toList());
        return new UserDto(user.getId(), user.getUsername(), user.getUuid(), user.getGender(), user.getStatus(), user.getRegistrationDate(),
                groups, getUserTextures(user));
    }

    public UserDto toMiniUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getUuid(), user.getGender(), user.getStatus(), user.getRegistrationDate(),
                null, getUserTextures(user));
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

    private Map<String, String> deserializeMetadata(String metadata) {
        try {
            TypeReference<Map<String, String>> type = new TypeReference<>() {
            };
            return objectMapper.readValue(metadata, type);
        } catch (JsonProcessingException e) {
            return Map.of();
        }
    }

    public Map<String, UserDto.UserTexture> getUserTextures(User user) {
        return user.getAssets().stream().collect(Collectors.toMap(UserAsset::getName,
                this::getUserTexture));
    }

    public UserDto.UserTexture getUserTexture(UserAsset asset) {
        return new UserDto.UserTexture(userAssetService.makeAssetUrl(asset), asset.getHash(), deserializeMetadata(asset.getMetadata()));
    }

    public List<UserGroup> getUserGroups(User user) {
        return user.getGroups();
    }
}
