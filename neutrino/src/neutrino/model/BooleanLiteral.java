package neutrino.model;


public class BooleanLiteral extends PrimitiveLiteral<Boolean> {

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}