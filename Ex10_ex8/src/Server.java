import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int serverPort;
    private String serverDir;
    private ServerSocket serverSocket;
    private int MAX_DATA = 4000;

    public Server(int serverPort, String serverDir) {
        this.serverPort = serverPort;
        this.serverDir = serverDir;
    }

    public void start(ConsoleThreadV1 consoleThreadV1){
        System.out.println("Server is running...");
        try {
            serverSocket = new ServerSocket(serverPort);
            byte[] buffer = new byte[MAX_DATA];

            while (!consoleThreadV1.getStopThread()){
                Socket socket = serverSocket.accept();
                InputStream iS = socket.getInputStream();
                OutputStream oS = socket.getOutputStream();

                int nBytes = iS.read(buffer);
                String fileName = new String(buffer, 0, nBytes);

                File fileToDownload = new File(serverDir, fileName);
                if(!fileToDownload.exists()){
                    System.out.println("File " + fileToDownload.getPath() + " not found!");
                    continue;
                }

                FileInputStream fIS = new FileInputStream(fileToDownload);
                while (fIS.available() > 0){
                    int numberBytes = fIS.read(buffer);
                    oS.write(buffer,0,numberBytes);
                    oS.flush();
                }
                System.out.println("File " + fileToDownload.getPath() + " sent to " + socket.getInetAddress().getHostAddress());

                fIS.close();
                iS.close();
                oS.close();
                socket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //metodo 1
        ConsoleThreadV1 consoleThreadCode = new ConsoleThreadV1();
        Thread consoleThreadV1 = new Thread(consoleThreadCode);
        consoleThreadV1.start();

        //metodo 2
        ConsoleThreadV2 consoleThreadV2 = new ConsoleThreadV2();
        consoleThreadV2.start();

        Server serverTCP = new Server(9001, "./ServerFiles");
        serverTCP.start(consoleThreadCode);
    }
}
