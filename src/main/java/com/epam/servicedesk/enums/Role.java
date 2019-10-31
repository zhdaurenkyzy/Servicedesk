package com.epam.servicedesk.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    OPERATOR(0),
    ENGINEER(1),
    CLIENT(2),
    GUEST(3);

    private long id;

    Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static Role getRole(long id) {
        Role[] roles = Role.values();
        return Optional.of(Arrays.stream(roles).filter(r ->
                r.getId() == id).findFirst().get())
                .orElseThrow(IllegalArgumentException::new);
    }
}
