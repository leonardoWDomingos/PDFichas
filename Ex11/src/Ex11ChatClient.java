import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Locale;
import java.util.Scanner;

public class Ex11ChatClient {
    private final String MULTICAST_IP = "239.3.2.1";
    private final int MULTICAST_PORT = 9005;
    private InetAddress mutlicastGroup;
    private MulticastSocket multicastSocket;
    private String username;

    public void start(){
        System.out.println("Program is running...");
        try {
            multicastSocket = new MulticastSocket(MULTICAST_PORT);
            mutlicastGroup = InetAddress.getByName(MULTICAST_IP);
            multicastSocket.joinGroup(mutlicastGroup);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Username: ");
            username = scanner.nextLine();

            ReceiveThread receiveThreadCode = new ReceiveThread();
            Thread receiveThread = new Thread(receiveThreadCode);
            receiveThread.start();

            while (true){
                String msg = scanner.nextLine();
                //Msg mensagem = new Msg(username, msg);

                if (msg.equals("exit")){
                    multicastSocket.leaveGroup(mutlicastGroup);
                    multicastSocket.close();
                    break;
                }

                msg = "[" + username + "]:" + msg;
                DatagramPacket dP = new DatagramPacket(msg.getBytes(), msg.getBytes().length, mutlicastGroup, MULTICAST_PORT);
                multicastSocket.send(dP);
            }

            receiveThread.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    class ReceiveThread implements Runnable{
        @Override
        public void run() {
            try {
                while (true){
                    DatagramPacket dP = new DatagramPacket(new byte[1024], 1024);
                    multicastSocket.receive(dP);
                    String msg = new String(dP.getData(), 0, dP.getLength());
                    System.out.println(msg);

                    if(msg.toLowerCase().endsWith("list")){
                        msg = "User " + username + " is online";
                        dP.setData(msg.getBytes());
                        dP.setLength(msg.getBytes().length);
                        multicastSocket.send(dP);
                    }
                }
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Client is closing...");
            }
        }
    }


    public static void main(String[] args) {
        Ex11ChatClient ex11ChatClient = new Ex11ChatClient();
        ex11ChatClient.start();
    }
}
