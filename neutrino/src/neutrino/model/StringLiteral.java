package neutrino.model;


public class StringLiteral extends PrimitiveLiteral<String> {

    @Override
    public String toString() {
        final int length = value.length();
        final StringBuilder stringBuilder = new StringBuilder(length + 2);
        stringBuilder.append('\"');

        for (int i = 0; i < length; i++) {
            stringBuilder.append(CharacterLiteral.toEscapedString(value.charAt(i)));
        }

        stringBuilder.append('\"');
        return stringBuilder.toString();
    }
}