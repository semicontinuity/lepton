package neutrino.model;

import java.util.ArrayList;
import java.util.List;

public abstract class CollectionLiteral<ElementType, ValueType> extends NonPrimitiveLiteral<ValueType> {
    protected List<ElementType> elements = new ArrayList<ElementType>();

    public List<? extends ElementType> getElements() { return elements; }

    public void setElements(final List<ElementType> elements) { this.elements = elements; }

    protected abstract char openingCharacter();

    protected abstract char closingCharacter();

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(openingCharacter());
        builder.append(contentsToString());
        builder.append(closingCharacter());
        return builder.toString();
    }

    public String contentsToString() {
        final StringBuilder builder = new StringBuilder();
        final int maxIndex = elements.size() - 1;
        for (int i = 0; i <= maxIndex; i++) {
            builder.append(elements.get(i).toString());
            if (i != maxIndex) builder.append(' ');
        }
        return builder.toString();
    }
}
