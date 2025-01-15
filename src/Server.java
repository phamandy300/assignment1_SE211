import java.io.*;
import java.net.*;
import static java.lang.Integer.parseInt;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
            int port = parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.print("The user typed: ");
                System.out.println(inputLine);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void logMessage() {}

    public void printLog() {}

    public void printRecent() {}

    public void printText() {}

    public void quit() {}

    //invalid command gerror handling
}

