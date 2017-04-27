package un.TJU.SSE.PCP.processor;


import un.TJU.SSE.PCP.util.ParamHelper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.util.Map;

/**
 * Created by yiros on 2017/3/28.
 */
public class NPreProcessor extends AbstractProcessor implements Processor {

    private static final int DEFAULT_INPUT_BUFFER_SIZE = 4 * 1024 * 1024;
    private int inputBufferSize = 0;
    private int outputBufferSize = 0;

    private void init (final Map<String, String> params) {
        inputBufferSize= ParamHelper.getIntParam(params, "inputBufferSize");
        if (inputBufferSize <= 0) {
            LOGGER.warn("Unsupported input. Use default input buffer size setting");
            inputBufferSize = DEFAULT_INPUT_BUFFER_SIZE;
        }
        LOGGER.info("Input buffer size set to " + String.valueOf(inputBufferSize));

        outputBufferSize= ParamHelper.getIntParam(params, "outputBufferSize");
        if (outputBufferSize <= 0) {
            LOGGER.warn("Unsupported input. Use default input output size setting");
            outputBufferSize = DEFAULT_INPUT_BUFFER_SIZE;
        }
        LOGGER.info("output buffer size set to " + String.valueOf(inputBufferSize));
    }

    private void read (String input, MappedByteBuffer output) {
    }

    private void syncProcess (RandomAccessFile inputFile, RandomAccessFile outputFile) throws IOException {

    }

    private void convert2floats (RandomAccessFile inputFile, RandomAccessFile outputFile) {

    }


    @Override
    public void process(Map<String, String> params) throws IOException {

    }
}
