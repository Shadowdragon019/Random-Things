package lol.roxxane.random_things.util;

@FunctionalInterface
public interface QuadConsumer<A, B, C, D> {
	void accept(A a, B b, C c, D d);
}
