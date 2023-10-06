import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostname;
    private String userName;
    private int port;

    public Client(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    private void connect(){
        try{
            Socket socket = new Socket(this.hostname, port);

            new ReadThread(socket, this).run();
            new WriteThread(socket, this).run();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setUserName(String name){
        this.userName = name;
    }

    public String getUserName(){
        return userName;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("<your hostname>", 1234);
        client.connect();
    }
}
