package function;

@FunctionalInterface
public interface F<A, B> {
    B apply(A a);

    default <C> F<C, B> compose(F<C, A> g) {
        return c -> this.apply(g.apply(c));
    }
}
