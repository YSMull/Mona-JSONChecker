package chkr;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static type.CheckedValue.fail;
import static type.CheckedValue.pure;

public class T {

    public static Chkr Any = Chkr.judge(Objects::nonNull, Chkr.id, "is null");

    public static Chkr Num = Chkr.judge(v -> {
        try {
            Long.parseLong(v.toString());
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }, Any, "is not Num");

    public static Chkr StrictNum = Chkr.judge(v -> v instanceof Number, Any, "is not a StrictNum");

    public static Chkr Str = Chkr.judge(v -> v instanceof String, Any, "is not Str");

    public static Chkr Bool = Chkr.judge(v -> {
        String t = v.toString().toLowerCase();
        return t.equals("false") || t.equals("true");
    }, Any, "is not Bool");

    public static Chkr StrictBool = Chkr.judge(v -> v instanceof Boolean, Any, "is not a StrictBool");

    public static Chkr OrVal(Object... args) {
        return Chkr.compose(value -> {
            boolean exist = Arrays.asList(args).contains(value);
            String argStr = Arrays.stream(args).map(JSON::toJSONString).collect(Collectors.joining(","));
            if (!exist) return fail("{{ " + JSON.toJSONString(value) + " }} dose not match OrVal(" + argStr + ")");
            return pure(value);
        }, Chkr.id);
    }

}
