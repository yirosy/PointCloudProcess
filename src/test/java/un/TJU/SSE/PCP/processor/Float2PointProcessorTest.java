package un.TJU.SSE.PCP.processor;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yiros on 2017/4/28.
 *
 */
public class Float2PointProcessorTest {
    @Test
    public void main () throws IOException {
        Float2PointProcessor float2PointProcessor = new Float2PointProcessor();
        Map<String, String> params = new HashMap<>();
        params.put("input","temp\\pre");
        params.put("output","temp\\f2p");
        float2PointProcessor.process(params);
    }
}