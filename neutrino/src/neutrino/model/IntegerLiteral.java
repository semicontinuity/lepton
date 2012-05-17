package neutrino.model;

public class IntegerLiteral extends PrimitiveLiteral<Integer> {
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}