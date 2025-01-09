package com.roomaccesscontrol;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AccessControl {

    public static void attemptAccess(User user, Room room) {
        LocalTime currentTime = LocalTime.now(); // For simplicity, using current time
        boolean hasAccess = user.canAccessRoom(room.getRoomState(), currentTime);

        // Log the access attempt
        logAccessAttempt(user, room, hasAccess, currentTime);
    }

    private static void logAccessAttempt(User user, Room room, boolean hasAccess, LocalTime time) {
        String status = hasAccess ? "Granted" : "Denied";
        String roomDetails = room.getRoomDetails();
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.println("Date/Time: " + formattedTime);
        System.out.println("User: " + user.getName() + " (ID: " + user.getId() + ")");
        System.out.println("Room: " + roomDetails + " (" + room.getType() + ")");
        System.out.println("Access: " + status);
        System.out.println("Room State: " + room.getRoomState());
        System.out.println("-------------------------------------");
    }
}
