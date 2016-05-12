/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aprestos.code.bok.rdbms.utils;

/**
 *
 * @author jmv
 */
public enum Property 
{
    //common properties
    driver("driver"),
    protocol("protocol"), 
    user("user"),
    password("password"),
    db_name("db.name"),
    db_schema("db.schema"),

    //derby embedded specific properties
    derby_embedded_rdbms_folder("derby.embedded.rdbms.folder"),
    derby_embedded_vars_create("derby.embedded.vars.create"),
    derby_embedded_vars_shutdown("derby.embedded.vars.shutdown"),
    ;
        
        String key;
        
        Property(String key)
        {
            this.key = key;
        }
        
        public String getKey()
        {
            return this.key;
        }
}
