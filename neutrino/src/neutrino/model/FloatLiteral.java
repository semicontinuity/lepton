package neutrino.model;

public class FloatLiteral extends PrimitiveLiteral<Float> {
    @Override
    public String toString() {
        return String.valueOf(value) + "f";
    }
}