package org.aprestos.labs.challenges.nexmo.three;

public class CB {
	private final String name;

	public static CB create(String name) {
		return new CB(name);
	}

	private CB(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void f() {

		// System.out.println(String.format("%d %s", System.nanoTime() / 1000000,
		// name));
		System.out.print(name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CB other = (CB) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
