package neutrino.parser;

import neutrino.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static neutrino.parser.LiteralElementTypes.POUND;

public class Parser {

    public Evaluable parseLiteral(final Scanner scanner) throws IOException {
        final Evaluable literal = readLiteral(scanner);
        if (scanner.nextTokenBuffered().getType() != LiteralElementTypes.EOF)
            throw new IllegalArgumentException();
        else
            return literal;
    }


    public Evaluable readLiteral(final Scanner scanner) throws IOException {
        final AbstractBindingsLiteral<?> context = readContexts(scanner);
        final Evaluable annotation = readAnnotation(scanner);

        if (context != null) {
            final NonPrimitiveLiteral nonPrimitiveLiteral = readNonPrimitiveLiteral(scanner, context);
            nonPrimitiveLiteral.setAnnotation(annotation);
            return nonPrimitiveLiteral;
        }

        final LiteralElement token = scanner.nextTokenBuffered();
        if (token.getType() == POUND) {
            scanner.pushBack(token);
            final FunctionDeclarationLiteral functionDeclarationLiteral = readFunctionDeclaration(scanner);
            functionDeclarationLiteral.setAnnotation(annotation);
            return functionDeclarationLiteral;
        }

        if (annotation == null) {
            switch (token.getType()) {
                case CHARACTER_LITERAL:
                    final CharacterLiteral characterLiteral = new CharacterLiteral();
                    characterLiteral.setValue((Character) token.getValue());  // TODO introduce CharacterToken
                    return characterLiteral;
                case STRING_LITERAL:
                    final StringLiteral stringLiteral = new StringLiteral();
                    stringLiteral.setValue((String) token.getValue());    // TODO introduce StringToken
                    return stringLiteral;
                case INTEGER_LITERAL:
                    final IntegerLiteral integerLiteral = new IntegerLiteral();
                    integerLiteral.setValue((Integer) token.getValue());
                    return integerLiteral;
                case LONG_LITERAL:
                    final LongLiteral longLiteral = new LongLiteral();
                    longLiteral.setValue((Long) token.getValue());
                    return longLiteral;
                case BOOLEAN_LITERAL:
                    final BooleanLiteral booleanLiteral = new BooleanLiteral();
                    booleanLiteral.setValue((Boolean) token.getValue());
                    return booleanLiteral;
                case FLOAT_LITERAL:
                    final FloatLiteral floatLiteral = new FloatLiteral();
                    floatLiteral.setValue((Float) token.getValue());
                    return floatLiteral;
                case DOUBLE_LITERAL:
                    final DoubleLiteral doubleLiteral = new DoubleLiteral();
                    doubleLiteral.setValue((Double) token.getValue());
                    return doubleLiteral;
            }
        }

        scanner.pushBack(token);
        final NonPrimitiveLiteral nonPrimitiveLiteral = readNonPrimitiveLiteral(scanner, context);
        nonPrimitiveLiteral.setAnnotation(annotation);
        return nonPrimitiveLiteral;
    }


    private Evaluable readAnnotation(final Scanner scanner) throws IOException {
        final LiteralElement temp1 = scanner.nextTokenBuffered();
        if (temp1.getType() == LiteralElementTypes.AT) {
            return readLiteral(scanner);
        } else {
            scanner.pushBack(temp1);
            return null;
        }
    }


    /**
     * Reads non-primitive literals
     */
    NonPrimitiveLiteral readNonPrimitiveLiteral(
        final Scanner scanner,
        final AbstractBindingsLiteral<?> literalContext)
        throws IOException {

        final LiteralElement token = scanner.nextTokenBuffered();
        scanner.pushBack(token);

        if (token.getType() == LiteralElementTypes.LBRACK) {
            final NonPrimitiveLiteral<?> compositeLiteral = readCompositeLiteral(scanner);
            compositeLiteral.setContext(literalContext);
            return compositeLiteral;
        }
        else if (token.getType() == LiteralElementTypes.LPAREN) {
            final CollectionLiteral compositeLiteral = readArrayOrBindingsLiteral(scanner);
            compositeLiteral.setContext(literalContext);
            return compositeLiteral;
        }
        else if (token.getType() == LiteralElementTypes.IDENTIFIER) {
            final ReferenceLiteral referenceLiteral = readReference(scanner);

            final LiteralElement tokenAfterReference = scanner.nextTokenBuffered();
            scanner.pushBack(tokenAfterReference);

            if (/*tokenAfterReference.getType() == TokenType.LT ||*/
                tokenAfterReference.getType() == LiteralElementTypes.LPAREN
                || tokenAfterReference.getType() == LiteralElementTypes.LBRACE) {

                final FunctionCallLiteral callLiteral = new FunctionCallLiteral();
                callLiteral.setReference(referenceLiteral);
                callLiteral.setContext(literalContext);
                if (tokenAfterReference.getType() == LiteralElementTypes.LPAREN) {
                    callLiteral.setArguments(readArrayOrBindingsLiteral(scanner));
                }
                else {
                    callLiteral.setArguments(new ArrayLiteral());
                }
                // parse initializers, if any
                callLiteral.setInitializers(readInitializers(scanner));
                return callLiteral;
            }
            else {
                referenceLiteral.setContext(literalContext);
                return referenceLiteral;
            }
        }
        else throw new IllegalArgumentException(token.toString());
    }


