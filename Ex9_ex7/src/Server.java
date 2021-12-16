import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class Server {
    private int serverPort;

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void start(){
        System.out.println("Server is running...");
        boolean run = true;

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (run){
                Socket socket = serverSocket.accept();

                ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream());

                String command = (String) oIS.readObject();

                if(command.equals("TIME")){
                    System.out.println("Time request received from " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());

                    Time time = new Time(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
                    oOS.writeUnshared(time);
                    oOS.flush();
                }
                else {
                    run = false;
                }

                oIS.close();
                oOS.close();
                socket.close();
            }
            serverSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server serverTCP = new Server(9001);
        serverTCP.start();
    }
}
