/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package org.aprestos.labs.studies.j2ee;

import javax.faces.bean.ManagedBean;


@ManagedBean
public class Home {
    final String message = "oi, esta é a primeira página da applicação de estudo de j2ee!";

    public String getMessage() {
        return message;
    }
}
