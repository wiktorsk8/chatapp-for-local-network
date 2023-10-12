import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;

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
        System.out.println("Chat here...");
        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your username: ");
        try {

            writer.println(sysIn.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                String message = sysIn.readLine();
                writer.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
