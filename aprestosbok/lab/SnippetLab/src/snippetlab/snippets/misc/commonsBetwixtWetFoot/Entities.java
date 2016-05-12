/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.commonsBetwixtWetFoot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jtviegas
 */
public class Entities 
{
    List<Role> roles=new ArrayList<Role>();
    
    public Entities(){}
    
    public List<Role> getRoles()
    {
        return roles;
    }
    
    public void addRole(Role role)
    {
        roles.add(role);
    }
    
    public void removeRole(Role role)
    {
        roles.remove(role);
    }
    
     public void setList(List<Role> roles)
    {
    	this.roles = roles;
    }
    
}
