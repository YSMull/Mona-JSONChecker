package cn.ysmul.type;

import java.util.function.Function;
import java.util.function.Supplier;

public class Maybe<A> {

    @SuppressWarnings("unchecked")
    public final static Maybe NOTHING = new Maybe(null);

    private final A value;

    private Maybe(A value) {
        this.value = value;
    }

    public static <A> Maybe just(A value) {
        return new Maybe<>(value);
    }

    public static <A> A fromJust(Maybe<A> m) {
        if (m == NOTHING) {
            throw new RuntimeException("fromJust got Nothing");
        } else {
            return m.value;
        }
    }

    @SuppressWarnings("unchecked")
    public <B> Maybe<B> bind(Function<A, Maybe<B>> f) {
        if (this == NOTHING) {
            return NOTHING;
        } else {
            return f.apply(this.value);
        }
    }

    @SuppressWarnings("unchecked")
    public Maybe<A> at(Supplier<Maybe<A>> f) {
        if (this == NOTHING) return (Maybe<A>) NOTHING;
        return f.get();
    }

}
