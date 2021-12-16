import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;

public class Server {
    private int serverPort;

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start(){
        boolean keepAlive = true;

        try {
            DatagramSocket socket = new DatagramSocket(serverPort);
            while (keepAlive){
                DatagramPacket packet = new DatagramPacket(new byte[512], 512);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received Message: " + message);

                if(message.equals("TIME")){
                    Date time = Calendar.getInstance().getTime();

                    InetAddress clientIP = packet.getAddress();
                    int clientPort = packet.getPort();

                    byte[] buffer = time.toString().getBytes();

                    packet = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
                    socket.send(packet);
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server serverUDP = new Server(9007);
        serverUDP.start();
    }
}
