import javax.imageio.IIOException;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.io.Console;

public class WriteThread extends Thread {
    private Socket socket;
    private Client client;
    private PrintWriter writer;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter name: ");
        String userName = null;
        try {
            userName = sysIn.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        client.setUserName(userName);
        writer.println(userName);

        while (true) {
            String message = null;
            try {
                message = sysIn.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.println(message);
        }
    }
}
