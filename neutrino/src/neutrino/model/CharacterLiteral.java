package neutrino.model;

public class CharacterLiteral extends PrimitiveLiteral<Character> {

    static String toEscapedString(final char c) {
        if (c == '\n') return "\\n";
        if (c == '\t') return "\\t";
        if (c == '\b') return "\\b";
        if (c == '\f') return "\\f";
        if (c == '\r') return "\\r";
        if (c == '\'') return "\\'";
        if (c == '\"') return "\\\"";
        if (c == '\\') return "\\\\";
        if (c < 0x20 || c > 0x7f) {
            return "\\u"
                + Character.forDigit((c >> 12) & 0xF, 16)
                + Character.forDigit((c >> 8) & 0xF, 16)
                + Character.forDigit((c >> 4) & 0xF, 16)
                + Character.forDigit((c) & 0xF, 16);
        }
        return String.valueOf(c);
    }

    @Override
    public String toString() {
        return '\'' + toEscapedString(value) + '\'';
    }
}
