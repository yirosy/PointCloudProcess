package un.TJU.SSE.PCP.type;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by yiros on 2017/3/28.
 */
public interface ByteArraySerializable extends Serializable {

    byte[] toBytes () throws IOException;
    long bytes ();
}
