import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {

    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private BufferedReader reader;
    private String serverMessage;

    private String userName;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            setInputOutput();
            addUser();

            String clientMessage;
            do {
                clientMessage = reader.readLine();
                this.serverMessage = "[" + userName + "]: "+ clientMessage;
                server.broadcast(serverMessage, this);
            } while (!clientMessage.equals("bye"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        System.out.println("message: " + message);
        writer.println(message + '\n');
        if (writer.checkError()){
            System.out.println("Error occured");
        }
    }

    public String getUserName(){
        return userName;
    }

    private void addUser() throws IOException {
        this.userName = reader.readLine();
        server.addUserName(userName);
    }

    private void setInputOutput() throws IOException {
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));

        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output);
    }
}