    FunctionDeclarationLiteral readFunctionDeclaration(final Scanner scanner) throws IOException {
        final LiteralElement pound = scanner.nextTokenBuffered();
        if (pound.getType() != POUND)
            throw new IllegalArgumentException(pound.toString() + " - expected POUND");
        final LiteralElement leftParenthesis = scanner.nextTokenBuffered();
        if (leftParenthesis.getType() != LiteralElementTypes.LPAREN)
            throw new IllegalArgumentException(leftParenthesis.toString() + " - expected LPAREN");

        final Map<String, Integer> argumentOrder = new HashMap<String, Integer>();
        int i = 0;
        while (true) {
            final LiteralElement token = scanner.nextTokenBuffered();
            if (token.getType() == LiteralElementTypes.IDENTIFIER) {
                argumentOrder.put((String) token.getValue(), i++);
            }
            else if (token.getType() == LiteralElementTypes.RPAREN) {
                break;
            }
            else throw new IllegalArgumentException(token.toString());
        }

        FunctionDeclarationLiteral functionLiteral = new FunctionDeclarationLiteral();
        functionLiteral.argumentOrder = argumentOrder;
        functionLiteral.returnValue = readLiteral(scanner);
        return functionLiteral;
    }


    List<CompositeLiteral> readInitializers(Scanner scanner) throws IOException {
        final List<CompositeLiteral> initializers = new ArrayList<CompositeLiteral>();
        while (true) {
            final LiteralElement token1 = scanner.nextTokenBuffered();
            scanner.pushBack(token1);

            if (token1.getType() != LiteralElementTypes.LBRACE) return initializers;

            final CompositeLiteral result;
            final ArrayList children = new ArrayList();
            boolean b = readSequence(scanner, children, true, LiteralElementTypes.LBRACE, LiteralElementTypes.RBRACE);
            if (b) {
                ListLiteral listLiteral = new ListLiteral();    // TODO: use Array
                listLiteral.setElements(children);
                result = listLiteral;
            }
            else {
                MapLiteral mapLiteral = new MapLiteral();       // TODO: use Bindings
                mapLiteral.setElements(children);
                result = mapLiteral;
            }

            initializers.add(result);
        }
    }


    AbstractBindingsLiteral<?> readContexts(final Scanner scanner) throws IOException {
        AbstractBindingsLiteral<?> parent = readContext(scanner);
        if (parent == null) return null;
        while (true) {
            final AbstractBindingsLiteral<?> child = readContext(scanner);
            if (child == null) return parent;
            child.setContext(parent);
            parent = child;
        }
    }


    AbstractBindingsLiteral<?> readContext(
        final Scanner scanner) throws IOException {

        final LiteralElement token = scanner.nextTokenBuffered();
        scanner.pushBack(token);
        if (token.getType() != LiteralElementTypes.LT) return null;

        final ArrayList<Object> children = new ArrayList<Object>();
        final AbstractBindingsLiteral result = readSequence(scanner, children, false, LiteralElementTypes.LT, LiteralElementTypes.GT)
            ? new PrefixListBindingsLiteral()
            : new BindingsContextLiteral();
        result.setElements(children);
        return result;
    }


    CompositeLiteral readCompositeLiteral(final Scanner scanner) throws IOException {
        final ArrayList children = new ArrayList();
        boolean b = readSequence(scanner, children, true, LiteralElementTypes.LBRACK, LiteralElementTypes.RBRACK);
        if (b) {
            ListLiteral<?> listLiteral = new ListLiteral();
            listLiteral.setElements(children);
            return listLiteral;
        }
        else {
            MapLiteral mapLiteral = new MapLiteral();
            mapLiteral.setElements(children);
            return mapLiteral;
        }
    }

    private CollectionLiteral readArrayOrBindingsLiteral(final Scanner scanner) throws IOException {
        return readSequence(scanner);
    }

    private CollectionLiteral readSequence(final Scanner scanner) throws IOException {
        final ArrayList children = new ArrayList();
        boolean b = readSequence(scanner, children, true, LiteralElementTypes.LPAREN, LiteralElementTypes.RPAREN);
        if (b) {
            ArrayLiteral<?> arrayLiteral = new ArrayLiteral();
            arrayLiteral.setElements(children);
            return arrayLiteral;
        }
        else {
            BindingsLiteral bindingsLiteral = new BindingsLiteral();
            bindingsLiteral.setElements(children);
            return bindingsLiteral;
        }
    }


