package neutrino.model;

public class DoubleLiteral extends PrimitiveLiteral<Double> {
    @Override
    public String toString() {
        return String.valueOf(value) + "d";
    }
}