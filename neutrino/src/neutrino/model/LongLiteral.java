package neutrino.model;

public class LongLiteral extends PrimitiveLiteral<Long> {
    @Override
    public String toString() {
        return String.valueOf(value);
    }
}