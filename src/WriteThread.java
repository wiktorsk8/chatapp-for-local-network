import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;

public class WriteThread implements Runnable{
    private Socket socket;
    private Client client;
    private BufferedWriter writer;
    public WriteThread(Socket socket, Client client){
        this.socket = socket;
        this.client = client;

        try{
            OutputStream output = socket.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(output));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        while (true){
            Console console = System.console();
            String userName = console.readLine("Enter your name: ");
            client.setUserName(userName);

            try {
                writer.write(userName);
                writer.newLine();
                writer.flush();

                String text;
                do {
                    text = console.readLine("["+ userName+"]: ");
                    writer.write(text);
                    writer.newLine();
                    writer.flush();
                }while (!text.equals("bye"));

                socket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
