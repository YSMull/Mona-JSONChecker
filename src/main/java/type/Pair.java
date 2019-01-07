package type;

import function.F;

public final class Pair<A, B> {
    private final A left;
    private final B right;

    private static <A, B> Pair<A, B> of(A left, B right) {
        return new Pair<>(left, right);
    }

    @Deprecated
    public static <A, B> Pair<A, B> make(A left, B right) {
        return Pair.of(left, right);
    }

    private Pair(A left, B right) {
        super();
        this.left = left;
        this.right = right;

    }

    public A left() {
        return this.left;
    }

    public B right() {
        return this.right;
    }

    public Pair<B, A> swap() {
        return new Pair<>(this.right, this.left);
    }

    public <A1> Pair<A1, B> mapLeft(F<A, A1> f) {
        return new Pair<>(f.apply(this.left), this.right);
    }

    public <B1> Pair<A, B1> mapRight(F<B, B1> f) {
        return new Pair<>(this.left, f.apply(this.right));
    }
}

