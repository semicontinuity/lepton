package neutrino.model;


import javax.script.Bindings;

/**
 * A literal that evaluates to {@linkplain Bindings}.
 */
public abstract class AbstractBindingsLiteral<ElementType> extends CollectionLiteral<ElementType, Bindings> {
}