package com.roomaccesscontrol;

public class Room {
    private String buildingCode;
    private int floor;
    private int roomNumber;
    private String type;
    private String roomState;

    public Room(String buildingCode, int floor, int roomNumber, String type, String roomState) {
        this.buildingCode = buildingCode;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.type = type;
        this.roomState = roomState;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getRoomDetails() {
        return buildingCode + floor + roomNumber;
    }

    public String getType() {
        return type;
    }
}
