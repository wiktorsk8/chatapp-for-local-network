import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    private ServerSocket serverSocket;
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void boot(){

        System.out.println("Server started.");

        while(true){
            try{
                Socket clientSocket = serverSocket.accept();
                System.out.println("User connected!!!!!!!!!");
                
                UserThread userThread = new UserThread(clientSocket, this);
                userThreads.add(userThread);

                Thread thread = new Thread(userThread);
                thread.start();

            }catch (IOException ex) {
                System.out.println("Error in the server: " + ex.getMessage());
                ex.printStackTrace();
            }

        }
    }

    public void broadcast(String message, UserThread excludeUser){
        for (UserThread user: userThreads){
            if (excludeUser != user){
                System.out.println("Broadcasting message from: [" + excludeUser.getUserName() + "] to: " + user.getUserName());
                System.out.println("Message content: " + message);
                user.sendMessage(message);
            }
        }
    }

    public void addUserName(String name){
        this.userNames.add(name);
    }

    public Set<String> getUserNames(){
        return this.userNames;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.boot();
    }

}
