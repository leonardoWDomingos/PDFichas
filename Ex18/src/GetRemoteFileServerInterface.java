import java.io.IOException;
import java.rmi.Remote;

public interface GetRemoteFileServerInterface extends Remote {
    void getFile(String fileName, GetRemoteFileClientInterface cliRef) throws IOException;
}
