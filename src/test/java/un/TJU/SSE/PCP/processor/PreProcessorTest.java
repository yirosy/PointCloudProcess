package un.TJU.SSE.PCP.processor;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yiros on 2017/3/29.
 */
public class PreProcessorTest {

    @Test
    public void main () throws IOException{
        PreProcessor preProcessor = new PreProcessor();
        Map<String, String> params = new HashMap<>();
        params.put("input","testData");
        params.put("output","pre");
        preProcessor.process(params);
    }

}