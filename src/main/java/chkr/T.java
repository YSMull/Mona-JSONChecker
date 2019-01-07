package chkr;

import type.Maybe;

public class T {

    public static Chkr Any = Chkr.match((value, errMsgs) -> {
        if (value == null) {
            errMsgs.add("is null");
            return Maybe.NOTHING;
        } else {
            return Maybe.just(value);
        }
    }, Chkr.id);

    public static Chkr Any1 = Chkr.judge(value -> value == null, Chkr.id, "is null");

    public static Chkr Num = Chkr.match((value, errMsgs) -> {
        try {
            return Maybe.just(Long.parseLong(value.toString()));
        } catch (Exception ignore) {
            errMsgs.add("{{ " + value.toString() + " }} is not Num");
            return Maybe.NOTHING;
        }
    }, Any);

    public static Chkr Str = Chkr.match((value, errMsgs) -> {
        if (value instanceof String) {
            return Maybe.just(value);
        } else {
            errMsgs.add("{{ " + value.toString() + " }} is not a Str");
            return Maybe.NOTHING;
        }
    }, Any);

    public static Chkr Bool = Chkr.match((value, errMsgs) -> {
        String t = value.toString().toLowerCase();
        if (t.equals("false") || t.equals("true")) {
            return Maybe.just(Boolean.parseBoolean(value.toString()));
        } else {
            errMsgs.add("{{ " + value.toString() + " }} is not Bool");
            return Maybe.NOTHING;
        }
    }, Any);
}
