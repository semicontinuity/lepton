package neutrino.idea.parsing;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import neutrino.idea.psi.impl.LiteralFileImpl;
import neutrino.idea.psi.impl.ReferenceImpl;
import org.jetbrains.annotations.NotNull;

public class LiteralParserDefinition implements ParserDefinition {

    @NotNull
    public Lexer createLexer(final Project project) {
        return new LiteralLexer();
    }

    public IFileElementType getFileNodeType() {
        return LiteralTokenTypes.FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return LiteralTokenTypes.WHITESPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return LiteralTokenTypes.COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new LiteralParser();
    }

    public PsiFile createFile(final FileViewProvider viewProvider) {
        return new LiteralFileImpl(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(final ASTNode node) {
        final IElementType type = node.getElementType();
        if (type == LiteralElementTypes.REFERENCE) {
            return new ReferenceImpl(node);
        }


        if (node instanceof PsiElement)
            return (PsiElement) node;
        else
            return new ASTWrapperPsiElement(node);
    }
}