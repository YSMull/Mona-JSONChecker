package cn.ysmul.parser;


public class Token {
    public ChkrLexer.TokenEnum type;
    public String text;

    public Token(ChkrLexer.TokenEnum type, String text) {
        this.type = type;
        this.text = text;
//        System.out.print(text);
    }

    public String toString() {
        String tname = type.name();
        return "<'" + text + "'," + tname + ">";
    }
}
