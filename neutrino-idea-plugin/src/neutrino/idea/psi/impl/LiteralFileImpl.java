package neutrino.idea.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import neutrino.idea.LiteralFileType;
import org.jetbrains.annotations.NotNull;

public class LiteralFileImpl extends PsiFileBase {
    public LiteralFileImpl(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, LiteralFileType.FILE_TYPE.getLanguage());
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return LiteralFileType.FILE_TYPE;
    }
}
