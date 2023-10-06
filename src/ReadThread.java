import java.io.*;
import java.net.Socket;

public class ReadThread implements Runnable{
    private Socket socket;
    private Client client;
    private BufferedReader reader;
    public ReadThread(Socket socket, Client client){
        this.socket = socket;
        this.client = client;

        try{
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                System.out.println(response);

                if (client.getUserName() != null) {
                    System.out.print("[" + client.getUserName() + "]: ");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
