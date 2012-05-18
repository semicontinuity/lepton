package neutrino.idea;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author max
 */
public class LiteralLanguage extends Language {
    public static final LiteralLanguage INSTANCE = new LiteralLanguage();

    public LiteralLanguage() {
        super("ObjectLiteral", "text/object-literal");
        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
            @NotNull
            protected SyntaxHighlighter createHighlighter() {
                return new LiteralHighlighter();
            }
        });
    }
}