package un.TJU.SSE.PCP.processor;

import un.TJU.SSE.PCP.type.Point;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by yiros on 2017/4/28.
 *
 */
public class Float2PointProcessor extends AbstractProcessor implements Processor {

    private static final String OUTPUT_POSTFIX = ".f2p";

    @Override
    public void process(Map<String, String> params) throws IOException {
        LOGGER.info("F2P process start");
        try {
            folderProcess(Paths.get(params.get("input")),Paths.get(params.get("output")));
        }catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("F2P process finish");
    }

    @Override
    protected void fileProcess(Path inputPath, String outputPath) throws IOException {
        if (outputPath.lastIndexOf(PreProcessor.OUTPUT_POSTFIX) != outputPath.length() - PreProcessor.OUTPUT_POSTFIX.length())
            throw new IOException("File type not valid");
        else
            outputPath= outputPath.substring(0, outputPath.length() - PreProcessor.OUTPUT_POSTFIX.length()) + OUTPUT_POSTFIX;
        LOGGER.info("Start processing " + inputPath.toString() + " to " + outputPath);
        Date startTime = new Date();

        RandomAccessFile input = new RandomAccessFile(inputPath.toString(), "r");
        float[] floatBuffer = new float[(int) (input.length()/Float.BYTES)];
        MappedByteBuffer buffer = input.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, input.length());

        buffer.asFloatBuffer().get(floatBuffer);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) ((floatBuffer.length/ Point.defaultPoint.getNum()) * Point.defaultPoint.bytes()));
        for (int i = 0; i < floatBuffer.length; i+= Point.defaultPoint.getNum()) {
            outputStream.write(new Point(Arrays.copyOfRange(floatBuffer, i, i + Point.defaultPoint.getNum())).toBytes()) ;
        }

        RandomAccessFile output = new RandomAccessFile(outputPath, "rw");
        MappedByteBuffer out = output.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, outputStream.size());
        out.put(outputStream.toByteArray());

        output.close();
        input.close();
        LOGGER.debug("Finish processing " + inputPath.toString() + " to " + outputPath + " in " + String.valueOf((new Date()).getTime() - startTime.getTime()) + "ms");
    }
}
