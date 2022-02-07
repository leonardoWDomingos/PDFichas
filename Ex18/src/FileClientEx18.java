import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileClientEx18 extends UnicastRemoteObject implements GetRemoteFileClientInterface {

    private final String CLIENT_DIR = "./ClientFiles";
    private static final String FILENAME = "File.txt";

    private FileOutputStream fos;

    protected FileClientEx18(String filename) throws RemoteException, FileNotFoundException {
        File f = new File(CLIENT_DIR, filename);
        fos = new FileOutputStream(f);
    }

    @Override
    public void writeFileChunk(byte[] fileChunk, int nBytes) throws IOException {
        if(fileChunk == null){
            fos.flush();
            fos.close();
            System.out.println("File was downloaded.");
            unexportObject(this, true);
            return;
        }
        fos.write(fileChunk, 0, nBytes);
    }

    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
            GetRemoteFileServerInterface fileServerInterface = (GetRemoteFileServerInterface) r.lookup("fileserver");

            FileClientEx18 fileClientObj = new FileClientEx18(FILENAME);
            fileServerInterface.getFile(FILENAME, fileClientObj);

        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
