package ai.util;

/**
 * Created by KateCarey on 8/25/16.
 */
public class Pair<A, B> {

    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A> A getFirstOfPair(Pair<A, ?> pair) {
        return pair.getFirst();
    }

    public A getFirst() {
        return first;
    }

    public static <B> B getSecondOfPair(Pair<?, B> pair) {
        return pair.getSecond();
    }

    public B getSecond() {
        return second;
    }

}
