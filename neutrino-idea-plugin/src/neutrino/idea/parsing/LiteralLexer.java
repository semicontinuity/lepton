package neutrino.idea.parsing;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class LiteralLexer extends FlexAdapter {
    public LiteralLexer() {
        super(new _LiteralLexer((Reader) null));
    }
}
