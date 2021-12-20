import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;

public class ServerThread implements Runnable{
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream());

            String command = null;

            command = (String) oIS.readObject();

            if (command.equals("TIME")) {
                System.out.println("Time request received from " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());

                Time time = new Time(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
                oOS.writeUnshared(time);
                oOS.flush();
            }

            oIS.close();
            oOS.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
