package neutrino.idea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.ClassUtil;
import com.intellij.util.IncorrectOperationException;
import neutrino.idea.parsing.LiteralElementTypes;
import neutrino.idea.psi.Reference;
import neutrino.idea.psi.ReferenceStub;
import org.jetbrains.annotations.NotNull;

public class ReferenceImpl extends LiteralStubElementImpl<ReferenceStub> implements Reference, PsiReference {
    public ReferenceImpl(final ASTNode node) {
        super(node);
        System.out.println("Created ReferenceImpl from node " + node);
    }

    public ReferenceImpl(final ReferenceStub stub) {
        super(stub, LiteralElementTypes.REFERENCE);
        System.out.println("Created ReferenceImpl from stub " + stub);
    }

    public String toString() {
        return "Reference";
    }

    @Override
    public PsiReference getReference() {
        return this;
    }


    // PsiReference
    // =================================================================================================================

    @Override
    public PsiElement getElement() {
        return this;
    }

    @Override
    public TextRange getRangeInElement() {
        int textLength = getTextLength();
        System.out.println("textLength = " + textLength);
        return new TextRange(0, textLength);
//        return getTextRange();
    }

    @Override
    public PsiElement resolve() {
        final String canonicalText = getCanonicalText();
        final PsiElement resolved = resolve(canonicalText);
        if (resolved != null) return resolved;

        final String prefix = ClassUtil.extractPackageName(canonicalText);
        final PsiElement resolvedPrefix = resolve(prefix);
        if (resolvedPrefix instanceof PsiClass) {
            final PsiClass psiClass = (PsiClass) resolvedPrefix;
            final String memberName = ClassUtil.extractClassName(canonicalText);

            final PsiField field = psiClass.findFieldByName(memberName, false);
            if (field != null) return field;

            final PsiMethod[] methods = psiClass.findMethodsByName(memberName, false);// TODO: poly ref?
            if (methods.length > 0) return methods[0];
        }
        return null;
    }

    private PsiElement resolve(final String text) {
        System.out.println("Resolving " + text);
        return JavaPsiFacade.getInstance(getProject()).findClass(text, GlobalSearchScope.allScope(getProject()));
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return getText().replace(" ", "");
    }

    @Override
    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
        return null;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        PsiElement resolve = resolve();
        System.out.println("isReferenceTo: resolve=" + resolve + " element=" + element);
        System.out.println("isReferenceTo: resolve=" + System.identityHashCode(resolve) + " element=" + System.identityHashCode(element));
        boolean b = element.isEquivalentTo(resolve);
        System.out.println("isReferenceTo: ref=" + this.getText() + " element=" + element + " result=" + b);
        return b;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
