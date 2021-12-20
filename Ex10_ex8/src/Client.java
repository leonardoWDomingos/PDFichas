import java.io.*;
import java.net.Socket;

public class Client {
    private String serverIP;
    private int serverPort;
    private String clientDir;
    private String fileName;
    private Socket socket;
    private int MAX_DATA = 4000;

    public Client(String serverIP, int serverPort, String clientDir, String fileName) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.clientDir = clientDir;
        this.fileName = fileName;
    }

    public void execute(){
        System.out.println("Client is running...");
        File newFile = null;

        try {
            socket = new Socket(serverIP,serverPort);
            InputStream iS = socket.getInputStream();
            OutputStream oS = socket.getOutputStream();

            oS.write(fileName.getBytes());
            oS.flush();

            newFile = new File(clientDir,fileName);
            FileOutputStream fOS = new FileOutputStream(newFile);

            byte[] buffer = new byte[MAX_DATA];
            int nBytes;
            while ((nBytes = iS.read(buffer)) > 0){
                fOS.write(buffer,0,nBytes);
            }

            System.out.println("File Downloaded!");

            fOS.flush();
            fOS.close();
            iS.close();
            oS.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client clientTCP = new Client("127.0.0.1", 9001, "./ClientFiles", "TextFile.txt");
        clientTCP.execute();
    }
}
