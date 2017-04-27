package un.TJU.SSE.PCP.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by yiros on 2017/4/27.
 */
public class NOIHelper {
    public static void createFolderSafely (Path path) throws IOException {
        if (Files.exists(path)) {
            if (!Files.isDirectory(path))
                throw new IOException(path.toString() + " exists and is not directory.");
        } else {
            Files.createDirectory(path);
        }
    }

    public static boolean isNumric (char c) {
        if (c >= '0' && c <= '9')
            return true;
        if (c == '.')
            return true;
        if (c == '-')
            return true;
        return false;
    }
}
