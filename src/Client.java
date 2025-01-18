import java.io.*;
import java.net.*;

import static java.lang.Integer.parseInt;

public class Client {
    public static void main(String[] args) {
        Client myClient = new Client();
        myClient.run(args);
    }

    private void run(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Client <port>");
            return;
        }
        Socket echoSocket = null;

        try {
            int port = parseInt(args[0]);
            echoSocket = new Socket("localhost", port);
            PrintWriter out =
                    new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                if (userInput.equals("quit")) {
                    System.out.println("Shutting down client.");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (echoSocket != null) echoSocket.close();
                System.out.println("Server sockets closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}