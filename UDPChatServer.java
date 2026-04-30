package org.example;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPChatServer {
    public static void main(String[] args) {
        int port = 5000;
        // Buffer pentru stocarea datelor primite (1024 octeți sunt suficienți pentru chat)
        byte[] receiveBuffer = new byte[1024];

        try (DatagramSocket serverSocket = new DatagramSocket(port);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Serverul UDP a pornit pe portul " + port + ". Așteaptă mesaje...");

            while (true) {
                // 1. Așteptare și primire pachet de la client
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket); // Metoda blochează execuția până vine un pachet

                String messageFromClient = new String(
                        receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8
                ).trim();

                System.out.println("Client [" + receivePacket.getAddress() + "]: " + messageFromClient);

                if (messageFromClient.equalsIgnoreCase("exit")) {
                    System.out.println("Clientul a semnalat închiderea.");
                    break;
                }

                // 2. Trimitere răspuns către client
                // În UDP, trebuie să extragem adresa și portul de unde a venit pachetul pentru a răspunde
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.print("Server (tu): ");
                String messageToClient = scanner.nextLine();
                byte[] sendData = messageToClient.getBytes(StandardCharsets.UTF_8);

                DatagramPacket sendPacket = new DatagramPacket(
                        sendData, sendData.length, clientAddress, clientPort
                );
                serverSocket.send(sendPacket);

                if (messageToClient.equalsIgnoreCase("exit")) {
                    System.out.println("Serverul se închide...");
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Eroare UDP Server: " + e.getMessage());
        }
    }
}