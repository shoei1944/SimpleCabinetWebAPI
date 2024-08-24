package pro.gravit.simplecabinet.web.service.shop.group.delivery;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.gravit.simplecabinet.web.model.shop.GroupOrder;
import pro.gravit.simplecabinet.web.model.shop.GroupProduct;
import pro.gravit.simplecabinet.web.model.user.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Service("luckPermsDeliveryService")
public class LuckPermsDeliveryService implements GroupDeliveryService {
    @Value("${shop.group.luckperms.table}")
    private String table;
    @Value("${shop.group.luckperms.prefixWeight}")
    private String prefixWeight;
    @PersistenceContext
    private EntityManager manager;

    public void insertPermission(UUID uuid, String permission, boolean value, String server, String world, long timestampExpire, String context) {
        Query query = manager.createNativeQuery(String.format("INSERT INTO %s (\"uuid\",\"permission\",value,\"server\",world,expiry,contexts) " +
                " VALUES (?,?,?,?,?,?,?);", table));
        query.setParameter(1, uuid.toString());
        query.setParameter(2, permission);
        query.setParameter(3, value);
        query.setParameter(4, server);
        query.setParameter(5, world);
        query.setParameter(6, timestampExpire);
        query.setParameter(7, context);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<LuckPermsPermission> searchUserPermission(UUID uuid, String prefix, String server, String world) {
        Query query = manager.createNativeQuery(String.format("SELECT uuid, permission, value, server, world, expiry, contexts from %s where uuid = ? and permission like ? escape '\\' and server = ? and world = ? and (expiry = 0 or expiry > ?)", table),
                LuckPermsPermission.class);

        query.setParameter(1, uuid.toString());
        query.setParameter(2, escapeLike(prefix, "\\") + "%");
        query.setParameter(3, server);
        query.setParameter(4, world);
        query.setParameter(5, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        return (List<LuckPermsPermission>) query.getResultList();
    }

    public int upUserPermission(UUID uuid, String prefix, String server, String world, long expiry) {
        Query query = manager.createNativeQuery(String.format("UPDATE %s SET expiry = ? where uuid = ? and permission like ? escape '\\' and server = ? and world = ? and (expiry != 0 or expiry > ?)", table));
        query.setParameter(1, expiry);
        query.setParameter(2, uuid.toString());
        query.setParameter(3, escapeLike(prefix, "\\") + "%");
        query.setParameter(4, server);
        query.setParameter(5, world);
        query.setParameter(6, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        return query.executeUpdate();
    }

    public boolean deletePermission(LuckPermsPermission permission) {
        return deletePermission(permission.getUuid(), permission.getPermission(), permission.getValue(), permission.getServer(),
                permission.getWorld(), permission.getExpiry(), permission.getContexts());
    }

    public boolean deletePermission(UUID uuid, String permission, boolean value, String server, String world, long timestampExpire, String context) {
        Query query = manager.createNativeQuery(String.format("DELETE FROM %s where uuid = ? and permission = ? and value = ? and server = ? and world = ? and expity = ? and contexts = ?", table));
        query.setParameter(1, uuid.toString());
        query.setParameter(2, permission);
        query.setParameter(3, value);
        query.setParameter(4, server);
        query.setParameter(5, world);
        query.setParameter(6, timestampExpire);
        query.setParameter(7, context);
        return query.executeUpdate() > 0;
    }

    private String escapeLike(String value, String escape) {
        return value.replace(escape, escape + escape).replace("%", escape + "%").replace("_", escape + "_");
    }

    @Override
    @Transactional
    public void delivery(GroupOrder order) {
        User user = order.getUser();
        GroupProduct product = order.getProduct();
        long expiry = product.getExpireDays() == 0 ? 0 : LocalDateTime.now().plusDays(product.getExpireDays() * order.getQuantity()).toEpochSecond(ZoneOffset.UTC);
        insertPermission(user.getUuid(), String.format("group.%s", product.getName().toLowerCase(Locale.ROOT)), true, product.getServer(), product.getWorld(),
                expiry,
                product.getContext());
    }

    @Override
    @Transactional
    public void updatePrefix(String prefix, UUID userUUID, LocalDateTime endDate) {
        var list = searchUserPermission(userUUID, "prefix." + prefixWeight + ".", "global", "global");
        if (!list.isEmpty()) {
            if (list.size() > 1) {
                throw new SecurityException("Found more than one prefix permission");
            }
            deletePermission(list.getFirst());
        }
        insertPermission(userUUID, "prefix." + prefixWeight + "." + prefix, true, "global", "global",
                endDate == null ? 0 : endDate.toEpochSecond(ZoneOffset.UTC), "{}");
    }

    @Override
    public boolean deletePrefix(UUID userUUID) {
        var list = searchUserPermission(userUUID, "prefix." + prefixWeight + ".", "global", "global");
        if (!list.isEmpty()) {
            if (list.size() > 1) {
                throw new SecurityException("Found more than one prefix permission");
            }
            return deletePermission(list.getFirst());
        }
        return false;
    }

    @Entity
    public static final class LuckPermsPermission {
        @Id
        private UUID uuid;
        private String permission;
        private boolean value;
        private String server;
        private String world;
        private long expiry;
        private String contexts;

        public LuckPermsPermission() {
        }

        public LuckPermsPermission(UUID uuid, String permission, boolean value, String server, String world,
                                   long expiry, String contexts) {
            this.uuid = uuid;
            this.permission = permission;
            this.value = value;
            this.server = server;
            this.world = world;
            this.expiry = expiry;
            this.contexts = contexts;
        }

        public UUID getUuid() {
            return uuid;
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public boolean getValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getWorld() {
            return world;
        }

        public void setWorld(String world) {
            this.world = world;
        }

        public long getExpiry() {
            return expiry;
        }

        public void setExpiry(long expiry) {
            this.expiry = expiry;
        }

        public String getContexts() {
            return contexts;
        }

        public void setContexts(String contexts) {
            this.contexts = contexts;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (LuckPermsPermission) obj;
            return Objects.equals(this.uuid, that.uuid) &&
                    Objects.equals(this.permission, that.permission) &&
                    this.value == that.value &&
                    Objects.equals(this.server, that.server) &&
                    Objects.equals(this.world, that.world) &&
                    this.expiry == that.expiry &&
                    Objects.equals(this.contexts, that.contexts);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uuid, permission, value, server, world, expiry, contexts);
        }

        @Override
        public String toString() {
            return "LuckPermsPermission[" +
                    "uuid=" + uuid + ", " +
                    "permission=" + permission + ", " +
                    "value=" + value + ", " +
                    "server=" + server + ", " +
                    "world=" + world + ", " +
                    "expiry=" + expiry + ", " +
                    "contexts=" + contexts + ']';
        }

    }
}
