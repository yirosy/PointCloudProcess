package un.TJU.SSE.PCP.processor;


import un.TJU.SSE.PCP.type.Point;
import un.TJU.SSE.PCP.util.ParamHelper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.util.Map;

/**
 * Created by yiros on 2017/3/28.
 */
public class PreProcessor extends AbstractProcessor implements Processor {

    private static final int DEFAULT_INPUT_BUFFER_SIZE = 4 * 1024 * 1024;
    private int inputBufferSize = 0;
    private int outputBufferSize = 0;
    private int blockSize = 0;

    private void init (final Map<String, String> params) {
        inputBufferSize= ParamHelper.getIntParam(params, "inputBufferSize");
        inputBufferSize = inputBufferSize - inputBufferSize% 4;
        if (inputBufferSize <= 0) {
            LOGGER.warn("Unsupported input. Use default input buffer size setting");
            inputBufferSize = DEFAULT_INPUT_BUFFER_SIZE;
        }
        LOGGER.info("Input buffer size set to " + String.valueOf(inputBufferSize));

        outputBufferSize= ParamHelper.getIntParam(params, "outputBufferSize");
        outputBufferSize = (int) (outputBufferSize - outputBufferSize% Point.defaultPoint.bytes());
        if (outputBufferSize <= 0) {
            LOGGER.warn("Unsupported input. Use default output buffer size setting");
            outputBufferSize = (int) (inputBufferSize/ 16* Point.defaultPoint.bytes());
        }
        LOGGER.info("Output buffer size set to "+ String.valueOf(outputBufferSize));

        blockSize= ParamHelper.getIntParam(params, "blockSize");
        blockSize = blockSize - blockSize% 16;
        if (blockSize <= 0) {
            LOGGER.warn("Unsupported input. Use default block size setting");
            blockSize = inputBufferSize * 64;
        }
        LOGGER.info("Block size set to "+ String.valueOf(blockSize));
    }

    private void readBlock (MappedByteBuffer inputBlock, MappedByteBuffer outputBlock) {
        byte[] inputBuffer = new byte[inputBufferSize];

    }

    private void syncProcess (RandomAccessFile inputFile, RandomAccessFile outputFile) throws IOException {

    }


    @Override
    public void process(final Map<String, String> params, int PROCESS_MODE) throws IOException {

        init(params);
        try (
                RandomAccessFile inputFile = new RandomAccessFile(ParamHelper.getNotNullStringParam(params, "input"), "r");
                RandomAccessFile outputFile = new RandomAccessFile(ParamHelper.getNotNullStringParam(params, "output"), "rw")
                ) {
            switch (PROCESS_MODE) {
                case PreProcessor.ASYNC:
                    LOGGER.info("Start in async mode");
                    break;
                case PreProcessor.FULL_PERFORM:
                    LOGGER.info("Start in full performance mode");
                    break;
                case PreProcessor.GL:
                    LOGGER.info("Start in graphic mode");
                    break;
                case PreProcessor.SYNC:
                    LOGGER.info("Start in sync mode");
                    syncProcess(inputFile, outputFile);
                    break;
                default:
                    LOGGER.error("Mode code " + String.valueOf(PROCESS_MODE) + " not support");
                    break;
            }
        }
        LOGGER.info("Finish successfully");
    }
}