    // TODO: validate element types on return
    boolean readSequence(
        final Scanner scanner,
        final List children,
        final boolean allowEmpty,
        final LiteralElementTypes openingToken, final LiteralElementTypes closingToken) throws IOException {

        if (scanner.nextTokenBuffered().getType() != openingToken)
            throw new IllegalArgumentException();

        boolean commaRequired = false;
        boolean firstElementParsed = false;

        /**
         * 0: expect value or terminator
         * 1: expect ':' or value (list element key of the map entry) or terminator
         * 2: expect value (key of the map entry) or terminator
         * 3: expect ':'
         * 4: expect value (value of the map entry)
         * 5: expect value (list element) or terminator
         */
        int state = 0;
        Evaluable firstElement = null;
        MapEntry<Evaluable> mapEntry = null;
        while (true) {
            LiteralElement token;
            switch (state) {
            case 0: // expect value or terminator (just started parsing)
                token = scanner.nextTokenBuffered();
                if (token.getType() == closingToken) {
                    if (allowEmpty)
                        return true;   // let it be a map?
                    else
                        throw new IllegalArgumentException(token.toString());
                }
                else {
                    scanner.pushBack(token);
                    firstElement = readLiteral(scanner);
                    state = 1;
                    break;
                }
            case 1: // expect ':' or value (list element key of the map entry) or terminator
                token = scanner.nextTokenBuffered();
                if (token.getType() == LiteralElementTypes.COLON) {
                    // map detected
                    mapEntry = new MapEntry<Evaluable>();
                    mapEntry.setKey(firstElement);
                    children.add(mapEntry);
                    state = 4;
                    break;
                }
                else if (token.getType() == LiteralElementTypes.COMMA) {
                    commaRequired = true;
                    token = scanner.nextTokenBuffered();
                }
                // list detected. 'firstElement' was a first item of the list.
                children.add(firstElement);
                if (token.getType() == closingToken) {return true;}
                else {
                    // token is a part of second item of the list
                    scanner.pushBack(token);
                    children.add(readLiteral(scanner));
                    state = 5;
                }
                break;
            case 2:
                token = scanner.nextTokenBuffered();
                if (token.getType() != closingToken) {
                    if (!firstElementParsed) {
                        firstElementParsed = true;
                        if (token.getType() == LiteralElementTypes.COMMA) {
                            token = scanner.nextTokenBuffered();
                            commaRequired = true;
                        }
                    }
                    else if (commaRequired) {
                        if (token.getType() == LiteralElementTypes.COMMA) {
                            token = scanner.nextTokenBuffered(); // consume ','
                        }
                        else {
                            throw new IllegalArgumentException("Expected ','");
                        }
                    }

                    // token is a part of firstElement (key of the map entry)
                    scanner.pushBack(token);
                    mapEntry = new MapEntry<Evaluable>();
                    mapEntry.setKey(readLiteral(scanner));
                    children.add(mapEntry);
                    state = 3;
                    break;
                } else {return false;}
            case 3:
                token = scanner.nextTokenBuffered();
                if (token.getType() != LiteralElementTypes.COLON) throw new IllegalStateException("Expected ':'");
                state = 4;
                break;
            case 4: // expect value (returnValue of the map entry)
                // token is a part of literal
                mapEntry.setValue(readLiteral(scanner));
                state = 2;
                break;
            case 5: // expect value (list element) or terminator
                token = scanner.nextTokenBuffered();
                if (token.getType() == closingToken) {return true;}
                if (commaRequired) {
                    if (token.getType() == LiteralElementTypes.COMMA) {
                        token = scanner.nextTokenBuffered();
                    }
                    else {
                        throw new IllegalStateException("Expected ','");
                    }
                }


                scanner.pushBack(token);
                children.add(readLiteral(scanner));
                state = 5;
                break;
            }
        }
    }


    ReferenceLiteral readReference(final Scanner scanner) throws IOException {
        final StringBuilder referenceBuilder = new StringBuilder();

        while (true) {
            final LiteralElement token = scanner.nextTokenBuffered();
            if (token.getType() != LiteralElementTypes.IDENTIFIER) throw new IllegalArgumentException(token.toString());
            referenceBuilder.append(token.getValue());

            final LiteralElement nextToken = scanner.nextTokenBuffered();
            if (nextToken.getType() == LiteralElementTypes.DOT) {
                referenceBuilder.append('.');
            }
            else {
                scanner.pushBack(nextToken);
                break;
            }
        }
        final ReferenceLiteral referenceLiteral = new ReferenceLiteral();
        referenceLiteral.setValue(referenceBuilder.toString());
        return referenceLiteral;
    }
}
