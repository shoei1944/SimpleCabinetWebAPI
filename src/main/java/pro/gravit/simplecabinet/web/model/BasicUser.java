package pro.gravit.simplecabinet.web.model;

import java.util.UUID;

public interface BasicUser {
    long getId();

    String getUsername();

    UUID getUuid();
}
