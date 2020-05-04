package org.challenges.gt.gcodechal.model;

public enum Gender {
	Female, Male;

	static Gender fromString(String gender) {
		Gender result = null;
		if ("male".equalsIgnoreCase(gender))
			result = Male;
		else if ("female".equalsIgnoreCase(gender))
			result = Female;
		return result;
	}
}
