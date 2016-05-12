package snippetlab.snippets.gui.mvc.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
	protected String name = null;

	protected String director = null;

	protected String genre = null;

	protected List<String> actors = new ArrayList<String>();

	public Movie() {
	}

	public Movie(String name, String director, String genre, List<String> actors) {
		this.name = name;
		this.director = director;
		this.genre = genre;
		this.actors = actors;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @param director
	 *            the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void addActor(String actor) {
		actors.add(actor);
	}

	public void removeActor(String actor) {
		actors.remove(actor);
	}

	public void removeActors() {
		actors.clear();
	}

	/**
	 * @return the actors
	 */
	public String[] getActors() {
		return (String[]) actors.toArray();
	}

	/**
	 * @param actors
	 *            the actors to set
	 */
	public void setActors(String[] actors) {
		this.actors = new ArrayList<String>();
		for (String s : actors) {
			this.actors.add(s);
		}
		// this.actors = Arrays.asList(actors);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actors == null) ? 0 : actors.hashCode());
		result = prime * result
				+ ((director == null) ? 0 : director.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (actors == null) {
			if (other.actors != null)
				return false;
		} else if (!actors.equals(other.actors))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
