import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private String hostname;
    private String userName;
    private int port;

    public Client(String hostname, int port, String userName){
        this.hostname = hostname;
        this.port = port;
        this.userName = userName;
    }

    public void connect(){
        try{
            Socket socket = new Socket(this.hostname, port);
            new WriteThread(socket, this).run();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public String getUserName(){
        return userName;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.nextLine();
        Client client = new Client("localhost", 1234, userName);
        client.connect();
    }
}
