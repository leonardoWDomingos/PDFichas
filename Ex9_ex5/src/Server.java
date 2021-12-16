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
        System.out.println("Server is running...");
        boolean keepAlive = true;

        try {
            DatagramSocket socket = new DatagramSocket(serverPort);
            while (keepAlive){
                DatagramPacket packet = new DatagramPacket(new byte[512], 512);
                socket.receive(packet);

                String command = Utils.byteArrayToString(packet.getData());

                if(command.equals("TIME")){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());

                    InetAddress clientIP = packet.getAddress();
                    int clientPort = packet.getPort();

                    byte[] buffer = Utils.objectToByteArray(calendar);

                    packet = new DatagramPacket(buffer, buffer.length, clientIP, clientPort);
                    socket.send(packet);
                }
                else{
                    keepAlive = false;
                }
            }
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server serverUDP = new Server(9001);
        serverUDP.start();
    }
}
