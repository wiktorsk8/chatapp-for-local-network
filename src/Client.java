import java.io.IOException;
import java.net.Socket;

public class Client {
    private String hostname;
    private String userName;
    private int port;



    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
    }


}
