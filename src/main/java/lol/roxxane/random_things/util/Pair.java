package lol.roxxane.random_things.util;

import org.jetbrains.annotations.NotNull;

public class Pair<A, B> {
	public final A a;
	public final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public static <A, B> Pair<A, B> of(A a, B b) {
		return new Pair<>(a, b);
	}

	@Override
	public @NotNull String toString() {
		return "(" + StringUtils.stringify(a) + ", " + StringUtils.stringify(b) + ")";
	}
}
