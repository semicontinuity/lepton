package neutrino.idea.findUsages;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.psi.tree.TokenSet;
import neutrino.idea.parsing.LiteralLexer;
import neutrino.idea.parsing.LiteralTokenTypes;

public class LiteralWordsScanner extends DefaultWordsScanner {
  public LiteralWordsScanner() {
    super(new LiteralLexer(),
        TokenSet.create(LiteralTokenTypes.IDENTIFIER),
        LiteralTokenTypes.COMMENTS,
        TokenSet.create(LiteralTokenTypes.CHAR_DATA));
  }
}
