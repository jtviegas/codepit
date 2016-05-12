/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.openejb.test.singleton;

import javax.ejb.EJBHome;

/**
 * [6] Should be run as the sixth test suite of the BasicSingletonTestClients
 * 
 */
public class SingletonHomeHandleTests extends BasicSingletonTestClient{

    public SingletonHomeHandleTests(){
        super("HomeHandle.");
    }

    protected void setUp() throws Exception{
        super.setUp();
        Object obj = initialContext.lookup("client/tests/singleton/BasicSingletonHome");
        ejbHome = (BasicSingletonHome)javax.rmi.PortableRemoteObject.narrow( obj, BasicSingletonHome.class);
        ejbHomeHandle = ejbHome.getHomeHandle();
    }
        
    //=================================
    // Test home handle methods
    //
    public void test01_getEJBHome(){
        try{
            EJBHome home = ejbHomeHandle.getEJBHome();
            assertNotNull( "The EJBHome is null", home );
        } catch (Exception e){
            fail("Received Exception "+e.getClass()+ " : "+e.getMessage());
        }
    }
    //
    // Test home handle methods
    //=================================

}
