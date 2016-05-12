// Copyright (c) 2011 by CaseNET, LLC
//
// This file is protected by Federal Copyright Law, with all rights
// reserved. No part of this file may be reproduced, stored in a
// retrieval system, translated, transcribed, or transmitted, in any
// form, or by any means manual, electric, electronic, mechanical,
// electro-magnetic, chemical, optical, or otherwise, without prior
// explicit written permission from CaseNET, LLC.
package com.casenet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

@Transactional
@ContextConfiguration("/applicationContext.xml")
@TestExecutionListeners( { TransactionalTestExecutionListener.class })
public class BaseTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    EntityManager em;

    @BeforeMethod
    public void populateDatabase() {
        Member michal = new Member("Michal");
        michal.getDiagnosis().add(new Diagnosis(michal, "color blindness"));
        michal.getDiagnosis().add(new Diagnosis(michal, "stereovision blindness"));
        em.persist(michal);

        Member jarek = new Member("Jarek");
        jarek.getDiagnosis().add(new Diagnosis(jarek, "fatness"));
        jarek.getDiagnosis().add(new Diagnosis(jarek, "depression"));
        em.persist(jarek);

        Member marek = new Member("Marek");
        marek.getDiagnosis().add(new Diagnosis(marek, "deafness"));
        marek.getDiagnosis().add(new Diagnosis(michal, "color blindness"));
        em.persist(marek);
    }

}
