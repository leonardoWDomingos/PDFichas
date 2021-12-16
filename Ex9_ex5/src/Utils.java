import java.io.*;
import java.util.Calendar;

public class Utils {

    public static byte[] objectToByteArray(Object obj) throws IOException{
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        ObjectOutputStream oOS = new ObjectOutputStream(bAOS);
        oOS.writeObject(obj);
        oOS.close();
        return bAOS.toByteArray();
    }

    public static String byteArrayToString(byte[] textBytes) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bAIS = new ByteArrayInputStream(textBytes);
        ObjectInputStream oIS = new ObjectInputStream(bAIS);
        String text = (String) oIS.readObject();
        oIS.close();
        return text;
    }

    public static Calendar byteArrayToCalendar(byte[] textBytes) throws IOException, ClassNotFoundException{
        ByteArrayInputStream bAIS = new ByteArrayInputStream(textBytes);
        ObjectInputStream oIS = new ObjectInputStream(bAIS);
        Calendar calendar = (Calendar) oIS.readObject();
        oIS.close();
        return calendar;
    }
}
