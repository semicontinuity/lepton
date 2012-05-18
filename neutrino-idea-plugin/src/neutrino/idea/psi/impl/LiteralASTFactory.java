package neutrino.idea.psi.impl;

import com.intellij.lang.ASTFactory;
import com.intellij.psi.impl.source.tree.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import neutrino.idea.parsing.LiteralTokenTypes;
import org.jetbrains.annotations.Nullable;

public class LiteralASTFactory extends ASTFactory {
    @Nullable
    public CompositeElement createComposite(final IElementType type) {
        if (type instanceof IFileElementType) {
            return new FileElement(type, null);
        }
        return new CompositeElement(type);
    }

    @Nullable
    public LeafElement createLeaf(final IElementType type, final CharSequence text) {
        System.out.println("---------------------------------------------");
        System.out.println("type = " + type);
        System.out.println("text = " + text);

        if (type == LiteralTokenTypes.BLOCK_COMMENT
            || type == LiteralTokenTypes.END_OF_LINE_COMMENT) {
            return new PsiCommentImpl(type, text);
        }
/*
        else if (type == LiteralTokenTypes.IDENTIFIER) {
            return new PsiCommentImpl(type, text);
        }
*/
        else return new LeafPsiElement(type, text);
    }
}
