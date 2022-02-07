import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileServerEx19 extends UnicastRemoteObject implements GetRemoteFileServerInterface {
    private final String SERVER_DIR = "./ServerFiles";
    protected FileServerEx19() throws RemoteException {
    }

    @Override
    public void getFile(String fileName, GetRemoteFileClientInterface cliRef) throws IOException {
        File f = new File(SERVER_DIR, fileName);
        FileInputStream fis = new FileInputStream(f);
        byte[] fileChunk = new byte[512];
        int nBytes;

        while ((nBytes = fis.read(fileChunk)) > -1){
            cliRef.writeFileChunk(fileChunk, nBytes);
        }

        fis.close();
        cliRef.writeFileChunk(null, -1);
    }

    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            FileServerEx19 fs = new FileServerEx19();
            r.rebind("fileserver", fs);
            System.out.println("File Server is running...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
