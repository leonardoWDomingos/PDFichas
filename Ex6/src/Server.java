import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    private int serverPort;
    private String serverDir;
    private int MAX_DATA = 4000;

    public Server(int serverPort, String serverDir) {
        this.serverPort = serverPort;
        this.serverDir = serverDir;
    }

    public void start(){
        boolean stop = false;
        byte[] buffer = new byte[MAX_DATA];

        try {
            DatagramSocket socket = new DatagramSocket(serverPort);
            DatagramPacket packet;

            while (!stop){
                packet = new DatagramPacket(new byte[512], 512);
                socket.receive(packet);

                String fileName = new String(packet.getData(), 0, packet.getLength());
                InetAddress clientIP = packet.getAddress();
                int clientPort = packet.getPort();

                File fileToDownload = new File(serverDir, fileName);
                if(!fileToDownload.exists()){
                    System.out.println("File " + fileToDownload.getPath() + "not found!");
                    stop = true;
                    continue;
                }

                FileInputStream fIS = new FileInputStream(fileToDownload);
                while (fIS.available() > 0){
                    int nBytes = fIS.read(buffer);
                    packet = new DatagramPacket(buffer, nBytes, clientIP, clientPort);
                    socket.send(packet);
                }

                packet = new DatagramPacket(buffer, 0, clientIP, clientPort);
                socket.send(packet);
                System.out.println("File " + fileToDownload.getPath() + " sent to " + clientIP.getHostAddress());
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server serverUDP = new Server(9007, "./ServerFiles");
        serverUDP.start();
    }
}
