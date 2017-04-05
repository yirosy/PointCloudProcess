package un.TJU.SSE.PCP.processor;


import un.TJU.SSE.PCP.type.Point;
import un.TJU.SSE.PCP.util.ParamHelper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by yiros on 2017/3/28.
 */
public class PreProcessor extends AbstractProcessor implements Processor {

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
        MappedByteBuffer inputBuffer = inputFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, inputFile.length());
        byte[] inBuffer = new byte[inputBufferSize];
        Point[] outBuffer = new Point[outputBufferSize];
        int count= 0;

        StringBuilder temp= new StringBuilder();

        for (long i= 0; i< inputBuffer.limit(); i+= inputBufferSize) {
            //System.out.println(i);
            if (inputBuffer.limit()- i >= inputBufferSize) {
                inputBuffer.get(inBuffer, 0, inputBufferSize);
            } else {
                inBuffer = new byte[(int) (inputBuffer.limit()- i)];
                inputBuffer.get(inBuffer, 0, (int) (inputBuffer.limit()- i));
            }

            temp.append(new String(inBuffer));
            String[] num = new String(temp).split("[ |\r\n]+");
            //System.out.print(new String(buffer));
            temp.setLength(0);
            //System.out.println(num.length);
            int limit= num.length - num.length% Point.defaultPoint.getNum();
            for (int j = limit; j < num.length; j++) {
                temp.append(" ").append(num[j]);
            }

            for (int j = 0; j< limit;) {
                float[] para= new float[Point.defaultPoint.getNum()];
                for (int k = 0; k < Point.defaultPoint.getNum(); k++) {
                    try {
                        para[k]= Float.parseFloat(num[j++]);
                    } catch (Exception e) {
                        para[k]= Integer.parseInt(num[j- 1]);
                    }
                }
                Point point = new Point(para);
                point.toBytes();
            }
        }

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
