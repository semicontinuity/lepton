package neutrino.idea;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class LiteralColorsAndFontsPage implements ColorSettingsPage {
    @NotNull
    public String getDisplayName() {
        return "Object Literals";
    }

    @Nullable
    public Icon getIcon() {
        return IconLoader.getIcon("/fileTypes/properties.png");
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[]{
        new AttributesDescriptor("Keyword", LiteralHighlighter.TA__KEYWORD),
        new AttributesDescriptor("Number", LiteralHighlighter.TA__NUMBER),
        new AttributesDescriptor("String", LiteralHighlighter.TA__STRING),
        new AttributesDescriptor("Valid escape in string", LiteralHighlighter.TA__VALID_STRING_ESCAPE),
        new AttributesDescriptor("Invalid escape in string", LiteralHighlighter.TA__INVALID_STRING_ESCAPE),
        new AttributesDescriptor("Operator sign", LiteralHighlighter.TA__OPERATOR_SIGN),
        new AttributesDescriptor("Parentheses", LiteralHighlighter.TA__PARENTHESES),
        new AttributesDescriptor("Braces", LiteralHighlighter.TA__BRACES),
        new AttributesDescriptor("Brackets", LiteralHighlighter.TA__BRACKETS),
        new AttributesDescriptor("Colon", LiteralHighlighter.TA__COLON),
        new AttributesDescriptor("Dot", LiteralHighlighter.TA__DOT),
        new AttributesDescriptor("Pound sign", LiteralHighlighter.TA__POUND_SIGN),
        new AttributesDescriptor("Triangular braces", LiteralHighlighter.TA__TRIANGULAR_BRACES),
        new AttributesDescriptor("Line comment", LiteralHighlighter.TA__END_OF_LINE_COMMENT),
        new AttributesDescriptor("Block comment", LiteralHighlighter.TA__BLOCK_COMMENT),
        new AttributesDescriptor("Identifier", LiteralHighlighter.TA__IDENTIFIER),


        new AttributesDescriptor("Class", LiteralHighlighter.TA__CLASS),
        new AttributesDescriptor("Property", LiteralHighlighter.TA__PROPERTY),
        new AttributesDescriptor("Parameter", LiteralHighlighter.TA__PARAMETER),
    };

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return new LiteralHighlighter();
    }

    @NonNls
    @NotNull
    public String getDemoText() {
        return
            "/*\n" +
            " * Block comment\n" +
            " */\n" +
            "[\n" +
            "  // line comment\n" +
            "  true\n" +
            "  123\n" +
            "  123456L\n" +
            "  123.456f\n" +
            "  123.456789d\n" +
            "  'c'\n" +
            "  '\\n'\n" +
            "  \"String with escapes: valid\\t, invalid \\x.\"\n" +
            "  (a:+1 b:-2)\n" +
            "  <button: #() javax.swing.JButton() {width:200 heigth:100}> button {text: \"Hi\"}\n" +
            "]\n";
    }

    @Nullable
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        Map<String, TextAttributesKey> map = new HashMap<String, TextAttributesKey>();
/*
    map.put("annotation", LiteralHighlighter.ANNOTATION);
    map.put("statmet", LiteralHighlighter.STATIC_METHOD_ACCESS);
    map.put("statfield", LiteralHighlighter.STATIC_FIELD);
    map.put("instfield", LiteralHighlighter.INSTANCE_FIELD);
    map.put("gdoc", LiteralHighlighter.DOC_COMMENT_CONTENT);
    map.put("doctag", LiteralHighlighter.DOC_COMMENT_TAG);
    map.put("unresolved", LiteralHighlighter.UNRESOLVED_ACCESS);
    map.put("classref", LiteralHighlighter.CLASS_REFERENCE);
    map.put("literal", LiteralHighlighter.LITERAL_CONVERSION);
    map.put("mapkey", LiteralHighlighter.MAP_KEY);
    map.put("prop", LiteralHighlighter.INSTANCE_PROPERTY_REFERENCE);
    map.put("staticprop", LiteralHighlighter.STATIC_PROPERTY_REFERENCE);
*/
        return map;
    }
}
