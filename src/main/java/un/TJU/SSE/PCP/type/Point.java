package un.TJU.SSE.PCP.type;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Created by yiros on 2017/3/28.
 * Data Type
 */
public class Point implements ByteArraySerializable{

    private static int BYTES;
    private static final Logger LOGGER = LogManager.getRootLogger();
    public static final Point defaultPoint = new Point(0,0,0,0);

    private final float[] para;

    static {
        try {
            BYTES = defaultPoint.toBytes().length;
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Serialize fault.");
            throw new Error("Serialize fault.");
        }
        LOGGER.info("Serialized size is " + String.valueOf(BYTES));
    }

    @Override
    public byte[] toBytes() throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public long bytes() {
        return BYTES;
    }

    public int getNum() {
        return para.length;
    }

    public Point (float x, float y, float z, float light) {
        para= new float[]{
            x, y, z, light
        };
    }

    public Point (float[] para) {
        this.para= para.clone();
    }

    float get(int i) {
        return para[i];
    }
    float getX() {
        return para[0];
    }
    float getY() {
        return para[1];
    }
    float getZ() {
        return para[2];
    }
    float getLight() {
        return para[3];
    }
}
