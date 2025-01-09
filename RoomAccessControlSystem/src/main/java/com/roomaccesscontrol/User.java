package com.roomaccesscontrol;

import java.time.LocalTime;

public class User {
    private String name;
    private String id;
    private String role;
    private LocalTime accessStart;
    private LocalTime accessEnd;

    public User(String name, String id, String role, LocalTime accessStart, LocalTime accessEnd) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.accessStart = accessStart;
        this.accessEnd = accessEnd;
    }

    public String getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean canAccessRoom(String roomState, LocalTime currentTime) {
        if ("Emergency".equals(roomState)) {
            return true; // Emergency responders have unrestricted access in Emergency mode
        }
        if (accessStart != null && accessEnd != null) {
            return currentTime.isAfter(accessStart) && currentTime.isBefore(accessEnd);
        }
        return false; // Default: no access if no time restriction
    }
}
