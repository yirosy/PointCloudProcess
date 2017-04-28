package un.TJU.SSE.PCP.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import un.TJU.SSE.PCP.util.NIOHelper;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by yiros on 2017/3/28.
 *
 */
abstract class AbstractProcessor implements Processor {

    
    
    void folderProcess(Path inputFolderPath, Path outputFolderPath) throws IOException {

        if (Files.isDirectory(inputFolderPath)){
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(inputFolderPath)) {
                for (Path inputFilePath : directoryStream
                        ) {
                    Path outputFileRoot = Paths.get(outputFolderPath.toString() + "\\" + inputFilePath.getFileName().toString());
                    folderProcess(inputFilePath, Paths.get(outputFileRoot.toString()));
                }
            }
        } else {
            NIOHelper.createFolderSafely(outputFolderPath.getParent().toAbsolutePath());
            fileProcess(inputFolderPath, outputFolderPath.toString());
        }
    }

    protected abstract void fileProcess(Path inputFilePath, String s) throws IOException;

    static final Logger LOGGER = LogManager.getRootLogger();
}
