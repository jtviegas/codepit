// Copyright (c) 2011 by CaseNET, LLC
//
// This file is protected by Federal Copyright Law, with all rights
// reserved. No part of this file may be reproduced, stored in a
// retrieval system, translated, transcribed, or transmitted, in any
// form, or by any means manual, electric, electronic, mechanical,
// electro-magnetic, chemical, optical, or otherwise, without prior
// explicit written permission from CaseNET, LLC.
package com.casenet;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

	private static final String SQL_BY_DIAG="select d.member from Diagnosis d where d.description = :desc";
	
    @PersistenceContext
    EntityManager em;

    public List<Member> findMembersByDiagnosis(String diagnosisDescription) 
    {
    	Query _q = em.createQuery(SQL_BY_DIAG);
    	_q.setParameter("desc", diagnosisDescription);
    	
    	List<Member> _r = _q.getResultList();
    	
        return _r;
    }

}
