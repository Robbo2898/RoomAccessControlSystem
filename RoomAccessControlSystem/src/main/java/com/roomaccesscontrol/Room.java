public class Room {
    private String buildingCode;
    private int floor;
    private int roomNumber;
    private String roomType;
    private String roomState; // NORMAL or EMERGENCY

    public Room(String buildingCode, int floor, int roomNumber, String roomType) {
        this.buildingCode = buildingCode;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomState = "NORMAL";  // Default state
    }

    // Getter and Setter methods
    public String getRoomID() {
        return buildingCode + floor + roomNumber;
    }

    public void setRoomState(String state) {
        this.roomState = state;
    }

    public String getRoomState() {
        return this.roomState;
    }

    // Display room info
    public String getRoomInfo() {
        return "Room " + getRoomID() + " (" + roomType + ") - State: " + roomState;
    }
}
