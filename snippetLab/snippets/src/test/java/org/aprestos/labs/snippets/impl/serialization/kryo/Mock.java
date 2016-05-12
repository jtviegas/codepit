package org.aprestos.labs.snippets.impl.serialization.kryo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class Mock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -40085676425439438L;
	private int id;
	private String name;
	private List<String> words = new ArrayList<String>();
	private Map<Integer, String> references = new HashMap<Integer,String>();
	
	
	private static int magicNumberOne = 512, magicNumberTwo = 64, magicNumberThree = 1024, magicNumberFour = 512; 
	
	public static Mock createRandomMock() {
		
		Random r = new Random();
		Mock o = new Mock(r.nextInt(), RandomStringUtils.random(magicNumberFour));
		
		for(int i=0;i<magicNumberThree;i++)
			o.getWords().add(RandomStringUtils.random(magicNumberOne));
		
		for(int i=0;i<magicNumberOne;i++)
			o.getReferences().put(r.nextInt(), RandomStringUtils.random(magicNumberTwo));
		
		return o;
	}
	
	public Mock() {}
	public Mock(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
	public Map<Integer, String> getReferences() {
		return references;
	}
	public void setReferences(Map<Integer, String> references) {
		this.references = references;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((references == null) ? 0 : references.hashCode());
		result = prime * result + ((words == null) ? 0 : words.hashCode());
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
		Mock other = (Mock) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (references == null) {
			if (other.references != null)
				return false;
		} else if (!references.equals(other.references))
			return false;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}
	
	
	

}
