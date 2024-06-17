package org.example.k2_client;

public class ClientReceiver {
    public static ClientController controller;
    public static ConnectionThread thread;

    public static void receiveWord(String word) {
        controller.onWordReceived(word);
    }
}
