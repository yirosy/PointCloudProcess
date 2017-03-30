package un.TJU.SSE.PCP.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by yiros on 2017/3/28.
 */
public class ArrayHelper {
    public static<T>  T[] concat (T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}