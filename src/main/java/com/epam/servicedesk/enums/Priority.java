package com.epam.servicedesk.enums;

import java.util.Arrays;

public enum Priority {
    LOW(0),
    MEDIUM(1),
    HIGH(2),
    CRITICAL(3);

    private int id;

    Priority(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Priority getPriority(long id) {
        Priority[]priorities = Priority.values();
        return Arrays.stream(priorities).filter(priority -> priority.getId()==id).findFirst().get();
    }
}
