import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileObserverEx19 extends UnicastRemoteObject implements GetRemoteFileObserverInterface {

    protected FileObserverEx19() throws RemoteException {
    }

    @Override
    public void notifyNewOperationConcluded(String description) throws RemoteException {
        System.out.println(description);
        unexportObject(this, true);
    }

    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.getRegistry("127.0.0.1", Registry.REGISTRY_PORT);
            GetRemoteFileClientInterface cliRef = (GetRemoteFileClientInterface) r.lookup("fileclient");
            FileObserverEx19 obs = new FileObserverEx19();
            cliRef.addObserver(obs);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
