package lol.roxxane.random_things.util;

import java.util.List;

public class ListUtil {
	public static <T> T next_element(List<T> list, T element) {
		var i = list.indexOf(element) + 1;
		if (i >= list.size())
			i = 0;
		return list.get(i);
	}
}
