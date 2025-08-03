package lol.roxxane.random_things.util;

import org.jetbrains.annotations.NotNull;

public record Pair<A, B>(A a, B b) {
	@Override
	public @NotNull String toString() {
		return "(" + StringUtils.stringify(a) + ", " + StringUtils.stringify(b) + ")";
	}
}
