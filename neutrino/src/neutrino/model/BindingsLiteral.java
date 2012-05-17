package neutrino.model;

public class BindingsLiteral extends MapBindingsLiteral {
    @Override
    protected char openingCharacter() { return '('; }

    @Override
    protected char closingCharacter() { return ')'; }
}
