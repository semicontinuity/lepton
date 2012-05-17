package neutrino.parser;

public class LiteralElement {

    private LiteralElementTypes type;
    private Object value;
    private int line;
    private int column;

    public LiteralElement(LiteralElementTypes type, Object value) {
        this(type, -1, -1, -1, -1, value);
    }

    public LiteralElement(LiteralElementTypes type, int line, int column) {
        this(type, line, column, -1, -1, null);
    }

    public LiteralElement(LiteralElementTypes type, int line, int column, Object value) {
        this(type, line, column, -1, -1, value);
    }

    public LiteralElement(LiteralElementTypes type, int line, int column, int left, int right, Object value) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public LiteralElementTypes getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String toString() {
        return "line " + line + ", column " + column + ", sym: " + type + (value == null ? "" : (", value: '" + value + "'"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LiteralElement)) return false;

        LiteralElement token = (LiteralElement) o;

        if (type != token.type) return false;
        if (value != null ? !value.equals(token.value) : token.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + line;
        result = 31 * result + column;
        return result;
    }
}
