package neutrino.idea.parsing;

import com.intellij.psi.tree.IElementType;
import neutrino.idea.LiteralLanguage;

public class LiteralElementType extends IElementType {
    public LiteralElementType(String debugName) {
        super(debugName, LiteralLanguage.INSTANCE);
    }

    public String toString() {
        return "Literal:" + super.toString();
    }
}