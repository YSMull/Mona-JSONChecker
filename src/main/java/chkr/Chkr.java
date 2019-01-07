package chkr;

import function.F2;
import type.Maybe;

import java.util.List;
import java.util.function.Predicate;

public class Chkr {

    private F2<Object, List<String>, Maybe> checkF;

    private Chkr(F2<Object, List<String>, Maybe> checkF) {
        this.checkF = checkF;
    }

    // Identity checker
    public static final Chkr id = new Chkr((value, errMsgs) -> Maybe.just(value));

    public Maybe check(Object value, List<String> errMsgs) {
        return checkF.apply(value, errMsgs);
    }

    /**
     * compose a checkF and an exist Chkr, return a new Chkr
     */
    @SuppressWarnings("unchecked")
    public static Chkr match(F2<Object, List<String>, Maybe> checkF, Chkr parentChkr) {
        return new Chkr((value, errMsgs) -> parentChkr.check(value, errMsgs).at(()-> checkF.apply(value,errMsgs)));
    }

    // A little util for the real chkr generator --- match
    public static Chkr judge(Predicate<Object> p, Chkr parentChkr, String errMsg) {
        return match((value, errMsgs) -> {
             if (p.test(value)) {
                 return Maybe.just(value);
             } else {
                 errMsgs.add("{{ " + (value == null ? "null" : value.toString()) + " }} " + errMsg);
                 return Maybe.NOTHING;
             }
        }, parentChkr);
    }
}
