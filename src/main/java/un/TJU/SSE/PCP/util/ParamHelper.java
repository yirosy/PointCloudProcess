package un.TJU.SSE.PCP.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by yiros on 2017/3/29.
 */
public class ParamHelper {

    private static final Logger LOGGER = LogManager.getRootLogger();

    public static String getNotNullStringParam (Map<String, String> params, String key){
        String result = params.get(key);
        if (result== null) {
            LOGGER.error("Miss param \"" + key + "\"");
            throw new RuntimeException("Miss param \"" + key + "\"");
        }

        return result;
    }

    public static int getIntParam (Map<String, String> params, String key) {
        String temp = params.get(key);
        if (temp!= null)
            return Integer.parseInt(temp);
        LOGGER.warn("Int param \"" + key + "\" not found");
        return 0;
    }
}
