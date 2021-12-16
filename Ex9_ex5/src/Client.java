import java.io.IOException;
import java.net.*;
import java.util.Calendar;

public class Client {
    private String serverIP;
    private int serverPort;
    private final String GET_TIME = "TIME";

    public Client(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void execute(){
        System.out.println("Client is running...");
        Calendar localtime;

        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(serverIP);

            byte[] buffer = Utils.objectToByteArray(GET_TIME);

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, serverPort);
            datagramSocket.send(packet);

            packet = new DatagramPacket(new byte[4000], 4000);
            datagramSocket.receive(packet);

            localtime = Utils.byteArrayToCalendar(packet.getData());
            System.out.println("Day: " + localtime.get(Calendar.DATE));
            System.out.println("Month: " + localtime.get(Calendar.MONTH));
            System.out.println("Year: " + localtime.get(Calendar.YEAR));
            System.out.println("Hour: " + localtime.get(Calendar.HOUR));
            System.out.println("Minute: " + localtime.get(Calendar.MINUTE));
            System.out.println("Second: " + localtime.get(Calendar.SECOND));

            datagramSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client clientUDP = new Client("127.0.0.1", 9001);
        clientUDP.execute();
    }
}
