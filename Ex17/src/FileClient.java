import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileClient {
    private static final String Client_DIR = "./ClientFiles";
    private static final String FILENAME = "File.txt";
    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
            RemoteFileInterface rfi = (RemoteFileInterface) r.lookup("fileserver");

            File f = new File(Client_DIR, FILENAME);
            FileOutputStream fos = new FileOutputStream(f);
            long offset = 0;
            byte[] fileChunk;

            do{
                fileChunk = rfi.getFileChunk(FILENAME, offset);

                if(fileChunk == null){
                    //TODO: FIM DO FICHEIRO
                    fos.flush();
                    fos.close();
                    System.out.println("File '" + FILENAME + "' was downloaded.");
                }
                else {
                    fos.write(fileChunk);
                    offset += fileChunk.length;
                }
            }while (fileChunk != null);
        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
