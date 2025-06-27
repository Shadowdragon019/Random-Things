package lol.roxxane.random_things.util;

public class StringUtil {
	public static String format_name(String input) {
		StringBuilder output = new StringBuilder();
		Character last_char = null;

		for (var _char : input.toCharArray()) {
			if (_char == '_')
				_char = ' ';

			if (last_char == null || Character.isWhitespace(last_char))
				output.append(Character.toUpperCase(_char));
			else output.append(_char);

			last_char = _char;
		}

		return output.toString();
	}
}
