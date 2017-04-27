package un.TJU.SSE.PCP.type;

import org.junit.Test;


/**
 * Created by yiros on 2017/3/28.
 */
public class PointTest {
    @Test
    public void main () throws Exception {
        Point test = new Point(1,2,3,4);
        System.out.println(test.bytes());
        System.out.println(test.toBytes());

        for (int i = 0; i < 4; i++) {
            if (test.get(i)!= i+1)
                throw new Exception("Param error." + String.valueOf(i));
        }

        if (test.getX()!= 1)
            throw new Exception("X error.");
        if (test.getY()!= 2)
            throw new Exception("Y error.");
        if (test.getZ()!= 3)
            throw new Exception("Z error.");
        if (test.getLight()!= 4)
            throw new Exception("Lighting error.");
    }
}