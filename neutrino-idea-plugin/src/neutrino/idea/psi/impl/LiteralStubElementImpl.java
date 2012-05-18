package neutrino.idea.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import neutrino.idea.LiteralLanguage;
import org.jetbrains.annotations.NotNull;

public abstract class LiteralStubElementImpl<T extends StubElement> extends StubBasedPsiElementBase<T> {
    public LiteralStubElementImpl(final T stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public LiteralStubElementImpl(final ASTNode node) {
        super(node);
    }

    @NotNull
    public Language getLanguage() {
        return LiteralLanguage.INSTANCE;
    }
}