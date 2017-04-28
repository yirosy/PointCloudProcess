package un.TJU.SSE.PCP.processor;

import un.TJU.SSE.PCP.type.Point;
import un.TJU.SSE.PCP.util.NIOHelper;
import un.TJU.SSE.PCP.util.ParamHelper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Date;

/**
 * Created by yiros on 2017/4/27.
 *
 */
public class PreProcessor extends AbstractProcessor implements Processor {

    private static int DEFAULT_INPUT_BUFFER_SIZE = 4 * 1024 * 1024;
    private static int DEFAULT_OUTPUT_FILE_SIZE = (int) (((Integer.MAX_VALUE - 10)/ Point.defaultPoint.bytes()) -
            (((Integer.MAX_VALUE - 10)/ Point.defaultPoint.bytes()) % Point.defaultPoint.getNum()));
    static final String OUTPUT_POSTFIX = ".pre";

    private int bufferSize = 0;
    private int maxOutputFileSize = 0;


    private void init (Map<String, String> params) {
        bufferSize= ParamHelper.getIntParam(params, "bufferSize");
        if (bufferSize <= 0) {
            LOGGER.warn("Unsupported input. Use default buffer size setting");
            bufferSize = DEFAULT_INPUT_BUFFER_SIZE;
        }
        LOGGER.debug("Buffer size set to " + String.valueOf(bufferSize));

        maxOutputFileSize= ParamHelper.getIntParam(params, "maxOutputFileSize");
        maxOutputFileSize= maxOutputFileSize - (maxOutputFileSize% Point.defaultPoint.getNum());
        if (maxOutputFileSize <= 0 || maxOutputFileSize> DEFAULT_INPUT_BUFFER_SIZE) {
            LOGGER.warn("Unsupported input. Use default max output file size setting");
            maxOutputFileSize = DEFAULT_OUTPUT_FILE_SIZE;
        }
        LOGGER.debug("Max output file size set to " + String.valueOf(maxOutputFileSize));
    }

    protected void fileProcess(Path inputPath, String outputPathPrefix) throws IOException {

        Date startTime = new Date();

        NIOHelper.createFolderSafely(Paths.get(outputPathPrefix));
        outputPathPrefix = outputPathPrefix + "\\" + Paths.get(outputPathPrefix).getFileName();
        LOGGER.info("Start processing " + inputPath.toString() + " to " + outputPathPrefix + OUTPUT_POSTFIX);

        float[] outputBuffer = new float[maxOutputFileSize];
        int t= 0;
        int page = 0;
        StringBuilder tempString = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        RandomAccessFile input = new RandomAccessFile(inputPath.toString(), "r");
        long position = (input.length() - 1) % bufferSize + 1;
        MappedByteBuffer buffer = input.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, position);
        byte[] inputBuffer = new byte[bufferSize];
        for (; ; position+= bufferSize) {

            buffer.get(inputBuffer, 0, buffer.limit());
            tempString.append(new String(inputBuffer));
            for (int i = 0; i < buffer.limit(); i++) {
                if (NIOHelper.isNumeric(tempString.charAt(i))) {
                    temp.append(tempString.charAt(i));
                } else if (temp.length()> 0){
                    try {
                        float tempFloat = Float.parseFloat(temp.toString());
                        outputBuffer[t++] = tempFloat;
                    } catch (Exception exception){
                        try {
                            float tempFloat = Integer.parseInt(temp.toString());
                            outputBuffer[t++] = tempFloat;
                        } catch (Exception e) {
                            LOGGER.warn("Can not convert \"" + temp + "\"");
                        }
                    } finally {
                        temp.setLength(0);
                    }

                    if (t== maxOutputFileSize) {
                        RandomAccessFile output = new RandomAccessFile(outputPathPrefix + String.valueOf(++page) + OUTPUT_POSTFIX, "rw");
                        MappedByteBuffer oBuffer = output.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, t * Float.BYTES);
                        oBuffer.asFloatBuffer().put(outputBuffer);
                        output.close();
                        t = 0;
                    }
                }
            }
            if (position >= input.length()) break;
            tempString.setLength(0);

            buffer = input.getChannel().map(FileChannel.MapMode.READ_ONLY, position, bufferSize);
        }

        if (temp.length() > 0)
            try {
                float tempFloat = Float.parseFloat(temp.toString());
                outputBuffer[t++] = tempFloat;
            } catch (Exception exception) {
                try {
                    float tempFloat = Integer.parseInt(temp.toString());
                    outputBuffer[t++] = tempFloat;
                } catch (Exception e) {
                    LOGGER.warn("Can not convert \"" + temp + "\"");
                }
            }

        RandomAccessFile output = new RandomAccessFile(outputPathPrefix + String.valueOf(++page) + OUTPUT_POSTFIX, "rw");
        MappedByteBuffer oBuffer = output.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, t * Float.BYTES);
        oBuffer.asFloatBuffer().put(outputBuffer, 0, t);
        output.close();

        input.close();
        LOGGER.debug("Finish processing " + inputPath.toString() + " to " + outputPathPrefix + " in " + String.valueOf((new Date()).getTime() - startTime.getTime()) + "ms");
    }

    @Override
    public void process(Map<String, String> params) throws IOException {
        LOGGER.info("Pre process start");
        try {
            init(params);
            folderProcess(Paths.get(params.get("input")),Paths.get(params.get("output")));
        }catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("Pre process finish");
    }
}
