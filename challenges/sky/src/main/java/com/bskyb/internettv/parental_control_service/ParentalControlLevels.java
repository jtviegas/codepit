package com.bskyb.internettv.parental_control_service;

public enum ParentalControlLevels {

	U("U", 0), PG("PG", 1), TWELVE("12", 2), FIFTEEN("15", 3), EIGHTEEN("18", 4);

	private int level;
	private String name;

	private ParentalControlLevels(String name, int level) {
		this.name = name;
		this.level = level;
	}

	public String asString() {
		return this.name;
	}

	public int asLevel() {
		return this.level;
	}

	public static ParentalControlLevels fromString(String s) {
		ParentalControlLevels r = null;
		for (ParentalControlLevels e : ParentalControlLevels.values()) {
			if (e.asString().equals(s)) {
				r = e;
				break;
			}
		}
		return r;
	}

}
