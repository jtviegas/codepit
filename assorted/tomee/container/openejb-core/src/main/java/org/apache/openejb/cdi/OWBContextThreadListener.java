/*
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


package org.apache.openejb.cdi;

import org.apache.openejb.AppContext;
import org.apache.openejb.BeanContext;
import org.apache.openejb.ModuleContext;
import org.apache.openejb.core.ThreadContext;
import org.apache.openejb.core.ThreadContextListener;
import org.apache.openejb.loader.SystemInstance;
import org.apache.webbeans.config.WebBeansContext;

/**
 * @version $Rev:$ $Date:$
 */
public class OWBContextThreadListener implements ThreadContextListener {

    private ThreadSingletonService singletonService = SystemInstance.get().getComponent(ThreadSingletonService.class);

    @Override
    public void contextEntered(final ThreadContext oldContext, final ThreadContext newContext) {
        final BeanContext beanContext = newContext.getBeanContext();
        if (beanContext == null) {
            return;
        }
        final ModuleContext moduleContext = beanContext.getModuleContext();
        //TODO its not clear what the scope for one of these context should be: ejb, module, or app
        //For now, go with the attachment of the BeanManager to AppContext
        final AppContext appContext = moduleContext.getAppContext();
        final WebBeansContext owbContext = appContext.getWebBeansContext();
        if (owbContext == null) {
            return;
        }
        final Object oldOWBContext = singletonService.contextEntered(owbContext);
        final OWBContextHolder holder = new OWBContextHolder(oldOWBContext);
        newContext.set(OWBContextHolder.class, holder);
    }

    @Override
    public void contextExited(final ThreadContext exitedContext, final ThreadContext reenteredContext) {
        final OWBContextHolder oldOWBContext = exitedContext.get(OWBContextHolder.class);
        if (oldOWBContext == null) {
            throw new NullPointerException("OWBContext not set in this thread");
        }
        singletonService.contextExited(oldOWBContext.getContext());
    }

    private static final class OWBContextHolder {
        private final Object context;

        private OWBContextHolder(final Object context) {
            this.context = context;
        }

        public Object getContext() {
            return context;
        }
    }

}
