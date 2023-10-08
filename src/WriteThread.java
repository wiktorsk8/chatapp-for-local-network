import javax.imageio.IIOException;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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
            try {
                System.out.println("Write thread open");
                writer.write(client.getUserName());
                writer.newLine();
                writer.flush();

                Scanner scanner = new Scanner(System.in);

                String text;
                do {
                    System.out.println("Git ziomek");

                    text = scanner.nextLine();
                    writer.write(client.getUserName() +": " + text);
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
