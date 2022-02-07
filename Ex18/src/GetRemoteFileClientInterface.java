import java.io.IOException;
import java.rmi.Remote;

public interface GetRemoteFileClientInterface extends Remote {
    void writeFileChunk(byte[] fileChunk, int nBytes) throws IOException;

}
