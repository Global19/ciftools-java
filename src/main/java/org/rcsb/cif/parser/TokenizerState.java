package org.rcsb.cif.parser;

import org.rcsb.cif.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TokenizerState {
    private String data;

    private int position;
    private int length;
    private boolean isEscaped;

    private int lineNumber;
    private CifTokenType tokenType;
    private int tokenStart;
    private int tokenEnd;

    public TokenizerState(String data) {
        this.data = data;

        this.position = 0;
        this.length = data.length();
        this.tokenStart = 0;
        this.tokenEnd = 0;
        this.tokenType = CifTokenType.END;
        this.lineNumber = 1;
        this.isEscaped = false;
    }

    public CifTokenType getTokenType() {
        return tokenType;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getTokenStart() {
        return tokenStart;
    }

    public int getTokenEnd() {
        return tokenEnd;
    }

    public String getData() {
        return data;
    }

    /**
     * Eat everything until a whitespace/newline occurs.
     */
    private void eatValue() {
        while (position < length) {
            switch (data.charAt(position)) {
                case '\t': case '\n': case '\r': case ' ':
                    tokenEnd = position;
                    return;
                default:
                    position++;
                    break;
            }
        }
        tokenEnd = position;
    }

    /**
     * Eats an escaped value. Handles the "degenerate" cases as well.
     * "Degenerate" cases:
     *  - 'xx'x' => xx'x
     *  - 'xxxNEWLINE => 'xxx
     * @param esc escaping char
     */
    private void eatEscaped(int esc) {
        position++;
        while (position < length) {
            int c = data.charAt(position);

            if (c == esc) {
                // check for end of file
                if (position + 1 >= length) {
                    tokenStart++;
                    tokenEnd = position;
                    isEscaped = true;
                    position++;
                    return;

                }

                int next = data.charAt(position + 1);
                if (next == '\t' || next == '\n' || next == '\r' || next == ' ') {
                    // get rid of the quotes
                    tokenStart++;
                    tokenEnd = position;
                    isEscaped = true;
                    position++;
                    return;
                }

                position++;
            } else {
                // handle 'xxxNEWLINE => 'xxx
                if (c == '\r' || c == '\n') {
                    tokenEnd = position;
                    return;
                }
                position++;
            }
        }

        tokenEnd = position;
    }

    /**
     * Eats a multiline token of the form NL;....NL;
     */
    private void eatMultiline() {
        int prev = ';';
        int pos = position + 1;

        while (pos < length) {
            int c = data.charAt(pos);
            if (c == ';' && (prev == '\n' || prev == '\r')) {
                position = pos + 1;
                // get rid of the ;
                tokenStart++;

                // remove trailing newlines
                pos--;
                c = data.charAt(pos);
                while (c == '\n' || c == '\r') {
                    pos--;
                    c = data.charAt(pos);
                }
                tokenEnd = pos + 1;

                isEscaped = true;
                return;
            } else {
                // handle line numbers
                if (c == '\r') {
                    lineNumber++;
                } else if (c == '\n' && prev != '\r') {
                    lineNumber++;
                }

                prev = c;
                pos++;
            }
        }

        position = pos;
    }

    /**
     * Skips until \n or \r occurs -- therefore the newlines get handled by the "skipWhitespace" function.
     */
    private void skipCommentLine() {
        while (position < length) {
            int c = data.charAt(position);
            if (c == '\r' || c == '\n') {
                return;
            }
            position++;
        }
    }

    /**
     * Skips all whitespaces - space, tab, \r, \n. Handles incrementing the line number.
     * @return the last character read
     */
    private int skipWhitespace() {
        int prev = '\n';
        while (position < length) {
            int c = data.charAt(position);
            switch (c) {
                case '\t': case ' ':
                    prev = c;
                    position++;
                    break;
                case '\n':
                    // handle \r\n
                    if (prev != '\r') {
                        lineNumber++;
                    }
                    prev = c;
                    position++;
                    break;
                case '\r':
                    prev = c;
                    position++;
                    lineNumber++;
                    break;
                default:
                    return prev;
            }
        }
        return prev;
    }

    private boolean isData() {
        // here we already assume the 5th char is _ and that the length >= 5
        return "data".equalsIgnoreCase(data.substring(tokenStart, tokenStart + 4));
    }

    private boolean isSave() {
        // here we already assume the 5th char is _ and that the length >= 5
        return "save".equalsIgnoreCase(data.substring(tokenStart, tokenStart + 4));
    }

    private boolean isLoop() {
        // here we already assume the 5th char is _ and that the length >= 5
        return "loop".equalsIgnoreCase(data.substring(tokenStart, tokenStart + 4));
    }

    /**
     * Checks if the current token shares the namespace with string at <start,end).
     */
    private boolean isNamespace(int start, int end) {
        int i;
        int nsLen = end - start;
        int offset = tokenStart - start;
        int tokenLen = tokenEnd - tokenStart;

        if (tokenLen < nsLen) {
            return false;
        }

        for (i = start; i < end; i++) {
            if (data.charAt(i) != data.charAt(i + offset)) {
                return false;
            }
        }

        if (nsLen == tokenLen) {
            return true;
        }
        return data.charAt(i + offset) == '.';
    }

    /**
     * Returns the index of '.' in the current token. If no '.' is present, returns currentTokenEnd.
     */
    private int getNamespaceEnd() {
        return data.substring(tokenStart, tokenEnd).indexOf(".") + tokenStart;
    }

    /**
     * Get the namespace string. endIndex is obtained by the getNamespaceEnd() function.
     */
    private String getNamespace(int endIndex) {
        return data.substring(tokenStart, endIndex);
    }

    private String getTokenString() {
        return data.substring(tokenStart, tokenEnd);
    }

    /**
     * Move to the next token.
     */
    private void moveNextInternal() {
        int prev = skipWhitespace();

        // end of file reached
        if (position >= length) {
            tokenType = CifTokenType.END;
            return;
        }

        tokenStart = position;
        tokenEnd = position;
        isEscaped = false;
        int c = data.charAt(position);
        switch (c) {
            case '#':
                skipCommentLine();
                tokenType = CifTokenType.COMMENT;
                break;
            case '"': case '\'':
                eatEscaped(c);
                tokenType = CifTokenType.VALUE;
                break;
            case ';': // possible multiline value
                // multiline value must start at the beginning of the line
                if (prev == '\n' || prev == '\r') {
                    eatMultiline();
                } else {
                    eatValue();
                }
                tokenType = CifTokenType.VALUE;
                break;
            default:
                eatValue();
                // escaped is always Value
                if (isEscaped) {
                    tokenType = CifTokenType.VALUE;
                    // _ always means column name
                } else if (data.charAt(tokenStart) == '_') {
                    tokenType = CifTokenType.COLUMN_NAME;
                    // 5th char needs to be _ for data_ or loop_
                } else if (tokenEnd - tokenStart >= 5 && data.charAt(tokenStart + 4) == '_') {
                    if (isData()) {
                        tokenType = CifTokenType.DATA;
                    } else if (isSave()) {
                        tokenType = CifTokenType.SAVE;
                    } else if (isLoop()) {
                        tokenType = CifTokenType.LOOP;
                    } else {
                        tokenType = CifTokenType.VALUE;
                    }
                    // all other tests failed, we are at Value token.
                } else {
                    tokenType = CifTokenType.VALUE;
                }
        }
    }

    /**
     * Move to the next non-comment token.
     */
    public void moveNext() {
        moveNextInternal();
        while (tokenType == CifTokenType.COMMENT) {
            moveNextInternal();
        }
    }

    /**
     * Reads a category containing a single row.
     * @param ctx
     * @throws ParsingException
     */
    public void handleSingle(FrameContext ctx) throws ParsingException {
        final int nsStart = tokenStart;
        final int nsEnd = getNamespaceEnd();
        final String name = getNamespace(nsEnd);
        final Map<String, CifField> fields = new LinkedHashMap<>();

        while (tokenType == CifTokenType.COLUMN_NAME && isNamespace(nsStart, nsEnd)) {
            final String fieldName = getTokenString().substring(name.length() + 1);
            moveNext();
            if (tokenType != CifTokenType.VALUE) {
                throw new ParsingException("Expected value.", lineNumber);
            }

            fields.put(fieldName, new TextCifField(data, tokenStart, tokenEnd));
            moveNext();
        }

        final String catName = name.substring(1);
        ctx.getCategories().put(catName, new TextCifCategory(catName, fields));
    }

    /**
     * Reads a loop.
     * @param ctx
     */
    public void handleLoop(FrameContext ctx) {
        final int loopLine = lineNumber;

        moveNext();
        final String name = getNamespace(getNamespaceEnd());
        final List<String> fieldNames = new ArrayList<>();
        final List<List<Integer>> start = new ArrayList<>();
        final List<List<Integer>> end = new ArrayList<>();
        int tokenCount = 0;

        while (tokenType == CifTokenType.COLUMN_NAME) {
            fieldNames.add(getTokenString().substring(name.length() + 1));
            moveNext();
            start.add(new ArrayList<>());
            end.add(new ArrayList<>());
        }

        while (tokenType == CifTokenType.VALUE) {
            int i = tokenCount % fieldNames.size();
            start.get(i).add(tokenStart);
            end.get(i).add(tokenEnd);
            moveNext();
            tokenCount++;
        }

        if (start.size() % fieldNames.size() != 0) {
            throw new Error("The number of values for loop starting at line " + loopLine + " is not a multiple of the number of columns.");
        }

        Map<String, CifField> fields = new LinkedHashMap<>();
        for (int i = 0; i < start.size(); i++) {
            fields.put(fieldNames.get(i), new TextCifField(data,
                    start.get(i).stream().mapToInt(j -> j).toArray(),
                    end.get(i).stream().mapToInt(j -> j).toArray()));
        }

        String catName = name.substring(1);
        ctx.getCategories().put(catName, new TextCifCategory(catName, fields));
    }
}