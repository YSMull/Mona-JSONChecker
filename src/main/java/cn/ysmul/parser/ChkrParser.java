package cn.ysmul.parser;

import cn.ysmul.chkr.core.Chkr;

import java.util.ArrayList;
import java.util.List;

import static cn.ysmul.chkr.BasicType.*;
import static cn.ysmul.chkr.Control.*;
import static cn.ysmul.parser.ChkrLexer.TokenEnum.*;

public class ChkrParser extends Parser {

    public ChkrParser(Lexer input) {
        super(input);
    }

    public Chkr value() {

        if (lookahead.type == LBRACE) {
            return object();
        }

        if (lookahead.type == NUM_C) {
            System.out.print("t.Num");
            match(NUM_C);
            return Num;
        }

        if (lookahead.type == STR_C) {
            System.out.print("t.Str");
            match(STR_C);
            return Str;
        }

        if (lookahead.type == BOOL_C) {
            System.out.print("t.Bool");
            match(BOOL_C);
            return Bool;
        }

        if (lookahead.type == MIXIN_C || lookahead.type == OR_C) {
            ChkrLexer.TokenEnum t = lookahead.type;
            switch (lookahead.type) {
                case OR_C:
                    System.out.print("c.Or");
                    break;
                case MIXIN_C:
                    System.out.print("c.Extend");
                    break;
            }
            match(lookahead.type);
            return listOfChkr(t);
        }

        if (lookahead.type == OR_VAL_C) {
            match(OR_VAL_C);
            return orVal();
        }

        if (lookahead.type == LBRACK) {
            return arr();
        }

        throw new Error("???");
    }

    private Chkr object() {
        System.out.print("c.Obj({\n");
        match(LBRACE);
        boolean done = false;
        List<Object> params = new ArrayList<>();
        do {
            if (lookahead.type == STRING) {
                String key = lookahead.text.replaceAll("(^\"|\"$)", "");
                params.add(key);
                match(STRING);
                System.out.print("'" + key + "'");
                match(COLON);
                System.out.print(": ");
                params.add(value());

            }
            if (lookahead.type == COMMA) {
                System.out.print(",\n");
                consume();
            } else {
                System.out.print("\n");
                done = true;
            }
        } while (!done);
        match(RBRACE);
        System.out.print("})");
        return Obj(params.toArray());
    }

    private Chkr listOfChkr(ChkrLexer.TokenEnum type) {
        System.out.println("(");
        match(LPARAEN);
        boolean done = false;
        List<Chkr> params = new ArrayList<>();
        do {
            params.add(value());
            if (lookahead.type == COMMA) {
                System.out.print(",\n");
                consume();
            } else {
                done = true;
            }
        } while (!done);
        System.out.print("\n)");
        match(RPARAEN);
        switch (type) {
            case MIXIN_C:
                return Mixin(params.toArray(new Chkr[0]));
            case OR_C:
                return Or(params.toArray(new Chkr[0]));
            default:
                throw new Error("invalid listOfChkr type");
        }

    }

    private Chkr arr() {
        match(LBRACK);
        System.out.print("c.Arr(");
        Chkr value = value();
        match(RBRACK);
        System.out.print(")");
        return Arr(value);
    }

    private Chkr orVal() {
        match(LPARAEN);
        System.out.print("c.OrVal(");
        boolean done = false;
        List<Object> params = new ArrayList<>();
        do {

            switch (lookahead.type) {
                case STRING:
                    lookahead.text = lookahead.text.replaceAll("(^\"|\"$)", "'");
                    params.add(lookahead.text);
                    break;
                case NUMBER:
                    params.add(Double.valueOf(lookahead.text));
                    break;
                case TRUE:
                case FALSE:
                    params.add(Boolean.valueOf(lookahead.text));
                    break;
            }
            System.out.print(lookahead.text);
            match(lookahead.type);

            if (lookahead.type == COMMA) {
                consume();
                System.out.print(",");
            } else {
                done = true;
            }
        } while (!done);
        match(RPARAEN);
        System.out.print(")");
        return OrVal(params.toArray());
    }
}