import java.io.*;
import java.net.Socket;

public class Client {
    private String serverIP;
    private int serverPort;
    private Time localTime;

    public Client(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void execute(){
        System.out.println("Client is running...");

        try {
            Socket socket = new Socket(serverIP, serverPort);

            ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream());

            oOS.writeObject("TIME");
            oOS.flush();

            localTime = (Time) oIS.readObject();

            System.out.println("Hour: " + localTime.getHour());
            System.out.println("Minutes: " + localTime.getMinutes());
            System.out.println("Seconds: " + localTime.getSeconds());

            oIS.close();
            oOS.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client clientTCP = new Client("127.0.0.1", 9001);
        clientTCP.execute();
    }
}
