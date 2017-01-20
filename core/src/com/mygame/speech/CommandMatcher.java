package com.mygame.speech;

import java.util.HashMap;

public class CommandMatcher {

	HashMap<String, Integer> library;

	public CommandMatcher() {

		library = new HashMap<String, Integer>();
		library.put("one", 1);
		library.put("two", 2);
		library.put("three", 3);
		library.put("four", 4);
		library.put("five", 5);
		library.put("six", 6);
		library.put("seven", 7);
		library.put("eight", 8);
		library.put("nine", 9);
		library.put("ten", 10);
		library.put("eleven", 11);
		library.put("twelve", 12);
		library.put("thirteen", 13);
		library.put("fourteen", 14);
		library.put("fifteen", 15);
		library.put("sixteen", 16);
		library.put("seventeen", 17);
		library.put("eighteen", 18);
		library.put("nineteen", 19);
		library.put("twenty", 20);

	}

	public int stringToInt(String str) {

		// for (int i = 0; i < library.size(); i++) {
		if (library.containsKey(str)) {
			return library.get(str).intValue();
			// }
		} else
			return 0;
	}

}
