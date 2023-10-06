import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {

    private Socket socket;
    private Server server;
    private BufferedWriter writer;
    private BufferedReader reader;
    private String serverMessage;

    private String name;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            printUsers();
            setInputOutput();
            addUser();

            String clientMessage;
            do {
                clientMessage = reader.readLine();
                this.serverMessage = "[" + name + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

            } while (!clientMessage.equals("bye"));

            server.removeUser(name, this);
            socket.close();

            serverMessage = name + " has quitted.";
            server.broadcast(serverMessage, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            this.writer.write(message);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addUser() throws IOException {
        this.name = reader.readLine();
        server.add(name);

        this.serverMessage = "New user connected: " + name;
        server.broadcast(serverMessage, this);
    }

    private void setInputOutput() throws IOException {
        InputStream input = socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(input));

        OutputStream output = socket.getOutputStream();
        this.writer = new BufferedWriter(new OutputStreamWriter(output));
    }

    private void printUsers() {
        try {
            if (server.hasUsers()) {
                this.writer.write("Connected users: " + server.getUserNames());
            } else {
                this.writer.write("No other users connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
