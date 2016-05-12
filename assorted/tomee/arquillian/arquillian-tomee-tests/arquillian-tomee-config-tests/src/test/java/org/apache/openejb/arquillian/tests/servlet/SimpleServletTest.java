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
package org.apache.openejb.arquillian.tests.servlet;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SimpleServletTest {

    @Deployment(testable = false)
    public static WebArchive getArchive() {
      return  ShrinkWrap.create(WebArchive.class,"ROOT.war")
                .addClass(SimpleServlet.class)
                .addAsWebInfResource(new ClassLoaderAsset(
                        SimpleServletTest.class.getPackage().getName().replace('.', '/').concat("/").concat("web.xml")), "web.xml");

    }

    @Test
    public void testDeployment() {
        // should succeed without errors for load-on-startup value with leading/trailing space
    }

}
