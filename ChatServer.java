package org.example;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serverul a pornit pe portul " + port + ". Se așteaptă conexiunea clientului...");

            try (Socket clientSocket = serverSocket.accept();
                 // Specificăm explicit StandardCharsets.UTF_8 pentru fluxurile de intrare/ieșire
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8), true);
                 Scanner scanner = new Scanner(System.in)) {

                System.out.println("Client conectat de la adresa: " + clientSocket.getInetAddress());

                String messageFromClient;
                String messageToClient;

                while (true) {
                    // 1. Așteaptă și citește mesajul de la client
                    messageFromClient = in.readLine();

                    if (messageFromClient == null || messageFromClient.equalsIgnoreCase("exit")) {
                        System.out.println("Clientul a închis conversația.");
                        break;
                    }

                    System.out.println("Client: " + messageFromClient);

                    // 2. Trimite răspuns (Serverul tastează)
                    System.out.print("Server (tu): ");
                    messageToClient = scanner.nextLine();
                    out.println(messageToClient);

                    if (messageToClient.equalsIgnoreCase("exit")) {
                        System.out.println("Serverul se închide...");
                        break;
                    }
                }
            }
            // Resursele (Socket, In, Out) se închid automat prin Try-with-resources
            System.out.println("Conexiunea a fost închisă.");

        } catch (IOException e) {
            System.err.println("Eroare la server: " + e.getMessage());
        }
    }
}