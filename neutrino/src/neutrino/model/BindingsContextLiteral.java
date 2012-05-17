package neutrino.model;

public class BindingsContextLiteral extends MapBindingsLiteral {
    @Override
    protected char openingCharacter() { return '<'; }

    @Override
    protected char closingCharacter() { return '>'; }
}
