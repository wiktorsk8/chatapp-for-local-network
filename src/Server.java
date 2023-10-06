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
        while(true){
            try{
                Socket clientSocket = serverSocket.accept();
                System.out.println("User connected!!!!!!!!!");
                UserThread user = new UserThread(clientSocket, this);
                userThreads.add(user);

            }catch (IOException ex) {
                System.out.println("Error in the server: " + ex.getMessage());
                ex.printStackTrace();
            }

        }
    }

    public void broadcast(String message, UserThread currentUser){
        for (UserThread user: userThreads){
            if (!user.equals(currentUser)){
                user.sendMessage(message);
            }
        }
    }

    public void add(String name){
        this.userNames.add(name);
    }

    public void removeUser(String name, UserThread user){
        userNames.remove(name);
        userThreads.remove(user);
    }

    public boolean hasUsers() {
        return !this.userNames.isEmpty();
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
