package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ConnectionThread extends Thread {
    Socket client;
    PrintWriter writer;
    String username;

    public ConnectionThread(String address, int port, String username) throws IOException {
        client = new Socket(address, port);
        this.username = username;
    }

    @Override
    public void run() {
        try (InputStream input = client.getInputStream();
             OutputStream output = client.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            writer = new PrintWriter(output, true);

            // Send login message
            send(new Message(MessageType.Login, username));

            String rawMessage;

            while ((rawMessage = reader.readLine()) != null) {
                Message message = new ObjectMapper().readValue(rawMessage, Message.class);

                if (message.type == MessageType.Broadcast) {
                    System.out.println(message.content);
                } else if (message.type == MessageType.Request) {
                    System.out.println(message.content);
                } else if (message.type == MessageType.Whisper) {
                    System.out.println(message.content);
                }
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            try {
                // Send logout message
                send(new Message(MessageType.Logout, username));
                client.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void send(Message message) throws JsonProcessingException {
        String rawMessage = new ObjectMapper().writeValueAsString(message);
        writer.println(rawMessage);
    }
}
