import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Server {
    private int serverPort;

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start(){
        System.out.println("Server is running...");
        boolean run = true;

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (run){
                Socket socket = serverSocket.accept();

                ServerThread serverThread = new ServerThread(socket);
                Thread threadServer = new Thread(serverThread);
                threadServer.start();

                System.out.println("Thread: " + threadServer.getName() + " was launched for client " + socket.getInetAddress().getHostAddress()+": "+ socket.getPort());
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server serverTCP = new Server(9001);
        serverTCP.start();
    }
}
