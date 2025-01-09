package com.roomaccesscontrol;

public class Main {
    public static void main(String[] args) {
        // Start the RoomAccessControlGUI in the Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of RoomAccessControlGUI and make it visible
                RoomAccessControlGUI gui = new RoomAccessControlGUI();
                gui.setVisible(true);
            }
        });
    }
}
