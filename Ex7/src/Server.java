import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private int serverPort;

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start(){
        boolean run = true;

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (run){
                Socket socket = serverSocket.accept();
                InputStream iS = socket.getInputStream();
                OutputStream oS = socket.getOutputStream();

                byte[] buffer = new byte[512];
                int nBytes = iS.read(buffer);
                String msg = new String(buffer, 0, nBytes);

                if(msg.equals("TIME")){
                    System.out.println("Time request received from " + socket.getInetAddress().getHostAddress());

                    SimpleDateFormat sDF = new SimpleDateFormat("HH:mm:ss");
                    String localTime = sDF.format(new Date());

                    oS.write(localTime.getBytes());
                    oS.flush();
                }
                else {
                    run = false;
                }

                iS.close();
                oS.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("Server is running...");
        Server serverTCP = new Server(9001);
        serverTCP.start();

    }
}
