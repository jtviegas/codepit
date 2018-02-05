package org.aprestos.labs.data.jpahibernateorm.fk.one;

import java.math.BigDecimal;

import org.aprestos.labs.data.jpahibernateorm.fk.one.model.LightPart;
import org.aprestos.labs.data.jpahibernateorm.fk.one.model.Part;
import org.aprestos.labs.data.jpahibernateorm.fk.one.model.PartType;
import org.aprestos.labs.data.jpahibernateorm.fk.one.repository.LightPartRepository;
import org.aprestos.labs.data.jpahibernateorm.fk.one.repository.PartRepository;
import org.aprestos.labs.data.jpahibernateorm.fk.one.repository.PartTypeRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

  @Autowired
  private PartTypeRepository partTypeRepository;

  @Autowired
  private PartRepository partRepository;

  @Autowired
  private LightPartRepository lightPartRepository;

  @Test
  public void test_00() {

    PartType t = partTypeRepository.save(new PartType("ahahah"));
    LightPart lp = lightPartRepository.save(new LightPart("ahahah and more...", BigDecimal.valueOf(5), t.getId()));
    Part p2 = partRepository.findOne(lp.getId());

    Assert.assertNotNull(lp.getId());
    Assert.assertNotNull(p2.getId());

    Assert.assertEquals(t, p2.getPartType());
    Assert.assertEquals(t.getId(), lp.getPartTypeId());

    Assert.assertNotNull(lp.getPartTypeId());
    Assert.assertEquals(lp.getPartTypeId(), p2.getPartType().getId());

  }

}
