import java.io.IOException;
import java.net.Socket;

public class Client {
    private String hostname;
    private int port;

    public Client(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void connect(){
        try{
            Socket socket = new Socket(this.hostname, port);
            Thread writer = new Thread(new WriteThread(socket, this));
            Thread reader = new Thread(new ReadThread(socket, this));

            writer.start();
            reader.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 1234);
        client.connect();
    }
}
