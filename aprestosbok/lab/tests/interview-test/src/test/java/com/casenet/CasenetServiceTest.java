// Copyright (c) 2011 by CaseNET, LLC
//
// This file is protected by Federal Copyright Law, with all rights
// reserved. No part of this file may be reproduced, stored in a
// retrieval system, translated, transcribed, or transmitted, in any
// form, or by any means manual, electric, electronic, mechanical,
// electro-magnetic, chemical, optical, or otherwise, without prior
// explicit written permission from CaseNET, LLC.
package com.casenet;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class CasenetServiceTest extends BaseTest {

    @Autowired
    CasenetService casenetService;

    @Test
    public void testFindMembersByDiagnosesFromXml() {

    }

}
