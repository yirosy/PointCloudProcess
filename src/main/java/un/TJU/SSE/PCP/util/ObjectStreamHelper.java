package un.TJU.SSE.PCP.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by yiros on 2017/3/28.
 */
public class ObjectStreamHelper {
    public static Object readObject (byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object o = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return o;
    }
}
