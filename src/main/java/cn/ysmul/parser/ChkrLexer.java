package cn.ysmul.parser;

import static cn.ysmul.parser.ChkrLexer.TokenEnum.*;

public class ChkrLexer extends Lexer {

    public enum TokenEnum {
        // EOF
        EOF_TYPE,

        // punctuation
        COMMA("a"), LBRACK("["), RBRACK("]"),
        COLON(":"), LBRACE("{"), RBRACE("}"),
        LPARAEN("("), RPARAEN(")"),

        // value
        STRING, NUMBER, TRUE, FALSE,

        // checker
        NUM_C("Num"), STR_C("Str"), BOOL_C("Bool"), MIXIN_C("Mixin"), OR_C("Or"), OR_VAL_C("OrVal");

        private String text;

        TokenEnum() {
        }

        TokenEnum(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }


    public String getTokenName(TokenEnum tokenEnum) {
        return tokenEnum.name();
    }

    public ChkrLexer(String input) {
        super(input);
    }

    private boolean isDIGIT() {
        return c >= '0' && c <= '9';
    }

    private boolean isLETTER() {
        return c >= 'a' && c <= 'z'
            || c >= 'A' && c <= 'Z'
            || c >= '0' && c <= '9'
            || "=+-_)(*&^%$#@!~`';.:,<>/?] [{}\\|".contains(String.valueOf(c));
    }

    public Token nextToken() {
        while (c != EOF) {
            switch (c) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    WS();
                    continue;
                case ',':
                    consume();
                    return new Token(COMMA, ",");
                case '[':
                    consume();
                    return new Token(LBRACK, "[");
                case ']':
                    consume();
                    return new Token(RBRACK, "]");
                case ':':
                    consume();
                    return new Token(COLON, ":");
                case '{':
                    consume();
                    return new Token(LBRACE, "{");
                case '}':
                    consume();
                    return new Token(RBRACE, "}");
                case '"':
                    return STRING();
                case 't':
                    return TRUE();
                case 'f':
                    return FALSE();
                case '-':
                    return NUMBER();
                case 'N':
                    return CHKR(NUM_C);
                case 'S':
                    return CHKR(STR_C);
                case 'O':
                    if (input.charAt(p + 2) == 'V') return CHKR(OR_VAL_C);
                    return CHKR(OR_C);
                case 'M':
                    return CHKR(MIXIN_C);
                case 'B':
                    return CHKR(BOOL_C);
                case '(':
                    consume();
                    return new Token(LPARAEN, "(");
                case ')':
                    consume();
                    return new Token(RPARAEN, ")");
                default:
                    if (isDIGIT()) return NUMBER();
                    throw new Error("invalid character: " + c);
            }
        }
        return new Token(EOF_TYPE, "<EOF>");
    }


    private Token NUMBER() {
        int flag = 1;
        if (c == '-') {
            consume();
            flag = -1;
        }
        double n = 0.0;
        do {
            n *= 10;
            n += c - '0';
            DIGIT();
        } while (isDIGIT());

        double m = 0;
        if (c == '.') {
            consume();
            int k = 1;
            while (isDIGIT()) {
                m *= 10;
                m += c - '0';
                k *= 10;
                DIGIT();
            }
            m = m / k;
        }
        n += m;
        n *= flag;
        return new Token(NUMBER, Double.toString(n));
    }

    private Token STRING() {
        StringBuilder buf = new StringBuilder();
        consume();
        buf.append("\"");
        do {
            buf.append(c);
            LETTER();
        } while (isLETTER());
        consume();
        buf.append("\"");
        return new Token(STRING, buf.toString());
    }

    private Token TRUE() {
        String expectTRUE = input.substring(p, p + 4);
        if (expectTRUE.equals("true")) {
            consumeN(4);
            return new Token(TRUE, "true");
        } else {
            throw new Error("expecting true, found " + expectTRUE);
        }
    }

    private Token FALSE() {
        String expectFALSE = input.substring(p, p + 5);
        if (expectFALSE.equals("false")) {
            consumeN(5);
            return new Token(FALSE, "false");
        } else {
            throw new Error("expecting false ,found " + expectFALSE);
        }
    }

    private void DIGIT() {
        if (isDIGIT()) consume();
        else throw new Error("expecting DIGIT; found " + c);
    }

    private void LETTER() {
        if (isLETTER()) consume();
        else throw new Error("expecting LETTER; found " + c);
    }

    private void WS() {
        while (c == ' ' || c == '\t' || c == '\n' || c == '\r') consume();
    }


    private Token CHKR(TokenEnum type) {
        int tokenLen = type.getText().length();
        String expectTRUE = input.substring(p, p + tokenLen);
        if (expectTRUE.equals(type.getText())) {
            consumeN(tokenLen);
            return new Token(type, type.name());
        } else {
            throw new Error("expecting true, found " + expectTRUE);
        }
    }


}
