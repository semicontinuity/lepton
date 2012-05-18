package neutrino.idea;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import neutrino.idea.parsing.LiteralElementTypes;
import neutrino.idea.psi.impl.ReferenceImpl;
import org.jetbrains.annotations.NotNull;

public class LiteralAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof ReferenceImpl) {
            final ReferenceImpl reference = (ReferenceImpl) element;
            if (reference.resolve() != null) {
                Annotation annotation = holder.createInfoAnnotation(element, null);
                annotation.setTextAttributes(LiteralHighlighter.TA__CLASS);
            }
        }
        else if (element.getNode().getElementType() == LiteralElementTypes.ARGUMENT) {
            Annotation annotation = holder.createInfoAnnotation(element, null);
            annotation.setTextAttributes(LiteralHighlighter.TA__PARAMETER);
        }
        else if (element.getNode().getElementType() == LiteralElementTypes.PROPERTY) {
            Annotation annotation = holder.createInfoAnnotation(element, null);
            annotation.setTextAttributes(LiteralHighlighter.TA__PROPERTY);
        }
    }
}
