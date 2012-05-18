package neutrino.idea.parsing;

import com.intellij.lexer.LayeredLexer;
import com.intellij.lexer.StringLiteralLexer;
import com.intellij.psi.tree.IElementType;


public class LiteralHighlightingLexer extends LayeredLexer {

    public LiteralHighlightingLexer() {
        super(new LiteralLexer());
        registerSelfStoppingLayer(new StringLiteralLexer('\"', LiteralElementTypes.STRING_LITERAL),
            new IElementType[]{LiteralElementTypes.STRING_LITERAL}, IElementType.EMPTY_ARRAY);

        registerSelfStoppingLayer(new StringLiteralLexer('\'', LiteralElementTypes.CHAR_LITERAL),
            new IElementType[]{LiteralElementTypes.CHAR_LITERAL}, IElementType.EMPTY_ARRAY);
    }
}
