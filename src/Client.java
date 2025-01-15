import java.io.*;
import java.net.*;

import static java.lang.Integer.parseInt;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            int port = parseInt(args[0]);
            Socket echoSocket = new Socket("localhost", port);
            PrintWriter out =
                    new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}