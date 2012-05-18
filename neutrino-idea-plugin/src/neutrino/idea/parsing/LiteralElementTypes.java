package neutrino.idea.parsing;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public interface LiteralElementTypes {
    IElementType ARRAY_LITERAL = new LiteralElementType("ARRAY_LITERAL");

    IElementType STRING_LITERAL = new LiteralElementType("STRING_LITERAL");
    IElementType CHAR_LITERAL = new LiteralElementType("CHAR_LITERAL");

    IStubElementType REFERENCE = new ReferenceStubElementType();
    IElementType CONTEXTS = new LiteralElementType("CONTEXTS");
    IElementType LIST_CONTEXT = new LiteralElementType("LIST_CONTEXT");
    IElementType MAP_CONTEXT = new LiteralElementType("MAP_CONTEXT");
    IElementType MAP_ENTRY = new LiteralElementType("MAP_ENTRY");
    IElementType PROPERTY = new LiteralElementType("PROPERTY");

    IElementType CONTEXT_EXPRESSION = new LiteralElementType("CONTEXT_EXPRESSION");

    IElementType ARRAY = new LiteralElementType("ARRAY");
    IElementType BINDINGS = new LiteralElementType("BINDINGS");

    IElementType LIST = new LiteralElementType("LIST");
    IElementType LIST_ELEMENTS = new LiteralElementType("LIST_ELEMENTS");
    IElementType MAP = new LiteralElementType("MAP");
    IElementType MAP_ELEMENTS = new LiteralElementType("MAP_ELEMENTS");

    IElementType FUNCTION_DECLARATION = new LiteralElementType("FUNCTION_DECLARATION");
    IElementType FUNCTION_CALL = new LiteralElementType("FUNCTION_CALL");


    IElementType ARGUMENT = new LiteralElementType("ARGUMENT");
}
