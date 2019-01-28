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
            match(NUM_C);
            return Num;
        }

        if (lookahead.type == STR_C) {
            match(STR_C);
            return Str;
        }

        if (lookahead.type == BOOL_C) {
            match(BOOL_C);
            return Bool;
        }

        if (lookahead.type == MIXIN_C || lookahead.type == OR_C) {
            ChkrLexer.TokenEnum t = lookahead.type;
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
        match(LBRACE);
        boolean done = false;
        List<Object> params = new ArrayList<>();
        do {
            if (lookahead.type == STRING) {
                String key = lookahead.text;
                params.add(key.replaceAll("(^\"|\"$)", ""));
                match(STRING);
                match(COLON);
                params.add(value());
            }
            if (lookahead.type == COMMA) {
                consume();
            } else {
                done = true;
            }
        } while (!done);
        match(RBRACE);
        return Obj(params.toArray());
    }

    private Chkr listOfChkr(ChkrLexer.TokenEnum type) {
        match(LPARAEN);
        boolean done = false;
        List<Chkr> params = new ArrayList<>();
        do {
            params.add(value());
            if (lookahead.type == COMMA) {
                consume();
            } else {
                done = true;
            }
        } while (!done);
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
        Chkr value = value();
        match(RBRACK);
        return Arr(value);
    }

    private Chkr orVal() {
        match(LPARAEN);
        boolean done = false;
        List<Object> params = new ArrayList<>();
        do {

            switch (lookahead.type) {
                case STRING:
                    params.add(lookahead.text.replaceAll("(^\"|\"$)", ""));
                    break;
                case NUMBER:
                    params.add(Double.valueOf(lookahead.text));
                    break;
                case TRUE:
                case FALSE:
                    params.add(Boolean.valueOf(lookahead.text));
                    break;
            }
            match(lookahead.type);

            if (lookahead.type == COMMA) {
                consume();
            } else {
                done = true;
            }
        } while (!done);
        match(RPARAEN);
        return OrVal(params.toArray());
    }
}