package un.TJU.SSE.PCP.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by yiros on 2017/4/27.
 *
 */
public class NIOHelper {
    public static void createFolderSafely (Path path) throws IOException {

        if (Files.exists(path)) {
            if (!Files.isDirectory(path))
                throw new IOException(path.toString() + " exists and is not directory.");
        } else {
            if (!Files.exists(path.getParent().toAbsolutePath()))
                NIOHelper.createFolderSafely(path.getParent().toAbsolutePath());
            Files.createDirectory(path);
        }
    }

    public static boolean isNumeric (char c) {
        return c >= '0' && c <= '9' || c == '.' || c == '-';
    }
}
