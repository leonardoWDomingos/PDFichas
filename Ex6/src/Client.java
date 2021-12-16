import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

public class Client {
    private String serverIp;
    private int serverPort;
    private String fileName;
    private String clientDir;
    private int MAX_DATA = 4000;

    public Client(String serverIp, int serverPort, String fileName, String clientDir) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.fileName = fileName;
        this.clientDir = clientDir;
    }

    public void execute(){
        try {
            InetAddress serverIP = InetAddress.getByName(serverIp);

            DatagramSocket socket = new DatagramSocket();
            byte[] buffer = fileName.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverIP, serverPort);
            socket.send(packet);

            packet = new DatagramPacket(new byte[MAX_DATA], MAX_DATA);
            File newFile = new File(clientDir, fileName);
            FileOutputStream fOS = new FileOutputStream(newFile);

            do{
                socket.receive(packet);
                fOS.write(packet.getData());
            }while (packet.getLength() > 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client clientUDP = new Client("127.0.0.1", 9007, "TextFile.txt", "./ClientFiles");
        clientUDP.execute();
    }
}
