package un.TJU.SSE.PCP.type;

import un.TJU.SSE.PCP.util.ObjectStreamHelper;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by yiros on 2017/3/28.
 */
public class NIOPointList {

    private final RandomAccessFile sourceFile;
    private final FileChannel sourceChannel;
    private final long start;
    private final long end;


    public NIOPointList(String path) throws IOException {

        sourceFile= new RandomAccessFile(path, "rw");
        sourceChannel= sourceFile.getChannel();
        start= 0;
        end= sourceFile.length();
    }

    private NIOPointList(NIOPointList original, long start, long end) throws IOException {

        sourceFile= original.sourceFile;
        sourceChannel= original.sourceChannel;
        this.start= start;
        this.end= end;
    }

    public long length () throws IOException {

        return sourceChannel.size()/Point.defaultPoint.bytes();
    }

    public Point get(long index) throws IOException, ClassNotFoundException {
        MappedByteBuffer mappedByteBuffer = sourceChannel.map(FileChannel.MapMode.READ_ONLY, index*Point.defaultPoint.bytes(), Point.defaultPoint.bytes());
        byte[] buffer = new byte[(int)Point.defaultPoint.bytes()];
        mappedByteBuffer.get(buffer);
        return (Point) ObjectStreamHelper.readObject(buffer);
    }

    public void put(long index, Point point) throws IOException {
        MappedByteBuffer mappedByteBuffer = sourceChannel.map(FileChannel.MapMode.READ_WRITE, index*Point.defaultPoint.bytes(), Point.defaultPoint.bytes());
        byte[] buffer = point.toBytes();
        mappedByteBuffer.put(buffer);
    }

    public NIOPointList subList (long start, long length) throws IOException {
        return new NIOPointList(this, start*Point.defaultPoint.bytes(), (start+ length) * Point.defaultPoint.bytes());
    }
}
