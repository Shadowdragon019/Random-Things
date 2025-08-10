package lol.roxxane.random_things.util;

import static java.lang.Integer.signum;
import static lol.roxxane.random_things.util.StringUtils.stringify;

public class ComparablePair<A extends Comparable<A>, B extends Comparable<B>> implements
	Comparable<ComparablePair<A, B>>
{
	public final A a;
	public final B b;
	public ComparablePair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	public static <A extends Comparable<A>, B extends Comparable<B>> ComparablePair<A, B> of(A a, B b) {
		return new ComparablePair<>(a, b);
	}
	@Override
	public int compareTo(ComparablePair<A, B> other) {
		return signum(a.compareTo(other.a) + b.compareTo(other.b));
	}
	@Override
	public String toString() {
		return "(" + stringify(a) + ", " + stringify(b) + ")";
	}
}
