import java.io.*;
import java.net.*;
import static java.lang.Integer.parseInt;

import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

public class Server {
    private String logFileName = "log.txt";
    private String filePath = "./log.txt";

    public static void main(String[] args) throws Exception {
        Server myServer = new Server();
        myServer.run(args);
    }

    private void run(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java Server <port>");
            return;
        }

        try {
            int port = parseInt(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                handleInput(inputLine);
            }

        } catch (Exception e) {
            e.printStackTrace(); // change maybe
        }
    }

    private void handleInput(String input) {
        StringTokenizer stInput = new StringTokenizer(input, " ");

        while (stInput.hasMoreTokens()) {
            String currToken = stInput.nextToken();
            String command = currToken.toLowerCase(Locale.ROOT);

            switch (command) {
                case "logmessage":
                    logMessage(stInput.nextToken());
                    break;
                case "printlog":
                    printLog();
                    break;
                case "printrecent":
                    printRecent();
                    break;
                case "printtext":
                    printText(stInput.nextToken());
                    break;
                case "quit":
                    quit();
                    break;
                case "help":
                    listCommands();
                    break;
                default:
                    invalidCommand();
                    break;
            }
        }
    }

    private void logMessage(String text) {
        try (FileWriter fw = new FileWriter(getLogFileName(), true); BufferedWriter bw = new BufferedWriter(fw);) {
            bw.write(text);
            bw.newLine();
            bw.close();
            System.out.println("User message \"" + text + "\" logged in " + getLogFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printLog() {
        try (BufferedReader br = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void printRecent() {
        try (BufferedReader br = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            String lastLine = null;
            while ((line = br.readLine()) != null) {
                lastLine = line;
            }

            System.out.println(Objects.requireNonNullElse(lastLine, "Log file is empty"));

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void printText(String text) {
        try (BufferedReader br = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(text)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private void quit() {

    }

    private void listCommands() {
        System.out.println(
                "Here is a list of all commands:\n" +
                        "logMessage text: The Server will store the text in the log file\n" +
                        "printLog: The Server will print out the contents of the log file\n" +
                        "printRecent: The Server will print out the most recent message that was stored in the log file\n" +
                        "printText text:  The Server will print out only those messages in the log file that contain text\n" +
                        "quit: Both the Client and the Server will terminate their execution\n" +
                        "help: Lists all available commands"

        );
    }

    private void invalidCommand() {
        System.out.println("Invalid command. Type help to list all available commands.");
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}

