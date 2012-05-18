package neutrino.idea;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class LiteralFileTypeFactory extends FileTypeFactory {
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(LiteralFileType.FILE_TYPE, LiteralFileType.DEFAULT_EXTENSION);
    }
}