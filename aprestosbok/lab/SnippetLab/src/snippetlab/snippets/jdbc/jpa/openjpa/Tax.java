/**
 * Tax.java Copyright (C)Wincor-Nixdorf Portugal
 * 2010/04/08 23:26:13
 * @author	joao.viegas
 *
 * TODO
 * 
 * CHANGES:
 */
package snippetlab.snippets.jdbc.jpa.openjpa;

import javax.persistence.Entity;

/**
 * Tax
 * ...description... 
 *
 * @see
 * @since
 */
@Entity(name = "tax")
public class Tax
{
	private int id;
	private String description;
	private float value;
	
	
}
