package neutrino.idea.findUsages;

import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiFormatUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiteralFindUsagesProvider implements FindUsagesProvider {

    public static final LiteralFindUsagesProvider INSTANCE = new LiteralFindUsagesProvider();

    public LiteralFindUsagesProvider() {
    }

    @Nullable
    public WordsScanner getWordsScanner() {
        return new LiteralWordsScanner();
    }

    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiClass;
    }

    @Nullable
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @NotNull
    public String getType(@NotNull PsiElement element) {
        if (element instanceof PsiClass) return "class";
        return "";
    }

    @NotNull
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof PsiClass) {
            final PsiClass aClass = (PsiClass) element;
            String qName = aClass.getQualifiedName();
            return qName == null ? "" : qName;
        } else if (element instanceof PsiMethod) {
            final PsiMethod method = (PsiMethod) element;
            String result = PsiFormatUtil.formatMethod(method,
                    PsiSubstitutor.EMPTY, PsiFormatUtil.SHOW_NAME | PsiFormatUtil.SHOW_PARAMETERS,
                    PsiFormatUtil.SHOW_TYPE);
            final PsiClass clazz = method.getContainingClass();
            if (clazz != null) {
                result += " of " + getDescriptiveName(clazz);
            }

            return result;
        } else if (element instanceof PsiVariable) {
            final String name = ((PsiVariable) element).getName();
            if (name != null) {
                return name;
            }
        }

        return "";
    }

    @NotNull
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof PsiClass) {
            String name = ((PsiClass) element).getQualifiedName();
            if (name == null || !useFullName) {
                name = ((PsiClass) element).getName();
            }
            if (name != null) return name;
        } else if (element instanceof PsiMethod) {
            return PsiFormatUtil.formatMethod((PsiMethod) element,
                    PsiSubstitutor.EMPTY,
                    PsiFormatUtil.SHOW_NAME | PsiFormatUtil.SHOW_PARAMETERS,
                    PsiFormatUtil.SHOW_TYPE);

        } else if (element instanceof PsiVariable) {
            final String name = ((PsiVariable) element).getName();
            if (name != null) {
                return name;
            }
        }

        return "";
    }
}
