package type;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CheckedValue<A> {

    private Maybe<A> value;
    private List<String> path;

    private CheckedValue(Maybe<A> value, List<String> path) {
        this.value = value;
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    public static <A> CheckedValue<A> of(Object value, List<String> path) {
        return new CheckedValue(Maybe.just(value), path);
    }

    //todo: not null check
    public static <A> CheckedValue<A> pure(Object value) {
        return of(value, new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    public static <A> CheckedValue<A> fail(String errMsg) {
        return new CheckedValue(Maybe.NOTHING, List.of(errMsg));
    }

    public CheckedValue<A> bind(Function<Object, CheckedValue<A>> checkF) {
        if (this.value == Maybe.NOTHING) {
            return this;
        } else {
            CheckedValue<A> r = checkF.apply(Maybe.fromJust(value));
            this.path.addAll(r.path);
            r.path = this.path;
            return r;
        }
    }


    public CheckedValue<A> addPath(String errMsg) {
        this.getPath().add(errMsg);
        return this;
    }

    public static boolean isFail(CheckedValue checkedValue) {
        return checkedValue.getValue() == Maybe.NOTHING;
    }

    public A getJustValue() {
        if (isFail(this)) {
            throw new RuntimeException("checkedValue is fail");
        }
        return Maybe.fromJust(value);
    }

    public Maybe<A> getValue() {
        return value;
    }

    public List<String> getPath() {
        return path;
    }
}
