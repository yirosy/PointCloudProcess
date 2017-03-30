package un.TJU.SSE.PCP.processor;


import java.io.IOException;
import java.util.Map;

/**
 * Created by yiros on 2017/3/28.
 */
public interface Processor {
    void process (Map<String, String> params, int PROCESS_MODE) throws IOException;
}
