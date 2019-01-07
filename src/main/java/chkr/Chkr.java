package chkr;

import com.alibaba.fastjson.JSON;
import type.CheckedValue;

import java.util.function.Function;
import java.util.function.Predicate;

import static type.CheckedValue.fail;
import static type.CheckedValue.pure;

public class Chkr {

    private Function<Object, CheckedValue> checkFn;

    private Chkr(Function<Object, CheckedValue> checkFn) {
        this.checkFn = checkFn;
    }

    // Identity checker
    public static final Chkr id = new Chkr(CheckedValue::pure);

    public CheckedValue check(Object value) {
        return checkFn.apply(value);
    }

    /**
     * compose a checkFn and an exist Chkr, return a new Chkr
     */
    public static Chkr compose(Function<Object, CheckedValue> checkFn, Chkr parentChkr) {
        return new Chkr(value -> parentChkr.check(value).bind(checkFn));
    }

    // A little util for the real chkr generator --- compose
    public static Chkr judge(Predicate<Object> p, Chkr parentChkr, String errMsg) {
        return compose((value) -> {
            if (p.test(value)) {
                return pure(value);
            } else {
                return fail("{{ " + (value == null ? "null" : JSON.toJSONString(value)) + " }} " + errMsg);
            }
        }, parentChkr);
    }
}
