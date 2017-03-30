package un.TJU.SSE.PCP.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by yiros on 2017/3/28.
 */
public abstract class AbstractProcessor implements Processor {

    protected static final Logger LOGGER = LogManager.getRootLogger();

    public static final int SYNC = 0;
    public static final int ASYNC = 1;
    public static final int GL = 2;
    public static final int FULL_PERFORM = 3;


}
