import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileClientEx19 extends UnicastRemoteObject implements GetRemoteFileClientInterface {

    private final String CLIENT_DIR = "./ClientFiles";
    private static final String FILENAME = "TextFile.txt";

    private String filename;
    private FileOutputStream fos;
    private CopyOnWriteArrayList<GetRemoteFileObserverInterface> observers;

    protected FileClientEx19(String filename) throws RemoteException, FileNotFoundException {
        File f = new File(CLIENT_DIR, filename);
        this.filename = filename;
        fos = new FileOutputStream(f);
        observers = new CopyOnWriteArrayList<>();
    }

    @Override
    public void writeFileChunk(byte[] fileChunk, int nBytes) throws IOException {
        if(fileChunk == null){
            fos.flush();
            fos.close();
            System.out.println("File '" + filename + "' was downloaded.");

            for(GetRemoteFileObserverInterface obs : observers){
                obs.notifyNewOperationConcluded("File '" + filename + "' was downloaded.");
            }

            unexportObject(this, true);
            return;
        }
        fos.write(fileChunk, 0, nBytes);
    }

    @Override
    public void addObserver(GetRemoteFileObserverInterface obsRef) throws RemoteException {
        observers.add(obsRef);
    }

    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
            GetRemoteFileServerInterface fileServerInterface = (GetRemoteFileServerInterface) r.lookup("fileserver");

            FileClientEx19 fileClientObj = new FileClientEx19(FILENAME);
            r.rebind("fileclient", fileClientObj);

            System.out.println("Waiting 10 seconds for observers...");
            Thread.sleep(10000);

            fileServerInterface.getFile(FILENAME, fileClientObj);

        } catch (NotBoundException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
