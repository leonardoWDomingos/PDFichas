import java.io.IOException;
import java.net.*;

public class Client {
    private String serverIP;
    private int serverPort;
    private final String GET_TIME = "TIME";

    public Client(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void execute(){
        try {
            InetAddress ip = InetAddress.getByName(serverIP);
            byte[] buffer = GET_TIME.getBytes();
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, serverPort);
            socket.send(packet);

            packet = new DatagramPacket(new byte[512], 512);
            socket.receive(packet);
            System.out.println("Current Time: " + new String(packet.getData(), 0, packet.getLength()));

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client clientUDP = new Client("127.0.0.1", 9007);
        clientUDP.execute();
    }
}
