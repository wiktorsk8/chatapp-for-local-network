import java.io.*;
import java.net.Socket;

public class ReadThread implements Runnable {
    private Socket socket;
    private Client client;
    private BufferedReader reader;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String response;
        while (true) {
            try {
                response = reader.readLine();
                System.out.print(response + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
