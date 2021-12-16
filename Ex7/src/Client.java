import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private String serverIP;
    private int serverPort;
    private String msg = "TIME";

    public Client(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void execute(){
        try {
            Socket socket = new Socket(serverIP,serverPort);
            InputStream iS = socket.getInputStream();
            OutputStream oS = socket.getOutputStream();

            oS.write(msg.getBytes());
            oS.flush();

            byte[] buffer = new byte[512];
            int nBytes = iS.read(buffer);
            String message = new String(buffer, 0, nBytes);
            System.out.println("Server local time: " + message);

            iS.close();
            oS.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Client is running...");
        Client clientTCP = new Client("127.0.0.1", 9001);
        clientTCP.execute();
    }
}
